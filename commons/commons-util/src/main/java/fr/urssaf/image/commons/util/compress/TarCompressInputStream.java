package fr.urssaf.image.commons.util.compress;

import java.io.InputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

public class TarCompressInputStream extends AbstractCompressInputStream<TarArchiveInputStream>{

   protected TarCompressInputStream(String compressFileName, String repertory) {
     super(compressFileName, repertory);
    
   }

   @Override
   protected TarArchiveInputStream getArchiveInputStream(InputStream inputStream) {
      return new TarArchiveInputStream(inputStream);
   }
   
}
