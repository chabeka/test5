package fr.urssaf.image.commons.util.compress.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import fr.urssaf.image.commons.util.file.FileWriterUtil;

public class GzFileOutputStream extends AbstractFileOutputStream<GzipCompressorOutputStream>{

   public GzFileOutputStream(String compressFileName, String fileName) {
      super(compressFileName, fileName);
   }

   @Override
   protected final void compressFile(File file, GzipCompressorOutputStream out)
         throws IOException {
      FileWriterUtil.copy(file, out);

   }

   @Override
   protected final GzipCompressorOutputStream createOutputStream(
         BufferedOutputStream buff) throws IOException {
      GzipCompressorOutputStream out = new GzipCompressorOutputStream(
            buff);
      return out;
   }
}
