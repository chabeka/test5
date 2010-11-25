package fr.urssaf.image.sae.anais.framework.service;

import org.apache.commons.lang.NotImplementedException;

import anaisJavaApi.AnaisConnection_Application;
import anaisJavaApi.AnaisHabilitationList;
import anaisJavaApi.AnaisUserInfo;
import anaisJavaApi.AnaisUserResult;
import fr.urssaf.image.sae.anais.framework.component.AnaisConnectionSupport;
import fr.urssaf.image.sae.anais.framework.component.ConnectionFactory;
import fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException;

/**
 * Classe de services sur le serveur ANAIS<br>
 * Necessite une l'instanciation d'une connection factory
 * 
 * @see ConnectionFactory
 */
public class SaeAnaisService {

   private final ConnectionFactory connectionFactory;

   public SaeAnaisService(ConnectionFactory connectionFactory) {
      this.connectionFactory = connectionFactory;
   }

   /**
    * 
    * @param userLogin
    * @param userPassword
    * @param codeInterRegion
    * @param codeOrganisme
    * @return
    * @throws SaeAnaisApiException
    * @throws IllegalArgumentException
    */
   public String authentifierPourSaeParLoginPassword(String userLogin,
         String userPassword, String codeInterRegion, String codeOrganisme)
         throws SaeAnaisApiException, IllegalArgumentException {

      if (userLogin == null) {
         throw new IllegalArgumentException(
               "L’identifiant de l’utilisateur doit être renseigné");
      }

      if (userPassword == null) {
         throw new IllegalArgumentException(
               "Le mot de passe de l’utilisateur doit être renseigné");
      }

      AnaisConnection_Application connection = connectionFactory
            .createConnection();

      AnaisConnectionSupport support = new AnaisConnectionSupport(connection);

      try {
         AnaisUserResult userResult = support.checkUserCredential(userLogin,
               userPassword);

         AnaisUserInfo userInfo = support.getUserInfo(userResult.getUserDn());

         AnaisHabilitationList hablist = support.getUserHabilitations(
               userResult.getUserDn(), codeInterRegion, codeOrganisme);

         throw new NotImplementedException("user info:" + userInfo
               + " habilitations:" + hablist);

      } finally {
         support.close();
      }

   }

}
