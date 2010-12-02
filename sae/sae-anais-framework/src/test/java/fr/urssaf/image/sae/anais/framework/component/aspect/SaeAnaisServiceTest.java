package fr.urssaf.image.sae.anais.framework.component.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.sae.anais.framework.modele.ObjectFactory;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCompteApplicatif;
import fr.urssaf.image.sae.anais.framework.service.SaeAnaisService;
import fr.urssaf.image.sae.anais.framework.service.exception.EnvironnementNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.HoteNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.ParametresApplicatifsNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.PortNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.UserLoginNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.UserPasswordNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.util.InitFactory;

@SuppressWarnings({"PMD.JUnitAssertionsShouldIncludeMessage"})
public class SaeAnaisServiceTest {

   private SaeAnaisService service;

   private static final String NOTEMPTY = "not empty";
   
   private static final String FAIL_MESSAGE = "le test ne doit pas passer";

   @Before
   public void initService() {
      service = new SaeAnaisService();
   }

   private static SaeAnaisAdresseServeur serveur;

   @BeforeClass
   public static void initClass() {

      serveur = InitFactory.initServeur();

   }

   @Test
   public void emptyEnvironnement() {

      try {
         service.authentifierPourSaeParLoginPassword(null, serveur,
               SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, NOTEMPTY,
               NOTEMPTY, NOTEMPTY);
         fail(FAIL_MESSAGE);
      } catch (EnvironnementNonRenseigneException e) {
         assertEquals(
               "L'environnement (Développement / Validation  / Production) doit être renseigné",
               e.getMessage());
      }

   }

   @Test
   public void emptyLogin() {

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
               SaeAnaisEnumCompteApplicatif.Sae, null, null, NOTEMPTY,
               NOTEMPTY, NOTEMPTY);
         fail(FAIL_MESSAGE);
      } catch (UserLoginNonRenseigneException e) {
         assertEquals("L'identifiant de l'utilisateur doit être renseigné", e
               .getMessage());
      }

   }

   @Test
   public void emptyPassword() {

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
               SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, null,
               NOTEMPTY, NOTEMPTY);
         fail(FAIL_MESSAGE);
      } catch (UserPasswordNonRenseigneException e) {
         assertEquals("Le mot de passe de l'utilisateur doit être renseigné", e
               .getMessage());
      }

   }

   @Test
   public void emptyHost() {

      SaeAnaisAdresseServeur serveur = ObjectFactory
            .createSaeAnaisAdresseServeur();
      serveur.setHote(null);
      serveur.setPort(1352);

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
               SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, NOTEMPTY,
               NOTEMPTY, NOTEMPTY);
         fail(FAIL_MESSAGE);
      } catch (HoteNonRenseigneException e) {
         assertEquals(
               "L'adresse IP ou le nom d'hôte du serveur ANAIS doit être renseigné dans les paramètres de connexion",
               e.getMessage());
      }

   }

   @Test
   public void emptyPort() {

      SaeAnaisAdresseServeur serveur = ObjectFactory
            .createSaeAnaisAdresseServeur();
      serveur.setHote("hostname");
      serveur.setPort(null);

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
               SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, NOTEMPTY,
               NOTEMPTY, NOTEMPTY);
         fail(FAIL_MESSAGE);
      } catch (PortNonRenseigneException e) {
         assertEquals(
               "Le port du serveur ANAIS doit être renseigné dans les paramètres de connexion",
               e.getMessage());
      }

   }

   @Test
   public void emptyCompteAppli() {

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
               SaeAnaisEnumCompteApplicatif.Autre, null, NOTEMPTY, NOTEMPTY,
               NOTEMPTY, NOTEMPTY);
         fail(FAIL_MESSAGE);
      } catch (ParametresApplicatifsNonRenseigneException e) {
         assertEquals(
               "Les paramètres du compte applicatif ne sont pas renseignés alors qu'aucun profil de compte applicatif n'a été spécifié",
               e.getMessage());
      }
   }

}
