package fr.urssaf.image.commons.util.compress;

import java.io.IOException;

import fr.urssaf.image.commons.util.compress.impl.GzFileInputStream;
import fr.urssaf.image.commons.util.compress.impl.TarFileInputStream;
import fr.urssaf.image.commons.util.compress.impl.TarGzFileInputStream;
import fr.urssaf.image.commons.util.compress.impl.ZipFileInputStream;

/**
 * Cette classe permet le désarchivage de
 * <ul>
 * <li>zip</li>
 * <li>tar</li>
 * <li>tgz</li>
 * <li>gz</li>
 * </ul>
 * 
 * @author Bertrand BARAULT
 * 
 */
public final class UnCompressUtil {

   private UnCompressUtil() {

   }

   /**
    * décompression d'un fichier zip
    * 
    * @param archiveName
    *           chemin de l'archive
    * @param repertory
    *           répertoire de décompression
    * @throws IOException
    *            exception sur les fichiers
    */
   public static void unzip(String archiveName, String repertory)
         throws IOException {

      ZipFileInputStream inputStream = new ZipFileInputStream(archiveName,
            repertory);

      inputStream.uncompress();

   }

   /**
    * décompression d'un fichier zip
    * 
    * @param archiveName
    *           chemin de l'archive
    * @param repertory
    *           répertoire de décompression
    * @throws IOException
    *            exception sur les fichiers
    */
   public static void untar(String archiveName, String repertory)
         throws IOException {

      TarFileInputStream inputStream = new TarFileInputStream(archiveName,
            repertory);

      inputStream.uncompress();

   }

   /**
    * décompression d'un fichier zip
    * 
    * @param archiveName
    *           chemin de l'archive
    * @param repertory
    *           répertoire de décompression
    * @throws IOException
    *            exception sur les fichiers
    */
   public static void ungz(String archiveName, String repertory)
         throws IOException {

      GzFileInputStream inputStream = new GzFileInputStream(archiveName,
            repertory);

      inputStream.uncompress();

   }

   /**
    * décompression d'un fichier zip
    * 
    * @param archiveName
    *           chemin de l'archive
    * @param repertory
    *           répertoire de décompression
    * @throws IOException
    *            exception sur les fichiers
    */
   public static void untgz(String archiveName, String repertory)
         throws IOException {

      TarGzFileInputStream inputStream = new TarGzFileInputStream(archiveName,
            repertory);

      inputStream.uncompress();

   }
}
