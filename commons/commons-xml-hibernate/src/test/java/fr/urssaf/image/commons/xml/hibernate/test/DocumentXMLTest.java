package fr.urssaf.image.commons.xml.hibernate.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.commons.xml.hibernate.dao.DocumentXML;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class DocumentXMLTest {

   @Autowired
   private DocumentXML documentXML;

   private static final String FILE;

   private static final String DIRECTORY;

   static {
      DIRECTORY = FilenameUtils.concat(SystemUtils.getJavaIoTmpDir()
            .getAbsolutePath(), "mappingXML");
      FILE = FilenameUtils.concat(DIRECTORY, "documents_hibernate.xml");
   }

   @BeforeClass
   public static void init() throws IOException {
      File directory = new File(DIRECTORY);
      FileUtils.forceMkdir(directory);

      FileUtils.cleanDirectory(directory);
   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void writeAllDocument() throws IOException {

      documentXML.writeAllDocument(FILE);

   }

}
