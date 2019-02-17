package ba.unsa.etf.rpr;

public class Grad {
    private int id;
    private String naziv;
    private int brojStanovnika;
    private Drzava drzava;
    private int nadmorskaVisina;

    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava, int nadmorskaVisina) {
        this.id = id;
        this.naziv = naziv;
        this.brojStanovnika = brojStanovnika;
        this.drzava = drzava;
        this.nadmorskaVisina = nadmorskaVisina;
    }

    public Grad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    public int getNadmorskaVisina() {
        return nadmorskaVisina;
    }

    public void setNadmorskaVisina(int nadmorskaVisina) {
        this.nadmorskaVisina = nadmorskaVisina;
    }

    @Override
    public String toString() { return naziv; }
}
