package ba.unsa.etf.rpr;

import java.time.LocalDate;

public class Docent extends Nastavnik {


    public Docent(String imePrezime) {
        super(imePrezime);
    }

    @Override
    public String toString() {
        return "Doc. " + super.toString();
    }

    public String rodjendan(LocalDate datum2) {
        LocalDate ld = LocalDate.now();
        if(ld.getMonth().equals(datum2.getMonth()) && ld.getDayOfYear() == datum2.getDayOfYear()){
            return "Sretan rodjendan!";
        } else {
            return "";
        }
    }
}
