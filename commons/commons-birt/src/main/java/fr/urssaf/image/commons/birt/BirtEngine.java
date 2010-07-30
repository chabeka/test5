package fr.urssaf.image.commons.birt ;

import java.util.logging.Level;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformServletContext;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtException;
import fr.urssaf.image.commons.birt.exception.NoEngineBirtEngineException;
import fr.urssaf.image.commons.birt.exception.NoInstanceBirtEngineException;
import fr.urssaf.image.commons.birt.exception.NullConfigBirtEngineException;
import fr.urssaf.image.commons.birt.exception.NullFactoryBirtEngineException;

/**
 * Classe permettant de démarrer le serveur Birt
 */
public final class BirtEngine
{
   public static final Logger LOGGER = Logger.getLogger(BirtEngine.class) ;
   
   private static BirtEngine instance = null ;
   
   private ServletContext servletContext ;
   private String reportEnginePath ;
   private String logPath ;
   
   // protected Level logLevel = Level.OFF ;
   private Level logLevel = Level.ALL ;
   private IReportEngine engine = null ;
   private boolean stopped = true ;
   
   
   /**
    * Démarre le serveur Birt
    * @param reportEnginePath
    * @param logPath
    * @param context
    * @throws MissingConstructorParamBirtException 
    * @throws BirtException 
    * @throws NullFactoryBirtEngineException 
    */
   private BirtEngine(
         String reportEnginePath,
         String logPath,
         ServletContext context )
   throws
         MissingConstructorParamBirtException,
         BirtException, NullFactoryBirtEngineException {
         
      LOGGER.debug(String.format("reportEnginePath = %s",reportEnginePath));
      LOGGER.debug(String.format("logPath = %s",logPath));
      LOGGER.debug(String.format("ServletContext = %s",context));
      
      setConstructorParams( reportEnginePath, logPath, context );
      EngineConfig config = getConfig() ;
      startEngine( config ) ;

   }
   
   /**
    * Retourne l'instance du BirtEngine, qu'il soit démarré ou non
    * @throws NoInstanceBirtEngineException 
    */
   public static BirtEngine getInstance() 
   throws NoInstanceBirtEngineException {
      
      if( BirtEngine.instance == null )
      {
         throw new NoInstanceBirtEngineException() ;
      }
      
      return BirtEngine.instance ;
   }
   
   /**
    * Retourne l'instance existante du BirtEngine, ou en créé une nouvelle
    * @param reportEnginePath
    * @param logPath
    * @param context
    * @return l'instance du BirtEngine
    * @throws NullFactoryBirtEngineException 
    * @throws BirtException 
    * @throws MissingConstructorParamBirtException 
    */
   public static BirtEngine getInstance(
         String reportEnginePath,
         String logPath,
         ServletContext context ) 
   throws 
      MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
      
      BirtEngine returnedInstance = null ;
      
      try {
         returnedInstance = BirtEngine.getInstance() ;
      } catch ( NoInstanceBirtEngineException e ) {
         returnedInstance = BirtEngine.instance = new BirtEngine(reportEnginePath, logPath, context) ;
      }      
      
