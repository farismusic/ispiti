package ba.unsa.etf.rpr;

public class Docent extends Nastavnik implements Mladi {
    public Docent(String imePrezime) {
        super(imePrezime);
    }

    @Override
    public String toString() {
        String rez = "Doc. dr "+getImePrezime();
        return rez;
    }
}
