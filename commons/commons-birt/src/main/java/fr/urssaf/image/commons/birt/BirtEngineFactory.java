package fr.urssaf.image.commons.birt;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;

import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtException;
import fr.urssaf.image.commons.birt.exception.MissingKeyInHashMapBirtEngineFactoryException;
import fr.urssaf.image.commons.birt.exception.NullFactoryBirtEngineException;

public final class BirtEngineFactory {
   
   public static final Logger LOGGER = Logger.getLogger( BirtEngineFactory.class.getName() );
   
   public static final int WEB_APP = 1 ;
   public static final int JAVA_APP = 2 ;
   
   private BirtEngineFactory()
   {}
   
   public static BirtEngine getBirtEngineInstance( int appType, Map<String,Object> param ) 
   throws MissingKeyInHashMapBirtEngineFactoryException, 
      MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
      
      BirtEngine engine = null ;
      ServletContext servletContext = null ;
      String reportEngineHome = null ;
      String logPath = null ;
      
      LOGGER.debug("Démarrage du serveur Birt");
      
      switch ( appType ) {
         case WEB_APP :
            LOGGER.debug("Mode Web");
            if ( param.containsKey( BirtEngineFactoryKeys.SERVLET_CONTEXT ) )
            {
               servletContext = (ServletContext) param.get( BirtEngineFactoryKeys.SERVLET_CONTEXT ) ;
            }
            else
            {
               throw new MissingKeyInHashMapBirtEngineFactoryException( BirtEngineFactoryKeys.SERVLET_CONTEXT ) ;
            }
               
            if ( param.containsKey( BirtEngineFactoryKeys.LOG_PATH ) )
            {
               logPath = (String) param.get( BirtEngineFactoryKeys.LOG_PATH ) ;
            }

            LOGGER.debug(String.format("logPath = %s",logPath));
            engine = getBirtEngineInstanceForWebApp( servletContext, logPath );
            break ;
         case JAVA_APP :
         default :
            LOGGER.debug("Mode Java App");
            if ( param.containsKey( BirtEngineFactoryKeys.REPORT_ENGINE_HOME ) )
            {
               reportEngineHome = (String) param.get( BirtEngineFactoryKeys.REPORT_ENGINE_HOME ) ;
            }
            else
            {
               throw new MissingKeyInHashMapBirtEngineFactoryException( BirtEngineFactoryKeys.REPORT_ENGINE_HOME ) ;
            }
               
            if ( param.containsKey( BirtEngineFactoryKeys.LOG_PATH ) )
            {
               logPath = (String) param.get( BirtEngineFactoryKeys.LOG_PATH ) ;
            }

            engine = getBirtEngineInstanceForJavaApp( reportEngineHome, logPath );
            break ;
      }
      
      
      LOGGER.debug("Serveur Birt démarré");
      
      return engine ;
      
   }
   
   private static BirtEngine getBirtEngineInstanceForWebApp( ServletContext servletContext, String logPath ) 
   throws MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
        
      LOGGER.debug(String.format("logPath = %s",logPath));
      
      BirtEngine engine = BirtEngine.getInstance(
            "", 
            logPath, 
            servletContext ) ;
      
      return engine ;
   }
   
   private static BirtEngine getBirtEngineInstanceForJavaApp( String reportEngineHome, String logPath ) 
   throws MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
            
      BirtEngine engine = null ;
      
      try {
         engine = BirtEngine.getInstance(
               reportEngineHome, 
               logPath, 
               null );
      } catch (MissingConstructorParamBirtException e) {
         // hcangement du message s'il n'est pas cohérent avec l'utilisation de la factory
         if ( e.getMessage().contains(": context") )
         {
            throw new MissingConstructorParamBirtException("reportEnginePath",e);
         }
         LOGGER.error(e.getMessage());
         LOGGER.fatal(e);
      }
      
      return engine ;
   }

}
