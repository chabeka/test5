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
   public final Date getStartDate() {
      return start == null ? null : (Date) start.clone();
   }

   /**
    * @param start
    *           initialise le critère 'A partir de'
    */
   public final void setStartDate(Date start) {
      if (start == null) {
         this.start = start;
      } else {
         this.start = (Date) start.clone();
      }
   }

   /**
    * @return valeur du critère 'Jusqu'au'
    */
   public final Date getEndDate() {
      return end == null ? null : (Date) end.clone();
   }

   /**
    * @param end
    *           initialise le critère 'Jusqu'au'
    */
   public final void setEndDate(Date end) {
      if (end == null) {
         this.end = end;
      } else {
         this.end = (Date) end.clone();
      }
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
         errors.rejectValue("endDate", "error.validDate");
      }

   }

}
