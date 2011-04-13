package fr.urssaf.image.sae.webservice.client.demo;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class ClientTest {

   private static final Logger LOG = Logger.getLogger(ClientTest.class);

   @Test
   public void ping_success() {

      LOG.debug("ping");
      Client.main(new String[] { "ping" });
   }

   @Test
   public void pingSecure_success() {

      LOG.debug("ping secure avec ROLE TOUS");
      Client.main(new String[] { "ping_secure", "ROLE_TOUS" });
   }

   @Test
   public void pingSecure_failure() {

      LOG.debug("ping secure avec ROLE OTHER");
      Client.main(new String[] { "ping_secure", "ROLE_OTHER" });
   }

   @Test
   public void pingSecure_role_required() {

      try {
         Client.main(new String[] { "ping_secure" });
      } catch (IllegalArgumentException e) {

         assertEquals("role is required", e.getMessage());
      }
   }

   @Test
   public void action_unknown() {

      String action = "unknown";

      try {
         Client.main(new String[] { action });
      } catch (IllegalArgumentException e) {

         assertEquals("Unknown action defined: " + action, e.getMessage());
      }
   }

   @Test
   public void args_empty() {

      args_empty(null);
      args_empty(ArrayUtils.EMPTY_STRING_ARRAY);

   }

   private void args_empty(String[] args) {

      try {
         Client.main(args);
      } catch (IllegalArgumentException e) {

         assertEquals("action required", e.getMessage());
      }
   }
}
