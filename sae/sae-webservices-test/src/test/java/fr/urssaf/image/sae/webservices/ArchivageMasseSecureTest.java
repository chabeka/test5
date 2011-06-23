package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.sae.webservices.configuration.SecurityConfiguration;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ArchivageMasse;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ArchivageMasseResponse;
import fr.urssaf.image.sae.webservices.service.RequestServiceFactory;
import fr.urssaf.image.sae.webservices.util.AuthenticateUtils;

@SuppressWarnings("PMD.MethodNamingConventions")
public class ArchivageMasseSecureTest {

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
   public void archivageMasse_success() throws RemoteException,
         URISyntaxException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      URI url = new URI(
            "ecde://cer69-ecde.cer69.recouv/DCL001/19991231/3/documents/sommaire.xml");

      ArchivageMasse request = RequestServiceFactory.createArchivageMasse(url);

      ArchivageMasseResponse response = service.archivageMasse(request);

      assertNotNull("Test de l'archivage masse", response
            .getArchivageMasseResponse());

   }

}
