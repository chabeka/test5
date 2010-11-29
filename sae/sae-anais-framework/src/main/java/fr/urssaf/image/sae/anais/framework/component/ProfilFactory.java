package fr.urssaf.image.sae.anais.framework.component;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisProfilConnexion;

/**
 * Classe de création de profil pour ANAIS<br>
 * La configuration s'appuie sur le fichier sae-anais-framework.properties dans
 * le classpath<br>
 * les paramètres de ce fichier sont:
 * <ul>
 * <li>anais.port : Numéro de port TCP sur lequel écoute le serveur ANAIS</li>
 * <li>anais.tls : Activation de TLS pour la communication avec le serveur ANAIS
 * </li>
 * <li>anais.timeout : Le temps de réponse maximal d'un serveur lors d'une
 * connexion</li>
 * <li>anais.cn : DN du compte applicatif utilisé pour se connecter à l’annuaire
 * </li>
 * <li>anais.code-application : Code de l'application</li>
 * <li>anais.compte-applicatif-password : Mot de passe du compte applicatif pour
 * se connecter à l'annuaire</li>
 * <li>anais.host.DEV : Nom d'hôte ou adresse IP du serveur ANAIS en mode «
 * Tests pour les développements »</li>
 * <li>anais.host.VAL : Nom d'hôte ou adresse IP du serveur ANAIS en mode «
 * Validation »</li>
 * <li>anais.host.PROD : Nom d'hôte ou adresse IP du serveur ANAIS en mode «
 * Production »</li>
 * </ul>
 * <br>
 * <br>
 * La récupération des paramètres dans le fichier de properties s'appuie sur le
 * Framework <a href='http://commons.apache.org/configuration/'>Apache commons
 * configuration</a><br>
 * Le délimiteur des valeurs de chaque paramètre est '|'
 */
public class ProfilFactory {

   private static final Logger LOG = Logger.getLogger(ProfilFactory.class);

   /**
    * Méthode de création de profil pour ANAIS à partir d'un code
    * d'environnement
    * 
    * @see SaeAnaisEnumCodesEnvironnement
    * 
    * @param environnement
    *           code d'environnement de ANAIS
    * @return profil ANAIS spécifique au code environnement
    */
   public final SaeAnaisProfilConnexion createProfil(
         SaeAnaisEnumCodesEnvironnement environnement) {

      SaeAnaisProfilConnexion profil = null;

      switch (environnement) {
      case Developpement:
         profil = new DevProfil();
         break;
      case Validation:
         profil = new ValidProfil();
         break;
      case Production:
         profil = new ProdProfil();
         break;
      default:
         throw new IllegalArgumentException("'SaeAnaisEnumCodesEnvironnement' "
               + environnement.name() + " is not used ");

      }

      return profil;
   }

   private abstract class AbstractProfil extends SaeAnaisProfilConnexion {

      protected AbstractProfil(SaeAnaisEnumCodesEnvironnement codeEnvironnement) {
         super();

         try {
            AbstractConfiguration.setDefaultListDelimiter("|".charAt(0));

            Configuration config = new PropertiesConfiguration(
                  "sae-anais-framework.properties");

            this.setCompteApplicatifDn(config.getString("anais.cn"));
            this.setCodeApplication(config.getString("anais.code-application"));
            this.setCompteApplicatifPassword(config
                  .getString("anais.compte-applicatif-password"));
            this.setCodeEnvironnement(codeEnvironnement);

            int port = config.getInt("anais.port");
            int timeout = config.getInt("anais.timeout");
            boolean tls = config.getBoolean("anais.tls");
            String host = config.getString("anais.host."
                  + codeEnvironnement.code(),null);
            
            SaeAnaisAdresseServeur serveur = new SaeAnaisAdresseServeur();
            serveur.setHote(host);
            serveur.setPort(port);
            serveur.setTimeout(timeout);
            serveur.setTls(tls);

            this.getServeurs().add(serveur);

            LOG.debug("INFO APPLICATION");
            LOG.debug("codeenv : " + this.getCodeEnvironnement().code());
            LOG.debug("appdn : " + this.getCompteApplicatifDn());
            LOG.debug("codeapp : " + this.getCodeApplication());
            LOG.debug("paswd : " + this.getCompteApplicatifPassword());

            LOG.debug("INFO SERVEUR");
            LOG.debug("host : " + host);
            LOG.debug("port : " + port);
            LOG.debug("timeout : " + timeout);
            LOG.debug("tls : " + tls);

         } catch (ConfigurationException e) {
            throw new IllegalStateException(e);
         }
      }
   }

   private class DevProfil extends AbstractProfil {

      protected DevProfil() {
         super(SaeAnaisEnumCodesEnvironnement.Developpement);
      }

   }

   private class ValidProfil extends AbstractProfil {

      protected ValidProfil() {
         super(SaeAnaisEnumCodesEnvironnement.Validation);
      }

   }

   private class ProdProfil extends AbstractProfil {

      protected ProdProfil() {
         super(SaeAnaisEnumCodesEnvironnement.Production);
      }

   }
}
