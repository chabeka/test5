package fr.urssaf.image.commons.util.compress.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

abstract class AbstractFileOutputStream<F extends OutputStream> {

   protected static final Logger LOG = Logger
         .getLogger(AbstractFileOutputStream.class);

   private final String compressFileName;

   private final String fileName;

   private String[] extensions;

   protected AbstractFileOutputStream(String compressFileName,
         String fileName) {

      this.compressFileName = compressFileName;
      this.fileName = fileName;
   }

   public void setExtensions(String... extensions) {
      this.extensions = extensions;
   }

   @SuppressWarnings("unchecked")
   public long compress() throws IOException {

      // création d'un flux d'écriture sur fichier
      FileOutputStream dest = new FileOutputStream(this.compressFileName);

      // calcul du checksum : Adler32 (plus rapide) ou CRC32
      CheckedOutputStream checksum = new CheckedOutputStream(dest, new CRC32());

      // création d'un buffer d'écriture
      BufferedOutputStream buff = new BufferedOutputStream(checksum);

      // création d'un flux d'écriture pour la compression
      F out = createOutputStream(buff);
      try {
         // creation du fichier à compresser
         File file = new File(this.fileName);

         if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, this.extensions,
                  true);
            for (File tmpFile : files) {
               compressFile(tmpFile, out);
            }
         } else {
            compressFile(file, out);
         }
      } finally {
         // fermeture du flux d'écriture
         out.close();
         buff.close();
         checksum.close();
         dest.close();
      }

      LOG.debug("compression de " + this.fileName + " en "
            + this.compressFileName);

      return checksum.getChecksum().getValue();

   }

   protected abstract F createOutputStream(BufferedOutputStream buff)
         throws IOException;

   protected abstract void compressFile(File file, F out) throws IOException;

}
