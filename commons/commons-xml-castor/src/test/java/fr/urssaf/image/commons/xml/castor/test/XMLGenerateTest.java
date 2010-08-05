package fr.urssaf.image.commons.xml.castor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.commons.xml.castor.XMLReader;
import fr.urssaf.image.commons.xml.castor.XMLWriter;
import fr.urssaf.image.commons.xml.castor.modele.Auteur;
import fr.urssaf.image.commons.xml.castor.modele.Document;
import fr.urssaf.image.commons.xml.castor.modele.Documents;
import fr.urssaf.image.commons.xml.castor.modele.Etat;

public class XMLGenerateTest {

   private static final String DIRECTORY;

   private static final String MAPPING;

   private static final Logger LOG = Logger.getLogger(XMLGenerateTest.class);

   static {
      DIRECTORY = FilenameUtils.concat(SystemUtils.getJavaIoTmpDir()
            .getAbsolutePath(), "mappingCastorXML");

      MAPPING = FilenameUtils.concat("src/test/resources/mapping",
            "Documents.xml");
   }

   private static final String XML = "src/test/resources/xml";

   private static XMLReader<Document> xmlReader;

   private static XMLWriter<Documents> xmlWriter;

   private static final String FORMAT = "dd/MM/yyyy";

   @BeforeClass
   public static void init() throws IOException, MappingException {
      File directory = new File(DIRECTORY);
      FileUtils.forceMkdir(directory);
      FileUtils.cleanDirectory(directory);

      xmlReader = new XMLReader<Document>(Document.class, MAPPING);

      xmlWriter = new XMLWriter<Documents>(MAPPING);
   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void xmlWriterTest() throws IOException, MarshalException,
         ValidationException, ParseException {

      Document document = new Document("titre 0", this.parseDate("10/12/2010"));
      document.setId(1);
      Auteur auteur = new Auteur("auteur 2");
      auteur.setId(2);
      document.setAuteur(auteur);

      Etat etat0 = new Etat();
      etat0.setId(0);
      etat0.setDate(this.parseDate("10/12/1999"));
      etat0.setEtat("open");

      Etat etat1 = new Etat();
      etat1.setId(1);
      etat1.setDate(this.parseDate("10/12/1998"));
      etat1.setEtat("close");

      Etat etat2 = new Etat();
      etat2.setId(2);
      etat2.setDate(this.parseDate("10/12/1996"));
      etat2.setEtat("init");

      document.getEtats().add(etat0);
      document.getEtats().add(etat1);
      document.getEtats().add(etat2);

      String FILE = FilenameUtils.concat(DIRECTORY, "documents_castor.xml");

      FileWriter writer = new FileWriter(FILE);

      Documents documents = new Documents();
      documents.getDocuments().add(document);
      documents.getDocuments().add(
            new Document("titre 2", this.parseDate("12/07/1998")));

      xmlWriter.write(documents, writer);

   }

   @Test
   public void xmlReaderTest() throws MarshalException, ValidationException,
         IOException {

      FileReader reader = new FileReader(FilenameUtils.concat(XML,
            "documents_castor.xml"));

      Document doc = xmlReader.read(reader);

      assertEquals("erreur titre", "titre 0", doc.getTitre());
      assertEquals("erreur identifiant", Integer.valueOf(1), doc.getId());
      assertEquals("erreur date", "10/12/2010", formatDate(doc.getDate()));
      assertEquals("erreur auteur@id", Integer.valueOf(2), doc.getAuteur()
            .getId());
      assertEquals("erreur auteur@nom", "auteur 2", doc.getAuteur().getNom());

      Object[] etats = doc.getEtats().toArray();

      assertEquals("erreur etat", 3, etats.length);

      LOG.debug(((Etat) etats[0]).getId());
      LOG.debug(((Etat) etats[1]).getId());
      LOG.debug(((Etat) etats[2]).getId());

      assertEtat((Etat) etats[0]);
      assertEtat((Etat) etats[1]);
      assertEtat((Etat) etats[2]);

      reader.close();

   }

   private void assertEtat(Etat etat) {

      if (etat.getId().equals(0)) {

         assertEquals("erreur etat@id", Integer.valueOf(0), etat.getId());
         assertEquals("erreur etat@etat", "open", etat.getEtat());
         assertEquals("erreur etat@date", "10/12/1999", formatDate(etat
               .getDate()));

      } else if (etat.getId().equals(1)) {

         assertEquals("erreur etat@id", Integer.valueOf(1), etat.getId());
         assertEquals("erreur etat@etat", "close", etat.getEtat());
         assertEquals("erreur etat@date", "10/12/1998", formatDate(etat
               .getDate()));

      } else if (etat.getId().equals(2)) {

         assertEquals("erreur etat@id", Integer.valueOf(2), etat.getId());
         assertEquals("erreur etat@etat", "init", etat.getEtat());
         assertEquals("erreur etat@date", "10/12/1996", formatDate(etat
               .getDate()));

      } else {
         fail("etat non prise en compte " + etat.getId());
      }

   }

   private Date parseDate(String date) throws ParseException {

      return DateUtils.parseDate(date, new String[] { FORMAT });
   }

   private String formatDate(Date date) {

      SimpleDateFormat formatter = new SimpleDateFormat(FORMAT, Locale
            .getDefault());

      return formatter.format(date);
   }

}
