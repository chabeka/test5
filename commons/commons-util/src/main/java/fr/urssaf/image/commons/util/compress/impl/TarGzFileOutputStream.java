package fr.urssaf.image.commons.util.compress.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;


public class TarGzFileOutputStream {

   private final String fileName;
   private final String path;

   private String[] extensions;

   public TarGzFileOutputStream(String path, String fileName) {
      this.fileName = fileName;
      this.path = path;
   }

   public void setExtensions(String... extensions) {
      this.extensions = extensions;
   }

   @SuppressWarnings("unchecked")
   public long compress() throws IOException {

      String tar = FilenameUtils.concat(this.path, FilenameUtils
            .getBaseName(this.fileName)
            + ".tar");

      // création d'un flux d'écriture sur fichier
      TarFileOutputStream tarCompress = new TarFileOutputStream(tar,
            this.fileName);

      // création d'un buffer d'écriture
      ByteArrayOutputStream tarDest = new ByteArrayOutputStream();

      // création d'un buffer d'écriture
      BufferedOutputStream tarBuff = new BufferedOutputStream(tarDest);

      // création d'un flux d'écriture pour la compression
      TarArchiveOutputStream tarOutput = tarCompress
            .createOutputStream(tarBuff);
      try {
         // archivage en tar du fichier
         File file = new File(this.fileName);
         if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, this.extensions,
                  true);
            for (File tmpFile : files) {
               tarCompress.compressFile(tmpFile, tarOutput);
            }
         } else {
            tarCompress.compressFile(file, tarOutput);
         }

         String compressFileName = FilenameUtils.concat(path, FilenameUtils
               .getName(GzipUtils.getCompressedFilename(tar)));

         // gzip du fichier
         FileOutputStream dest = new FileOutputStream(compressFileName);

         // calcul du checksum : Adler32 (plus rapide) ou CRC32
         CheckedOutputStream checksum = new CheckedOutputStream(dest,
               new CRC32());

         // création d'un buffer d'écriture
         BufferedOutputStream buff = new BufferedOutputStream(checksum);

         // création d'un flux d'écriture pour la compression
         GzipCompressorOutputStream out = new GzipCompressorOutputStream(buff);

         try {

            // création d'un buffer d'écriture
            tarDest.writeTo(out);

         } finally {
            // fermeture du flux d'écriture
            out.close();
            buff.close();
            checksum.close();
            dest.close();
         }

         return checksum.getChecksum().getValue();

      } finally {
         // fermeture du flux d'écriture
         tarOutput.close();
         tarBuff.close();
         tarDest.close();
      }

   }
}
