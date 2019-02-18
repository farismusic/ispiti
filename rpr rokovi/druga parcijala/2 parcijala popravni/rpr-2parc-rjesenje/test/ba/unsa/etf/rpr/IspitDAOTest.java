package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IspitDAOTest {

    @Test
    void testGrad() {
        Grad grad = new Grad();
        grad.setNaziv("Sarajevo");
        grad.setNadmorskaVisina(550);
        assertEquals(550, grad.getNadmorskaVisina());
    }

    @Test
    void testGradCtor() {
        Drzava drzava = new Drzava();
        Grad grad = new Grad(0, "Sarajevo", 350000, drzava, 516); // Nadmorska visina je posljednji parametar
        assertEquals(516, grad.getNadmorskaVisina());
    }

    @Test
    void testIzmijeniGrad() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Grad bech = dao.glavniGrad("Austrija");
        bech.setNadmorskaVisina(128);
        dao.izmijeniGrad(bech);

        Grad b2 = dao.glavniGrad("Austrija");
        assertEquals(128, b2.getNadmorskaVisina());
        b2.setNadmorskaVisina(-50);
        dao.izmijeniGrad(b2);

        Grad b3 = dao.glavniGrad("Austrija");
        assertEquals(-50, b2.getNadmorskaVisina());
    }

    @Test
    void testDodajGrad() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava francuska = dao.nadjiDrzavu("Francuska");
        Grad sarajevo = new Grad(0, "Sarajevo", 350000, francuska, 550);

        dao.dodajGrad(sarajevo);

        Grad s2 = null;
        for(Grad grad : dao.gradovi()) {
            if (grad.getNaziv().equals("Sarajevo"))
                s2 = grad;
        }
        assertNotNull(s2);

        assertEquals(550, s2.getNadmorskaVisina());
    }


    @Test
    void testNadjiGrad() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava francuska = dao.nadjiDrzavu("Francuska");

        Grad sarajevo = new Grad(0, "Sarajevo", 350000, francuska, 520);
        dao.dodajGrad(sarajevo);

        Grad s2 = dao.nadjiGrad("Sarajevo");
        assertNotNull(s2);

        assertEquals(520, s2.getNadmorskaVisina());
    }


    @Test
    void testNadjiDrzavu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava francuska = dao.nadjiDrzavu("Francuska");

        Grad sarajevo = new Grad(0, "Sarajevo", 350000, francuska, 490); // Nadmorska visina je posljednji parametar
        dao.dodajGrad(sarajevo);

        Grad s2 = dao.nadjiGrad("Sarajevo");
        assertNotNull(s2);

        Drzava bih = new Drzava(0, "Bosna i Hercegovina", s2);
        dao.dodajDrzavu(bih);

        Drzava d2 = dao.nadjiDrzavu("Bosna i Hercegovina");

        assertNotNull(d2);
        assertEquals(490, d2.getGlavniGrad().getNadmorskaVisina());
    }

    @Test
    void testBazaDirekt() {
        // Test koji direktno pristupa bazi zaobilazeći DAO klasu

        // Regenerišemo bazu ako je promijenjena prethodnim testovima
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();

        // Sad ćemo se opet diskonektovati jer radimo sa bazom direktno
        GeografijaDAO.removeInstance();

        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
            try {
                PreparedStatement nadmorskaUpit = conn.prepareStatement("SELECT nadmorska_visina FROM grad WHERE id=1");
                nadmorskaUpit.execute();
                conn.close();
            } catch (SQLException e) {
                fail("Tabela grad ne sadrži kolonu nadmorska_visina");
            }
        } catch (SQLException e) {
            fail("Datoteka sa bazom ne postoji ili je nedostupna");
        }

    }
}