package fr.urssaf.image.commons.util.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;


/**
 * Tests unitaires de la classe FileUtil
 *
 */
public class FileUtilTest {


   private final static String FILE_LECTURE = "src/test/resources/file/file_test.txt";

   private final static String CRLF_FILE_LECTURE = "\r\n";
   
	@Test
	public void read() throws IOException {
		
	   String text = FileReaderUtil.read(FILE_LECTURE,"UTF-8");
		
		String message = "La lecture du fichier a échoué";
		
		String expected = 
		   "1ère ligne du fichier" + 
		   CRLF_FILE_LECTURE +
		   "2ème ligne du fichier" + 
		   CRLF_FILE_LECTURE + 
		   "dernière ligne du fichier";
		
		String actual = text;
		
		assertEquals(message,expected,actual);
		
	}
	
	
	
	@Test
   public void write() throws IOException {
      
	   // Récupération de quelques propriétés système
	   String tmpdir = System.getProperty("java.io.tmpdir") ;
	   String lineSeparator = System.getProperty("line.separator");
	   
	   // Quelques paramètres
	   String encoding = "UTF-8";

	   // Construction du chemin complet du fichier à écrire
	   SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()) ;
	   String laDate = formatDate.format(new Date());
	   String nomFichierTest = "FileUtilTest_write_" + laDate + ".txt";
	   String fichierTest = FilenameUtils.concat(tmpdir, nomFichierTest);
	   
	   // Ecriture d'une première ligne
	   FileWriterUtil.write("1ère ligne" + lineSeparator, fichierTest,encoding);
	   try
	   {

	      // Vérification du contenu du fichier
	      String actual = FileReaderUtil.read(fichierTest,encoding);
	      String message = "Le texte relu ne correspond pas au texte écrit";
	      String expected =  "1ère ligne" + lineSeparator;
	      assertEquals(message,expected,actual);

	      // Ecrit une 2ème ligne dans le fichier
	      FileWriterUtil.write("2ème ligne" + lineSeparator, fichierTest,encoding);

	      // Vérification du contenu du fichier
	      actual = FileReaderUtil.read(fichierTest,encoding);
	      message = "Le texte relu ne correspond pas au texte écrit";
	      expected = 
	         "1ère ligne" + lineSeparator + 
	         "2ème ligne" + lineSeparator ;
	      assertEquals(message,expected,actual);

	   }
	   finally
	   {
	      // Suppression du fichier
	      File file = new File(fichierTest);
	      file.delete();
	   }
	}
   
}
