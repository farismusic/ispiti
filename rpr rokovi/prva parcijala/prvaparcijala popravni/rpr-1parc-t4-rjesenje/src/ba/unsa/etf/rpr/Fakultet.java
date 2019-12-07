package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Fakultet {

    private List<Osoba> osobe = new ArrayList<>();

    public void dodajOsobu(Osoba o){
        osobe.add(o);
    }


    @Override
    public String toString() {

        String ispisi = "";

        for(Osoba o : osobe){
            ispisi = ispisi + o.toString() + "\n";
        }

        return ispisi;

    }

    public Set<Student> studenti() {

        Set<Student> students = new TreeSet<>();

        for (Osoba o : osobe){
            if(o instanceof Student){
                students.add((Student) o );
            }
        }

        return students;
    }

    public List<Osoba> filtriraj(Predicate<Osoba> fun) {


        return osobe.stream().filter(osoba -> fun.test(osoba)).collect(Collectors.toList());

    }


    public List<BachelorStudent> topBachelor() {

        List bachelori = osobe.stream().filter(osoba -> osoba instanceof BachelorStudent && ((BachelorStudent) osoba).prosjek() >= 8).collect(Collectors.toList());
        return bachelori;

    }

    public List<Mladi> mladi() {

        List<Mladi> rez = new ArrayList<>();

        for (Osoba o : osobe) {
            if (o instanceof MasterStudent || o instanceof Docent) {

                o = new Mladi(o.getImePrezime());

                rez.add((Mladi) o);
            }
        }
        return rez;
    }
}


