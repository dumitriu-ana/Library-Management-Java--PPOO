package clase.modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;
/**
 * Clasa Imprumutare este utilizata pentru stocarea imprumuturilor.
 * Pentru fiecare obiect de tip imprumutare se retin urmatoarele informatii:
 * id-ul persoanei care a realizat imprumutul, data de inceput a imprumutului, perioada si lista de carti imprumutate.
 * @author Dumitriu Ana Maria
 * @version 1.0
 */
public class Imprumutare {

    private int idPersoana;
    private LocalDate dataInceput;
    private int perioada;
    private TreeSet<Integer> ListaIdCarti;

    public Imprumutare( int idPersoana, LocalDate dataInceput, int perioada, TreeSet<Integer> listaIdCarti) {
        this.idPersoana = idPersoana;
        this.dataInceput = dataInceput;
        this.perioada = perioada;
        ListaIdCarti = listaIdCarti;
    }


    public int getIdPersoana() {
        return idPersoana;
    }

    public void setIdPersoana(int idPersoana) {
        this.idPersoana = idPersoana;
    }

    public LocalDate getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(LocalDate dataInceput) {
        this.dataInceput = dataInceput;
    }

    public int getPerioada() {
        return perioada;
    }

    public void setPerioada(int perioada) {
        this.perioada = perioada;
    }

    public TreeSet<Integer> getListaIdCarti() {
        return ListaIdCarti;
    }

    public void setListaIdCarti(TreeSet<Integer> listaIdCarti) {
        ListaIdCarti = listaIdCarti;
    }

    @Override
    public String toString() {
        return "{" + "idPersoana=" + idPersoana +
                ", dataInceput='" + dataInceput + '\'' +
                ", perioada=" + perioada +
                ", ListaIdCarti=" + ListaIdCarti +
                '}';
    }


}
