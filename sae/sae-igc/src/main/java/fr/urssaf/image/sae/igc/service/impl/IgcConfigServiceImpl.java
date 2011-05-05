package fr.urssaf.image.sae.igc.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertyConverter;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

import fr.urssaf.image.sae.igc.exception.IgcConfigException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;
import fr.urssaf.image.sae.igc.service.IgcConfigService;
import fr.urssaf.image.sae.igc.util.FileUtils;
import fr.urssaf.image.sae.igc.util.TextUtils;

/**
 * classe d'implémentation de {@link IgcConfigService}
 * 
 * 
 */
public class IgcConfigServiceImpl implements IgcConfigService {

   @Override
   public final IgcConfig loadConfig(String pathConfigFile)
         throws IgcConfigException {

      try {

         XMLConfiguration config = new XMLConfiguration(pathConfigFile);
         IgcConfig igcConfig = this.loadConfig(config);
         this.validate(igcConfig, config.getFileName());

         return igcConfig;
      } catch (ConversionException e) {
         throw new IgcConfigException(e);
      } catch (ConfigurationException e) {
         throw new IgcConfigException(e);
      }

   }

   private void validate(IgcConfig igcConfig, String pathConfig)
         throws IgcConfigException {

      if (!StringUtils.isNotBlank(igcConfig.getRepertoireACRacines())) {

         throw new IgcConfigException(TextUtils.getMessage(AC_RACINES_REQUIRED,
               pathConfig));
      }

      if (!FileUtils.isDirectory(igcConfig.getRepertoireACRacines())) {

         throw new IgcConfigException(TextUtils.getMessage(AC_RACINES_NOTEXIST,
               igcConfig.getRepertoireACRacines(), pathConfig));
      }

      if (!StringUtils.isNotBlank(igcConfig.getRepertoireCRLs())) {
         throw new IgcConfigException(TextUtils.getMessage(CRLS_REQUIRED,
               pathConfig));
      }

      if (!FileUtils.isDirectory(igcConfig.getRepertoireCRLs())) {

         throw new IgcConfigException(TextUtils.getMessage(CRLS_NOTEXIST,
               igcConfig.getRepertoireCRLs(), pathConfig));
      }

      if (CollectionUtils.isEmpty(igcConfig.getUrlsTelechargementCRLs())) {

         throw new IgcConfigException(TextUtils.getMessage(URLS_CRL_REQUIRED,
               pathConfig));
      }
   }

   private IgcConfig loadConfig(XMLConfiguration config) {

      IgcConfig igcConfig = new IgcConfig();

      String acRacines = config.getString("repertoireACRacines");
      igcConfig.setRepertoireACRacines(acRacines);
      String crlsRepertory = config.getString("repertoireCRL");
      igcConfig.setRepertoireCRLs(crlsRepertory);

      List<URL> urls = new ArrayList<URL>();

      for (Object value : config.getList("URLTelechargementCRL.url")) {

         URL url = PropertyConverter.toURL(value);
         urls.add(url);

      }

      igcConfig.setUrlsTelechargementCRLs(urls);

      return igcConfig;

   }
}
