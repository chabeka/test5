package fr.urssaf.image.commons.util.compress;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class ZipCompressOutputStream extends
      AbstractCompressOutputStream<ZipArchiveOutputStream> {

   private final String fileName;

   protected ZipCompressOutputStream(String compressFileName, String fileName) {
      super(compressFileName, fileName);
      this.fileName = fileName;
   }

   @Override
   public ZipArchiveOutputStream createOutputStream(BufferedOutputStream buff) {

      // création d'un flux d'écriture Zip
      ZipArchiveOutputStream out = new ZipArchiveOutputStream(buff);

      // spécification de la méthode de compression
      out.setMethod(ZipOutputStream.DEFLATED);

      // spécifier la qualité de la compression 0..9
      out.setLevel(Deflater.BEST_COMPRESSION);

      return out;
   }

   @Override
   public void compressFile(File file, ZipArchiveOutputStream out)
         throws IOException {

      ZipArchiveEntry entry = new ZipArchiveEntry(file, CompressUtil.entry(
            this.fileName, file));
      CompressUtil.copy(file, out, entry);
   }

}
