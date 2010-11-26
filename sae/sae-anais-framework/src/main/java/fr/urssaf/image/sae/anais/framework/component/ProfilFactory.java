package fr.urssaf.image.sae.anais.framework.component;

import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisProfilConnexion;

/**
 * Classe de création de profil pour ANAIS
 */
public class ProfilFactory {

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

   //TODO prévoir des profil sur des fichiers de parametres
   private static final int PORT = 389;

   private static final int TIMEOUT = 5000;

   private abstract static class AbstractProfil extends SaeAnaisProfilConnexion {

      protected AbstractProfil(
            SaeAnaisEnumCodesEnvironnement codeEnvironnement, String host) {
         super();
         this
               .setCompteApplicatifDn("cn=USR_READ_NAT_APP_RECHERCHE-DOCUMENTAIRE,OU=RECHERCHE-DOCUMENTAIRE,OU=Applications,OU=Technique,dc=recouv");
         this.setCodeEnvironnement(codeEnvironnement);
         this.setCodeApplication("RECHERCHE-DOCUMENTAIRE");
         this.setCompteApplicatifPassword("rechercheDoc");

         SaeAnaisAdresseServeur serveur = new SaeAnaisAdresseServeur();
         serveur.setHote(host);
         serveur.setPort(PORT);
         serveur.setTimeout(TIMEOUT);
         serveur.setTls(false);

         this.getServeurs().add(serveur);
      }
   }

   private static class DevProfil extends AbstractProfil {

      protected DevProfil() {
         super(SaeAnaisEnumCodesEnvironnement.Production,
               "cer44anaistest.cer44.recouv");
      }

   }

   private static class ValidProfil extends AbstractProfil {

      protected ValidProfil() {
         super(SaeAnaisEnumCodesEnvironnement.Validation, null);
      }

   }

   private static class ProdProfil extends AbstractProfil {

      protected ProdProfil() {
         super(SaeAnaisEnumCodesEnvironnement.Production,
               "cer44anaisreg1.cer44.recouv");
      }

   }
}
