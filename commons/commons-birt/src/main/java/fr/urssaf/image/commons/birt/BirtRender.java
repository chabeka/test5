package fr.urssaf.image.commons.birt ;

import java.util.Map;
import java.util.logging.Level;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtRenderException;
import fr.urssaf.image.commons.birt.exception.MissingParamBirtRenderException;
import fr.urssaf.image.commons.birt.exception.NoEngineBirtRenderException;
import fr.urssaf.image.commons.path.PathUtil;


/**
 * Classe permettant de générer des rapports html ou pdf à partir de rapports BIRT
 */
public class BirtRender
{
	private String reportEnginePath ;
	private String logPath ;
	private String outputPath ;
	private String outputFilename ;
	
	final public static int _MODE_PDF_ = 1 ;
	final public static int _MODE_HTML_ = 2 ; 
	final public static int _MODE_DEFAULT_ = _MODE_PDF_ ;
	
	protected Level logLevel = Level.OFF ;
	protected IReportEngine engine = null ;
	protected boolean stopped = true ;
	
	
	/**
	 * @param reportEnginePath
	 * @param logPath
	 * @param outputPath
	 * @param output_filename
	 * Démarre le moteur de rendu
	 * @throws MissingConstructorParamBirtRenderException 
	 * @throws BirtException 
	 */
	public BirtRender(
	      String reportEnginePath,
	      String logPath,
	      String outputPath,
	      String outputFilename )
	throws
	      MissingConstructorParamBirtRenderException,
	      BirtException{
			
		setConstructorParams( reportEnginePath, logPath, outputPath, outputFilename );
		EngineConfig config = getConfig() ;
		setEngine( config ) ;

	}
	
	/**
	 * @param reportFilePath
	 * @param outputFilename
	 * @param renderMode
	 * @param paramValues
	 * @throws EngineException
	 * @throws MissingParamBirtRenderException 
	 * Lance la génération du rapport avec un nom de destination particulier
	 */
	@SuppressWarnings("unchecked")
	public void doRender(
	      String reportFilePath,
	      String outputFilename,
	      int renderMode,
	      Map paramValues )
	throws
	      EngineException,
	      MissingParamBirtRenderException
	{
		if( outputFilename == null )
		{
		   throw new MissingParamBirtRenderException("2ème paramètre : outputFilename") ;
		}
		else
		{
		   String oldOutputFilename = this.outputFilename ;
		   
		   this.outputFilename = outputFilename ;
		   
		   doRender( reportFilePath, renderMode, paramValues ) ;
	      
	      this.outputFilename = oldOutputFilename ;
		}
	}
	
	/**
	 * @param reportFilePath
	 * @param renderMode
	 * @param paramValues
	 * @throws EngineException
	 * @throws MissingParamBirtRenderException 
	 * Lance la génération du rapport
	 */
	@SuppressWarnings("unchecked")
	public void doRender( String reportFilePath, int renderMode, Map paramValues ) throws EngineException, MissingParamBirtRenderException
	{
		if( reportFilePath == null )
		{
			throw new MissingParamBirtRenderException("reportFilePath");
		}
		
		//Open the report design
		IReportRunnable design = engine.openReportDesign( reportFilePath ); 
			
		//Create task to run and render the report,
		IRunAndRenderTask task = engine.createRunAndRenderTask(design); 
		//Set parent classloader for engine
		task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, BirtRender.class.getClassLoader()); 
			
		// set and validate parameters
		if( paramValues != null )
		{
			task.setParameterValues( paramValues );
			task.validateParameters();
		}
		
		// set render options
		IRenderOption options = getRenderOptions( renderMode ) ;
		task.setRenderOption(options);
		
