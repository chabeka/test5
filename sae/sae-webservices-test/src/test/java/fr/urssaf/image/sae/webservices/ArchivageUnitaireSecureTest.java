package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.sae.webservices.configuration.SecurityConfiguration;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ArchivageUnitaire;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ArchivageUnitaireResponseType;
import fr.urssaf.image.sae.webservices.service.RequestServiceFactory;
import fr.urssaf.image.sae.webservices.util.AuthenticateUtils;

@SuppressWarnings("PMD.MethodNamingConventions")
public class ArchivageUnitaireSecureTest {

   private SaeServiceStub service;

   @Before
   public final void before() {

      service = SecurityConfiguration.before();
   }

   @After
   public final void after() {

      SecurityConfiguration.after();
   }

   @Test
   public void archivageUnitaire_success_url() throws RemoteException,
         URISyntaxException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      URI url = new URI(
            "ecde://ecde.cer69.recouv/DCL001/19991231/3/documents/resultats.xml");

      Map<String, String> metadonnees = new HashMap<String, String>();
      metadonnees.put("CodeRND", "rnd");
      metadonnees.put("DenominationCompte", "compte");
      metadonnees.put("CodeOrganisme", "orga");

      ArchivageUnitaire request = RequestServiceFactory
            .createArchivageUnitaire(url, metadonnees);

      ArchivageUnitaireResponseType response = service.archivageUnitaire(
            request).getArchivageUnitaireResponse();

      // String xml = ADBBeanUtils.print(response);
      // LOG.debug(xml);

      assertEquals("Test de l'archivage unitaire",
            "110E8400-E29B-11D4-A716-446655440000", response.getIdArchive()
                  .getUuidType());

   }

   @Test
   public void archivageUnitaire_success_content() throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      byte[] content = "content".getBytes();
      Map<String, String> metadonnees = new HashMap<String, String>();
      metadonnees.put("CodeRND", "rnd");
      metadonnees.put("DenominationCompte", "compte");
      metadonnees.put("CodeOrganisme", "orga");

      ArchivageUnitaire request = RequestServiceFactory
            .createArchivageUnitaire(content, metadonnees);

      ArchivageUnitaireResponseType response = service.archivageUnitaire(
            request).getArchivageUnitaireResponse();

      // String xml = ADBBeanUtils.print(response);
      // LOG.debug(xml);

      assertEquals("Test de l'archivage unitaire",
            "110E8400-E29B-11D4-A716-446655440000", response.getIdArchive()
                  .getUuidType());

   }

}
