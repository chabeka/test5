package fr.urssaf.image.commons.util.compress;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.io.FilenameUtils;

import fr.urssaf.image.commons.util.file.FileWriterUtil;

/**
 * Cette classe utilitaire propose l'archivage zip tar gz tgz
 * 
 * @author Bertrand BARAULT
 * 
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class CompressUtil {

   private CompressUtil() {

   }

   /**
    * compression avec zip
    * 
    * @param archiveName
    *           nom de l'archive (avec une extension .zip)
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum du zip
    * @throws IOException
    *            exception sur le fichier
    */
   public static long zip(String archiveName, String fileName)
         throws IOException {
      return zip(archiveName, fileName, new String[0]);
   }

   /**
    * compression avec zip
    * 
    * @param archiveName
    *           nom de l'archive (avec une extension .zip)
    * @param fileName
    *           nom du fichier à compresser
    * @param extensions
    *           filtre sur les extensions (text,java,xml)
    * @return checksum du zip
    * @throws IOException
    *            exception sur le fichier
    */
   public static long zip(String archiveName, final String fileName,
         String... extensions) throws IOException {

      AbstractCompressOutputStream<ZipArchiveOutputStream> outputStream = new AbstractCompressOutputStream<ZipArchiveOutputStream>(
            archiveName, fileName) {

         @Override
         public ZipArchiveOutputStream createOutputStream(
               BufferedOutputStream buff) {

            // création d'un flux d'écriture Zip
            ZipArchiveOutputStream out = new ZipArchiveOutputStream(buff);

            // spécification de la méthode de compression
            out.setMethod(ZipOutputStream.DEFLATED);

            // spécifier la qualité de la compression 0..9
            out.setLevel(Deflater.BEST_COMPRESSION);

            return out;
         }

         @Override
         public void compressFile(File file, ZipArchiveOutputStream out)
               throws IOException {

            ZipArchiveEntry entry = new ZipArchiveEntry(file, entry(fileName,
                  file));
            copy(file, out, entry);
         }

      };
      outputStream.setExtensions(extensions);
      long checksum = outputStream.compress();

      return checksum;

   }

   /**
    * compression avec tar
    * 
    * @param archiveName
    *           nom de l'archive (avec une extension .tar)
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum du tar
    * @throws IOException
    *            exception sur le fichier
    */
   public static long tar(String archiveName, final String fileName)
         throws IOException {

      return tar(archiveName, fileName, new String[0]);
   }

   /**
    * compression avec tar
    * 
    * @param archiveName
    *           nom de l'archive (avec une extension .tar)
    * @param fileName
    *           nom du fichier à compresser
    * @param extensions
    *           filtre sur les extensions (text,java,xml)
    * @return checksum du tar
    * @throws IOException
    *            exception sur le fichier
    */
   public static long tar(String archiveName, final String fileName,
         String... extensions) throws IOException {

      AbstractCompressOutputStream<TarArchiveOutputStream> outputStream = new AbstractCompressOutputStream<TarArchiveOutputStream>(
            archiveName, fileName) {

         @Override
         protected void compressFile(File file, TarArchiveOutputStream out)
               throws IOException {

            TarArchiveEntry entry = new TarArchiveEntry(file, entry(fileName,
                  file));
            copy(file, out, entry);

         }

         @Override
         protected TarArchiveOutputStream createOutputStream(
               BufferedOutputStream buff) {
            return new TarArchiveOutputStream(buff);
         }

      };
      outputStream.setExtensions(extensions);
      long checksum = outputStream.compress();

      return checksum;

   }

   /**
    * compression avec tgz
    * 
    * @param path
    *           chemin de l'archive
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum du tgz
    * @throws IOException
    *            exception sur le fichier
    */
   public static long tgz(String path, String filename) throws IOException {

      return tgz(path, filename, new String[0]);
   }

   /**
    * compression avec tgz
    * 
    * @param path
    *           chemin de l'archive
    * @param filename
    *           nom du fichier à compresser
    * @param extensions
    *           iltre sur les extensions (text,java,xml)
    * @return checksum du tgz
    * @throws IOException
    *            exception sur le fichier
    */
   public static long tgz(String path, String filename, String... extensions)
         throws IOException {

      String tar = FilenameUtils.concat(path, FilenameUtils
            .getBaseName(filename)
            + ".tar");
      tar(tar, filename, extensions);

      long checksum = gzip(path, tar);

      // suppression du fichier tar
      new File(tar).delete();

      return checksum;
   }

   /**
    * compression avec gz
    * 
    * @param path
    *           chemin de l'archive
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum du gz
    * @throws IOException
    *            exception sur le fichier
    */
   public static long gzip(String path, String fileName) throws IOException {

      String compressFileName = FilenameUtils.concat(path, FilenameUtils
            .getName(GzipUtils.getCompressedFilename(fileName)));

      AbstractCompressOutputStream<GzipCompressorOutputStream> outputStream = new AbstractCompressOutputStream<GzipCompressorOutputStream>(
            compressFileName, fileName) {

         @Override
         protected void compressFile(File file, GzipCompressorOutputStream out)
               throws IOException {
            FileWriterUtil.copy(file, out);

         }

         @Override
         protected GzipCompressorOutputStream createOutputStream(
               BufferedOutputStream buff) throws IOException {
            GzipCompressorOutputStream out = new GzipCompressorOutputStream(
                  buff);
            return out;
         }

      };

      long checksum = outputStream.compress();

      return checksum;

   }

   private static void copy(File file, ArchiveOutputStream out,
         ArchiveEntry entry) throws IOException {

      // ajout de cette entrée dans le flux d'écriture de l'archive Zip
      out.putArchiveEntry(entry);

      FileWriterUtil.copy(file, out);

      // Close the current entry
      out.closeArchiveEntry();

   }

   private static String entry(String path, File file) throws IOException {

      File pathFile = new File(path);

      String name;

      if (pathFile.isFile()) {
         name = file.getName();
      } else {

         name = file.getAbsolutePath().substring(
               FilenameUtils.getFullPath(pathFile.getAbsolutePath()).length());
      }

      return name;

   }
}
