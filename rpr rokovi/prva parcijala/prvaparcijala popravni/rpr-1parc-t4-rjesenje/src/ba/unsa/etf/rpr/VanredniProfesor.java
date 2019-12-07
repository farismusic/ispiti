package ba.unsa.etf.rpr;

public class VanredniProfesor extends Nastavnik {


    public VanredniProfesor(String imePrezime) {
        super(imePrezime);
    }

    @Override
    public String toString() {
        return "V. prof. " + super.toString();
    }
}
