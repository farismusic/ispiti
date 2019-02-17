package ba.unsa.etf.rpr;

public class Radnik implements Comparable<Radnik> {
    private String imePrezime, jmbg;
    private double[] plate = new double[1000];
    private int brojPlata = 0;

    public Radnik(String imePrezime, String jmbg) {
        this.imePrezime = imePrezime;
        this.jmbg = jmbg;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public void dodajPlatu(double plata) {
        if (brojPlata == 1000) throw new IllegalArgumentException("Ne možete registrovati više od 1000 plata za radnika "+imePrezime);
        plate[brojPlata++] = plata;
    }

    public double prosjecnaPlata() {
        double rezultat=0;
        for (int i=0; i<brojPlata; i++)
            rezultat += plate[i];
        if (brojPlata > 0) rezultat /= brojPlata;
        return rezultat;
    }

    @Override
    public int compareTo(Radnik radnik) {
        if (prosjecnaPlata() > radnik.prosjecnaPlata())
            return -1;
        else
            return 1;
    }
}
