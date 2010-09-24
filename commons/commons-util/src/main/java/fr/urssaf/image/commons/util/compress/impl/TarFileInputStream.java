package fr.urssaf.image.commons.util.compress.impl;

import java.io.InputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;


public class TarFileInputStream extends AbstractFileInputStream<TarArchiveInputStream>{

   public TarFileInputStream(String compressFileName, String repertory) {
     super(compressFileName, repertory);
    
   }

   @Override
   protected final TarArchiveInputStream getArchiveInputStream(InputStream inputStream) {
      return new TarArchiveInputStream(inputStream);
   }
   
}
