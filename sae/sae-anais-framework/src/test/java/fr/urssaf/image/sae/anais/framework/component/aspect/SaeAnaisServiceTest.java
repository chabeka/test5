package fr.urssaf.image.sae.anais.framework.component.aspect;

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
import fr.urssaf.image.sae.anais.framework.service.exception.ProfilCompteApplicatifNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.UserLoginNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.UserPasswordNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.util.InitFactory;

@SuppressWarnings( { "PMD.JUnitAssertionsShouldIncludeMessage" })
public class SaeAnaisServiceTest {

   private SaeAnaisService service;

   private static final String NOTEMPTY = "not empty";

   @Before
   public void initService() {
      service = new SaeAnaisService();
   }

   private static SaeAnaisAdresseServeur serveur;

   @BeforeClass
   public static void initClass() {

      serveur = InitFactory.initServeur();

   }

   @Test(expected = EnvironnementNonRenseigneException.class)
   public void emptyEnvironnement() {

      service.authentifierPourSaeParLoginPassword(null, serveur,
            SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, NOTEMPTY,
            NOTEMPTY, NOTEMPTY);

   }

   @Test(expected = ProfilCompteApplicatifNonRenseigneException.class)
   public void emptyCompteApplicatif() {

      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, serveur, null, null,
            NOTEMPTY, NOTEMPTY, NOTEMPTY, NOTEMPTY);

   }

   @Test(expected = UserLoginNonRenseigneException.class)
   public void emptyLogin() {

      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
            SaeAnaisEnumCompteApplicatif.Sae, null, " ", NOTEMPTY, NOTEMPTY,
            NOTEMPTY);

   }

   @Test(expected = UserPasswordNonRenseigneException.class)
   public void emptyPassword() {

      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
            SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, "", NOTEMPTY,
            NOTEMPTY);

   }

   @Test(expected = HoteNonRenseigneException.class)
   public void emptyHost() {

      SaeAnaisAdresseServeur serveur = ObjectFactory
            .createSaeAnaisAdresseServeur();
      serveur.setHote(null);
      serveur.setPort(1352);

      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
            SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, NOTEMPTY,
            NOTEMPTY, NOTEMPTY);

   }

   @Test(expected = PortNonRenseigneException.class)
   public void emptyPort() {

      SaeAnaisAdresseServeur serveur = ObjectFactory
            .createSaeAnaisAdresseServeur();
      serveur.setHote("hostname");
      serveur.setPort(null);

      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
            SaeAnaisEnumCompteApplicatif.Sae, null, NOTEMPTY, NOTEMPTY,
            NOTEMPTY, NOTEMPTY);

   }

   @Test(expected = ParametresApplicatifsNonRenseigneException.class)
   public void emptyCompteAppli() {

      service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
            SaeAnaisEnumCompteApplicatif.Autre, null, NOTEMPTY, NOTEMPTY,
            NOTEMPTY, NOTEMPTY);

   }

}
