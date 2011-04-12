package fr.urssaf.image.sae.webservice.client.demo.service;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class PingServiceTest {

   private static PingService service;

   private static final Logger LOG = Logger.getLogger(PingServiceTest.class);

   @BeforeClass
   public static void beforeClass() {

      service = new PingService();
   }

   @Test
   public void ping_success() {

      LOG.debug(service.ping());
   }

   @Test
   public void ping_success_main() {

      PingService.main(ArrayUtils.EMPTY_STRING_ARRAY);
   }
}
