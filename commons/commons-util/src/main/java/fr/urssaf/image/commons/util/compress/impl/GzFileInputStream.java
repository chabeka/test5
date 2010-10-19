package fr.urssaf.image.commons.util.compress.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.io.FilenameUtils;

public class GzFileInputStream {

   private final String compressFileName;
   private final String repertory;
   
   /**
    * Taille d'un buffer pour la lecture d'un fichier (en octets)
    */
   private static final int BUFFER_READ_SIZE = 2048;
   

   public GzFileInputStream(String compressFileName, String repertory) {
      this.compressFileName = compressFileName;
      this.repertory = repertory;
   }

   @SuppressWarnings("PMD.AssignmentInOperand")
   public final String uncompress() throws IOException {
      FileInputStream inputStream = new FileInputStream(this.compressFileName);
      try {

         String name = FilenameUtils.concat(
               repertory, 
               FilenameUtils.getName(GzipUtils.getUncompressedFilename(this.compressFileName)));
   
         FileOutputStream out = new FileOutputStream(name);
         try {
         
            GzipCompressorInputStream bzIn = new GzipCompressorInputStream(inputStream);
            try {
               final byte[] buffer = new byte[BUFFER_READ_SIZE];
               int byteInput = 0;
               while (-1 != (byteInput = bzIn.read(buffer))) {
                  out.write(buffer, 0, byteInput);
               }
            } finally {
               if (bzIn!=null) {
                  bzIn.close();
               }
            }
      
            return name;
            
         }
         finally {
            if (out!=null) {
               out.close();
            }
         }
         
      }
      finally {
         if (inputStream!=null) {
            inputStream.close();
         }
      }
         
   }
}
