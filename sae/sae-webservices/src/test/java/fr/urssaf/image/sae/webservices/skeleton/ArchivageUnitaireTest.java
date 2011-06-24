package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.axis2.context.MessageContext;
import org.apache.commons.io.output.XmlStreamWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.ArchivageUnitaire;
import fr.cirtil.www.saeservice.ArchivageUnitaireResponseType;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service.xml",
      "/applicationContext-security-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class ArchivageUnitaireTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   private MessageContext ctx;

   @Before
   public void before() {

      ctx = new MessageContext();
      MessageContext.setCurrentMessageContext(ctx);

   }

   @Test
   public void archivageUnitaire_success() {

      Axis2Utils.initMessageContext(ctx,
            "src/test/resources/request/archivageUnitaire_success.xml");

      ArchivageUnitaire request = new ArchivageUnitaire();

      ArchivageUnitaireResponseType response = skeleton
            .archivageUnitaireSecure(request).getArchivageUnitaireResponse();

      assertEquals("Test de l'archivage unitaire",
            "110E8400-E29B-11D4-A716-446655440000", response.getIdArchive()
                  .getUuidType());
   }

   public static String print(InputStream input) {

      try {
         TransformerFactory factory = TransformerFactory.newInstance();

         Transformer transformer = factory.newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty(
               "{http://xml.apache.org/xslt}indent-amount", "4");
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         transformer.transform(new StreamSource(input), new StreamResult(
               new XmlStreamWriter(out, "UTF-8")));

         return out.toString();
      } catch (TransformerException e) {

         throw new IllegalStateException(e);
      }
   }
}
