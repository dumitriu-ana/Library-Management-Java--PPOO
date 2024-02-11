import clase.exceptii.AbonatExceptii;
import clase.exceptii.CarteExceptii;
import clase.exceptii.ImprumutareExceptii;
import clase.modele.Abonat;
import clase.modele.Carte;
import clase.modele.Categorie;
import clase.modele.Imprumutare;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * In clasa Main sunt create functiile de citire si scriere in fisere text, inserare a obiectelor de tip Carte, Abonat si Imprumutare
 * in colectiile aferente, precum si functiile pentru realizarea statisticilor.
 * In clasa MAin sunt declarate static colectiile pentru stocarea cartilor, abonatilor si a imprumuturilor.
 * @author Dumitriu Ana Maria
 * @version 1.0
 */

public class Main {

    static HashSet<Carte> carti = new HashSet<>();
    static HashSet<Abonat> abonati = new HashSet<>();
    static List<Imprumutare> listaImprumuturi = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    /**
     * Metoda afisareMeniu() este folosita pentru afisarea meniului in consola.
     */

    private static void afisareMeniu(){
        System.out.println(" --- Gestionare Biblioteca --- ");
        System.out.println("1. Cautare carte dupa titlu");
        System.out.println("2. Afisare carti pentru autor");
        System.out.println("   -------------- Adaugare elemente ------------");
        System.out.println("3. Adaugare imprumut");
        System.out.println("4. Adaugare abonat");
        System.out.println("5. Adaugare carte");
        System.out.println("   --------------------------");
        System.out.println("6. Afisare carti imprumutate pentru un abonat");
        System.out.println("7. Returnare imprumut ");
        System.out.println("   ------------- Afisare statistici -------------");
        System.out.println("8. Afiseaza numarul de imprumuturi pentru diecare carte.");
        System.out.println("9. Afiseaza numarul de imprumuturi pentru fiecare abonat.");
        System.out.println("10. Adiseaza numarul de elevi si procentul de elevi care au abonament");
        System.out.println("   --------------------------");
        System.out.println("0. Inchidere ");
    }

