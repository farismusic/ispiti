package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

public class Fakultet {
    private List<Osoba> lista = new ArrayList<>();

    public void dodajOsobu(Osoba o) {
        lista.add(o);
    }

    @Override
    public String toString() {
        String rez = "";
        for (Osoba o : lista)
            rez += o + "\n";
        return rez;
    }

    public Set<Student> studenti() {
        TreeSet<Student> rez = new TreeSet<>();
        for (Osoba o : lista)
            if (o instanceof Student)
                rez.add((Student)o);
        return rez;
    }

    public List<Osoba> filtriraj(Function<Osoba, Boolean> f) {
        ArrayList<Osoba> rez = new ArrayList<>();
        for(Osoba o: lista)
            if (f.apply(o)) rez.add(o);
        return rez;
    }

    public List<BachelorStudent> topBachelor() {
        List lista = filtriraj((Osoba o) -> { return (o instanceof BachelorStudent && ((BachelorStudent) o).prosjek() >= 8);});
        return lista;
    }

    public List<Mladi> mladi() {
        List<Mladi> rez = new ArrayList<>();
        for (Osoba o : lista)
            if (o instanceof MasterStudent || o instanceof Docent)
                rez.add((Mladi)o);
        return rez;
    }
}
