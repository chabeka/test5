package fr.urssaf.image.sae.storage.dfce.validation;

import org.apache.commons.lang.Validate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.storage.dfce.messages.MessageHandler;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;

/**
 * Cet aspect permet de vérifier les paramètres de connection pour accéder à
 * DFCE.
 */
@Aspect
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class StorageConnectionServiceValidation {
   // Code erreur.
   private static final String CODE_ERROR = "connection.code.message";

   /**
    * Valide l'argument de la méthode
    * {@link fr.urssaf.image.sae.storage.dfce.services.impl.connection.StorageConnectionServiceImpl#setStorageConnectionServiceParameter(StorageConnectionParameter)
    * setStorageConnectionServiceParameter}.
    * 
    * 
    * @param storageConnectionParameter
    *           : Le paramètre de connection ( Host, user, password)
    */

   @Before(value = "execution(* fr.urssaf.image.sae.storage.services.connection..StorageConnectionService.setStorageConnectionServiceParameter(..)) && args(storageConnectionParameter)")
   @SuppressWarnings("PMD.LongVariable")
   public final void storageConnectionServiceValidation(
         final StorageConnectionParameter storageConnectionParameter) {
      checkBaseParameter(storageConnectionParameter);
      checkHostParameter(storageConnectionParameter);
      checkUserParameter(storageConnectionParameter);
   }

   /**
    * Valide les paramètres de la classe {@link fr.urssaf.image.sae.storage.model.connection.StorageBase StorageBase}
    * 
    * @param storageConnectionParameter
    *           : Le paramètre de connection ( Host, user, password).
    */
   @SuppressWarnings("PMD.LongVariable")
   private void checkBaseParameter(
         final StorageConnectionParameter storageConnectionParameter) {
      Validate.notNull(storageConnectionParameter, MessageHandler.getMessage(
            CODE_ERROR, "connection.parameters.required", "connection.impact",
            "connection.action"));
      // Vérification de la partie base name
      Validate.notNull(storageConnectionParameter.getStorageBase(),
            MessageHandler.getMessage(CODE_ERROR,
                  "connection.parameters.basename.required",
                  "connection.basename.impact", "connection.basename.action"));
      Validate.notNull(storageConnectionParameter.getStorageBase()
            .getBaseName(), MessageHandler.getMessage(CODE_ERROR,
            "connection.parameters.basename.required",
            "connection.basename.impact", "connection.basename.action"));
   }

   /**
    * Valide les paramètres de la classe {@link fr.urssaf.image.sae.storage.model.connection.StorageHost StorageHost}
    * 
    * @param storageConnectionParameter
    *           : Le paramètre de connection ( Host, user, password)
    */
   @SuppressWarnings("PMD.LongVariable")
   private void checkHostParameter(
         final StorageConnectionParameter storageConnectionParameter) {
      // Vérification de la partie Host
      Validate.notNull(storageConnectionParameter.getStorageHost(),
            MessageHandler.getMessage(CODE_ERROR,
                  "connection.parameters.host.required",
                  "connection.host.impact", "connection.host.action"));
      Validate.notNull(storageConnectionParameter.getStorageHost()
            .getContextRoot(), MessageHandler.getMessage(CODE_ERROR,
            "connection.parameters.contextroot.required",
            "connection.contextroot.impact", "connection.contextroot.action"));
      Validate.notNull(storageConnectionParameter.getStorageHost()
            .getHostName(), MessageHandler.getMessage(CODE_ERROR,
            "connection.parameters.host.required",
            "connection.hostname.impact", "connection.hostname.action"));
      Validate.notNull(storageConnectionParameter.getStorageHost()
            .getHostPort(), MessageHandler.getMessage(CODE_ERROR,
            "connection.parameters.hostport.required",
            "connection.hostport.impact", "connection.hostport.action"));
   }

   /**
    * Valide les paramètres de la classe {@link {@link fr.urssaf.image.sae.storage.model.connection.StorageUser StorageUser} 
    * 
    * @param storageConnectParam
    *           : Le paramètre de connection ( Host, user, password)
    */
   @SuppressWarnings("PMD.LongVariable")
   private void checkUserParameter(
         final StorageConnectionParameter storageConnectParam) {
      // Vérification des paramètres utilisateurs
      Validate.notNull(storageConnectParam.getStorageUser(), MessageHandler
            .getMessage(CODE_ERROR, "connection.user.required",
                  "connection.user.impact", "connection.user.action"));

      Validate.notNull(storageConnectParam.getStorageUser().getLogin(),
            MessageHandler.getMessage(CODE_ERROR, "connection.login.required",
                  "connection.login.impact", "connection.login.action"));
      Validate.notNull(storageConnectParam.getStorageUser().getPassword(),
            MessageHandler.getMessage(CODE_ERROR,
                  "connection.password.required", "connection.password.impact",
                  "connection.password.action"));
   }
}
