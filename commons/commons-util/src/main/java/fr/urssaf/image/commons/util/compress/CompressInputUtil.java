package fr.urssaf.image.commons.util.compress;

import java.io.File;
import java.io.IOException;

public final class CompressInputUtil {

   private CompressInputUtil() {

   }

   public static void unzip(String archiveName, String repertory)
         throws IOException {

      ZipCompressInputStream inputStream = new ZipCompressInputStream(
            archiveName, repertory);

      inputStream.uncompress();

   }

   public static void untar(String archiveName, String repertory)
         throws IOException {

      TarCompressInputStream inputStream = new TarCompressInputStream(
            archiveName, repertory);

      inputStream.uncompress();

   }

   public static void ungz(String archiveName, String repertory)
         throws IOException {

      GzCompressInputStream inputStream = new GzCompressInputStream(
            archiveName, repertory);

      inputStream.uncompress();

   }

   public static void untgz(String archiveName, String repertory)
         throws IOException {

      GzCompressInputStream inputStream = new GzCompressInputStream(
            archiveName, repertory);

      String tar = inputStream.uncompress();
      
      untar(tar,repertory);
      
      //suppression du fichier tar
      new File(tar).delete();

   }
}
