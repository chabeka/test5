package fr.urssaf.image.sae.anais.framework.service.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import anaisJavaApi.AnaisHabilitation;
import anaisJavaApi.AnaisHabilitationList;
import anaisJavaApi.AnaisUserInfo;
import anaisJavaApi.AnaisUserResult;
import fr.urssaf.image.sae.anais.framework.component.AnaisConnectionSupport;
import fr.urssaf.image.sae.anais.framework.component.ConnectionFactory;
import fr.urssaf.image.sae.anais.framework.component.TokenFactory;
import fr.urssaf.image.sae.anais.framework.modele.DroitApplicatif;
import fr.urssaf.image.sae.anais.framework.modele.ObjectFactory;

/**
 * Classe de type DAO sur le serveur ANAIS<br>
 * Cette classe permet d'accéder aux différentes méthodes de ANAIS à travers la
 * classe {@link AnaisConnectionSupport}<br>
 * Les méthodes implémentées suivent le modèle: <code><br>
 * try {<br><br>
   &nbsp;&nbsp;//APPEL DES METHODES DE AnaisConnectionSupport<br><br>
   } finally {<br>
   &nbsp;&nbsp;this.close();<br>
   }<br>
 * </code>
 * 
 * 
 * @see ConnectionFactory
 */
public class AuthentificationDAO extends AnaisConnectionSupport {

   private static final Logger LOG = Logger
         .getLogger(AnaisConnectionSupport.class);

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
    * @throws SaeAnaisApiException
    */
   public final String createXMLToken(String userLogin, String userPassword,
         String codeInterRegion, String codeOrganisme) {

      try {
         AnaisUserResult userResult = this.checkUserCredential(userLogin,
               userPassword);

         AnaisUserInfo userInfo = this.getUserInfo(userResult.getUserDn());

         AnaisHabilitationList hablist = this.getUserHabilitations(userResult
               .getUserDn(), codeInterRegion, codeOrganisme);

         String lastname = userInfo.getInfo("sn");
         String firstname = userInfo.getInfo("givenName");

         LOG.debug("INFO CONNECTION");
         LOG.debug(userInfo);
         LOG.debug("sn:" + lastname);
         LOG.debug("givenName:" + firstname);

         List<DroitApplicatif> droits = new ArrayList<DroitApplicatif>();
         for (AnaisHabilitation hab : hablist.getHabilitationsList()) {
            LOG.debug("habilitation:" + hab.getHabilitation());
            LOG.debug(hab.getOrganisme());

            DroitApplicatif droit = ObjectFactory.createDroitAplicatif();

            droit.setCode(hab.getHabilitation());
            droit.setPerimetreValue(hab.getOrganisme());
            droit.setPerimetreType("?");

            droits.add(droit);

         }

         TokenFactory factory = new TokenFactory();
         return factory.createTokenSecurity(lastname, firstname, droits);

      } finally {
         this.close();
      }

   }

}
