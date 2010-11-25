package fr.urssaf.image.sae.anais.framework.service;

import org.apache.commons.lang.NotImplementedException;

import fr.urssaf.image.sae.anais.framework.component.ConnectionFactory;
import fr.urssaf.image.sae.anais.framework.component.DataSource;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisProfilConnexion;
import fr.urssaf.image.sae.anais.framework.service.dao.AuthentificationDAO;

/**
 * Classe de services sur le serveur ANAIS<br>
 * Necessite une l'instanciation d'une connection factory
 * 
 * @see ConnectionFactory
 */
public class SaeAnaisService {

   /**
    * Création d’un jeton d’authentification à partir d’un couple login/mot de
    * passe
    * 
    * @param environnement
    *           L’environnement (Développement / Validation / Production)
    * @param serveur
    *           Les paramètres de connexion au serveur ANAIS
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
   public final String authentifierPourSaeParLoginPassword(
         SaeAnaisEnumCodesEnvironnement environnement,
         SaeAnaisAdresseServeur serveur, String userLogin, String userPassword,
         String codeInterRegion, String codeOrganisme) {

      if (environnement == null) {
         throw new IllegalArgumentException(
               "L’environnement (Développement / Validation  / Production) doit être renseigné");
      }

      // TODO TROUVER LE PROFIL EN FONCTION DE L'ENVIRONNEMENT

      throw new NotImplementedException();

   }

   protected final String createXMLToken(SaeAnaisProfilConnexion profil,
         SaeAnaisAdresseServeur serveur, String userLogin, String userPassword,
         String codeInterRegion, String codeOrganisme) {

      // TODO SPECIFIER LES EXCEPTIONS

      if (serveur != null) {

         if (serveur.getHote() == null) {
            throw new IllegalArgumentException(
                  "L’adresse IP ou le nom d’hôte du serveur ANAIS doit être renseigné dans les paramètres de connexion");
         }

         if (serveur.getPort() == null) {
            throw new IllegalArgumentException(
                  "Le port du serveur ANAIS doit être renseigné dans les paramètres de connexion");
         }

      }

      // initialisation du data source
      DataSource dataSource = new DataSource();
      dataSource.setAppdn(profil.getCompteApplicatifDn());
      dataSource.setCodeapp(profil.getCodeApplication());
      dataSource.setCodeenv(profil.getCodeEnvironnement().code());
      dataSource.setPasswd(profil.getCompteApplicatifPassword());

      if (serveur == null) {
         throw new NotImplementedException();
         // TODO QUELLE ADRESSE SERVEUR CHOISIR POUR UN PROFIL DONNE?

      }

      else {

         initAdresseServeur(dataSource, serveur);

      }

      // initialisation du connection factory
      ConnectionFactory connection = new ConnectionFactory(dataSource);

      AuthentificationDAO authDAO = new AuthentificationDAO(connection);

      return authDAO.createXMLToken(userLogin, userPassword, codeInterRegion,
            codeOrganisme);

   }

   private void initAdresseServeur(DataSource dataSource,
         SaeAnaisAdresseServeur serveur) {

      dataSource.setHostname(serveur.getHote());
      dataSource.setPort(serveur.getPort());
      dataSource.setTimeout(Integer.toString(serveur.getTimeout()));
      dataSource.setUsetls(serveur.isTls());
   }

}
