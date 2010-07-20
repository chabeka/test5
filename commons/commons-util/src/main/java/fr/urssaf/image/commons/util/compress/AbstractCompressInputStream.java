package fr.urssaf.image.commons.util.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public abstract class AbstractCompressInputStream<F extends ArchiveInputStream> {

   private final String compressFileName;

   private final String repertory;

   protected AbstractCompressInputStream(String compressFileName,
         String repertory) {
      this.compressFileName = compressFileName;
      this.repertory = repertory;
   }

   @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops","PMD.AssignmentInOperand"})
   public void uncompress() throws IOException {

      // création d'un flux d'écriture sur fichier
      FileInputStream inputStream = new FileInputStream(this.compressFileName);
      ArchiveInputStream archiveInput = getArchiveInputStream(inputStream);
      try {
         ArchiveEntry archiveEntry = archiveInput.getNextEntry();
         while (archiveEntry != null) {

            //traitement des fichiers compressés
            
            String name = this.getFileName(archiveEntry);

            FileUtils.forceMkdir(new File(FilenameUtils.getFullPath(name)));

            FileOutputStream output = new FileOutputStream(name);

            try {
               int byteInput = 0;
               final byte[] buffer = new byte[2048];
               while (-1 != (byteInput = archiveInput.read(buffer))) {
                  output.write(buffer, 0, byteInput);
               }
            } finally {
               output.close();
            }

            archiveEntry = archiveInput.getNextEntry();
         }
      } finally {
         archiveInput.close();
         inputStream.close();

      }

   }

   protected String getFileName(ArchiveEntry archiveEntry) {
      return FilenameUtils.concat(repertory, archiveEntry.getName());
   }

   protected abstract F getArchiveInputStream(InputStream inputStream);
}
