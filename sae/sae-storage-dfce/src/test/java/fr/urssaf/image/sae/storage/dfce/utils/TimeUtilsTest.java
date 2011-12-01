package fr.urssaf.image.sae.storage.dfce.utils;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("PMD.MethodNamingConventions")
public class TimeUtilsTest {

   @Test
   public void isValidate_success() {

      // heure à minuit
      assertTrueIsValidate("00:12:54");
      assertTrueIsValidate("0:12:54");
      assertTrueIsValidate("00:00:00");
      assertTrueIsValidate("0:00:00");

      // heure du matin
      assertTrueIsValidate("09:12:54");

      // heure du matin
      assertTrueIsValidate("9:48:45");

      // heure du soir
      assertTrueIsValidate("16:24:18");

   }

   private void assertTrueIsValidate(String time) {

      boolean isValidate = TimeUtils.isValidate(time);
      Assert.assertTrue("Le format '" + time + "' doit être au bon format",
            isValidate);
   }

   @Test
   public void isValidate_failure() {

      // chaine de caractères
      assertFalseIsValidate("09:4b:45");

      // heure > 23
      assertFalseIsValidate("24:42:45");

      // minutes > 59
      assertFalseIsValidate("09:60:45");

      // secondes > 59
      assertFalseIsValidate("09:45:60");

      // oubli des secondes
      assertFalseIsValidate("09:45");
      
      // oubli des secondes
      assertFalseIsValidate("0:0:54");
   }

   private void assertFalseIsValidate(String time) {

      boolean isValidate = TimeUtils.isValidate(time);
      Assert.assertFalse("Le format '" + time
            + "' ne doit pas être au bon format", isValidate);
   }
}