      return returnedInstance ;
   }
   
   /**
    * @param lvl
    * @return success or failure
    * Permet de modifier le niveau de log
    */
   public Boolean doChangeLogLevel( Level lvl ) {
      
      Boolean result ;
      
      if ( engine == null ) {
         result = false;
      } else {
         logLevel = lvl ;
         engine.changeLogLevel( logLevel );
         result = true ;
      }
      
      return result ;
   }
   
   /**
    * Arrête le moteur de rendu
    */
   public void stopEngine() {
      
      if ( engine != null ) {
         engine.destroy();
         Platform.shutdown();
         engine = null ; //NOPMD
         stopped = true ;
      }
   }
   
   /**
    * @return la configuration du moteur
    */
   private EngineConfig getConfig() {
      
      final EngineConfig config = new EngineConfig( );
      
      config.setEngineHome( reportEnginePath );
      LOGGER.debug(String.format("EngineConfig : reportEnginePath=%s",reportEnginePath));
      
      config.setLogConfig( logPath, logLevel );
      LOGGER.debug(String.format("EngineConfig : logPath=%s",logPath));
      LOGGER.debug(String.format("EngineConfig : logLevel=%s",logLevel));
      
      // compatibilité JavaApplication et WebApp
      if( getServletContext() == null )
      {
         LOGGER.debug("EngineConfig : Pas de servletContext");
      }
      else
      {
         LOGGER.debug("EngineConfig : Ajout du servletContext");
         config.setPlatformContext( getContext() ) ;
      }
      
      return config ;
   }
   
   /**
    * @return la configuration du moteur
    */
   private IPlatformContext getContext() {
      
      IPlatformContext context = new PlatformServletContext( servletContext );

      return context ;
   }
   
   /**
    * @param config
    * @throws BirtException
    * @throws NullFactoryBirtEngineException 
    * 
    */
   private void startEngine( EngineConfig config ) 
   throws 
      BirtException, 
      NullFactoryBirtEngineException {
      
      
      // Traces
      LOGGER.debug(String.format("EngineConfig.getBIRTHome() = %s",config.getBIRTHome()));
      LOGGER.debug(String.format("EngineConfig.getLogDirectory() = %s",config.getLogDirectory()));
      LOGGER.debug(String.format("EngineConfig.getLogFile() = %s",config.getLogFile()));
      LOGGER.debug(String.format("EngineConfig.getTempDir() = %s",config.getTempDir()));
      
      Platform.startup( config );  //If using RE API in Eclipse/RCP application this is not needed.
      IReportEngineFactory factory = (IReportEngineFactory) Platform
            .createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
      
      if( factory == null )
      {
         throw new NullFactoryBirtEngineException() ;
      }
         
      engine = factory.createReportEngine( config );
      
      stopped = false ;
   }
   
   /**
    * @param config
    * @return the engine, start it if not defined
    * @throws BirtException
    * @throws NullConfigBirtEngineException 
    * @throws NullFactoryBirtEngineException 
    */
   protected IReportEngine getEngine( EngineConfig config ) 
   throws 
      BirtException, 
      NullConfigBirtEngineException, 
      NullConfigBirtEngineException, 
      NullFactoryBirtEngineException {
      
      if( config == null )
      {
         throw new NullConfigBirtEngineException() ;
      }
      
      if( engine == null 
          && config != null ) {
         startEngine( config );       
      }
      
      return engine ;
   }
   
   /**
    * @return the engine
    * @throws NoEngineBirtEngineException
    */
   public IReportEngine getEngine() 
   throws NoEngineBirtEngineException {
      
      if ( engine == null ) {
         throw new NoEngineBirtEngineException("Le serveur Birt n'est pas démarré") ;
      }
      
      return engine ;
   }
   
   /**
    * Positionne les variables par défaut envoyées aux constructeurs
    * @param reportEnginePath
    * @param logPath
    * @throws MissingConstructorParamBirtException
    */
   private void setConstructorParams(
         String reportEnginePath,
         String logPath,
         ServletContext context )
   throws
         MissingConstructorParamBirtException {
      
      // peut être null
      this.logPath = logPath ;
      
      // reportEnginePath peut être vide mais pas null
      if ( reportEnginePath == null )
      {
         throw new MissingConstructorParamBirtException("reportEnginePath") ;
      }
      else
      {
         this.reportEnginePath = reportEnginePath ;
      }
      
      // en mode javaApplication, context peut être null SI reportEnginePath n'est pas vide
      if ( context == null 
            && ( reportEnginePath == null 
                  || reportEnginePath.isEmpty() 
               ) 
      )
      {
         throw new MissingConstructorParamBirtException("context") ;
      }
      else
      {
         this.servletContext = context ;
      }
      
   }
   
   /**
    * Supprime l'instance
    */
   protected static void removeInstance() {
      BirtEngine.instance = null ; //NOPMD
   }
   
   /**
    * @return the servletContext
    */
   public ServletContext getServletContext() {
      return servletContext;
   }

   /**
    * set the servletContext
    * @param servletContext
    */
   public void setServletContext(final ServletContext servletContext) {
      this.servletContext = servletContext;
   }
   
   /**
    * Méthodes pour les tests
    */
   public Boolean isStopped() {
      return stopped ;
   }
   
   /**
    * @return the logLevel
    */
   public Level getLogLevel() {
      return logLevel ;
   }
   
   /**
    * @return the reportEnginePath
    */
   public String getReportEnginePath() {
      return reportEnginePath;
   }

   /**
    * @return the logPath
    */
   public String getLogPath() {
      return logPath;
   }
   
   /**
    * Supprime l'instance
    */
   public static void killInstance() {
      BirtEngine.removeInstance() ;
   }
}
