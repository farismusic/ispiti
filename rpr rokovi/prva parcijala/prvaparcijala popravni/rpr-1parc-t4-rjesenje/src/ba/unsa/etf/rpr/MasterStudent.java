package ba.unsa.etf.rpr;

public class MasterStudent extends Student implements Mladi {
    private int[] ocjene2 = new int[100];
    private int brojOcjena2 = 0;

    public MasterStudent(String imePrezime) {
        super(imePrezime);
    }

    public void dodajOcjenuMaster(int ocjena) throws IlegalnaOcjena {
        if (ocjena < 5 || ocjena > 10) throw new IlegalnaOcjena("Ilegalna ocjena " + Integer.toString(ocjena));
        if (brojOcjena2 >= 100)
            throw new IllegalArgumentException("Dosegnut maksimalan broj ocjena");
        ocjene2[brojOcjena2++] = ocjena;
    }

    public double prosjekMaster() {
        if (brojOcjena2 == 0) return 0;
        double suma = 0;
        for (int i=0; i < brojOcjena2; i++)
            suma += ocjene2[i];
        return suma / brojOcjena2;
    }

    @Override
    public double prosjek() {
        if (getBrojOcjena() == 0 && brojOcjena2 == 0) return 0;
        double suma = super.prosjek() * getBrojOcjena();
        for (int i=0; i < brojOcjena2; i++)
            suma += ocjene2[i];
        return suma / ( getBrojOcjena() + brojOcjena2 );
    }

    @Override
    public String toString() {
        String rez = "Master " + super.toString();
        return rez;
    }
}
