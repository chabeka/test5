package fr.urssaf.image.sae.anais.framework.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anaisJavaApi.AnaisExceptionAuthFailure;
import anaisJavaApi.AnaisExceptionServerAuthentication;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCompteApplicatif;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisProfilCompteApplicatif;
import fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException;
import fr.urssaf.image.sae.anais.framework.util.CTD;
import fr.urssaf.image.sae.anais.framework.util.InitFactory;

@SuppressWarnings( { "PMD.JUnitAssertionsShouldIncludeMessage" })
public class SaeAnaisServiceFailureTest {

   private SaeAnaisService service;

   @Before
   public void initService() {
      service = new SaeAnaisService();
   }

   private static SaeAnaisAdresseServeur serveur;

   private static CTD ctd;

   @BeforeClass
   public static void initClass() {

      serveur = InitFactory.initServeur();

      ctd = InitFactory.initCTD("ctd1");

   }

   @Test
   public void failureAuth() {

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur,
               SaeAnaisEnumCompteApplicatif.Sae, null, ctd.getUserLogin(),
               "inconnu", ctd.getCodeir(), ctd.getCodeorg());
         fail("le test ne doit pas passer");
      } catch (SaeAnaisApiException e) {
         assertEquals("le login est incorrect",
               AnaisExceptionAuthFailure.class, e.getCause().getClass());
      }

   }

   @Test
   public void failureDN() {

      SaeAnaisProfilCompteApplicatif profil = InitFactory
            .initCompteApplicatif();
      profil.setDn("cn=USR_READ_NAT_APP_RECHERCHE-DOCUMENTAIRE");

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Production, serveur,
               SaeAnaisEnumCompteApplicatif.Autre, profil, ctd.getUserLogin(),
               ctd.getUserPassword(), ctd.getCodeir(), ctd.getCodeorg());

         fail("le test ne doit pas passer");
      } catch (SaeAnaisApiException e) {
         assertEquals(AnaisExceptionServerAuthentication.class, e.getCause()
               .getClass());
      }

   }

}
