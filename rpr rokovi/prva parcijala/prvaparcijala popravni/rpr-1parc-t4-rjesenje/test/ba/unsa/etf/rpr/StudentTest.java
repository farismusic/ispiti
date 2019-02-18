package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    @Test
    public void klasePostoje() {
        Student s = new BachelorStudent("Pero Peric");
        Student s2 = new MasterStudent("Hana Hanic");
        Osoba o = new MasterStudent("Alma Almic");
    }

    @Test
    public void sopl() {
        Student s = new BachelorStudent("Pero Perić");
        s.setBrojIndeksa("54321");
        // Ovo će dati isti efekat kao sopl
        String rezultat = "" + s;
        assertEquals("Bachelor student Pero Perić (54321), prosjek ocjena: 0.0", rezultat);

        Student s2 = new MasterStudent("Hana Hanić");
        s2.setBrojIndeksa("23232");
        rezultat = "" + s2;
        assertEquals("Master student Hana Hanić (23232), prosjek ocjena: 0.0", rezultat);
    }

    @Test
    public void prosjekOcjena() {
        Student s = new BachelorStudent("Sara Sarač");
        s.setBrojIndeksa("12121");
        try {
            s.dodajOcjenu(7);
            s.dodajOcjenu(7);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        assertEquals(7, s.prosjek());

        try {
            s.dodajOcjenu(8);
            s.dodajOcjenu(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        assertEquals(7.5, s.prosjek());
    }


    @Test
    public void prosjekOcjenaString() {
        Student s = new BachelorStudent("Sara Sarač");
        s.setBrojIndeksa("12121");
        try {
            s.dodajOcjenu(7);
            s.dodajOcjenu(7);
            s.dodajOcjenu(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        // Provjeravamo da li je prosjek u stringu tačan
        String rezultat = "" + s;
        assertEquals("Bachelor student Sara Sarač (12121), prosjek ocjena: 7.3", rezultat);

        try {
            s.dodajOcjenu(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        rezultat = "" + s;
        assertEquals("Bachelor student Sara Sarač (12121), prosjek ocjena: 7.5", rezultat);
    }

    @Test
    public void prosjekOcjenaMaster() {
        MasterStudent s = new MasterStudent("Alma Almić");
        s.setBrojIndeksa("33221");
        try {
            s.dodajOcjenu(8);
            s.dodajOcjenu(9);
            s.dodajOcjenu(9);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        String rezultat = "" + s;
        assertEquals("Master student Alma Almić (33221), prosjek ocjena: 8.7", rezultat);

        // Prosjek master
        try {
            s.dodajOcjenuMaster(9);
            s.dodajOcjenuMaster(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        assertEquals(8.5, s.prosjekMaster());

        try {
            s.dodajOcjenuMaster(8);
            // Ukupni prosjek je sada 8.5
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        assertEquals(8.5, s.prosjek());

        // Provjeravamo da li je prosjek u stringu tačan
        rezultat = "" + s;
        assertEquals("Master student Alma Almić (33221), prosjek ocjena: 8.5", rezultat);
    }

    @Test
    public void ilegalnaOcjena() {
        Student s = new BachelorStudent("Sara Sarač");
        s.setBrojIndeksa("12121");
        assertThrows(IlegalnaOcjena.class, () -> s.dodajOcjenu(4), "Ilegalna ocjena 4");
        assertThrows(IlegalnaOcjena.class, () -> s.dodajOcjenu(11), "Ilegalna ocjena 11");
        assertThrows(IlegalnaOcjena.class, () -> s.dodajOcjenu(-8), "Ilegalna ocjena -8");
        assertEquals(0, s.prosjek());

        // Sljedeće ocjene ne bacaju izuzetak
        try {
            s.dodajOcjenu(5);
            s.dodajOcjenu(10);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        assertEquals(7.5, s.prosjek());
    }

    @Test
    public void ilegalnaOcjenaMaster() {
        MasterStudent s = new MasterStudent("Alma Almić");
        s.setBrojIndeksa("33221");
        assertThrows(IlegalnaOcjena.class, () -> s.dodajOcjenuMaster(4), "Ilegalna ocjena 4");
        assertThrows(IlegalnaOcjena.class, () -> s.dodajOcjenuMaster(11), "Ilegalna ocjena 11");
        assertThrows(IlegalnaOcjena.class, () -> s.dodajOcjenuMaster(-8), "Ilegalna ocjena -8");
        assertEquals(0, s.prosjekMaster());
        assertEquals(0, s.prosjek());

        // Sljedeće ocjene ne bacaju izuzetak
        try {
            s.dodajOcjenuMaster(5);
            s.dodajOcjenuMaster(10);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        assertEquals(7.5, s.prosjek());
        assertEquals(7.5, s.prosjekMaster());

        try {
            s.dodajOcjenu(5);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        String rezultat = "" + s;
        assertEquals("Master student Alma Almić (33221), prosjek ocjena: 6.7", rezultat);
        assertEquals(7.5, s.prosjekMaster());
    }

    @Test
    public void previseOcjenaIzuzetak() {
        Student s = new BachelorStudent("Sara Sarač");
        s.setBrojIndeksa("12121");
        try {
            for (int i=0; i<99; i++) {
                s.dodajOcjenu(10);
            }
            s.dodajOcjenu(9);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        assertEquals(9.99, s.prosjek());
        assertThrows(IllegalArgumentException.class, () -> s.dodajOcjenu(9), "Dosegnut maksimalan broj ocjena");
    }

    @Test
    public void testRodjendan() {
        MasterStudent ms = new MasterStudent("Sara Sarač");
        LocalDate datum = LocalDate.of(1995, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        String cestitka = ms.rodjendan(datum);
        assertEquals("Sretan rodjendan!", cestitka);

        MasterStudent ms2 = new MasterStudent("Hana Hanić");
        LocalDate datum2 = LocalDate.of(1995, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()+1);
        cestitka = ms2.rodjendan(datum2);
        assertEquals("", cestitka);
    }
}