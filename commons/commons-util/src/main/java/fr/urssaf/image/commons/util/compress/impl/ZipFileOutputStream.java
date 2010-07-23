package fr.urssaf.image.commons.util.compress.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.Deflater;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class ZipFileOutputStream extends
      AbstractFileOutputStream<ZipArchiveOutputStream> {

   private final String fileName;

   public ZipFileOutputStream(String compressFileName, String fileName) {
      super(compressFileName, fileName);
      this.fileName = fileName;
   }

   @Override
   protected ZipArchiveOutputStream createOutputStream(BufferedOutputStream buff) {

      // création d'un flux d'écriture Zip
      ZipArchiveOutputStream out = new ZipArchiveOutputStream(buff);

      // spécifier la qualité de la compression 0..9
      out.setLevel(Deflater.BEST_COMPRESSION);
      
      return out;
   }

   @Override
   protected void compressFile(File file, ZipArchiveOutputStream out)
         throws IOException {

      ZipArchiveEntry entry = new ZipArchiveEntry(file, ArchiveUtil.entry(
            this.fileName, file));
      ArchiveUtil.copy(file, out, entry);
   }

}
