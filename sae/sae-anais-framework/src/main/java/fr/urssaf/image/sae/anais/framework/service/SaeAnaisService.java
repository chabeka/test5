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

   /**
    * initialise la connection factory
    * 
    * @param connectionFactory
    *           connection factory pour le serveur ANAIS
    */
   public SaeAnaisService(ConnectionFactory connectionFactory) {
      this.connectionFactory = connectionFactory;
   }

   /**
    * Création d’un jeton d’authentification à partir d’un couple login/mot de
    * passe
    * 
    * @param userLogin
    *           Le login de l’utilisateur
    * @param userPassword
    *           Le mot de passe de l’utilisateur
    * @param codeInterRegion
    *           Le code de l’inter-région où chercher les habilitations (peut
    *           être vide)
    * @param codeOrganisme
    *           Le code de l’organisme où chercher les habilitations (peut être
    *           vide)
    * @return Le jeton d’authentification sous la forme d’un flux XML
    */
   public final String authentifierPourSaeParLoginPassword(String userLogin,
         String userPassword, String codeInterRegion, String codeOrganisme) {

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
