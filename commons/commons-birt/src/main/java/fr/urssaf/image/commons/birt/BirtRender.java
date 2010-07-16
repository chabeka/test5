package fr.urssaf.image.commons.birt ;

import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtException;
import fr.urssaf.image.commons.birt.exception.MissingParamBirtRenderException;
import fr.urssaf.image.commons.birt.exception.NoEngineBirtEngineException;
import fr.urssaf.image.commons.birt.exception.NoInstanceBirtEngineException;


/**
 * Classe permettant de générer des rapports html ou pdf à partir de rapports BIRT
 */
public class BirtRender
{
	private String outputPath ;
	private String defaultOutputFilename ;
	private String outputFilename ;
	
	final public static int _MODE_PDF_ = 1 ;
	final public static int _MODE_HTML_ = 2 ; 
	final public static int _MODE_DEFAULT_ = _MODE_PDF_ ;
	
	private static final Logger LOGGER = Logger.getLogger(BirtRender.class);
	
	/**
	 * Démarre le moteur de rendu
	 * @param outputPath
	 * @param output_filename
	 * @throws MissingConstructorParamBirtException 
	 * @throws BirtException 
	 * @throws NoEngineBirtEngineException 
	 * @throws NoEngineBirtEngineException 
	 * @throws NoInstanceBirtEngineException 
	 * @throws NoInstanceBirtEngineException 
	 */
	public BirtRender(
	      String outputPath,
	      String outputFilename )
	throws
	      MissingConstructorParamBirtException,
	      BirtException, 
	      NoEngineBirtEngineException, 
	      NoInstanceBirtEngineException {
			   
	   if( BirtEngine.getInstance() != null 
	      && BirtEngine.getInstance().isStopped() )
	      throw new NoEngineBirtEngineException( "Le serveur Birt a été arrété" ) ;
	   
		setConstructorParams( outputPath, outputFilename );
	}
	
	/**
	 * Lance la génération du rapport avec un nom de destination particulier
	 * @param reportFilePath
	 * @param outputFilename
	 * @param renderMode
	 * @param paramValues
	 * @throws EngineException
	 * @throws MissingParamBirtRenderException 
	 * @throws NoInstanceBirtEngineException 
	 * @throws NoEngineBirtEngineException 
	 */
	@SuppressWarnings("unchecked")
	public void doRender(
	      String reportFilePath,
	      String outputFilename,
	      int renderMode,
	      Map paramValues )
	throws
	      EngineException,
	      MissingParamBirtRenderException, 
	      NoInstanceBirtEngineException, 
	      NoEngineBirtEngineException {
	   
		if ( outputFilename == null )
		   this.outputFilename = defaultOutputFilename ;
		else
		   this.outputFilename = outputFilename ;
	   
	   doRender( reportFilePath, renderMode, paramValues ) ;
	}
	
	/**
	 * Lance la génération du rapport
	 * @param reportFilePath
	 * @param renderMode
	 * @param paramValues
	 * @throws EngineException
	 * @throws MissingParamBirtRenderException 
	 * @throws NoInstanceBirtEngineException 
	 * @throws NoEngineBirtEngineException 
	 */
	@SuppressWarnings("unchecked")
	public void doRender( 
	      String reportFilePath, 
	      int renderMode, 
	      Map paramValues ) 
	throws 
	   EngineException, 
	   MissingParamBirtRenderException, 
	   NoInstanceBirtEngineException, 
	   NoEngineBirtEngineException {
	   
	   // Trace
	   LOGGER.debug("Demande de génération d'un rapport BIRT");
	   
	   if( reportFilePath == null )
			throw new MissingParamBirtRenderException("reportFilePath");

		// Récupération du moteur depuis l'instance BirtEngine
		IReportEngine engine = BirtEngine.getInstance().getEngine() ;
		
		//Open the report design
		IReportRunnable design = engine.openReportDesign( reportFilePath ); 
			
		//Create task to run and render the report,
		IRunAndRenderTask task = engine.createRunAndRenderTask(design); 
		
		//Set parent classloader for engine
		task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, BirtRender.class.getClassLoader()); 
			
		// set and validate parameters
		if ( paramValues != null ) {
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
	 * @param renderMode
	 * @return Les options de rendu pour le moteur
	 */
	protected IRenderOption getRenderOptions( int renderMode )
	{		
		IRenderOption options ;
		String fileExtension ;
		
		switch ( renderMode ) {
			case _MODE_HTML_:
			   // Trace
		      LOGGER.debug("Format de sortie du rapport : HTML");
			   //Setup rendering to HTML
				options = new HTMLRenderOption();	
				//Setting this to true removes html and body tags
				((HTMLRenderOption) options).setEmbeddable(false);
				options.setOutputFormat("html");
				fileExtension = "html" ;
				break ;
				
			case _MODE_PDF_ :
			default :
			   // Trace
            LOGGER.debug("Format de sortie du rapport : PDF");
				//Setup rendering to PDF
				options = new PDFRenderOption();	
				options.setOutputFormat("pdf");
				fileExtension = "pdf" ;
		}
			
		String outputFileName = FilenameUtils.concat(outputPath,outputFilename) + "." + fileExtension;
		
		LOGGER.debug(String.format("Fichier à générer : %s",outputFileName));
		
		options.setOutputFileName(outputFileName);

		return options;		
	}	
	
	/**
	 * Positionne les variables par défaut envoyées aux constructeurs
	 * @param outputPath
	 * @param outputFilename
	 * @throws MissingConstructorParamBirtException
	 */
	private void setConstructorParams(
	      String outputPath,
	      String outputFilename )
	throws
	      MissingConstructorParamBirtException {		
	   
		if( outputPath == null )
		   throw new MissingConstructorParamBirtException("outputPath") ;
		else
		   this.outputPath = outputPath ;
		
		if( outputFilename == null )
		   throw new MissingConstructorParamBirtException("outputFilename") ;
		else{
	      this.defaultOutputFilename = outputFilename ;
		   this.outputFilename = outputFilename ;
		}
		
	}

	/**
	 * @return the outputPath
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * @return the outputFilename
	 */
	public String getOutputFilename() {
		return outputFilename;
	}
	
	/**
    * @return the defaultOutputFilename
    */
   public String getDefaultOutputFilename() {
      return defaultOutputFilename;
   }

}