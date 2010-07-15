package fr.urssaf.image.commons.controller.spring.extjs;

import java.util.HashMap;
import java.util.Map;

/**
 * Valeur de retour pour un appel Ajax ExtJs.
 * 
 * Afin de ne pas directement écrire une chaîne JSON, il est plus pratique de passer par
 * cette classe, puis d'appeler la classe de service ExtJsJsonService afin de la transformer
 * en Json.
 * 
 * La classe est au "format" décrit dans la fiche de développement F022 : 
 * Javascript : Utilisation du framework extjs
 *
 */
public class ExtJsJsonReturnValue {

   
   /**
    * Booléen indiquant si le retour est un succès ou un échec 
    */
   private boolean success;
   
   /**
    * Message général (de succès ou d'échec) 
    */
   private String message;
   
   /**
    * Liste des erreurs de saisie utilisateur
    */
   private final Map<String, String> errors;
   
   
   /**
    * Constructeur
    */
   public ExtJsJsonReturnValue()
   {
      errors = new HashMap<String, String>();
   }


   /**
    * Booléen indiquant si le retour est un succès ou un échec
    * @return
    */
   public Boolean getSuccess() {
      return success;
   }


   /**
    * Booléen indiquant si le retour est un succès ou un échec
    * @param success
    */
   public void setSuccess(Boolean success) {
      this.success = success;
   }


   /**
    * Message général (de succès ou d'échec)
    * @return
    */
   public String getMessage() {
      return message;
   }


   /**
    * Message général (de succès ou d'échec)
    * @param message
    */
   public void setMessage(String message) {
      this.message = message;
   }


   /**
    * Liste des erreurs de saisie utilisateur
    * @return
    */
   public Map<String, String> getErrors() {
      return errors;
   }


   /**
    * Ajoute une erreur à la liste des erreurs
    * @param nomChamp Le nom du champ du formulaire
    * @param messageErreur Le message d'erreur
    */
   public void ajouteErreur(String nomChamp, String messageErreur)
   {
      this.errors.put(nomChamp, messageErreur);
   }
   
}
