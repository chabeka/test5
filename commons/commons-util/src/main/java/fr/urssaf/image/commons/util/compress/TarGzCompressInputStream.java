package fr.urssaf.image.commons.util.compress;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class TarGzCompressInputStream {

   private final String compressFileName;
   private final String repertory;

   protected TarGzCompressInputStream(String compressFileName, String repertory) {
      this.compressFileName = compressFileName;
      this.repertory = repertory;
   }

   @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
   protected void uncompress() throws IOException {

      FileInputStream fileInput = new FileInputStream(this.compressFileName);
      GzipCompressorInputStream gzInput = new GzipCompressorInputStream(
            fileInput);
      ByteArrayOutputStream gzOut = new ByteArrayOutputStream();

      try {

         IOUtils.copy(gzInput, gzOut);

         ByteArrayInputStream input = new ByteArrayInputStream(gzOut
               .toByteArray());
         ArchiveInputStream archiveInput = new TarArchiveInputStream(input);
         try {
            ArchiveEntry archiveEntry = archiveInput.getNextEntry();
           
            while (archiveEntry != null) {

               // traitement des fichiers compressés
               String name = FilenameUtils.concat(repertory, archiveEntry.getName());
               FileUtils.forceMkdir(new File(FilenameUtils.getFullPath(name)));
               FileOutputStream output = new FileOutputStream(name);
               try {
                  IOUtils.copy(archiveInput, output);
               } finally {
                  output.close();
               }

               archiveEntry = archiveInput.getNextEntry();

            }

         } finally {
            archiveInput.close();
            input.close();

         }

      } finally {
         gzOut.close();
         gzInput.close();
         fileInput.close();
      }

   }
}
