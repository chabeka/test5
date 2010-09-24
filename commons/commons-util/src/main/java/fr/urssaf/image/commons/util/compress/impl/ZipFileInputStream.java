package fr.urssaf.image.commons.util.compress.impl;

import java.io.InputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;


public class ZipFileInputStream extends AbstractFileInputStream<ZipArchiveInputStream>{

   public ZipFileInputStream(String compressFileName, String repertory) {
      super(compressFileName,repertory);
   }

   @Override
   protected final ZipArchiveInputStream getArchiveInputStream(InputStream inputStream) {
      return new ZipArchiveInputStream(inputStream);
   }
}
