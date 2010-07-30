package fr.urssaf.image.commons.webservice.wssecurity.spring.client.generate;

import java.util.Properties;

import org.apache.velocity.texen.util.PropertiesUtil;

import fr.urssaf.image.commons.webservice.generate.GenerateSourceCxf;

public final class GenerateSource {

   private GenerateSource() {

   }

   public static void main(String[] args) {

      PropertiesUtil util = new PropertiesUtil();
      Properties prop = util.load("document.properties");

      String url = prop.getProperty("url");
      String path = prop.getProperty("path");

      GenerateSourceCxf generateSource = new GenerateSourceCxf(path, url);
      generateSource.generate();
   }

}
