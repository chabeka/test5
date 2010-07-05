package fr.urssaf.image.commons.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;


/**
 * Fonctions de manipulations de fichier
 */
public final class FileUtil {

   @SuppressWarnings("PMD.LongVariable")
   private static final String DEFAULT_LINE_SEPARATOR = "\n";
   
   
	private FileUtil() {

	}

	/**
	 * Ecrit une ligne à la fin d'un fichier
	 * @param text la ligne à écrire
	 * @param file le chemin du fichier
	 * @throws IOException
	 */
	public static void write(String text, String file) throws IOException {
		write(text, new File(file));
	}

	/**
	 * Ecrit une ligne à la fin d'un fichier
	 * @param text la ligne à écrire
	 * @param file le chemin du fichier
	 * @param encoding l'encodage à utiliser (par exemple : "UTF-8")
	 * @throws IOException
	 */
	public static void write(String text, String file, String encoding) throws IOException {
		write(text, new File(file), encoding);
	}

	/**
	 * Ecrit une ligne à la fin d'un fichier
	 * @param text la ligne à écrire
	 * @param file le fichier dans lequel écrire
	 * @throws IOException
	 */
	public static void write(String text, File file) throws IOException {
		write(text, file, Charset.defaultCharset().name());
	}

	
	/**
	 * Ecrit une ligne à la fin d'un fichier 
	 * @param text la ligne à écrire
	 * @param file le fichier dans lequel écrire
	 * @param encoding l'encodage à utiliser (par exemple : "UTF-8")
	 * @throws IOException
	 */
	public static void write(String text, File file, String encoding) throws IOException {

		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(file, true), encoding);
		try {
			writer.write(text);
		} finally {
			writer.close();
		}

	}

	
	/**
	 * Lit intégralement un fichier et le restitue dans une chaîne de caractères
	 * @param file le chemin du fichier à lire 
	 * @return la chaîne de caractères contenant l'ensemble du fichier
	 * @throws IOException
	 */
	public static String read(String file) throws IOException {
		return read(new File(file));
	}

	
	/**
	 * Lit intégralement un fichier et le restitue dans une chaîne de caractères
	 * @param file le chemin du fichier à lire
	 * @param encoding l'encodage à utiliser pour lire le fichier (par exemple : "UTF-8")
	 * @return la chaîne de caractères contenant l'ensemble du fichier
	 * @throws IOException
	 */
	public static String read(String file, String encoding) throws IOException {
		return read(new File(file), encoding);
	}

	
	/**
	 * Lit intégralement un fichier et le restitue dans une chaîne de caractères
	 * @param file le fichier à lire
	 * @return la chaîne de caractères contenant l'ensemble du fichier
	 * @throws IOException
	 */
	public static String read(File file) throws IOException {
		return read(file, Charset.defaultCharset().name());
	}

	
	/**
	 * Lit intégralement un fichier et le restitue dans une chaîne de caractères
	 * @param file le fichier à lire
	 * @param encoding l'encodage à utiliser pour lire le fichier (par exemple : "UTF-8")
	 * @return la chaîne de caractères contenant l'ensemble du fichier
	 * @throws IOException
	 */
	public static String read(File file, String encoding) throws IOException {
	   return read(file,encoding,DEFAULT_LINE_SEPARATOR);
	}
	
	
	/**
    * Lit intégralement un fichier et le restitue dans une chaîne de caractères
    * @param cheminFichier le chemin du fichier à lire
    * @param encoding l'encodage à utiliser pour la lecture (exemple : "UTF-8")
    * @param lineSeparator le séparateur de ligne à utiliser dans la chaîne de caractères renvoyée par
    * la méthode. Par défaut, "\n"
    * @return la chaîne de caractères contenant l'ensemble du fichier
    * @throws IOException
    */
	public static String read(String cheminFichier, String encoding, String lineSeparator) throws IOException {
	   File file = new File(cheminFichier);
	   return read(file,encoding,lineSeparator);
	}
	
	
	/**
	 * Lit intégralement un fichier et le restitue dans une chaîne de caractères
	 * @param file le fichier à lire
	 * @param encoding l'encodage à utiliser pour la lecture (exemple : "UTF-8")
	 * @param lineSeparator le séparateur de ligne à utiliser dans la chaîne de caractères renvoyée par
	 * la méthode. Par défaut, "\n"
	 * @return la chaîne de caractères contenant l'ensemble du fichier
	 * @throws IOException
	 */
	public static String read(File file, String encoding, String lineSeparator) throws IOException {

      BufferedReader buff = 
         new BufferedReader(
            new InputStreamReader(
                  new FileInputStream(file), encoding));
      StringBuffer text = new StringBuffer();
      try
      {
         String line;
         while ((line = buff.readLine()) != null)
         {
            text.append(line);
            text.append(lineSeparator);
         }
         return text.toString();
      }
      finally
      {
         buff.close();
      }
   }

}
