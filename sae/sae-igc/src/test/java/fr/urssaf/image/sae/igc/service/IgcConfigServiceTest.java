package fr.urssaf.image.sae.igc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.sae.igc.component.IgcConfigServiceValidate;
import fr.urssaf.image.sae.igc.exception.IgcConfigException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;
import fr.urssaf.image.sae.igc.util.TextUtils;

@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class IgcConfigServiceTest {

   private static final String FAIL_MESSAGE = "le test doit échouer";

   private IgcConfigService service;

   private IgcConfig igcConfig;

   @Before
   public void before() {

      igcConfig = new IgcConfig();
      service = new IgcConfigService() {

         @Override
         public IgcConfig loadConfig(String pathConfigFile)
               throws IgcConfigException {

            return igcConfig;
         }
      };

   }

   @Test
   public void loadConfig_success() throws IgcConfigException {

      String pathConfig = "src/test/resources/igcConfig/igcConfig_success.xml";

      assertNotNull("les arguments doivent être valide", service
            .loadConfig(pathConfig));

   }

   @Test
   public void loadConfig_failure_pathconfig_required()
         throws IgcConfigException {

      String pathConfig = " ";

      try {
         service.loadConfig(pathConfig);
         fail(FAIL_MESSAGE);
      } catch (IllegalArgumentException e) {

         assertEquals("erreur la cause de l'exception", TextUtils.getMessage(
               IgcConfigServiceValidate.ARG_EMPTY, "pathConfigFile"), e
               .getMessage());
      }

   }

   @Test
   public void loadConfig_failure_pathconfig_notexist()
         throws IgcConfigException {

      String pathConfig = "/notExist/igcConfig/igcConfig.xml";

      try {
         service.loadConfig("/notExist/igcConfig/igcConfig.xml");
         fail(FAIL_MESSAGE);
      } catch (IgcConfigException e) {

         assertEquals("erreur la cause de l'exception", TextUtils.getMessage(
               IgcConfigServiceValidate.IGC_CONFIG_NOTEXIST, pathConfig), e
               .getMessage());
      }

   }
}
