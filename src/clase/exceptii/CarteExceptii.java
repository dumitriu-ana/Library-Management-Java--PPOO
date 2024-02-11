package clase.exceptii;

/**
 * Clasa CarteExceptii este utilizata pentru aruncarea exceptiilor in lucrul cu obiecte de tip Carte.
 * @author Dumitriu Ana Maria
 * @version 1.0
 */

public class CarteExceptii extends Exception{
    public CarteExceptii(){
        super();
    }

    public CarteExceptii(String mesaj){
        super(mesaj);
    }
}
