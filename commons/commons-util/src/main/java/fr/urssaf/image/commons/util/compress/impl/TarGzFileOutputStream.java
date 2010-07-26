package fr.urssaf.image.commons.util.compress.impl;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CheckedOutputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class TarGzFileOutputStream {

   private final String fileName;
   private final String path;

   private final TarOutputStream tarOutputStream;

   public TarGzFileOutputStream(String path, String fileName) {
      this.fileName = fileName;
      this.path = path;
      tarOutputStream = new TarOutputStream(fileName);
   }

   public void setExtensions(String... extensions) {
      tarOutputStream.setExtensions(extensions);
   }

   public long compress() throws IOException {

      String tar = FilenameUtils.concat(this.path, FilenameUtils
            .getBaseName(this.fileName)
            + ".tar");

      // création d'un buffer d'écriture
      ByteArrayOutputStream tarDest = new ByteArrayOutputStream();
      try {
         tarOutputStream.compress(tarDest);

         String compressFileName = FilenameUtils.concat(path, FilenameUtils
               .getName(GzipUtils.getCompressedFilename(tar)));

         // gzip du fichier
         FileOutputStream dest = new FileOutputStream(compressFileName);

         CheckedOutputStream checksum = ArchiveUtil.crc32(dest);

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
         tarDest.close();
      }

   }

}
