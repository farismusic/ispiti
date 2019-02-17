package ba.unsa.etf.rpr;

import java.util.*;
import java.util.function.Function;

public class Preduzece {
    private int osnovica;
    private ArrayList<RadnoMjesto> radnaMjesta = new ArrayList<RadnoMjesto>();

    public Preduzece(int osnovica) throws NeispravnaOsnovica {
        postaviOsnovicu(osnovica);
    }

    public int dajOsnovicu() { return osnovica; }
    public void postaviOsnovicu(int osnovica) throws NeispravnaOsnovica {
        if (osnovica <= 0) throw new NeispravnaOsnovica("Neispravna osnovica " + Integer.toString(osnovica));
        this.osnovica = osnovica;
    }

    public void novoRadnoMjesto(RadnoMjesto rm) {
        radnaMjesta.add(rm);
    }

    public void zaposli(Radnik r, String nazivRadnogMjesta) {
        for(RadnoMjesto rm : radnaMjesta) {
            if (rm.getNaziv().equals(nazivRadnogMjesta) && rm.getRadnik() == null) {
                rm.setRadnik(r);
                return;
            }
        }
        throw new IllegalStateException("Nijedno radno mjesto tog tipa nije slobodno");
    }

    public void obracunajPlatu() {
        for (RadnoMjesto rm : radnaMjesta) {
            if (rm.getRadnik() != null) {
                double plata = osnovica * rm.getKoeficijent();
                rm.getRadnik().dodajPlatu(plata);
            }
        }
    }

    public double iznosPlate() {
        double suma = 0;
        for (RadnoMjesto rm : radnaMjesta) {
            if (rm.getRadnik() != null) {
                suma += osnovica * rm.getKoeficijent();
            }
        }
        return suma;
    }

    public Set<Radnik> radnici() {
        TreeSet<Radnik> rezultat = new TreeSet<>();
        for (RadnoMjesto rm : radnaMjesta) {
            if (rm.getRadnik() != null) {
                rezultat.add(rm.getRadnik());
            }
        }
        return rezultat;
    }

    public Map<RadnoMjesto, Integer> sistematizacija() {
        HashMap<RadnoMjesto, Integer> rezultat = new HashMap<>();
        for (RadnoMjesto rm : radnaMjesta) {
            Integer broj = rezultat.get(rm);
            if (broj == null)
                rezultat.put(rm, 1);
            else
                rezultat.put(rm, broj+1);
        }
        return rezultat;
    }

    public List<Radnik> filterRadnici(Function<Radnik,Boolean> comp) {
        ArrayList<Radnik> rezultat = new ArrayList<>();
        for (RadnoMjesto rm : radnaMjesta) {
            if (rm.getRadnik() != null && comp.apply(rm.getRadnik())) {
                rezultat.add(rm.getRadnik());
            }
        }
        return rezultat;
    }

    public List<Radnik> vecaProsjecnaPlata(double limit) {
        return filterRadnici((Radnik r) -> { return r.prosjecnaPlata() > limit; });
    }
}