    /**
     * Metoda citireFisiere() este folosita pentru citirea fisierelor care contin carti, abonati si imprumuturi
     * si adaugarea elementelor citite in colectiile aferente.
     */
    private static void citireFisiere(){
        try (BufferedReader reader = new BufferedReader(new FileReader("carti.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                String[] detaliiCarte = linie.split(",");
                if (detaliiCarte.length >= 4) {
                    int id  = Integer.parseInt(detaliiCarte[0].trim());
                    String titlu = detaliiCarte[1];
                    String autor = detaliiCarte[2];
                    Categorie categorie = Categorie.valueOf(detaliiCarte[3].trim());
                    float pret = Float.parseFloat(detaliiCarte[4].trim());
                    Boolean status = Boolean.parseBoolean(detaliiCarte[5].trim());
                    Carte carte = new Carte(id,titlu, autor, categorie, pret, status);
                    carti.add(carte);
                } else {
                    System.err.println("Linie incorectă: " + linie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("abonati.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                String[] detaliiAbonat = linie.split(",");
                if (detaliiAbonat.length >= 4) {
                    int idAbonat = Integer.parseInt(detaliiAbonat[0]);
                    String nume = detaliiAbonat[1];
                    Integer anNastere = Integer.valueOf(detaliiAbonat[2]);
                    boolean elev = Boolean.parseBoolean(detaliiAbonat[3]);
                    boolean apt = Boolean.parseBoolean(detaliiAbonat[4]);

                    TreeSet<Integer> listaCartiImprumutate = new TreeSet<>();
                    for (int i = 5; i < detaliiAbonat.length; i++) {
                        listaCartiImprumutate.add(Integer.parseInt(detaliiAbonat[i]));
                    }

                    Abonat abonat = new Abonat(idAbonat, nume, anNastere, elev, apt, listaCartiImprumutate);
                    abonati.add(abonat);
                } else {
                    System.err.println("Linie incorectă: " + linie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("imprumuturi.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                linie = linie.trim();
                if (!linie.isEmpty()) {
                    String[] detaliiImprumut = linie.split(",");
                    if (detaliiImprumut.length >= 4) {
                        int idPersoana = Integer.parseInt(detaliiImprumut[0]);
                        LocalDate dataInceput = LocalDate.parse(detaliiImprumut[1]);
                        int perioada = Integer.parseInt(detaliiImprumut[2]);

                        TreeSet<Integer> listaIdCarti = new TreeSet<>();
                        for (int i = 3; i < detaliiImprumut.length; i++) {
                            listaIdCarti.add(Integer.parseInt(detaliiImprumut[i]));
                        }

                        Imprumutare imprumut = new Imprumutare(idPersoana, dataInceput, perioada, listaIdCarti);
                        listaImprumuturi.add(imprumut);
                    } else {
                        System.err.println("Linie incorectă: " + linie);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metoda salvareFisiere() este folosita pentru salvarea obiectelor din colectii (carti, abonati si imprumuturi) in fisiere
     * text prin rescrierea fisierelor.
     */
    private static void salvareFisiere(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("imprumuturi.txt"))) {
            for (Imprumutare i : listaImprumuturi) {
                writer.write(
                        i.getIdPersoana() + "," +
                        i.getDataInceput() + "," +
                        i.getPerioada() + ",");
                TreeSet<Integer> listaIdCartiNou = i.getListaIdCarti();
                for (Integer idCarte : listaIdCartiNou) {
                    writer.write(idCarte + ",");
                }

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("abonati.txt"))) {
            for (Abonat ab : abonati) {
                writer.write(ab.getIdAbonat() + ",");
                writer.write(ab.getNume() + ",");
                writer.write(ab.getAnNastere() + ",");
                writer.write(ab.getElev() + ",");
                writer.write(ab.getApt() + ",");
                for (Integer carteId : ab.getListaCartiImprumutate()) {
                    writer.write(carteId + ",");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("carti.txt"))) {
            for (Carte c : carti) {
                writer.write(c.getIdCarte()+ "," +c.getTitlu() + "," + c.getAutor() +
                        "," + c.getCategorie() + "," + c.getPret()+ "," + c.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Functia cautareCarte() este folosita pentru cautarea unei carti in lista de carti existente in biblioteca.
     * Titlul cartii cautate este introdus de la tastatura.
     * @throws CarteExceptii - in cazul in care cartea nu exista se aruca o exceptie.
     */
    private static void cautareCarte() throws CarteExceptii {
        System.out.println("Introdu titlul cartii: ");
        String titlu = scanner.nextLine();
        System.out.println(titlu);
        boolean gasit = false;

        for (Carte c : carti) {
            if (c.getTitlu().equals(titlu)) {
                gasit = true;
                System.out.println(c);
            }
        }
        if (!gasit) {
            throw new CarteExceptii("Cartea cautata nu exista");
        }
    }

    /**
     * Functia afisareCartiPentruAutor() este folosita pentru a afisa lista de carti aferenta unui autor.
     * Numele autorului este introdus de la tastatura.
     * @throws CarteExceptii - in cazul in care nu exista carti aferente autorului precizat.
     */
    private static void afisareCartiPentruAutor() throws CarteExceptii {
        System.out.println("Introdu numele autorului: ");
        String autor = scanner.nextLine();
        System.out.println(autor);

        List<Carte> cartiPentruAutor = new ArrayList<>();
        for (Carte c : carti) {
            if (c.getAutor().equals(autor)) {
                cartiPentruAutor.add(c);
            }
        }

        if (cartiPentruAutor.isEmpty()) {
            throw new CarteExceptii("Nu exista carti pentru autorul precizat");
        } else {
            System.out.println(cartiPentruAutor);
        }
    }

    /**
     * Metoda adaugareImprumut() este folosita pentru a adauga un imprumut in colectia de imprumuturi.
     * Se introduc de la tastatura urmatoarele informatii: id-ul abonatului care doreste sa realizeze un imprumut,
     * data de inceput a imprumutului, perioada pentru care se realizeaza imprumutul si lista de titluri pe care doreste sa le imprumute.
     * @throws ImprumutareExceptii - in cazul in care abonatul are un imprumut pe care nu l-a returnat inca (abonatul nu este apt pentru un nou imprumut).
     * @throws AbonatExceptii - in cazul in care id-ul introdus de la tastatura nu apartine unui abonat existent.
     */
    private static void adaugareImprumut() throws ImprumutareExceptii, AbonatExceptii {
        boolean corect = false;
        int codAbonat =0;
        boolean esteElev = false;
        boolean esteApt = true;

        while (!corect){
            System.out.println("Introdu id-ul abonatului: ");
            codAbonat = scanner.nextInt();
            for (Abonat ab: abonati){
                if(ab.getIdAbonat() == codAbonat){
                    corect = true;
                    esteElev = ab.getElev();
                    esteApt = ab.getApt();
                }
            }
            if(!corect){
                throw new AbonatExceptii("Adauga id-ul unui abonat existent");
            }
        }
        if(!esteApt){
            throw new ImprumutareExceptii("Abonatul nu poate realiza un imprumut, avand deja un imprumut activ");

        }

        corect = false;
        LocalDate dataInceput = LocalDate.parse("2023-11-01");
        while (!corect){
            System.out.println("Introdu data inceput (yyyy-MM-dd): ");
            scanner.nextLine();
            String dataInceputStr = scanner.nextLine();
            if(dataInceputStr.length()==10){
                corect = true;
                dataInceput = LocalDate.parse(dataInceputStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            else {
                System.out.println("Introdu o data valida");
            }
        }

        corect = false;
        int perioada = 0;
        while (!corect){
            System.out.println("Introdu perioada (în zile): ");
             perioada = scanner.nextInt();
             if(perioada < 7){
                 System.out.println("Perioada trebuie sa fie mai mare de 7 zile");
             }
             else if(esteElev && perioada>90){
                 System.out.println("Pentru un elev, perioada nu trebuie sa depaseasca 90 de zile");
             }
             else if(!esteElev && perioada>21){
                 System.out.println("Pentru un abonat care nu este elev, perioada maxima este de 21 de zile");
             }
             else {
                 corect = true;
             }
        }

        corect = false;
        TreeSet<Integer> listaIdCarti = new TreeSet<>();
        while (!corect) {

            System.out.println("Introdu titlurile (separate prin virgula): ");
            scanner.nextLine();
            String titluri = scanner.nextLine();
            String[] titluriArray = titluri.split(",");
            boolean carteGasita;
            int cartiGasite = 0;
            int cartiTotal = 0;
            for (String t : titluriArray) {
                cartiTotal++;
                String titluFaraSpatii = t.trim();
                carteGasita = false;
                for (Carte c : carti) {
                    if (c.getTitlu().equals(titluFaraSpatii)) {
                        if(c.getStatus()) {
                            listaIdCarti.add(c.getIdCarte());
                            carteGasita = true;
                        }
                    }
                }
                if(!carteGasita){
                    System.out.println("Cartea "+ titluFaraSpatii + " nu este disponibila in biblioteca.");
                }else {
                    cartiGasite++;
                }
            }
            if(cartiGasite == cartiTotal)
            {
                System.out.println("Toate cartile sunt disponibile si pot fi imprumutate");
                corect = true;
                for(Carte c: carti)
                {
                    c.setStatus(false);
                }
            }
            if(!corect){
                System.out.println("Toate titlurile introduse trebuie sa fie disponibile");
                listaIdCarti.clear();
            }
        }

        Imprumutare imprumut = new Imprumutare(codAbonat, dataInceput, perioada, listaIdCarti);
        listaImprumuturi.add(imprumut);

        System.out.println(listaImprumuturi);
    }

    /**
     * Metoda adaugareAbonat() este folosita pentru a adauga un abonat in colectia de abonati.
     * Se introduc de la tastatura urmatoarele informatii: id-ul abonatului, numele, anul nasterii, daca este sau nu elev si daca este sau nu apt pentru realizarea unui imprumut.
     * @throws AbonatExceptii - in cazul in care exista deja un abonat cu id-ul introdus, numele contine mai putin de doua litere,
     * varsta abonatului nu se afla in intervalul [14, 120] sau raspunsul primit in cazul intrebarilor legate de statut au raspuns diferit de da/nu.
     */
    private static void adaugareAbonat() {
        try {
            boolean corect = false;
            int idAbonat = 0;
            while (!corect) {
                try {
                    System.out.println("Introdu id-ul abonatului:");
                    idAbonat = scanner.nextInt();
                    scanner.nextLine();

                    for(Abonat ab : abonati){
                        if(idAbonat == ab.getIdAbonat())
                        {
                            throw new AbonatExceptii("Abonatul cu id-ul " + idAbonat + " există deja.");
                        }

                    }

                    corect = true;
                } catch (InputMismatchException e) {
                    System.out.println("Introdu un număr valid.");
                    scanner.nextLine();
                }
            }

            corect = false;
            String numeAbonat = "Ana";
            while (!corect) {
                System.out.println("Introdu numele abonatului:");
                numeAbonat = scanner.nextLine();

                if (numeAbonat.length() < 2) {
                    throw new AbonatExceptii("Numele trebuie să conțină cel puțin două litere.");
                }

                corect = true;
            }

            corect = false;
            int anulNasterii = 2000;
            while (!corect) {
                System.out.println("Introdu anul nasterii:");
                anulNasterii = scanner.nextInt();
                if (anulNasterii < LocalDate.now().getYear() - 120 || anulNasterii > LocalDate.now().getYear() - 14) {
                    throw new AbonatExceptii("Varsta minima pentru un abonat este de 14 ani, iar varsta maxima este 120 de ani.");
                } else {
                    corect = true;
                }

            }

            corect = false;
            boolean statusElev = false;
            scanner.nextLine();

            while (!corect) {
                System.out.println("Abonatul este Elev? da/nu:");
                String elev = scanner.nextLine().trim();
                if (elev.equalsIgnoreCase("da")) {
                    statusElev = true;
                    corect = true;
                } else if (elev.equalsIgnoreCase("nu")) {
                    corect = true;
                } else {
                    throw new AbonatExceptii("Introdu un raspuns valid (da/nu).");

                }
            }

            corect = false;
            boolean abonatApt = true;
            while (!corect) {
                System.out.println("Abonatul este apt pentru imprumut? da/nu:");
                String apt = scanner.nextLine().trim();
                if (apt.equalsIgnoreCase("da") || apt.equalsIgnoreCase("nu")) {
                    corect = true;
                    if (apt.equalsIgnoreCase("nu")) {
                        abonatApt = false;
                    }
                } else {
                    throw new AbonatExceptii("Introdu un raspuns valid (da/nu).");
                }
            }

            TreeSet<Integer> listaCartiImprumutate = new TreeSet<>();
            Abonat abonat = new Abonat(idAbonat, numeAbonat, anulNasterii, statusElev, abonatApt, listaCartiImprumutate);
            abonati.add(abonat);
            System.out.println("Abonatul " + numeAbonat + " a fost adaugat cu succes");

            System.out.println(abonati);
        } catch (AbonatExceptii e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }


    /**
     * Metoda adaugareCarte() este folosita pentru a adauga o carte in colectia de carti.
     * Se introduc de la tastatura urmatoarele informatii: id-ul cartii, titlul, autorul, categoria si pretul.
     * @throws CarteExceptii - in cazul in care exista deja o carte cu id-ul introdus
     */
    private static void adaugareCarte() throws Exception {
        boolean corect = false;
        int idCarte = 0;

        while (!corect) {
            System.out.println("Introdu id-ul cartii:");
            idCarte = scanner.nextInt();
            scanner.nextLine();

            boolean gasit = false;
            for (Carte c : carti) {
                if (c.getIdCarte() == idCarte) {
                   gasit = true;
                }
            }
            corect = !gasit;
        }

        corect = false;
        String titluCarte = "Anna Karenina";

        while (!corect) {
            System.out.println("Introdu titlul cartii:");
            titluCarte = scanner.nextLine();

            if (titluCarte.trim().length() < 2) {
                System.out.println("Titlul trebuie să conțină minim două litere");
            } else {
                corect = true;
            }
        }



        corect=false;
        String autorCarte = "Lev Tolstoi";
        while (!corect){
            System.out.println("Introdu autorul cartii:");
             autorCarte = scanner.nextLine();
            if(autorCarte.trim().length()<2){
                System.out.println("Autorul trebuie sa contina minim doua litere");
            }
            else {
                corect = true;
            }
        }

        corect = false;
        Categorie categorieCarte = null;

        while (!corect) {
            System.out.println("Introdu categorie cartii:");
            String categ = scanner.nextLine();

            try {
                categorieCarte = Categorie.valueOf(categ);
                corect = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Categoria " + categ + " nu există în bibliotecă.");
            }
        }

        corect=false;
        Float pretCarte = 10.0f;
        while (!corect){
            System.out.println("Introdu pretul cartii:");
             pretCarte = scanner.nextFloat();
            if(pretCarte>2.0 && pretCarte<800.0f){
                corect = true;
            }else {
                System.out.println("Pretul trebuie sa fie cuprins intre 2 si 800 RON.");
            }
        }

        Carte carte = new Carte(idCarte, titluCarte, autorCarte, categorieCarte, pretCarte, true);
        carti.add(carte);
        System.out.println("Cartea " + titluCarte + " a fost adaugata cu succes.");
        System.out.println(carti);
    }


    /**
     * Metoda afisareImprumuturiPentruAbonat() este folosita pentru afisa lista de carti aferenta unui abonat.
     * Numele abonatului este introdus de la tastatura. Se parcurge colectia de imprumuturi si se salveaza intr-o colectie toate cartie aflate in imprumuturile realizate de abonatul introdus.
     * @throws AbonatExceptii - in cazul in care nu exista un abonat cu numele introdus.
     */
    private static void afisareImprumuturiPentruAbonat() throws AbonatExceptii {
        System.out.println("Introdu numele abonatului: ");
        boolean corect = false;
        int idAbonat =0;
        String nume = "";
        while (!corect){
             nume = scanner.nextLine();
            for(Abonat a: abonati){
                if(a.getNume().equals(nume)){
                    corect = true;
                    idAbonat = a.getIdAbonat();
                }
            }
            if(!corect){
                throw new AbonatExceptii("Abonatul introdus nu exista.");

            }
        }

        ArrayList<Integer> listaIdCarti = new ArrayList<>();
        for(Imprumutare i: listaImprumuturi){
            if(i.getIdPersoana() == idAbonat){
                for(Integer idC: i.getListaIdCarti()){
                    listaIdCarti.add(idC);
                }
            }
        }
        ArrayList<Carte> listaCarti = new ArrayList<>();
        for(Integer idC: listaIdCarti){
            for(Carte c:carti){
                if(c.getIdCarte() == idC){
                    listaCarti.add(c);
                }
            }
        }
        System.out.println("Abonatul " + nume + " (id = "+ idAbonat+ ") a imprumutat " + listaIdCarti.size() + " carti:");
        System.out.println(listaIdCarti);
        System.out.println(listaCarti);


    }

    /**
     * Metoda returnareImprumut() este folosita pentru a reliza returnarea unui imprumut.
     * Id-ul abonatului este introdus de la tastatura. Se parcurge colectia de imprumuturi si se cauta imprumutul care are id-ul persoanei egal cu cel introdus (abonatul respectiv nu este apt pentru un imprumut, avand deja unul activ).
     * Dupa returnarea imprumutului, cartile devin disponibile, iar abonatul devine apt pentr un nou imprumut.
     * In cazul in care perioada imprumutului a fost depasita, se calculeaza suma pe care abonatul trebuie sa o plateasca bibliotecii.
     * @throws AbonatExceptii - in cazul in care nu exista un abonat id-ul introdus.
     */
    private static void returnareImprumut() throws AbonatExceptii {
        System.out.println("Introdu id-ul abonatului");
        int id = 0;
        Imprumutare imp=null;
        boolean corect = false;
        while (!corect){
           id = Integer.parseInt(scanner.nextLine());
           for(Imprumutare a:listaImprumuturi){
               if(a.getIdPersoana() ==id && !getAbonatCuId(id).getApt()){
                   corect = true;
                   imp = a;
               }
           }
           if(!corect){
               throw new AbonatExceptii("Nu exista un abonat cu acest id sau abonatul nu are un imprumut de retunat");
           }
        }
        for(Abonat abonat: abonati){
            if(abonat.getIdAbonat() == imp.getIdPersoana()){
                abonat.setApt(true);
                System.out.println("Abonatul a returnat cartile imprumutate si poate realiza un nou imprumut.");
            }
        }

        Float sumaCarti =0f;
        for(Integer i: imp.getListaIdCarti()){
            for(Carte c: carti){
                if(c.getIdCarte() == i){
                    c.setStatus(true);
                    sumaCarti += c.getPret();
                }
            }
        }


        LocalDate dataInceput = imp.getDataInceput();
        int perioada = imp.getPerioada();
        LocalDate dataReturnare = dataInceput.plusDays(perioada);

        if (dataReturnare.isBefore(LocalDate.now())) {
            long zileDepasite = ChronoUnit.DAYS.between(dataReturnare, LocalDate.now());
            System.out.println("Termen depășit cu " +zileDepasite + " zile.");
            System.out.println("Abonatul " + id + " tebuie sa plateasca o penazilare de " + sumaCarti *zileDepasite + " RON.");
        }

    }

    /**
     * Metoda getCarteCuId() este folosita pentru a returna obiectul de tip Carte care are id-ul egal cu cel primit ca parametru.
     * @param id - id-ul pentru care dorim sa returnam obiectul Carte
     * @return - obiectul Carte care are id-ul egal cu cel primit ca parametru sau null, in cazul in care nu se gaseste un obiect cu aceasta proprietate.
     */
    private static Carte getCarteCuId(int id) {
        for (Carte carte : carti) {
            if (carte.getIdCarte() == id) {
                return carte;
            }
        }
        return null;
    }


    /**
     * Metoda numarImprumuturiCarti() este folosita pentru a afisa numarul de imprumuturi pentru fiecare carte.
     * Se creaza o colectie HashMap cu cheia egala cu titlul cartii si valoarea egala cu numarul de imprumuturi aferent titlului.
     * Se parcurge colectia de imprumuturi si se incrementeaza valoarea pentru fiecare titlu gasit.
     */
    private static void numarImprumuturiCarti() {
        HashMap<String, Integer> mapCount = new HashMap<>();


        for (Imprumutare imp : listaImprumuturi) {
            for (Integer idCarte : imp.getListaIdCarti()) {
                Carte carte = getCarteCuId(idCarte);
                if (carte != null) {
                    String titlu = carte.getTitlu();
                    mapCount.put(titlu, mapCount.getOrDefault(titlu, 0) + 1);
                }
            }
        }

        for (String titlu : mapCount.keySet()) {
            int count = mapCount.get(titlu);
            if(count ==1){
                System.out.println("Cartea '" + titlu + "' a fost imprumutata o singura data.");
            }else {
                System.out.println("Cartea '" + titlu + "' a fost imprumutata de " + count + " ori.");
            }
        }
    }

    private static Abonat getAbonatCuId(int id) {
        for (Abonat abonat : abonati) {
            if (abonat.getIdAbonat() == id) {
                return abonat;
            }
        }
        return null;
    }

    /**
     * Metoda numarImprumuturiAbonat() este folosita pentru a afisa numarul de imprumuturi pentru fiecare abonat.
     * Se creaza o colectie HashMap cu cheia egala cu numele abonatului si valoarea egala cu numarul de imprumuturi aferent abonatului.
     * Se parcurge colectia de imprumuturi si se incrementeaza valoarea numarului de imprumuturi pentru fiecare imprumut aferent abonatului.
     */
    private static void numarImprumuturiAbonat() {
        HashMap<String, Integer> mapCount = new HashMap<>();

        System.out.println("Numar total imprumuturi: " + listaImprumuturi.size());
        for (Imprumutare imp : listaImprumuturi) {
            Abonat ab = getAbonatCuId(imp.getIdPersoana());
            if(ab!=null) {
                String nume = ab.getNume();
                mapCount.put(nume, mapCount.getOrDefault(nume, 0) + 1);
            }
        }


        for (String nume : mapCount.keySet()) {
            if(mapCount.get(nume) ==1){
                System.out.println("Abonatul " + nume + " are un imprumut.");
            }else {
                System.out.println("Abonatul " + nume + " are " + mapCount.get(nume) + " imprumuturi.");
            }

        }
    }

    /**
     * Metoda numarElevi() este folosita pentru a afisa numarul de elevi si procentul pe care acestia o reprezinta din numarul total de abonati.
     * Se parcurge colectia de abonati si se contorizeaza numarul de abonati si numarul de elevi.
     */
    private static void numarElevi(){
        int nrElevi=0;
        int nrAbonati = abonati.size();

        for(Abonat a: abonati){
            if(a.getElev()){
                nrElevi++;
            }
        }
        float procent = (float) nrElevi /nrAbonati*100;
        System.out.println("Din totalul de " + nrAbonati + " abonati, " + nrElevi + " sunt elevi ("+ procent+ "%)." );
    }


    /**
     * Metoda main() este folosita pentru a apela functiile de citire din fisier si pentru a afisa meniul. Meniul permite selectarea optiunilor
     * si apelarea functiilor pentru afisarea rezultatelor. In cadrul optriunii 0, colectiile sunt salvate in fisier si programul se inchide.
     */
    public static void main(String[] args) throws Exception, ImprumutareExceptii {
        afisareMeniu();
        citireFisiere();
        System.out.println(carti);
        System.out.println(abonati);
        System.out.println(listaImprumuturi);

        int optiune;
        do {
            System.out.println("Introdu optiune: ");
            optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    cautareCarte();

                    break;
                case 2:
                    afisareCartiPentruAutor();
                    break;
                case 3:
                    adaugareImprumut();
                    break;

                case 4:
                    adaugareAbonat();
                    break;
                case 5:
                   adaugareCarte();

                    break;
                case 6:
                    afisareImprumuturiPentruAbonat();
                    break;
                case 7:
                    returnareImprumut();
                    break;
                case 8:
                    numarImprumuturiCarti();
                    break;
                case 9:
                    numarImprumuturiAbonat();
                    break;
                case 10:
                    numarElevi();
                    break;
                case 0:
                    salvareFisiere();
            }

        } while (optiune != 0);


    }
}