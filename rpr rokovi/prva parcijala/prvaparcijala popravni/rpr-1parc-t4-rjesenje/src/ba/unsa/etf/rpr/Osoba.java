package ba.unsa.etf.rpr;

public abstract class Osoba {

    private String imePrezime;

    public Osoba(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    @Override
    public String toString() {
        return imePrezime;
    }
}
