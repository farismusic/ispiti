package ba.unsa.etf.rpr;

public class RadnoMjesto {
    private String naziv;
    private double koeficijent;
    private Radnik radnik = null;

    public RadnoMjesto(String naziv, double koeficijent, Radnik radnik) {
        this.naziv = naziv;
        this.koeficijent = koeficijent;
        this.radnik = radnik;
    }

    public RadnoMjesto() {
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getKoeficijent() {
        return koeficijent;
    }

    public void setKoeficijent(double koeficijent) {
        this.koeficijent = koeficijent;
    }

    public Radnik getRadnik() {
        return radnik;
    }

    public void setRadnik(Radnik radnik) {
        this.radnik = radnik;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RadnoMjesto) {
            RadnoMjesto rm = (RadnoMjesto) o;
            return (naziv.equals(rm.getNaziv()) && koeficijent == rm.getKoeficijent());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int)(naziv.hashCode() * koeficijent);
    }
}
