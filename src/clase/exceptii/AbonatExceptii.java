package clase.exceptii;

/**
 * Clasa AbonatExceptii este utilizata pentru aruncarea exceptiilor in lucrul cu obiecte de tip Abonat.
 * @author Dumitriu Ana Maria
 * @version 1.0
 */
public class AbonatExceptii extends Exception{
    public AbonatExceptii(){
        super();
    }
    public AbonatExceptii(String mesaj) {
        super(mesaj);
    }
}
