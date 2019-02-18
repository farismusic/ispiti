package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NastavnikTest {
    @Test
    public void klasePostoje() {
        Nastavnik nastavnik = new Docent("Suljo Suljić");
        Nastavnik nastavnik2 = new VanredniProfesor("Edo Edić");
        Nastavnik nastavnik3 = new RedovniProfesor("Nerma Nermić");
        Osoba o = new VanredniProfesor("Alma Almić");
    }

    @Test
    public void sopl() {
        Nastavnik nastavnik = new Docent("Suljo Suljić");
        Nastavnik nastavnik2 = new VanredniProfesor("Edo Edić");
        Nastavnik nastavnik3 = new RedovniProfesor("Nerma Nermić");
        // Ovo će dati isti efekat kao sopl
        String rezultat = "" + nastavnik;
        assertEquals("Doc. dr Suljo Suljić", rezultat);

        rezultat = "" + nastavnik2;
        assertEquals("V. prof. dr Edo Edić", rezultat);
        rezultat = "" + nastavnik3;
        assertEquals("R. prof. dr Nerma Nermić", rezultat);
    }


    @Test
    public void testRodjendan() {
        Docent d = new Docent("Edo Edić");
        LocalDate datum = LocalDate.of(1985, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        String cestitka = d.rodjendan(datum);
        assertEquals("Sretan rodjendan!", cestitka);

        Docent d2 = new Docent("Ana Anić");
        LocalDate datum2 = LocalDate.of(1995, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()+1);
        cestitka = d2.rodjendan(datum2);
        assertEquals("", cestitka);
    }
}