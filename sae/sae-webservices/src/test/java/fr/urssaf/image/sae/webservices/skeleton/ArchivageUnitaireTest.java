package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;

import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.ArchivageUnitaire;
import fr.cirtil.www.saeservice.ArchivageUnitaireResponseType;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;
import fr.urssaf.image.sae.webservices.util.XMLStreamUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service-test.xml",
      "/applicationContext-security-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class ArchivageUnitaireTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   @Before
   public void before() {

      Axis2Utils.initMessageContextSecurity();

   }

   @Test
   public void archivageUnitaire_success() {

      ArchivageUnitaire request = createArchivageMasseResponse("src/test/resources/request/archivageUnitaire_success.xml");

      ArchivageUnitaireResponseType response = skeleton
            .archivageUnitaireSecure(request).getArchivageUnitaireResponse();

      assertEquals("Test de l'archivage unitaire",
            "110E8400-E29B-11D4-A716-446655440000", response.getIdArchive()
                  .getUuidType());
   }

   private ArchivageUnitaire createArchivageMasseResponse(String filePath) {

      try {

         XMLStreamReader reader = XMLStreamUtils
               .createXMLStreamReader(filePath);
         return ArchivageUnitaire.Factory.parse(reader);

      } catch (Exception e) {
         throw new NestableRuntimeException(e);
      }

   }
}
