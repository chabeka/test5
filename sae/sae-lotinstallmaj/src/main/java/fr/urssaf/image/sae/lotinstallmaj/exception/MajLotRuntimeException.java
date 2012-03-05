package fr.urssaf.image.sae.lotinstallmaj.exception;


/**
 * Exception à lever dans le cas d'erreurs que l'on ne souhaite<br>
 * pas traiter ou que l'on ne peut pas traiter. 
 *
 */
public final class MajLotRuntimeException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   /**
    * MAJ LOT RUNTIME exception
    * 
    * @param cause
    *             cause de l'exception
    */
   public MajLotRuntimeException(Throwable cause) {
      super(cause);
   }
   
}
