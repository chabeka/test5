package fr.urssaf.image.commons.util.file.compress;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.util.compress.CompressUtil;

public class CompressUtilTest {

   private final static String DIRECTORY = SystemUtils.getJavaIoTmpDir()
         .getAbsolutePath();

   private final static String DIRECTORY_TEST = "src/test/resources/archive";

   private final static String FILE_TEST = "src/test/resources/archive/archive_1.txt";

   private static final Logger LOG = Logger.getLogger(CompressUtilTest.class);

   @Test
   public void zipDirectory() throws IOException {

      long checksum = CompressUtil.zip(archiveFile("dir_zip.zip"),
            DIRECTORY_TEST);
      
      LOG.debug("zip de "+DIRECTORY_TEST+":" + checksum);
      assertEquals("échec du zip "+DIRECTORY_TEST,370589373,checksum);

   }

   @Test
   public void zipFile() throws IOException {

      long checksum = CompressUtil.zip(archiveFile("file_zip.zip"), FILE_TEST);
      
      LOG.debug("zip de "+FILE_TEST+":" + checksum);
      assertEquals("échec du zip "+FILE_TEST,666617938,checksum);

   }

   @Test
   public void gzipFile() throws IOException {

      long checksum = CompressUtil.gzip(DIRECTORY, FILE_TEST);
      LOG.debug("gzip de "+FILE_TEST+":" + checksum);
      
      assertEquals("échec du gzip "+FILE_TEST,915649258,checksum);

   }

   @Test
   public void tgz() throws IOException {

      long checksum = CompressUtil.tgz(DIRECTORY, DIRECTORY_TEST);
      LOG.debug("gzip de "+DIRECTORY_TEST+":" + checksum);
      
      assertEquals("échec du gzip "+DIRECTORY_TEST,48803951,checksum);

   }

   @Test
   public void tarFile() throws IOException {

      long checksum = CompressUtil.tar(archiveFile("file_tar.tar"), FILE_TEST);
      LOG.debug("tar de "+FILE_TEST+":" + checksum);
      
      assertEquals("échec du tar "+FILE_TEST,70380185,checksum);

   }

   @Test
   public void tarDirectory() throws IOException {

      long checksum = CompressUtil.tar(archiveFile("dir_tar.tar"),
            DIRECTORY_TEST);
      LOG.debug("tar "+DIRECTORY_TEST+":" + checksum);
      
      assertEquals("échec du tar "+DIRECTORY_TEST,"2592367441",Long.toString(checksum));

   }

   private String archiveFile(String filename) {
      return FilenameUtils.concat(DIRECTORY, filename);
   }
}
