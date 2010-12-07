package fr.urssaf.image.sae.anais.framework.util;

// CHECKSTYLE:OFF

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public final class TokenCheck {

   private TokenCheck() {

   }

   public static boolean checkCTD0(String xml) throws IOException {

      return checkCTD("src/test/resources/ctd/ctd0.xml", xml);

   }

   public static boolean checkCTD1(String xml) throws IOException {

      return checkCTD("src/test/resources/ctd/ctd1.xml", xml);

   }

   public static boolean checkCTD2(String xml) throws IOException {

      return checkCTD("src/test/resources/ctd/ctd2.xml", xml);

   }

   public static boolean checkCTD(String name, String xml) throws IOException {

      File file = new File(name);
      return FileUtils.readFileToString(file, "UTF-8").equals(xml);
   }
}

//CHECKSTYLE:ON