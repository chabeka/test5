package fr.urssaf.image.sae.anais.framework.service.dao;

import org.apache.commons.lang.NotImplementedException;

import anaisJavaApi.AnaisHabilitationList;
import anaisJavaApi.AnaisUserInfo;
import anaisJavaApi.AnaisUserResult;
import fr.urssaf.image.sae.anais.framework.component.AnaisConnectionSupport;
import fr.urssaf.image.sae.anais.framework.component.ConnectionFactory;

/**
 * Classe de type DAO sur le serveur ANAIS<br>
 * Necessite une l'instanciation d'une connection factory
 * 
 * @see ConnectionFactory
 */
public class AuthentificationDAO extends AnaisConnectionSupport {

   /**
    * initialise la connection factory
    * 
    * @param connectionFactory
    *           connection factory pour le serveur ANAIS
    */
   public AuthentificationDAO(ConnectionFactory connectionFactory) {
      super(connectionFactory);
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
    *           Le code de l’inter-région où chercher les habilitations
    * @param codeOrganisme
    *           Le code de l’organisme où chercher les habilitations
    * 
    * @return Le jeton d’authentification sous la forme d’un flux XML
    */
   public final String createXMLToken(String userLogin, String userPassword,
         String codeInterRegion, String codeOrganisme) {

      try {
         AnaisUserResult userResult = this.checkUserCredential(userLogin,
               userPassword);

         AnaisUserInfo userInfo = this.getUserInfo(userResult.getUserDn());

         AnaisHabilitationList hablist = this.getUserHabilitations(userResult
               .getUserDn(), codeInterRegion, codeOrganisme);

         // TODO CREER JETON SECURITE
         // return null;
         throw new NotImplementedException("user info:" + userInfo
               + " habilitations:" + hablist);

      } finally {
         this.close();
      }

   }

}
