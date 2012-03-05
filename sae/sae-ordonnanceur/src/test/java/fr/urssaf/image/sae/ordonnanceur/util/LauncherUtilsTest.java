package fr.urssaf.image.sae.ordonnanceur.util;

import org.junit.Test;

@SuppressWarnings("PMD.MethodNamingConventions")
public class LauncherUtilsTest {

   private Process process;

   public void after() {

      if (process != null) {

         process.destroy();
      }
   }

   @Test
   public void launch_failure_notexist_executable() {

      process = LauncherUtils.launch("java -jar exemple.jar");

   }
}
