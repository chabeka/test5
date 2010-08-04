package fr.urssaf.image.commons.xml.castor.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.commons.xml.castor.XMLReader;
import fr.urssaf.image.commons.xml.castor.XMLWriter;
import fr.urssaf.image.commons.xml.castor.modele.Auteur;
import fr.urssaf.image.commons.xml.castor.modele.Document;

public class XMLGenerateTest {

   private static final String DIRECTORY;

   private static final String MAPPING;

   // private static final Logger LOGGER =
   // Logger.getLogger(XMLGenerateTest.class);

   static {
      DIRECTORY = FilenameUtils.concat(SystemUtils.getJavaIoTmpDir()
            .getAbsolutePath(), "mappingCastorXML");

      MAPPING = FilenameUtils.concat("src/test/resources/mapping",
            "Document.xml");
   }

   private static final String XML = "src/test/resources/xml";

   private static XMLReader<Document> xmlReader;

   private static XMLWriter<Document> xmlWriter;

   private static final String FORMAT = "dd/MM/yyyy";

   @BeforeClass
   public static void init() throws IOException, MappingException {
      File directory = new File(DIRECTORY);
      FileUtils.forceMkdir(directory);
      FileUtils.cleanDirectory(directory);

      xmlReader = new XMLReader<Document>(Document.class, MAPPING);

      xmlWriter = new XMLWriter<Document>(MAPPING);
   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert") 
   public void xmlWriterTest() throws IOException, MarshalException,
         ValidationException, ParseException {

      Document document = new Document("titre 0", DateUtils.parseDate(
            "10/12/2010", new String[] { FORMAT }));
      document.setId(1);
      Auteur auteur = new Auteur("auteur 2");
      auteur.setId(2);
      document.setAuteur(auteur);

      String FILE = FilenameUtils.concat(DIRECTORY, "documents_castor.xml");

      FileWriter writer = new FileWriter(FILE);

      xmlWriter.write(document, writer);

      // String checksum = ChecksumFileUtil.md5(FILE);
      // String xmlChecksum = ChecksumFileUtil.md5(MAPPING);

      // assertEquals("erreur sur le md5 de " + FILE, xmlChecksum, checksum);

   }

   @Test
   public void xmlReaderTest() throws MarshalException, ValidationException,
         IOException {

      FileReader reader = new FileReader(FilenameUtils.concat(XML,
            "documents_castor.xml"));

      Document doc = xmlReader.read(reader);

      SimpleDateFormat formatter = new SimpleDateFormat(FORMAT, Locale
            .getDefault());

      assertEquals("erreur sur le titre", "titre 0", doc.getTitre());
      assertEquals("erreur sur l'identifiant", Integer.valueOf(1), doc.getId());
      assertEquals("erreur sur la date", "10/12/2010", formatter.format(doc
            .getDate()));
      assertEquals("erreur sur l'auteur", Integer.valueOf(2), doc.getAuteur()
            .getId());
      assertEquals("erreur sur le nom de l'auteur", "auteur 2", doc.getAuteur()
            .getNom());

      reader.close();

   }

}
