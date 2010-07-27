package fr.urssaf.image.commons.util.compress;

import java.io.File;
import java.io.IOException;

import fr.urssaf.image.commons.util.compress.impl.GzFileOutputStream;
import fr.urssaf.image.commons.util.compress.impl.TarFileOutputStream;
import fr.urssaf.image.commons.util.compress.impl.TarGzFileOutputStream;
import fr.urssaf.image.commons.util.compress.impl.ZipFileOutputStream;

/**
 * Cette classe utilitaire propose l'archivage
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
public final class CompressUtil {

   private CompressUtil() {

   }

   /**
    * compression avec zip
    * <p>
    * Equivalent
    * {@linkplain fr.urssaf.image.commons.util.compress#zip(String , String ,String[])}
    * avec <code>extensions</code> à <code>null</code>
    * </p>
    * 
    * @param zipFile
    *           nom de l'archive (avec une extension .zip), not null
    * @param fileName
    *           nom du fichier ou du répertoire à compresser, not null
    * @return checksum en crc32 du zip
    * @throws IOException
    *            exception sur le fichier
    * 
    */
   public static String zip(String zipFile, String fileName) throws IOException {
      return zip(zipFile, fileName, null);
   }

   /**
    * compression avec zip
    * 
    * <p>
    * ex: zip d'un répertoire /etc/doc en doc.zip uniquement sur les fichier
    * .txt<br>
    * <code>CompressUtil.gzip("/tmp/doc.zip", "/etc/doc",new String[]{"txt"}); </code>
    * </p>
    * 
    * 
    * @param zipFile
    *           nom de l'archive (avec une extension .zip), not null
    * @param fileName
    *           nom du fichier ou du répertoire à compresser, not null
    * @param extensions
    *           filtre sur les extensions
    * @return checksum en crc32 du zip
    * @throws IOException
    *            exception sur le fichier
    */
   public static String zip(String zipFile, String fileName, String[] extensions)
         throws IOException {

      ZipFileOutputStream outputStream = new ZipFileOutputStream(zipFile,
            fileName);
      outputStream.setExtensions(extensions);
      String checksum = outputStream.compress();

      return checksum;

   }

   /**
    * compression avec tar
    * <p>
    * Equivalent
    * {@linkplain fr.urssaf.image.commons.util.compress#tar(String , String ,String[])}
    * avec <code>extensions</code> à <code>null</code>
    * </p>
    * 
    * @param archiveName
    *           nom de l'archive (avec une extension .tar), not null
    * @param fileName
    *           nom du fichier ou du répertoire à compresser, not null
    * @return checksum en crc32 du tar
    * @throws IOException
    *            exception sur le fichier
    */
   public static String tar(String tarFile, String fileName) throws IOException {

      return tar(tarFile, fileName, null);
   }

   /**
    * compression avec tar
    * 
    * <p>
    * ex: tar d'un répertoire /etc/doc en doc.tar uniquement sur les fichier
    * .txt<br>
    * <code>CompressUtil.tar("/tmp/doc.tar", "/etc/doc",new String[]{"txt"}); </code>
    * </p>
    * 
    * @param tarFile
    *           nom de l'archive (avec une extension .tar), not null
    * @param fileName
    *           nom du fichier ou du répertoire à compresser, not null
    * @param extensions
    *           filtre sur les extensions (text,java,xml)
    * @return checksum en crc32 du tar
    * @throws IOException
    *            exception sur le fichier
    */
   public static String tar(String tarFile, String fileName, String[] extensions)
         throws IOException {

      TarFileOutputStream outputStream = new TarFileOutputStream(tarFile,
            fileName);
      outputStream.setExtensions(extensions);

      String checksum = outputStream.compress();

      return checksum;

   }

   /**
    * compression avec tgz
    * 
    * @param tgzFile
    *           nom de l'archive compressée (avec une extension .tgz)
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum en crc32 du tgz
    * @throws IOException
    *            exception sur le fichier
    */
   public static String tgz(String tgzFile, String filename) throws IOException {

      return tgz(tgzFile, filename, null);
   }

   /**
    * compression avec tgz
    * 
    * @param tgzFile
    *           nom de l'archive compressée (avec une extension .tgz)
    * @param filename
    *           nom du fichier à compresser
    * @param extensions
    *           iltre sur les extensions (text,java,xml)
    * @return checksum en crc32 du tgz
    * @throws IOException
    *            exception sur le fichier
    */
   public static String tgz(String tgzFile, String filename, String[] extensions)
         throws IOException {

      TarGzFileOutputStream outputStream = new TarGzFileOutputStream(tgzFile,
            filename);
      outputStream.setExtensions(extensions);

      String checksum = outputStream.compress();

      return checksum;

   }

   /**
    * compression avec gz
    * 
    * @param gzFile
    *           nom de l'archive (avec une extension .gz)
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum en crc32 du gz
    * @throws IOException
    *            exception sur le fichier en particulier si le fichier à
    *            compresser est un répertoire
    */
   public static String gzip(String gzFile, String fileName) throws IOException {

      File file = new File(fileName);
      if (file.isDirectory()) {
         throw new IOException(
               "Gzip n'est pas fait pour compresser un répertoire");
      }

      GzFileOutputStream outputStream = new GzFileOutputStream(gzFile, fileName);

      String checksum = outputStream.compress();

      return checksum;

   }

}
