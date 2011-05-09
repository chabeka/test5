package fr.urssaf.image.sae.igc.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.sae.igc.exception.IgcDownloadException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;

@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class IgcDownloadServiceImplTest {

   private IgcDownloadServiceImpl service;

   private final static String DIRECTORY;

   private static URL url_download_exist;

   static {
      DIRECTORY = FilenameUtils.concat(SystemUtils.getJavaIoTmpDir()
            .getAbsolutePath(), "certificats.download");
   }

   @BeforeClass
   public static void beforeClass() throws IOException, ConfigurationException {

      Configuration config = new PropertiesConfiguration("sae-igc.properties");

      url_download_exist = new URL(config.getString("url.download"));

      File directory = new File(FilenameUtils.concat(DIRECTORY, "CRL"));
      FileUtils.forceMkdir(directory);
      FileUtils.cleanDirectory(directory);

   }

   @Before
   public void before() {

      this.service = new IgcDownloadServiceImpl();
   }

   @Test
   public void telechargeCRLs_success() throws IgcDownloadException {

      IgcConfig igcConfig = new IgcConfig();
      igcConfig.setRepertoireCRLs(DIRECTORY + "/CRL/");

      List<URL> urls = new ArrayList<URL>();
      urls.add(url_download_exist);

      igcConfig.setUrlsTelechargementCRLs(urls);

      Integer crlsNumber = service.telechargeCRLs(igcConfig);

      assertEquals("erreur sur le nombre d'urls à télécharger", Integer
            .valueOf(15), crlsNumber);

   }

   @Ignore
   @Test(expected = IgcDownloadException.class)
   public void telechargeCRLs_failure() throws IgcDownloadException {

      IgcConfig igcConfig = new IgcConfig();
      service.telechargeCRLs(igcConfig);

   }
}
