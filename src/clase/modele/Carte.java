package clase.modele;

import java.util.Objects;

/**
 * Clasa Carte este utilizata pentru stocarea cartilor din biblioteca.
 * Pentru fiecare carte se retin urmatoarele informatii:
 * idCarte, titlu, autor, categorie, pret, status (daca este sau nu imprumutata deja)
 * @author Dumitriu Ana Maria
 * @version 1.0
 */
public class Carte {

    private int idCarte;
    private String titlu;
    private String autor;
    private Categorie categorie;
    private float pret;
    private Boolean status;



    public Carte(int id, String titlu, String autor, Categorie categorie, float pret, Boolean status) {

        this.idCarte = id;
        this.titlu = titlu;
        this.autor = autor;
        this.categorie = categorie;
        this.pret = pret;
        this.status = status;
    }
    public int getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(int idCarte) {
        this.idCarte = idCarte;
    }


    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }


    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return idCarte == carte.idCarte && Float.compare(carte.pret, pret) == 0 && Objects.equals(titlu, carte.titlu) && Objects.equals(autor, carte.autor) && categorie == carte.categorie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarte, titlu, autor, categorie, pret);
    }

    @Override
    public String toString() {
        String valid = "indisponibila";
        if(status) {
            valid = "disponibila";
        }
        return  idCarte + ". '" + titlu + '\'' + ", autor: " + autor +
                ", pret: " + pret + " RON"  +
                ", categorie: " + categorie  +
                ", status: " + valid + "\n";
    }

}
