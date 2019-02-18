package ba.unsa.etf.rpr;

import java.time.LocalDate;

public interface Mladi {
    public default String rodjendan(LocalDate datumRodjenja) {
        if (datumRodjenja.getDayOfMonth() == LocalDate.now().getDayOfMonth() && datumRodjenja.getMonth().equals(LocalDate.now().getMonth()))
            return "Sretan rodjendan!";
        return "";
    }
}
