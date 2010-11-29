package fr.urssaf.image.sae.anais.framework.service;

import org.apache.commons.lang.NotImplementedException;

import fr.urssaf.image.sae.anais.framework.component.ConnectionFactory;
import fr.urssaf.image.sae.anais.framework.component.DataSource;
import fr.urssaf.image.sae.anais.framework.component.ProfilFactory;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisProfilConnexion;
import fr.urssaf.image.sae.anais.framework.service.dao.AuthentificationDAO;

/**
 * Classe principale de services sur le serveur ANAIS<br>
 * 
 * @see ConnectionFactory
 */
public class SaeAnaisService {

   /**
    * Création d’un jeton d’authentification à partir d’un couple login/mot de
    * passe <br>
    * Si <code>serveur</code> n'est pas renseigné <code>environnement</code>
    * paramètre l'adressage du serveur ANAIS<br>
    * <br>
    * L'appel de la méthode instancie dans l'ordre
    * <ol>
    * <li>{@link DataSource}</li>
    * <li>{@link ConnectionFactory}</li>
    * <li>{@link AuthentificationDAO}</li>
    * </ol>
    * Enfin elle appelle la méthode
    * {@link AuthentificationDAO#createXMLToken(String, String, String, String)}<br>
    * <br>
    * La méthode est soumise à une vérification par la méthode
    * {@link SaeAnaisServiceCheck#authentifierPourSaeParLoginPasswordCheck} par
    * une approche Aspect <br>
    * <br>
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
    * @throws EnvironnementNonRenseigneException
    * @throws UserLoginNonRenseigneException
    * @throws UserPasswordNonRenseigneException
    * @throws HoteNonRenseigneException
    * @throws PortNonRenseigneException
    * @throws SaeAnaisApiException
    */
   public final String authentifierPourSaeParLoginPassword(
         SaeAnaisEnumCodesEnvironnement environnement,
         SaeAnaisAdresseServeur serveur, String userLogin, String userPassword,
         String codeInterRegion, String codeOrganisme) {

      ProfilFactory factory = new ProfilFactory();

      return this.createXMLToken(factory.createProfil(environnement), serveur,
            userLogin, userPassword, codeInterRegion, codeOrganisme);

   }

   private String createXMLToken(SaeAnaisProfilConnexion profil,
         SaeAnaisAdresseServeur serveur, String userLogin, String userPassword,
         String codeInterRegion, String codeOrganisme) {

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
