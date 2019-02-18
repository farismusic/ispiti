package ba.unsa.etf.rpr;

public class Student extends Osoba implements Comparable<Student> {
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

    public int getBrojOcjena() { return brojOcjena; }

    public void dodajOcjenu(int ocjena) throws IlegalnaOcjena {
        if (ocjena < 5 || ocjena > 10) throw new IlegalnaOcjena("Ilegalna ocjena " + Integer.toString(ocjena));
        if (brojOcjena >= 100)
            throw new IllegalArgumentException("Dosegnut maksimalan broj ocjena");
        ocjene[brojOcjena++] = ocjena;
    }

    public double prosjek() {
        if (brojOcjena == 0) return 0;
        double suma = 0;
        for (int i=0; i < brojOcjena; i++)
            suma += ocjene[i];
        return suma / brojOcjena;
    }

    @Override
    public int compareTo(Student student) {
        if (prosjek() > student.prosjek()) return -1;
        if (prosjek() < student.prosjek()) return 1;
        return 0;
    }

    @Override
    public String toString() {
        String rez = "student "+getImePrezime()+" ("+getBrojIndeksa()+"), prosjek ocjena: ";
        double zaokruzen = Math.round(prosjek() * 10);
        zaokruzen /= 10;
        rez += zaokruzen;
        return rez;
    }
}
