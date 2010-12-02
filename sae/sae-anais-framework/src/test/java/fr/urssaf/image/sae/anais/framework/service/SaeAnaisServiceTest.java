package fr.urssaf.image.sae.anais.framework.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import anaisJavaApi.AnaisExceptionAuthFailure;
import fr.urssaf.image.sae.anais.framework.modele.ObjectFactory;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCompteApplicatif;
import fr.urssaf.image.sae.anais.framework.service.exception.EnvironnementNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.HoteNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.ParametresApplicatifsNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.PortNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException;
import fr.urssaf.image.sae.anais.framework.service.exception.UserLoginNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.UserPasswordNonRenseigneException;

@SuppressWarnings("PMD")
public class SaeAnaisServiceTest {

   private SaeAnaisService service;

   private static final String NOTEMPTY = "not empty";

   @Before
   public void initService() {
      service = new SaeAnaisService();
   }

   private static SaeAnaisAdresseServeur SERVEUR;

   @BeforeClass
   public static void initClass() {

      SERVEUR = ObjectFactory.createSaeAnaisAdresseServeur();
      SERVEUR.setHote("cer44anaistest.cer44.recouv");
      SERVEUR.setPort(389);
      SERVEUR.setTimeout(5000);
      SERVEUR.setTls(false);

   }

   @Test
   @Ignore
   public void envDEV() {

      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, null,
            SaeAnaisEnumCompteApplicatif.Sae, null, Users.User1.LOGIN,
            Users.User1.PASSWORD, Users.User1.CODEIR, Users.User1.CODE_ORG);
   }

   @Test
   @Ignore
   public void envPROD() {

      String xml = service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Production, null,
            SaeAnaisEnumCompteApplicatif.Sae, null, Users.User1.LOGIN,
            Users.User1.PASSWORD, Users.User1.CODEIR, Users.User1.CODE_ORG);

      System.out.println(xml);
   }

   @Test
   @Ignore
   public void envVAL() {

      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Validation, null,
            SaeAnaisEnumCompteApplicatif.Sae, null, Users.User1.LOGIN,
            Users.User1.PASSWORD, Users.User1.CODEIR, Users.User1.CODE_ORG);
   }

   @Test
   @Ignore
   public void serveurNotNull() {

      // TODO test de comparaison avec un fichier xml modele
      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, SERVEUR,
            SaeAnaisEnumCompteApplicatif.Sae, null, Users.User1.LOGIN,
            Users.User1.PASSWORD, Users.User1.CODEIR, Users.User1.CODE_ORG);
   }

   @Test
   @Ignore
   public void serveurNull() {

      // TODO test de comparaison avec un fichier xml modele
      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, null,
            SaeAnaisEnumCompteApplicatif.Sae, null, Users.User1.LOGIN,
            Users.User1.PASSWORD, Users.User1.CODEIR, Users.User1.CODE_ORG);
   }

   @Test
   public void environnementEmpty() {

      try {
         service.authentifierPourSaeParLoginPassword(null, SERVEUR,
               SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, NOTEMPTY,
               NOTEMPTY, NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (EnvironnementNonRenseigneException e) {
         assertEquals(
               "L'environnement (Développement / Validation  / Production) doit être renseigné",
               e.getMessage());
      }

   }

   @Test
   public void loginEmpty() {

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, SERVEUR,
               SaeAnaisEnumCompteApplicatif.Sae, null, null, NOTEMPTY,
               NOTEMPTY, NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (UserLoginNonRenseigneException e) {
         assertEquals("L'identifiant de l'utilisateur doit être renseigné", e
               .getMessage());
      }

   }

   @Test
   public void passwordEmpty() {

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, SERVEUR,
               SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, null,
               NOTEMPTY, NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (UserPasswordNonRenseigneException e) {
         assertEquals("Le mot de passe de l'utilisateur doit être renseigné", e
               .getMessage());
      }

   }

   @Test
   public void HostEmpty() {

      SaeAnaisAdresseServeur serveur = ObjectFactory
            .createSaeAnaisAdresseServeur();
      serveur.setHote(null);
      serveur.setPort(1352);

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
               SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, NOTEMPTY,
               NOTEMPTY, NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (HoteNonRenseigneException e) {
         assertEquals(
               "L'adresse IP ou le nom d'hôte du serveur ANAIS doit être renseigné dans les paramètres de connexion",
               e.getMessage());
      }

   }

   @Test
   public void PortEmpty() {

      SaeAnaisAdresseServeur serveur = ObjectFactory
            .createSaeAnaisAdresseServeur();
      serveur.setHote("hostname");
      serveur.setPort(null);

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
               SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, NOTEMPTY,
               NOTEMPTY, NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (PortNonRenseigneException e) {
         assertEquals(
               "Le port du serveur ANAIS doit être renseigné dans les paramètres de connexion",
               e.getMessage());
      }

   }

   @Test
   public void authFailure() {

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, SERVEUR,
               SaeAnaisEnumCompteApplicatif.Sae, null, Users.User1.LOGIN,
               "inconnu", Users.User1.CODEIR, Users.User1.CODE_ORG);
         fail("le test ne doit pas passer");
      } catch (SaeAnaisApiException e) {
         assertEquals("le login est incorrect",
               AnaisExceptionAuthFailure.class, e.getCause().getClass());
      }

   }

   @Test
   public void otherCodeAppliException() {

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, SERVEUR,
               SaeAnaisEnumCompteApplicatif.Autre, null, Users.User1.LOGIN,
               "inconnu", Users.User1.CODEIR, Users.User1.CODE_ORG);
         fail("le test ne doit pas passer");
      } catch (ParametresApplicatifsNonRenseigneException e) {
         assertEquals(
               "Les paramètres du compte applicatif ne sont pas renseignés alors qu'aucun profil de compte applicatif n'a été spécifié",
               e.getMessage());
      }
   }

}