		//run and render report
		task.run();
		task.close();
	}
	
	/**
	 * @param lvl
	 * @return success or failure
	 * Permet de modifier le niveau de log
	 */
	public Boolean doChangeLogLevel( Level lvl )
	{
		Boolean result ;
		
		if( engine == null )
		{
		   result = false;
		}
		else
		{
		   logLevel = lvl ;
         engine.changeLogLevel( logLevel );
         result = true ;
		}
		
		return result ;
	}
	
	/**
	 * Arrête le moteur de rendu
	 */
	public void stopEngine()
	{
		if( engine != null )
		{
			engine.destroy();
			Platform.shutdown();
			stopped = true ;
		}
	}
	
	/**
	 * @param renderMode
	 * @return Les options de rendu pour le moteur
	 */
	protected IRenderOption getRenderOptions( int renderMode )
	{		
		IRenderOption options ;
		String fileExtension ;
		
		switch( renderMode )
		{
			case _MODE_HTML_:
				//Setup rendering to HTML
				options = new HTMLRenderOption();	
				//Setting this to true removes html and body tags
				((HTMLRenderOption) options).setEmbeddable(false);
				options.setOutputFormat("html");
				fileExtension = "html" ;
				break ;
				
			case _MODE_PDF_ :
			default :
				//Setup rendering to PDF
				options = new PDFRenderOption();	
				options.setOutputFormat("pdf");
				fileExtension = "pdf" ;
		}
			
		options.setOutputFileName( PathUtil.combine(outputPath,outputFilename) + "." + fileExtension );

		return options;		
	}
	
	/**
	 * @return la configuration du moteur
	 */
	private EngineConfig getConfig()
	{
		final EngineConfig config = new EngineConfig( );
		config.setEngineHome( reportEnginePath );
		config.setLogConfig( logPath, logLevel );
		
		return config ;
	}
	
	/**
	 * @param config
	 * @throws BirtException
	 * 
	 */
	private void setEngine( EngineConfig config ) throws BirtException
	{
		Platform.startup( config );  //If using RE API in Eclipse/RCP application this is not needed.
		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
		
		engine = factory.createReportEngine( config );
		
		stopped = false ;
	}
	
	/**
	 * @param config
	 * @return the engine
	 * @throws BirtException
	 */
	protected IReportEngine getEngine( EngineConfig config ) throws BirtException
	{
		if( engine == null )
		{
			setEngine( config );			
		}
		return engine ;
	}
	
	/**
	 * @return the engine
	 * @throws Exception
	 */
	protected IReportEngine getEngine() throws NoEngineBirtRenderException
	{
		if( engine == null )
		{
			throw new NoEngineBirtRenderException("Le moteur de rendu n'a pas été instancié.") ;
		}
		return engine ;
	}
	
	/**
	 * @param reportEnginePath
	 * @param logPath
	 * @param outputPath
	 * @param outputFilename
	 * @throws MissingConstructorParamBirtRenderException
	 * Positionne les variables par défaut envoyées aux constructeurs
	 */
	private void setConstructorParams(
	      String reportEnginePath,
	      String logPath,
	      String outputPath,
	      String outputFilename )
	throws
	      MissingConstructorParamBirtRenderException
	{
		if( reportEnginePath == null )
		{
		   throw new MissingConstructorParamBirtRenderException("reportEnginePath") ;
		}
		else
		{
		   this.reportEnginePath = reportEnginePath ;
		}
			
		if( logPath == null )
		{
		   throw new MissingConstructorParamBirtRenderException("logPath") ;
		}
		else
		{
		   this.logPath = logPath ;
		}
		
		if( outputPath == null )
		{
		   throw new MissingConstructorParamBirtRenderException("outputPath") ;
		}
		else
		{
		   this.outputPath = outputPath ;
		}
		
		if( outputFilename == null )
		{
		   throw new MissingConstructorParamBirtRenderException("output_filename") ;
		}
		else
		{
		   this.outputFilename = outputFilename ;
		}
		
	}
	
	/**
	 * Méthodes pour les tests
	 */
	public Boolean isStopped()
	{
		return stopped ;
	}
	
	public Level getLogLevel()
	{
		return logLevel ;
	}
	
	public String getReportEnginePath() {
		return reportEnginePath;
	}

	public String getLogPath() {
		return logPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public String getOutputFilename() {
		return outputFilename;
	}


}
