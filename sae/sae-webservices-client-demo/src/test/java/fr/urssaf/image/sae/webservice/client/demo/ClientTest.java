package fr.urssaf.image.sae.webservice.client.demo;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

public class ClientTest {

   @Test
   public void ping_success() {

      Client.main(new String[] { "ping" });
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
