package fr.urssaf.image.commons.util.compress;

import java.io.InputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

public class ZipCompressInputStream extends AbstractCompressInputStream<ZipArchiveInputStream>{

   protected ZipCompressInputStream(String compressFileName, String repertory) {
      super(compressFileName,repertory);
   }

//   @Override
//   public void uncompress() throws IOException {
//
//      ZipFile zip = new ZipFile(compressFileName);
//
//      Enumeration<? extends ZipEntry> entries = zip.entries();
//
//      while (entries.hasMoreElements()) {
//         
//         ZipEntry entry = entries.nextElement();
//         InputStream input = zip.getInputStream(entry);
//
//         //nom complet du fichier à decompresser
//         String name = FilenameUtils.concat(repertory,entry.getName());
//         
//         //creation du repertoire
//         FileUtils.forceMkdir(new File(FilenameUtils.getFullPath(name)));
//         
//         FileOutputStream output = new FileOutputStream(name);
//         IOUtils.copy(input, output);
//         output.close();
//      }
//
//      zip.close();
//
//   }

   @Override
   protected ZipArchiveInputStream getArchiveInputStream(InputStream inputStream) {
      return new ZipArchiveInputStream(inputStream);
   }
}
