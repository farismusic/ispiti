package ba.unsa.etf.rpr;

public class BachelorStudent extends Student {


    public BachelorStudent(String imePrezime) {
        super(imePrezime);
    }

    @Override
    public String toString() {
        return "Bachelor " + super.toString();
    }


}
