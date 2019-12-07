package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.util.Arrays;

public class MasterStudent extends Student {

    private int[] ocjeneMaster = new int[100];
    private int brojOcjenaMaster = 0;


    public MasterStudent(String imePrezime) {
        super(imePrezime);
    }

    public void dodajOcjenuMaster(int ocjena) throws IlegalnaOcjena {
        if(brojOcjenaMaster == 100){
            throw new IllegalArgumentException("Dosegnut maksimalan broj ocjena");
        }else if(ocjena > 10 || ocjena < 5){
            throw new IlegalnaOcjena("Ilegalna ocjena " + ocjena);
        } else {
            ocjeneMaster[brojOcjenaMaster++] = ocjena;
        }
    }

    public double prosjekMaster(){
        if(brojOcjenaMaster == 0){
            return 0;
        } else {

            return Arrays.stream(ocjeneMaster).filter(ocjena -> ocjena != 0).average().getAsDouble();

        }
    }

    @Override
    public double prosjek() {
        if(brojOcjenaMaster + getBrojOcjena() == 0){
            return 0;
        }
        int sumaICiklusa = Arrays.stream(getOcjene()).sum();
        int sumaObaCiklusa = Arrays.stream(ocjeneMaster).reduce(sumaICiklusa, (a, b) -> a + b);
        return (double)sumaObaCiklusa/(brojOcjenaMaster + getBrojOcjena());
    }

    @Override
    public String toString() {
        return "Master " + super.toString();
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
