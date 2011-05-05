package fr.urssaf.image.sae.igc.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConversionException;
import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import fr.urssaf.image.sae.igc.exception.IgcConfigException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;
import fr.urssaf.image.sae.igc.service.IgcConfigService;
import fr.urssaf.image.sae.igc.util.TextUtils;

@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class IgcConfigServiceImplTest {

   private static IgcConfigServiceImpl service;

   private static final String BASE_IGC_CONFIG = "src/test/resources/igcConfig/";

   private static final String FAIL_MESSAGE = "le test doit échouer";

   @BeforeClass
   public static void beforeClass() {

      service = new IgcConfigServiceImpl();
   }

   @Test
   public void loadConfig_success() throws IgcConfigException,
         MalformedURLException {

      String pathConfigFile = BASE_IGC_CONFIG + "igcConfig_success.xml";

      IgcConfig config = service.loadConfig(pathConfigFile);

      assertEquals("erreur sur le repertoire acRacine",
            "src\\test\\resources\\certificats\\ACRacine", config
                  .getRepertoireACRacines());
      assertEquals("erreur sur le repertoire des crls",
            "src\\test\\resources\\certificats\\CRL\\", config
                  .getRepertoireCRLs());

      URL url = new URL("http://cer69idxpkival1.cer69.recouv/*.crl");

      List<URL> urls = new ArrayList<URL>();
      urls.add(url);

      String expected = StringUtils.join(urls, ",");
      String actual = StringUtils.join(config.getUrlsTelechargementCRLs(), ",");

      assertEquals("erreur sur les urls de téléchargement", expected, actual);
   }

   @Test
   public void loadConfig_failure_urls_crl_badformat() {

      String pathConfigFile = BASE_IGC_CONFIG
            + "igcConfig_failure_urls_crl_badformat.xml";

      try {
         service.loadConfig(pathConfigFile);
         fail(FAIL_MESSAGE);
      } catch (IgcConfigException e) {

         assertEquals("erreur sur la cause de l'exception",
               ConversionException.class, e.getCause().getClass());
         assertEquals("erreur sur le message de l'exception",
               IgcConfigException.MESSAGE, e.getMessage());

         String url = "mauvaise url";
         assertTrue("erreur sur:" + url, ((ConversionException) e.getCause())
               .getMessage().contains(url));
      }

   }

   @Test
   public void loadConfig_failure_xml() {

      String pathConfigFile = BASE_IGC_CONFIG + "igcConfig_failure.properties";

      try {
         service.loadConfig(pathConfigFile);
         fail(FAIL_MESSAGE);
      } catch (IgcConfigException e) {

         assertEquals("erreur la cause de l'exception",
               SAXParseException.class, e.getCause().getCause().getClass());
      }

   }

   private static void assertLoadConfig_failure(String config, String cause,
         String... args) {

      String pathConfigFile = BASE_IGC_CONFIG + config;

      try {
         service.loadConfig(pathConfigFile);
         fail(FAIL_MESSAGE);
      } catch (IgcConfigException e) {

         assertEquals("erreur la cause de l'exception", TextUtils.getMessage(
               cause, args), e.getMessage());

      }

   }

   @Test
   public void loadConfig_failure_ACracines_notexist() {

      String config = "igcConfig_failure_ac_racines_notexist.xml";

      assertLoadConfig_failure(config, IgcConfigService.AC_RACINES_NOTEXIST,
            "\\notExist\\certificats\\ACRacine", BASE_IGC_CONFIG + config);

   }

   @Test
   public void loadConfig_failure_ACracines_required() {

      String config = "igcConfig_failure_ac_racines_required.xml";

      assertLoadConfig_failure(config, IgcConfigService.AC_RACINES_REQUIRED,
            BASE_IGC_CONFIG + config);

   }

   @Test
   public void loadConfig_failure_crls_notexist() {

      String config = "igcConfig_failure_crls_notexist.xml";

      assertLoadConfig_failure(config, IgcConfigService.CRLS_NOTEXIST,
            "\\notExist\\certificats\\CRL\\", BASE_IGC_CONFIG + config);

   }

   @Test
   public void loadConfig_failure_crls_required() {

      String config = "igcConfig_failure_crls_required.xml";

      assertLoadConfig_failure(config, IgcConfigService.CRLS_REQUIRED,
            BASE_IGC_CONFIG + config);

   }

   @Test
   public void loadConfig_failure_urls_crl_required() {

      String config = "igcConfig_failure_urls_crl_required.xml";

      assertLoadConfig_failure(config, IgcConfigService.URLS_CRL_REQUIRED,
            BASE_IGC_CONFIG + config);

   }

}
