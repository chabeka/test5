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


/**
 * @author CER6990172
 * Classe permettant de générer des rapports html ou pdf à partir de rapports BIRT
 */
public class BirtRender
{
	private String _report_engine_path ;
	private String _log_path ;
	private String _output_path ;
	private String _output_filename ;
	
	final public static int _MODE_PDF_ = 1 ;
	final public static int _MODE_HTML_ = 2 ; 
	final public static int _MODE_DEFAULT_ = _MODE_PDF_ ;
	
	protected Level _logLevel = Level.OFF ;
	protected IReportEngine _engine = null ;
	protected boolean _isStopped = true ;
	
	/**
	 * @param report_engine_path
	 * @param log_path
	 * @param output_path
	 * @param output_filename
	 * Démarre le moteur de rendu
	 * @throws MissingConstructorParamBirtRenderException 
	 * @throws BirtException 
	 */
	public BirtRender( String report_engine_path, String log_path, String output_path, String output_filename ) throws MissingConstructorParamBirtRenderException, BirtException{
			
		setConstructorParams( report_engine_path, log_path, output_path, output_filename );
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
	public void doRender( String reportFilePath, String outputFilename, int renderMode, Map paramValues ) throws EngineException, MissingParamBirtRenderException
	{
		String oldOutputFilename = _output_filename ;
		
		if( outputFilename != null )
			_output_filename = outputFilename ;
		else
			throw new MissingParamBirtRenderException("2ème paramètre : outputFilename") ;
		
		doRender( reportFilePath, renderMode, paramValues ) ;
		
		_output_filename = oldOutputFilename ;
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
		IReportRunnable design = _engine.openReportDesign( reportFilePath ); 
			
		//Create task to run and render the report,
		IRunAndRenderTask task = _engine.createRunAndRenderTask(design); 
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
		Boolean changeDone = false ;
		
		if( _engine != null )
		{
			_logLevel = lvl ;
			_engine.changeLogLevel( _logLevel );
			changeDone = true ;
		}
		
		return changeDone ;
	}
	
	/**
	 * Arrête le moteur de rendu
	 */
	public void stopEngine()
	{
		if( _engine != null )
		{
			_engine.destroy();
			Platform.shutdown();
			_isStopped = true ;
		}
	}
	
	/**
	 * @param renderMode
	 * @return Les options de rendu pour le moteur
	 */
	protected IRenderOption getRenderOptions( int renderMode )
	{		
		IRenderOption options = null ;
		String fileExtension = null ;
		
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
			
		options.setOutputFileName( _output_path + "\\" + _output_filename + "." + fileExtension );

		return options;		
	}
	
	/**
	 * @return la configuration du moteur
	 */
	protected EngineConfig getConfig()
	{
		final EngineConfig config = new EngineConfig( );
		config.setEngineHome( _report_engine_path );
		config.setLogConfig( _log_path, _logLevel );
		
		return config ;
	}
	
	/**
	 * @param config
	 * @throws BirtException
	 * 
	 */
	protected void setEngine( EngineConfig config ) throws BirtException
	{
		Platform.startup( config );  //If using RE API in Eclipse/RCP application this is not needed.
		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
		
		_engine = factory.createReportEngine( config );
		
		_isStopped = false ;
	}
	
	/**
	 * @param config
	 * @return the engine
	 * @throws BirtException
	 */
	protected IReportEngine getEngine( EngineConfig config ) throws BirtException
	{
		if( _engine == null )
		{
			setEngine( config );			
		}
		return _engine ;
	}
	
	/**
	 * @return the engine
	 * @throws Exception
	 */
	protected IReportEngine getEngine() throws NoEngineBirtRenderException
	{
		if( _engine == null )
		{
			throw new NoEngineBirtRenderException("Le moteur de rendu n'a pas été instancié.") ;
		}
		return _engine ;
	}
	
	/**
	 * @param report_engine_path
	 * @param log_path
	 * @param output_path
	 * @param output_filename
	 * @throws MissingConstructorParamBirtRenderException
	 * Positionne les variables par défaut envoyées aux constructeurs
	 */
	private void setConstructorParams( String report_engine_path, String log_path, String output_path, String output_filename ) throws MissingConstructorParamBirtRenderException
	{
		if( report_engine_path != null )
			_report_engine_path = report_engine_path ;
		else
			throw new MissingConstructorParamBirtRenderException("report_engine_path") ;
			
		if( log_path != null )
			_log_path = log_path ;
		else
			throw new MissingConstructorParamBirtRenderException("log_path") ;
		
		if( output_path != null )
			_output_path = output_path ;
		else
			throw new MissingConstructorParamBirtRenderException("output_path") ;
		
		if( output_filename != null )
			_output_filename = output_filename ;
		else
			throw new MissingConstructorParamBirtRenderException("output_filename") ;
	}
	
	/**
	 * Méthodes pour les tests
	 */
	public Boolean isStopped()
	{
		return _isStopped ;
	}
	
	public Level getLogLevel()
	{
		return _logLevel ;
	}
	
	public String getReportEnginePath() {
		return _report_engine_path;
	}

	public String getLogPath() {
		return _log_path;
	}

	public String getOutputPath() {
		return _output_path;
	}

	public String getOutputFilename() {
		return _output_filename;
	}

	public Level get_logLevel() {
		return _logLevel;
	}

}
