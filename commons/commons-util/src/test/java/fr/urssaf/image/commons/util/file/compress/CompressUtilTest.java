package fr.urssaf.image.commons.util.file.compress;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.commons.util.checksum.ChecksumFileUtil;
import fr.urssaf.image.commons.util.compress.CompressUtil;

/**
 * les fichiers de compression sont placé dans le repertoire archive du
 * répertoire temporaire si il n'existe pas il est créé
 * 
 * @author Bertrand BARAULT
 * 
 */
public class CompressUtilTest {

   private final static String DIRECTORY;

   private final static String DIRECTORY_TEST = "src/test/resources/compress/archive";

   private final static String FILE_TEST = "src/test/resources/compress/archive/archive_1.txt";

   private static final Logger LOG = Logger.getLogger(CompressUtilTest.class);

   static {
      DIRECTORY = FilenameUtils.concat(SystemUtils.getJavaIoTmpDir()
            .getAbsolutePath(), "archives");
   }

   @BeforeClass
   public static void init() throws IOException {
      File directory = new File(DIRECTORY);
      FileUtils.forceMkdir(directory);

      FileUtils.cleanDirectory(directory);
   }

   @Test
   public void zipDirectory() throws IOException {

      long checksum = CompressUtil.zip(archiveFile("dir_zip.zip"),
            DIRECTORY_TEST, "txt");

      LOG.debug("zip de " + DIRECTORY_TEST + ":" + Long.toHexString(checksum));
      assertChecksum("échec du zip " + DIRECTORY_TEST, "dir_zip.zip", checksum);

   }

   @Test
   public void zipFile() throws IOException {

      long checksum = CompressUtil.zip(archiveFile("file_zip.zip"), FILE_TEST);

      LOG.debug("zip de " + FILE_TEST + ":" + Long.toHexString(checksum));
      assertChecksum("échec du zip " + FILE_TEST, "file_zip.zip", checksum);

   }

   @Test
   public void gzipFile() throws IOException {

      long checksum = CompressUtil.gzip(DIRECTORY, FILE_TEST);

      LOG.debug("gzip de " + FILE_TEST + ":" + Long.toHexString(checksum));
      assertChecksum("échec du gzip " + FILE_TEST, FilenameUtils
            .getName(FILE_TEST)
            + ".gz", checksum);

   }

   @Test
   public void tgz() throws IOException {

      long checksum = CompressUtil.tgz(DIRECTORY, DIRECTORY_TEST, "txt");

      LOG.debug("gzip de " + DIRECTORY_TEST + ":" + Long.toHexString(checksum));
      assertChecksum("échec du gzip " + DIRECTORY_TEST, FilenameUtils
            .getBaseName(DIRECTORY_TEST)
            + ".tgz", checksum);

   }

   @Test
   public void tarFile() throws IOException {

      long checksum = CompressUtil.tar(archiveFile("file_tar.tar"), FILE_TEST);

      LOG.debug("tar de " + FILE_TEST + ":" + Long.toHexString(checksum));
      assertChecksum("échec du tar " + FILE_TEST, "file_tar.tar", checksum);

   }

   @Test
   public void tarDirectory() throws IOException {

      long checksum = CompressUtil.tar(archiveFile("dir_tar.tar"),
            DIRECTORY_TEST, "txt");

      LOG.debug("tar " + DIRECTORY_TEST + ":" + Long.toHexString(checksum));
      assertChecksum("échec du tar " + DIRECTORY_TEST, "dir_tar.tar", checksum);

   }

   private void assertChecksum(String libelle, String file, long checksum)
         throws IOException {

      assertEquals(libelle, ChecksumFileUtil.crc32(archiveFile(file)), Long
            .toHexString(checksum));
   }

   private String archiveFile(String filename) {
      return FilenameUtils.concat(DIRECTORY, filename);
   }

}
