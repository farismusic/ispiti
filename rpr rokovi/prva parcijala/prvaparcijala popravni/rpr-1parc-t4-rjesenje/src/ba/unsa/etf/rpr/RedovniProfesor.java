package ba.unsa.etf.rpr;

public class RedovniProfesor extends Nastavnik {


    public RedovniProfesor(String imePrezime) {
        super(imePrezime);
    }

    @Override
    public String toString() {
        return "R. prof. " + super.toString();
    }
}
