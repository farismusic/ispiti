package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PreduzeceTest {
    @Test
    public void testCtor() {
        Preduzece pr = null;
        try {
            pr = new Preduzece(100);
        } catch(Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
        assertEquals(100, pr.dajOsnovicu());
    }

    @Test
    public void testCtorIzuzetak() {
        assertThrows(NeispravnaOsnovica.class,
                () -> new Preduzece(0),
                "Neispravna osnovica 0");
    }

    @Test
    public void testPostaviOsnovicu() {
        try {
            final Preduzece pr = new Preduzece(1);
            assertDoesNotThrow(() -> pr.postaviOsnovicu(200));
            assertEquals(200, pr.dajOsnovicu());
        } catch(Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testPostaviOsnovicuIzuzetak() {
        try {
            final Preduzece pr = new Preduzece(150);
            assertThrows(NeispravnaOsnovica.class,
                    () -> pr.postaviOsnovicu(-1234),
                    "Neispravna osnovica -1234");
            assertEquals(150, pr.dajOsnovicu());
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testNovoRadnoMjesto() {
        // Ovaj test ne provjerava detaljno kako radi metoda sistematizacija
        // Samo provjerava veličinu mape kao potvrdu da je dodavanje uspjelo
        try {
            Preduzece pr = new Preduzece(150);
            RadnoMjesto rm = new RadnoMjesto("Sekretar", 8, new Radnik("Kemo Kemić", "87878"));
            pr.novoRadnoMjesto(rm);
            Map<RadnoMjesto, Integer> sis = null;
            try {
                sis = pr.sistematizacija();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Izuzetak u metodi sistematizacija");
            }
            assertEquals(1, sis.size());
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testZaposli() {
        // Ovaj test ne provjerava detaljno kako radi metoda radnici
        // Samo provjerava veličinu skupa kao potvrdu da je dodavanje uspjelo
        try {
            final Preduzece pr = new Preduzece(150);
            RadnoMjesto rm = new RadnoMjesto("Sekretar", 8, null);
            Radnik r = new Radnik("Kemo Kemić", "87878");
            pr.novoRadnoMjesto(rm);
            assertDoesNotThrow(() -> pr.zaposli(r, "Sekretar"));
            Set<Radnik> radniks = null;
            try {
                radniks = pr.radnici();
            } catch(Exception e) {
                e.printStackTrace();
                fail("Izuzetak u metodi radnici");
            }
            assertEquals(1, radniks.size());
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testIznosPlate() {
        // Ovaj test ne provjerava detaljno kako radi metoda radnici
        // Samo provjerava veličinu mape kao potvrdu da je dodavanje uspjelo
        try {
            final Preduzece pr = new Preduzece(150);

            RadnoMjesto sekretar = new RadnoMjesto("Sekretar", 8, null);
            Radnik kemo = new Radnik("Kemo Kemić", "87878");
            pr.novoRadnoMjesto(sekretar);
            assertDoesNotThrow(() -> pr.zaposli(kemo, "Sekretar"));

            // Radno mjesto direktora je upražnjeno
            RadnoMjesto direktor = new RadnoMjesto("Direktor", 10, null);
            pr.novoRadnoMjesto(direktor);

            RadnoMjesto portir = new RadnoMjesto("Portir", 6, null);
            pr.novoRadnoMjesto(portir);
            Radnik hamo = new Radnik("Hamo Hamić", "56565");
            assertDoesNotThrow(() -> pr.zaposli(hamo, "Portir"));

            // 8*150 + 6*150 = 2100
            assertEquals(2100, pr.iznosPlate());

            // Plata nije obračunata!
            assertEquals(0, hamo.prosjecnaPlata());
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testObracunajPlatu() {
        // Ovaj test ne provjerava detaljno kako radi metoda radnici
        // Samo provjerava veličinu mape kao potvrdu da je dodavanje uspjelo
        try {
            final Preduzece pr = new Preduzece(150);

            RadnoMjesto sekretar = new RadnoMjesto("Sekretar", 8, null);
            Radnik kemo = new Radnik("Kemo Kemić", "87878");
            pr.novoRadnoMjesto(sekretar);
            assertDoesNotThrow(() -> pr.zaposli(kemo, "Sekretar"));

            // Radno mjesto direktora je upražnjeno
            RadnoMjesto direktor = new RadnoMjesto("Direktor", 10, null);
            pr.novoRadnoMjesto(direktor);

            RadnoMjesto portir = new RadnoMjesto("Portir", 6, null);
            pr.novoRadnoMjesto(portir);
            Radnik hamo = new Radnik("Hamo Hamić", "56565");
            assertDoesNotThrow(() -> pr.zaposli(hamo, "Portir"));

            pr.obracunajPlatu();
            // Promjena osnovice
            pr.postaviOsnovicu(100);
            pr.obracunajPlatu();

            // Hamine plate su: 6*150=900 i 6*100=600, prosjek 750
            assertEquals(750, hamo.prosjecnaPlata());

            // Kemine plate su: 8*150=1200 i 8*100=800, prosjek 1000
            assertEquals(1000, kemo.prosjecnaPlata());

            // Jos jedna plata
            pr.obracunajPlatu();
            // Sada Hamo ima: 900, 600, 600, prosjek 700
            assertEquals(700, hamo.prosjecnaPlata());

        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testObracunajPlatuIzuzetak() {
        // Ovaj test ne provjerava detaljno kako radi metoda radnici
        // Samo provjerava veličinu mape kao potvrdu da je dodavanje uspjelo
        try {
            final Preduzece pr = new Preduzece(150);

            RadnoMjesto direktor = new RadnoMjesto("Direktor", 10, null);
            Radnik kemo = new Radnik("Kemo Kemić", "87878");
            pr.novoRadnoMjesto(direktor);
            assertDoesNotThrow(() -> pr.zaposli(kemo, "Direktor"));

            for (int i=0; i<1000; i++)
                pr.obracunajPlatu();

            assertThrows(IllegalArgumentException.class,
                    () -> pr.obracunajPlatu(),
                    "Ne možete registrovati više od 1000 plata za radnika Kemo Kemić"
            );

            assertEquals(1500, kemo.prosjecnaPlata());

        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testZaposliIzuzetak() {
        // Ovaj test ne provjerava detaljno kako radi metoda radnici
        // Samo provjerava veličinu mape kao potvrdu da je dodavanje uspjelo
        try {
            final Preduzece pr = new Preduzece(150);
            RadnoMjesto rm = new RadnoMjesto("Radnik", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Radnik", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Radnik", 8, null);
            pr.novoRadnoMjesto(rm);

            Radnik selma = new Radnik("Selma Selmić", "87878");
            Radnik hana = new Radnik("Hana Hanić", "87878");
            Radnik maja = new Radnik("Maja Majić", "87878");
            Radnik sara = new Radnik("Sara Sarač", "87878");
            assertDoesNotThrow(() -> pr.zaposli(selma, "Radnik"));
            assertDoesNotThrow(() -> pr.zaposli(hana, "Radnik"));
            assertDoesNotThrow(() -> pr.zaposli(maja, "Radnik"));

            Set<Radnik> radniks = null;
            try {
                radniks = pr.radnici();
            } catch(Exception e) {
                e.printStackTrace();
                fail("Izuzetak u metodi radnici");
            }
            assertEquals(3, radniks.size());

            // Kreirali smo tri radna mjesta, sada pokušavamo zaposliti četvrtog radnika
            assertThrows(IllegalStateException.class,
                    () -> pr.zaposli(sara, "Radnik"),
                    "Nijedno radno mjesto tog tipa nije slobodno"
            );

            // Provjeravamo da li je broj radnika i dalje 3
            try {
                radniks = pr.radnici();
            } catch(Exception e) {
                e.printStackTrace();
                fail("Izuzetak u metodi radnici");
            }
            assertEquals(3, radniks.size());
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testZaposliIzuzetak2() {
        // Ovaj test ne provjerava detaljno kako radi metoda radnici
        // Samo provjerava veličinu mape kao potvrdu da je dodavanje uspjelo
        try {
            final Preduzece pr = new Preduzece(234);
            RadnoMjesto rm = new RadnoMjesto("Radnik", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Radnik", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Radnik", 8, null);
            pr.novoRadnoMjesto(rm);

            Radnik selma = new Radnik("Selma Selmić", "87878");
            Radnik hana = new Radnik("Hana Hanić", "87878");
            Radnik maja = new Radnik("Maja Majić", "87878");
            Radnik sara = new Radnik("Sara Sarač", "87878");
            assertDoesNotThrow(() -> pr.zaposli(selma, "Radnik"));
            assertThrows(IllegalStateException.class,
                    () -> pr.zaposli(sara, "Programer"),
                    "Nijedno radno mjesto tog tipa nije slobodno"
            );
            assertDoesNotThrow(() -> pr.zaposli(hana, "Radnik"));

            Set<Radnik> radniks = null;
            try {
                radniks = pr.radnici();
            } catch(Exception e) {
                e.printStackTrace();
                fail("Izuzetak u metodi radnici");
            }
            assertEquals(2, radniks.size());
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }
    @Test
    public void testSistematizacija1() {
        try {
            final Preduzece pr = new Preduzece(150);
            // Tri identična radna mjesta
            RadnoMjesto rm = new RadnoMjesto("Radnik", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Radnik", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Radnik", 8, null);
            pr.novoRadnoMjesto(rm);

            Map<RadnoMjesto, Integer> sis = null;
            try {
                sis = pr.sistematizacija();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Izuzetak u metodi sistematizacija");
            }

            // Treba biti samo jedna stavka u sistematizaciji!
            assertEquals(1, sis.size());

            // Ova petlja će se izvršiti samo jednom, pa možemo odmah tražiti tačne vrijednosti
            for (Map.Entry<RadnoMjesto,Integer> me : sis.entrySet()) {
                // Radno mjesto se ponavlja tri puta
                assertEquals(3, me.getValue());
                assertEquals("Radnik", me.getKey().getNaziv());
            }
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testSistematizacija2() {
        try {
            final Preduzece pr = new Preduzece(200);
            // Jedan direktor, dva sekretara i pet vozača
            RadnoMjesto rm = new RadnoMjesto("Direktor", 10, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Sekretar", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Sekretar", 8, null);
            pr.novoRadnoMjesto(rm);
            for (int i=0; i<5; i++) {
                rm = new RadnoMjesto("Vozač", 6, null);
                pr.novoRadnoMjesto(rm);
            }

            // Zaposlićemo jednog sekretara i jednog vozača
            Radnik hamo = new Radnik("Hamo Hamić", "56565");
            assertDoesNotThrow(() -> pr.zaposli(hamo, "Sekretar"));
            Radnik kemo = new Radnik("Kemo Kemić", "87878");
            assertDoesNotThrow(() -> pr.zaposli(kemo, "Vozač"));

            Map<RadnoMjesto, Integer> sis = null;
            try {
                sis = pr.sistematizacija();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Izuzetak u metodi sistematizacija");
            }

            // Tri stavke u sistematizaciji
            assertEquals(3, sis.size());
            // Promjenljiva rm i dalje ima vrijednost radnog mjesta "Vozač"
            assertEquals(5, sis.get(rm));

            rm = new RadnoMjesto("Sekretar", 8, null);
            assertEquals(2, sis.get(rm));

            // Iznos plate je: 8*200+6*200 = 2800
            assertEquals(2800, pr.iznosPlate());
            // Obračunajmo tri plate svima
            pr.obracunajPlatu();
            pr.obracunajPlatu();
            pr.obracunajPlatu();

            assertEquals(1600, hamo.prosjecnaPlata());
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testRadnici() {
        try {
            final Preduzece pr = new Preduzece(150);
            RadnoMjesto rm = new RadnoMjesto("Direktor", 10, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Sekretar", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Radnik", 9, null);
            pr.novoRadnoMjesto(rm);

            Radnik selma = new Radnik("Selma Selmić", "87878");
            Radnik hana = new Radnik("Hana Hanić", "87878");
            Radnik maja = new Radnik("Maja Majić", "87878");
            Radnik sara = new Radnik("Sara Sarač", "87878");
            assertDoesNotThrow(() -> pr.zaposli(selma, "Radnik"));
            assertDoesNotThrow(() -> pr.zaposli(hana, "Sekretar"));
            assertDoesNotThrow(() -> pr.zaposli(maja, "Direktor"));

            pr.obracunajPlatu();
            pr.obracunajPlatu();

            // Mijenjamo osnovicu
            pr.postaviOsnovicu(160);
            pr.obracunajPlatu();
            pr.obracunajPlatu();

            Set<Radnik> radniks = null;
            try {
                radniks = pr.radnici();
            } catch(Exception e) {
                e.printStackTrace();
                fail("Izuzetak u metodi radnici");
            }
            // Broj radnika: 3
            assertEquals(3, radniks.size());

            // Da li su radnici sortirani po prosječnoj plati?
            String imena = "";
            for (Radnik r : radniks)
                imena += r.getImePrezime() + " ";
            assertEquals("Maja Majić Selma Selmić Hana Hanić ", imena);
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testFilter() {
        try {
            final Preduzece pr = new Preduzece(110);
            RadnoMjesto rm = new RadnoMjesto("Direktor", 10, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Sekretar", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Radnik", 9, null);
            pr.novoRadnoMjesto(rm);

            Radnik selma = new Radnik("Selma Selmić", "87878");
            Radnik hamo = new Radnik("Hamo Hamić", "87878");
            Radnik maja = new Radnik("Maja Majić", "87878");
            Radnik sara = new Radnik("Sara Sarač", "87878");
            assertDoesNotThrow(() -> pr.zaposli(selma, "Radnik"));
            assertDoesNotThrow(() -> pr.zaposli(hamo, "Sekretar"));
            assertDoesNotThrow(() -> pr.zaposli(maja, "Direktor"));

            List<Radnik> radnici = pr.filterRadnici((Radnik r) -> { return r.getImePrezime().contains("mić"); });
            assertEquals(2, radnici.size());

            // Pošto ne znamo kojim redom su sortirani radnici, ručno ćemo ih sortirati
            String ime1 = radnici.get(0).getImePrezime();
            String ime2 = radnici.get(1).getImePrezime();
            if (ime2.compareTo(ime1) < 0) { String tmp=ime1; ime1=ime2; ime2=tmp; }
            assertEquals(ime1, "Hamo Hamić");
            assertEquals(ime2, "Selma Selmić");
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testFilter2() {
        try {

            final Preduzece pr = new Preduzece(150);
            for (int i=0; i<5; i++) {
                RadnoMjesto rm = new RadnoMjesto("Programer", 10, null);
                pr.novoRadnoMjesto(rm);
            }

            Radnik selma = new Radnik("Selma Selmić", "123456");
            Radnik hamo = new Radnik("Hamo Hamić", "97834501");
            Radnik maja = new Radnik("Amela Amelic", "878787");
            Radnik kemo = new Radnik("Kemo Kemić", "12121212");
            Radnik sara = new Radnik("Sara Sarač", "2345678");
            assertDoesNotThrow(() -> pr.zaposli(selma, "Programer"));
            assertDoesNotThrow(() -> pr.zaposli(hamo, "Programer"));
            assertDoesNotThrow(() -> pr.zaposli(maja, "Programer"));
            assertDoesNotThrow(() -> pr.zaposli(kemo, "Programer"));

            List<Radnik> radnici = pr.filterRadnici((Radnik r) -> { return r.getImePrezime().contains("el") || r.getJmbg().contains("345"); });
            assertEquals(3, radnici.size());

            // Pošto ne znamo kojim redom su sortirani radnici, ručno ćemo ih sortirati
            String ime1 = radnici.get(0).getImePrezime();
            String ime2 = radnici.get(1).getImePrezime();
            if (ime2.compareTo(ime1) < 0) { String tmp=ime1; ime1=ime2; ime2=tmp; }
            assertEquals(ime1, "Hamo Hamić");
            assertEquals(ime2, "Selma Selmić");
        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }

    @Test
    public void testVecaProsjecna() {
        try {
            final Preduzece pr = new Preduzece(100);
            RadnoMjesto rm = new RadnoMjesto("Direktor", 10, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Sekretar", 8, null);
            pr.novoRadnoMjesto(rm);
            rm = new RadnoMjesto("Radnik", 9, null);
            pr.novoRadnoMjesto(rm);

            Radnik selma = new Radnik("Selma Selmić", "87878");
            Radnik hamo = new Radnik("Hamo Hamić", "87878");
            Radnik maja = new Radnik("Maja Majić", "87878");
            Radnik sara = new Radnik("Sara Sarač", "87878");
            assertDoesNotThrow(() -> pr.zaposli(selma, "Radnik"));
            assertDoesNotThrow(() -> pr.zaposli(hamo, "Sekretar"));
            assertDoesNotThrow(() -> pr.zaposli(maja, "Direktor"));

            pr.obracunajPlatu();
            pr.postaviOsnovicu(200);
            pr.obracunajPlatu();

            // Sada je: Selma 900, 1800 - prosjek 1350
            // Hamo 800, 1600 - prosjek 1200
            // Maja 1000, 2000 - prosjek 1500
            List<Radnik> radnici = pr.vecaProsjecnaPlata(1300);
            assertEquals(2, radnici.size());

            // Pošto ne znamo kojim redom su sortirani radnici, ručno ćemo ih sortirati
            String ime1 = radnici.get(0).getImePrezime();
            String ime2 = radnici.get(1).getImePrezime();
            if (ime2.compareTo(ime1) < 0) { String tmp=ime1; ime1=ime2; ime2=tmp; }
            assertEquals(ime1, "Maja Majić");
            assertEquals(ime2, "Selma Selmić");

            pr.postaviOsnovicu(300);
            pr.obracunajPlatu();
            // Sada je: Selma 1800, Maja 2000, Hamo 1600
            radnici = pr.vecaProsjecnaPlata(1900);
            assertEquals(1, radnici.size());
            radnici = pr.vecaProsjecnaPlata(1500);
            assertEquals(3, radnici.size());

        } catch (Exception e) {
            fail("Konstruktor je bacio izuzetak, a ne treba!");
        }
    }
}