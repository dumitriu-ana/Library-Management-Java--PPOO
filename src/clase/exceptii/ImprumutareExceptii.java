package clase.exceptii;

/**
 * Clasa ImprumutareExceptii este utilizata pentru aruncarea exceptiilor in lucrul cu obiecte de tip Imprumutare.
 * @author Dumitriu Ana Maria
 * @version 1.0
 */
public class ImprumutareExceptii extends Throwable {
    public ImprumutareExceptii(String s){
        super(s);
    }


}
