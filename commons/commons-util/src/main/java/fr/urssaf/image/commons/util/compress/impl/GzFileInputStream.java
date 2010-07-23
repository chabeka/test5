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

   public GzFileInputStream(String compressFileName, String repertory) {
      this.compressFileName = compressFileName;
      this.repertory = repertory;
   }

   @SuppressWarnings("PMD.AssignmentInOperand")
   public String uncompress() throws IOException {
      FileInputStream inputStream = new FileInputStream(this.compressFileName);

      String name = FilenameUtils.concat(repertory, FilenameUtils
            .getName(GzipUtils.getUncompressedFilename(this.compressFileName)));

      FileOutputStream out = new FileOutputStream(name);
      GzipCompressorInputStream bzIn = new GzipCompressorInputStream(inputStream);
      try {
         final byte[] buffer = new byte[2048];
         int byteInput = 0;
         while (-1 != (byteInput = bzIn.read(buffer))) {
            out.write(buffer, 0, byteInput);
         }
      } finally {
         out.close();
         bzIn.close();
      }

      return name;
   }
}
