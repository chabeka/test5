package fr.urssaf.image.commons.util.file.compress;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.commons.util.checksum.ChecksumFileUtil;
import fr.urssaf.image.commons.util.compress.UnCompressUtil;

/**
 * les fichiers de compression sont placé dans le repertoire archive du
 * répertoire temporaire si il n'existe pas il est créé
 * 
 * @author Bertrand BARAULT
 * 
 */
public class UnCompressUtilTest {

   private final static String REPERTORY;

   private final static String COMPRESSION = "src\\test\\resources\\compress\\compression";

   private final static String ARCHIVE = "src\\test\\resources\\compress\\archive";

   static {
      REPERTORY = FilenameUtils.concat(SystemUtils.getJavaIoTmpDir()
            .getAbsolutePath(), "archives_decompression");
   }

   @BeforeClass
   public static void initClass() throws IOException {

      File repertory = new File(REPERTORY);
      FileUtils.forceMkdir(repertory);

   }

   @Before
   public void initTest() throws IOException {
      File repertory = new File(REPERTORY);
      FileUtils.cleanDirectory(repertory);
   }

   @Test
   public void unzipDirectory() throws IOException {

      UnCompressUtil.unzip(archiveFile("dir_zip.zip"), REPERTORY);
      assertExistsArchive();

      assertChecksum("échec sur misérables.txt", "victor_hugo//misérables.txt");
   }

   @Test
   public void untzgDirectory() throws IOException {

      UnCompressUtil.untgz(archiveFile("archive.tgz"), REPERTORY);
      assertExistsArchive();

      String checksum = ChecksumFileUtil.crc32(FilenameUtils.concat(REPERTORY,
            "archive//victor_hugo//mis￩rables.txt"));

      assertEquals("échec sur misérables.txt",
            ChecksumFileUtil.crc32(FilenameUtils.concat(ARCHIVE,
                  "victor_hugo//misérables.txt")), checksum);
   }

   @Test
   public void untarDirectory() throws IOException {

      UnCompressUtil.untar(archiveFile("dir_tar.tar"), REPERTORY);
      assertExistsArchive();

      String checksum = ChecksumFileUtil.crc32(FilenameUtils.concat(REPERTORY,
            "archive//victor_hugo//mis￩rables.txt"));

      assertEquals("échec sur misérables.txt",
            ChecksumFileUtil.crc32(FilenameUtils.concat(ARCHIVE,
                  "victor_hugo//misérables.txt")), checksum);
   }

   private void assertExistsArchive() throws IOException {
      assertChecksum("échec sur archive_1.txt", "archive_1.txt");
      assertChecksum("échec sur archive_2.txt", "archive_2.txt");
      assertChecksum("échec sur memoires_outre_tombe.txt",
            "chateaubriand//memoires_outre_tombe.txt");
      assertChecksum("échec sur contemplations.txt",
            "victor_hugo//contemplations.txt");

   }

   private void assertChecksum(String libelle, String file) throws IOException {

      String checksum = ChecksumFileUtil.crc32(FilenameUtils.concat(REPERTORY,
            "archive//" + file));

      assertEquals(libelle, ChecksumFileUtil.crc32(FilenameUtils.concat(
            ARCHIVE, file)), checksum);
   }

   private String archiveFile(String filename) {
      return FilenameUtils.concat(COMPRESSION, filename);
   }

}
