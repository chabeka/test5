package fr.urssaf.image.sae.webdemo.form;

import java.util.Date;

import org.springframework.validation.Errors;

/**
 * Formulaire de l'écran des traces d'exploitations
 * 
 * 
 */
public class LoggerForm {

   private Date start;

   private Date end;

   /**
    * @return valeur du critère 'A partir de'
    */
   public final Date getStart() {
      return start;
   }

   /**
    * @param start
    *           initialise le critère 'A partir de'
    */
   public final void setStart(Date start) {
      this.start = start;
   }

   /**
    * @return valeur du critère 'Jusqu'au'
    */
   public final Date getEnd() {
      return end;
   }

   /**
    * @param end
    *           initialise le critère 'Jusqu'au'
    */
   public final void setEnd(Date end) {
      this.end = end;
   }

   /**
    * Méthode de validation du formulaire
    * <ul>
    * <li>les dates <code>start</code> et <code>end</code> ne sont pas rangées :
    * erreur 'error.validDate'</li>
    * </ul>
    * 
    * @param errors
    *           liste des erreurs
    */
   public final void validate(Errors errors) {

      if (this.end != null && this.start != null
            && this.end.compareTo(this.start) < 0) {
         errors.rejectValue("end", "error.validDate");
      }

   }

}
