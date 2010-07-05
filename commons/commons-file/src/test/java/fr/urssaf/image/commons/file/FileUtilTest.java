package fr.urssaf.image.commons.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import fr.urssaf.image.commons.path.PathUtil;

/**
 * Tests unitaires de la classe FileUtil
 *
 */
public class FileUtilTest {


   private final static String FILE_LECTURE = "src/test/resources/test.txt";

   private final static String CRLF_FILE_LECTURE = "\r\n";
   

	@Test
	public void read() throws IOException {
		
	   String text = FileUtil.read(FILE_LECTURE,"UTF-8",CRLF_FILE_LECTURE);
		
		String message = "La lecture du fichier a échoué";
		
		String expected = 
		   "1ère ligne du fichier" + 
		   CRLF_FILE_LECTURE +
		   "2ème ligne du fichier" + 
		   CRLF_FILE_LECTURE + 
		   "dernière ligne du fichier" +
		   CRLF_FILE_LECTURE ;
		
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
	   String fichierTest = PathUtil.combine(tmpdir, nomFichierTest);
	   
	   // Ecriture d'une première ligne
	   FileUtil.write("1ère ligne" + lineSeparator, fichierTest,encoding);
	   try
	   {

	      // Vérification du contenu du fichier
	      String actual = FileUtil.read(fichierTest,encoding,lineSeparator);
	      String message = "Le texte relu ne correspond pas au texte écrit";
	      String expected =  "1ère ligne" + lineSeparator;
	      assertEquals(message,expected,actual);

	      // Ecrit une 2ème ligne dans le fichier
	      FileUtil.write("2ème ligne" + lineSeparator, fichierTest,encoding);

	      // Vérification du contenu du fichier
	      actual = FileUtil.read(fichierTest,encoding,lineSeparator);
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
