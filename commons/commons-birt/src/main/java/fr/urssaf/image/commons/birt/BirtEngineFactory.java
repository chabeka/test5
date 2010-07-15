package fr.urssaf.image.commons.birt;

import java.util.HashMap;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;

import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtException;
import fr.urssaf.image.commons.birt.exception.MissingKeyInHashMapBirtEngineFactoryException;
import fr.urssaf.image.commons.birt.exception.NullFactoryBirtEngineException;

public class BirtEngineFactory {
   
   public static final Logger logger = Logger.getLogger( BirtEngineFactory.class.getName() );
   
   public static final int WEB_APP = 1 ;
   public static final int JAVA_APP = 2 ;
   
   public class key {
      public static final String REPORT_ENGINE_FACTORY = "reportEngineFactory" ;
      public static final String SERVLET_CONTEXT = "servletContext" ;
      public static final String LOG_PATH = "logPath" ;
   }
   
   public static BirtEngine getBirtEngineInstance( int appType, HashMap<String,Object> param ) 
   throws MissingKeyInHashMapBirtEngineFactoryException, 
      MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
      
      BirtEngine engine = null ;
      ServletContext servletContext = null ;
      String reportEngineHome = null ;
      String logPath = null ;
      
      logger.info("Démarrage du serveur Birt");
      
      switch ( appType ) {
         case WEB_APP :
            if ( param.containsKey( key.SERVLET_CONTEXT ) )
               servletContext = (ServletContext) param.get( key.SERVLET_CONTEXT ) ;
            else
               throw new MissingKeyInHashMapBirtEngineFactoryException( key.SERVLET_CONTEXT ) ;
               
            if ( param.containsKey( key.LOG_PATH ) )
               logPath = (String) param.get( key.LOG_PATH ) ;

            engine = getBirtEngineInstanceForWebApp( servletContext, logPath );
            break ;
         case JAVA_APP :
         default :
            if ( param.containsKey( key.REPORT_ENGINE_FACTORY ) )
               reportEngineHome = (String) param.get( key.REPORT_ENGINE_FACTORY ) ;
            else
               throw new MissingKeyInHashMapBirtEngineFactoryException( key.REPORT_ENGINE_FACTORY ) ;
               
            if ( param.containsKey( key.LOG_PATH ) )
               logPath = (String) param.get( key.LOG_PATH ) ;

            engine = getBirtEngineInstanceForJavaApp( reportEngineHome, logPath );
            break ;
      }
      
      
      logger.info("Serveur Birt démarré");
      
      return engine ;
      
   }
   
   private static BirtEngine getBirtEngineInstanceForWebApp( ServletContext servletContext, String logPath ) 
   throws MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
           
      if ( logPath != null )
         logPath = servletContext.getRealPath(logPath) ;
            
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
            
      BirtEngine engine = BirtEngine.getInstance(
            reportEngineHome, 
            logPath, 
            null ) ;
      
      return engine ;
   }

}
