package fr.urssaf.image.commons.birt;

import fr.urssaf.image.commons.birt.exception.EnvVarNotSettedBirtEngineException;

public final class BirtTools {
   
   private BirtTools()
   {}

   /**
    * @return BIRT_HOME environements var
    * @throws EnvVarNotSettedBirtEngineException
    */
   public static String getBirtHomeFromEnvVar() throws EnvVarNotSettedBirtEngineException
   {
      // test de REPORTENGINE_PATH, si null c'est que la variable d'environnement n'a pas été créé
      // ou qu'il faut redémarrer la JVM
      String reportEngineHome = System.getenv( getBirtHomeVarName() ) ;
      if ( reportEngineHome == null 
            || reportEngineHome.isEmpty() )
      {
         throw new EnvVarNotSettedBirtEngineException() ;
      }
      
      return reportEngineHome;
   }
   
   /**
    * @return  BIRT_HOME var name
    */
   public static String getBirtHomeVarName()
   {
      return "BIRT_HOME" ;
   }
}
