package fr.urssaf.image.sae.anais.framework.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCompteApplicatif;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisProfilCompteApplicatif;
import fr.urssaf.image.sae.anais.framework.util.CTD;
import fr.urssaf.image.sae.anais.framework.util.InitFactory;
import fr.urssaf.image.sae.anais.framework.util.TokenCheck;

@SuppressWarnings( { "PMD.JUnitAssertionsShouldIncludeMessage" })
public class SaeAnaisServiceTest {

   private static final Logger LOG = Logger
         .getLogger(SaeAnaisServiceTest.class);

   private SaeAnaisService service;

   @Before
   public void initService() {
      service = new SaeAnaisService();
   }

   private static SaeAnaisAdresseServeur serveur;

   private static CTD ctd0;

   private static CTD ctd1;

   private static CTD ctd2;

   @BeforeClass
   public static void initClass() {

      serveur = InitFactory.initServeur();

      ctd0 = InitFactory.initCTD("ctd0");
      ctd1 = InitFactory.initCTD("ctd1");
      ctd2 = InitFactory.initCTD("ctd2");

   }

   @Test
   @Ignore
   public void envDEV() throws IOException {

      String xml = service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Developpement, null,
            SaeAnaisEnumCompteApplicatif.Sae, null, ctd1.getUserLogin(), ctd1
                  .getUserPassword(), ctd1.getCodeir(), ctd1.getCodeorg());

      LOG.debug(xml);
      assertTrue(TokenCheck.checkCTD1(xml));
   }

   @Test
   public void envPROD() throws IOException {

      String xml = service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Production, null,
            SaeAnaisEnumCompteApplicatif.Sae, null, ctd1.getUserLogin(), ctd1
                  .getUserPassword(), ctd1.getCodeir(), ctd1.getCodeorg());

      LOG.debug(xml);
      assertTrue(TokenCheck.checkCTD0(xml));
   }

   @Test
   public void envVAL() throws IOException {

      String xml = service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Validation, null,
            SaeAnaisEnumCompteApplicatif.Sae, null, ctd1.getUserLogin(), ctd1
                  .getUserPassword(), ctd1.getCodeir(), ctd1.getCodeorg());

      LOG.debug(xml);
      assertTrue(TokenCheck.checkCTD0(xml));
   }

   @Test
   @SuppressWarnings( { "PMD.MethodNamingConventions" })
   public void serveurNotNull_CTD0() throws IOException {

      String xml = service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Production, serveur,
            SaeAnaisEnumCompteApplicatif.Sae, null, ctd0.getUserLogin(), ctd0
                  .getUserPassword(), ctd0.getCodeir(), ctd0.getCodeorg());

      LOG.debug(xml);
      assertTrue(TokenCheck.checkCTD0(xml));
   }

   @Test
   @SuppressWarnings( { "PMD.MethodNamingConventions" })
   public void serveurNotNull_CTD1() throws IOException {

      String xml = service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Production, serveur,
            SaeAnaisEnumCompteApplicatif.Sae, null, ctd1.getUserLogin(), ctd1
                  .getUserPassword(), ctd1.getCodeir(), ctd1.getCodeorg());

      LOG.debug(xml);
      assertTrue(TokenCheck.checkCTD0(xml));
   }

   @Test
   @SuppressWarnings( { "PMD.MethodNamingConventions" })
   public void serveurNotNull_CTD2() throws IOException {

      String xml = service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Production, serveur,
            SaeAnaisEnumCompteApplicatif.Sae, null, ctd2.getUserLogin(), ctd2
                  .getUserPassword(), ctd2.getCodeir(), ctd2.getCodeorg());

      LOG.debug(xml);
      assertTrue(TokenCheck.checkCTD0(xml));
   }

   @Test
   public void cptAppliNotNull() throws IOException {

      SaeAnaisProfilCompteApplicatif profil = InitFactory
            .initCompteApplicatif();

      String xml = service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Production, serveur,
            SaeAnaisEnumCompteApplicatif.Autre, profil, ctd1.getUserLogin(),
            ctd1.getUserPassword(), ctd1.getCodeir(), ctd1.getCodeorg());

      LOG.debug(xml);
      assertTrue(TokenCheck.checkCTD0(xml));

   }

}
