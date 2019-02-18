package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FakultetTest {
    @Test
    public void dodavanjeOsobaRadi() {
        Fakultet fakultet = new Fakultet();
        fakultet.dodajOsobu(new Docent("Suljo Suljić"));
        fakultet.dodajOsobu(new VanredniProfesor("Edo Edić"));
        fakultet.dodajOsobu(new RedovniProfesor("Nerma Nermić"));
        fakultet.dodajOsobu(new BachelorStudent("Pero Perić"));
        fakultet.dodajOsobu(new MasterStudent("Hana Hanić"));
    }

    @Test
    public void sopl() {
        Fakultet fakultet = new Fakultet();
        fakultet.dodajOsobu(new Docent("Suljo Suljić"));
        fakultet.dodajOsobu(new VanredniProfesor("Edo Edić"));
        fakultet.dodajOsobu(new RedovniProfesor("Nerma Nermić"));
        fakultet.dodajOsobu(new BachelorStudent("Pero Perić"));
        fakultet.dodajOsobu(new MasterStudent("Hana Hanić"));

        String rezultat = "" + fakultet;
        rezultat = rezultat.trim(); // Nebitno je da li na kraju ima novi red ili ne

        String ocekivani = "Doc. dr Suljo Suljić\n" + "V. prof. dr Edo Edić\n" + "R. prof. dr Nerma Nermić\n";
        ocekivani += "Bachelor student Pero Perić (), prosjek ocjena: 0.0\n";
        ocekivani += "Master student Hana Hanić (), prosjek ocjena: 0.0";

        assertEquals(ocekivani, rezultat);
    }

    @Test
    public void ispisStudenataSaProsjekom() {
        Fakultet fakultet = new Fakultet();
        fakultet.dodajOsobu(new Docent("Suljo Suljić"));
        Student s = new BachelorStudent("Sara Sarač");
        s.setBrojIndeksa("23232");
        try {
            s.dodajOcjenu(6);
            s.dodajOcjenu(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        fakultet.dodajOsobu(s);

        String rezultat = "" + fakultet;
        rezultat = rezultat.trim(); // Nebitno je da li na kraju ima novi red ili ne

        String ocekivani = "Doc. dr Suljo Suljić\n";
        ocekivani += "Bachelor student Sara Sarač (23232), prosjek ocjena: 7.0";

        assertEquals(ocekivani, rezultat);

        // Dodajemo još neke ocjene
        try {
            s.dodajOcjenu(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }

        rezultat = "" + fakultet;
        rezultat = rezultat.trim(); // Nebitno je da li na kraju ima novi red ili ne

        ocekivani = "Doc. dr Suljo Suljić\n";
        ocekivani += "Bachelor student Sara Sarač (23232), prosjek ocjena: 7.3";

        assertEquals(ocekivani, rezultat);
    }

    @Test
    public void testStudenti() {
        Fakultet fakultet = new Fakultet();
        fakultet.dodajOsobu(new Docent("Suljo Suljić"));
        Student s = new BachelorStudent("Pero Perić");
        s.setBrojIndeksa("23232");
        try {
            s.dodajOcjenu(10);
            s.dodajOcjenu(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        fakultet.dodajOsobu(s); // Pero Perić ima prosjek 9.0

        fakultet.dodajOsobu(new VanredniProfesor("Edo Edić"));
        MasterStudent s2 = new MasterStudent("Meho Mehić");
        s2.setBrojIndeksa("12121");
        try {
            s2.dodajOcjenu(8);
            s2.dodajOcjenu(9);
            s2.dodajOcjenuMaster(10);
            s2.dodajOcjenuMaster(10);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        fakultet.dodajOsobu(s2); // Meho Mehić ima prosjek 9.25 zbog ocjena sa mastera

        fakultet.dodajOsobu(new RedovniProfesor("Nerma Nermić"));
        fakultet.dodajOsobu(new BachelorStudent("Sara Sarač")); // Sara Sarač ima prosjek 0
        Student s3 = new MasterStudent("Hana Hanić");
        s3.setBrojIndeksa("32323");
        try {
            s3.dodajOcjenu(10);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        fakultet.dodajOsobu(s3); // Hana Hanić ima prosjek 10.0

        Set<Student> studenti = fakultet.studenti();
        String rezultat = "";
        for (Student fs : studenti)
            rezultat += fs.getImePrezime() + " ";
        String ocekivani = "Hana Hanić Meho Mehić Pero Perić Sara Sarač ";

        assertEquals(ocekivani, rezultat);
    }


    @Test
    public void testStudentiPrazanSkup() {
        Fakultet fakultet = new Fakultet();
        fakultet.dodajOsobu(new Docent("Suljo Suljić"));
        fakultet.dodajOsobu(new VanredniProfesor("Edo Edić"));
        fakultet.dodajOsobu(new RedovniProfesor("Nerma Nermić"));

        Set<Student> studenti = fakultet.studenti();
        assertEquals(0, studenti.size());

        fakultet.dodajOsobu(new MasterStudent("Hana Hanić"));

        studenti = fakultet.studenti();
        String rezultat = "";
        for (Student fs : studenti)
            rezultat += fs.getImePrezime() + " ";
        String ocekivani = "Hana Hanić ";

        assertEquals(ocekivani, rezultat);
    }

    @Test
    public void testFiltriraj() {
        Fakultet fakultet = new Fakultet();
        fakultet.dodajOsobu(new Docent("Suljo Suljić"));
        fakultet.dodajOsobu(new VanredniProfesor("Edo Edić"));
        fakultet.dodajOsobu(new RedovniProfesor("Nerma Nermić"));
        fakultet.dodajOsobu(new BachelorStudent("Pero Perić"));
        fakultet.dodajOsobu(new MasterStudent("Hana Hanić"));

        List<Osoba> osobe = fakultet.filtriraj((Osoba o) -> o.getImePrezime().contains("o"));

        String rezultat = "";
        for (Osoba o : osobe)
            rezultat += o.getImePrezime() + " ";
        String ocekivani = "Suljo Suljić Edo Edić Pero Perić ";

        assertEquals(ocekivani, rezultat);
    }


    @Test
    public void testTopBachelor() {
        Fakultet fakultet = new Fakultet();
        fakultet.dodajOsobu(new Docent("Suljo Suljić"));
        fakultet.dodajOsobu(new BachelorStudent("Jozo Jozić")); // Ne ispunjava uslov, prosjek 0

        Student s = new BachelorStudent("Ena Enić");
        s.setBrojIndeksa("23232");
        try {
            s.dodajOcjenu(10);
            s.dodajOcjenu(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        fakultet.dodajOsobu(s); // Ispunjava uslov, prosjek 9

        Student s2 = new MasterStudent("Dino Dinić");
        s2.setBrojIndeksa("11111");
        try {
            s2.dodajOcjenu(10);
            s2.dodajOcjenu(9);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        fakultet.dodajOsobu(s2); // Ne ispunjava uslov, master student

        fakultet.dodajOsobu(new VanredniProfesor("Edo Edić"));

        Student s3 = new BachelorStudent("Kemo Kemić");
        s3.setBrojIndeksa("33333");
        try {
            s3.dodajOcjenu(8);
            s3.dodajOcjenu(6);
            s3.dodajOcjenu(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        fakultet.dodajOsobu(s3); // Ne ispunjava uslov, prosjek 7.666

        Student s4 = new BachelorStudent("Sara Sarač");
        s4.setBrojIndeksa("33333");
        try {
            s4.dodajOcjenu(8);
            s4.dodajOcjenu(8);
            s4.dodajOcjenu(8);
        } catch (IlegalnaOcjena ilegalnaOcjena) {
            fail("Dodavanje ocjene je bacilo izuzetak IlegalnaOcjena");
        }
        fakultet.dodajOsobu(s4); // Ispunjava uslov, prosjek 8.0

        List<BachelorStudent> osobe = fakultet.topBachelor();

        String rezultat = "";
        for (BachelorStudent bs : osobe)
            rezultat += bs.getImePrezime() + " ";
        String ocekivani = "Ena Enić Sara Sarač ";

        assertEquals(ocekivani, rezultat);
    }

    @Test
    public void testMladi() {
        Fakultet fakultet = new Fakultet();
        fakultet.dodajOsobu(new Docent("Suljo Suljić"));
        fakultet.dodajOsobu(new VanredniProfesor("Edo Edić"));
        fakultet.dodajOsobu(new RedovniProfesor("Nerma Nermić"));
        fakultet.dodajOsobu(new BachelorStudent("Pero Perić"));
        fakultet.dodajOsobu(new MasterStudent("Hana Hanić"));

        List<Mladi> mladi = fakultet.mladi();

        String rezultat = "";
        for (Mladi m : mladi) {
            Osoba o = (Osoba) m;
            rezultat += o.getImePrezime() + " ";
        }
        String ocekivani = "Suljo Suljić Hana Hanić ";

        assertEquals(ocekivani, rezultat);
    }
}