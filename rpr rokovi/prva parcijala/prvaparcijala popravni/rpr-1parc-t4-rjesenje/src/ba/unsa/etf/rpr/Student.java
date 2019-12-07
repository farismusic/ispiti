package ba.unsa.etf.rpr;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public abstract class Student extends Osoba implements Comparable{



    private String brojIndeksa = "";
    private int[] ocjene = new int[100];
    private int brojOcjena = 0;

    public Student(String imePrezime) {
        super(imePrezime);
    }

    public String getBrojIndeksa() {
        return brojIndeksa;
    }

    public void setBrojIndeksa(String brojIndeksa) {
        this.brojIndeksa = brojIndeksa;
    }

    public void dodajOcjenu(int ocjena) throws IlegalnaOcjena {

        if(brojOcjena == 100){
            throw new IllegalArgumentException("Dosegnut maksimalan broj ocjena");
        }else if(ocjena > 10 || ocjena < 5){
            throw new IlegalnaOcjena("Ilegalna ocjena " + ocjena);
        } else {
            ocjene[brojOcjena++] = ocjena;
        }

    }

    public double prosjek(){
        if(brojOcjena == 0){
            return 0;
        } else {

            return Arrays.stream(ocjene).filter(ocjena -> ocjena != 0).average().getAsDouble();

        }
    }

    @Override
    public String toString() {

        DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));
        Double returnValue = Double.valueOf(df.format(prosjek()));

        //Double formatiraniProsjek = Double.("%.1f", prosjek());
        return "student " + super.toString() + " (" +getBrojIndeksa() + ")" + ", prosjek ocjena: " + returnValue;
    }

    public int[] getOcjene() {
        return ocjene;
    }

    public int getBrojOcjena() {
        return brojOcjena;
    }

    @Override
    public int compareTo(Object o) {
        Student s1 = (Student) o;
        Student s2 = this;

        if(s1.prosjek() > s2.prosjek()){
            return 1;
        } else if(s1.prosjek() < s2.prosjek()) {
            return -1;
        }else {
            return s1.getImePrezime().compareToIgnoreCase(s2.getImePrezime());
        }
    }
}
