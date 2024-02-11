package clase.modele;

import java.util.TreeSet;

/**
 * Clasa Abonat este utilizata pentru stocarea abonatilor bibliotecii.
 * Pentru fiecare obiect de tip abonat se retin urmatoarele informatii:
 * idAbonat, nume, anul nasterii, daca este elev sau nu, daca poate realiza un imprumut sau nu si lista de carti imprumutate.
 * @author Dumitriu Ana Maria
 * @version 1.0
 */
public class Abonat {


    private Integer idAbonat;
    private String nume;
    private Integer anNastere;
    private Boolean elev;
    private Boolean apt;
    private TreeSet<Integer> listaCartiImprumutate;

    public Abonat(Integer idAbonat, String nume, Integer anNastere, Boolean elev, Boolean apt, TreeSet<Integer> listaCartiImprumutate) {
        this.idAbonat = idAbonat;
        this.nume = nume;
        this.anNastere = anNastere;
        this.elev = elev;
        this.apt = apt;
        if (listaCartiImprumutate == null) {
            this.listaCartiImprumutate = new TreeSet<>();
        } else {
            this.listaCartiImprumutate = listaCartiImprumutate;
        }
    }

    public Boolean getApt() {
        return apt;
    }

    public void setApt(Boolean apt) {
        this.apt = apt;
    }

    public Integer getIdAbonat() {
        return idAbonat;
    }

    public void setIdAbonat(Integer idAbonat) {
        this.idAbonat = idAbonat;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getAnNastere() {
        return anNastere;
    }

    public void setAnNastere(Integer anNastere) {
        this.anNastere = anNastere;
    }

    public Boolean getElev() {
        return elev;
    }

    public void setElev(Boolean elev) {
        this.elev = elev;
    }

    public TreeSet<Integer> getListaCartiImprumutate() {
        return listaCartiImprumutate;
    }

    public void setListaCartiImprumutate(TreeSet<Integer> listaCartiImprumutate) {
        this.listaCartiImprumutate = listaCartiImprumutate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Abonat abonat = (Abonat) obj;
        return idAbonat.equals(abonat.idAbonat);
    }
    @Override
    public int hashCode() {
        return idAbonat.hashCode();
    }

    @Override
    public String toString() {
        return "clase.modele.Abonat{" +
                "id='" + idAbonat + '\'' +
                "nume='" + nume + '\'' +
                ", anNastere='" + anNastere + '\'' +
                ", elev=" + elev +
                ", listaCartiImprumutate=" + listaCartiImprumutate +
                '}';
    }
}
