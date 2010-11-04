package fr.urssaf.image.commons.util.net;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

public class HttpsConfigurationTest {

   private HttpsConfiguration httpsConf;

   @Before
   public void init() {
      httpsConf = new HttpsConfiguration();
   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void initDefaultSSLSocketFactoryTest() throws KeyManagementException,
         NoSuchAlgorithmException, IOException {
      httpsConf.initDefaultSSLSocketFactory();
   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void initDefaultHostnameVerifier() {
      httpsConf.initDefaultHostnameVerifier();
   }
}
