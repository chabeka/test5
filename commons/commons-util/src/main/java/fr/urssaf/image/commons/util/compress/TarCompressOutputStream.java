package fr.urssaf.image.commons.util.compress;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

public class TarCompressOutputStream extends
      AbstractCompressOutputStream<TarArchiveOutputStream> {

   private final String fileName;

   protected TarCompressOutputStream(String compressFileName, String fileName) {
      super(compressFileName, fileName);
      this.fileName = fileName;

   }

   @Override
   protected void compressFile(File file, TarArchiveOutputStream out)
         throws IOException {

      TarArchiveEntry entry = new TarArchiveEntry(file, CompressUtil.entry(
            this.fileName, file));
      CompressUtil.copy(file, out, entry);

   }

   @Override
   protected TarArchiveOutputStream createOutputStream(BufferedOutputStream buff) {
      return new TarArchiveOutputStream(buff);
   }
}
