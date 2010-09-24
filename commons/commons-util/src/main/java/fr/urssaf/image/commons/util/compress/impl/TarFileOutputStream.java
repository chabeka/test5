package fr.urssaf.image.commons.util.compress.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

public class TarFileOutputStream extends
      AbstractFileOutputStream<TarArchiveOutputStream> {

   private final TarOutputStream tarOutputStream;

   public TarFileOutputStream(String compressFileName, String fileName) {
      super(compressFileName, fileName);
      tarOutputStream = new TarOutputStream(fileName);
   }

   @Override
   protected final void compressFile(File file, TarArchiveOutputStream out)
         throws IOException {

      tarOutputStream.compressFile(file, out);

   }

   @Override
   protected final TarArchiveOutputStream createOutputStream(BufferedOutputStream buff) {
      return tarOutputStream.createOutputStream(buff);
   }
}
