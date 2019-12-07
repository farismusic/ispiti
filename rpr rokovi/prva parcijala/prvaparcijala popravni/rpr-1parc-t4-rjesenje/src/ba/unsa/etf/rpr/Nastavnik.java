package ba.unsa.etf.rpr;

public abstract class Nastavnik extends Osoba {


    public Nastavnik(String imePrezime) {
        super(imePrezime);
    }

    @Override
    public String toString() {
        return "dr " + super.toString();
    }
}
