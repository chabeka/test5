package fr.urssaf.image.commons.birt.exemple;

import java.util.HashMap;
import java.util.logging.Level;

import org.eclipse.birt.core.exception.BirtException;

import fr.urssaf.image.commons.birt.BirtRender;
import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtRenderException;

public final class ExempleBirtRender {
	
   private ExempleBirtRender()
   {
      
   }
   
   /**
	 * chemin relatif au projet
	 */
	private final static String REPORTENGINE_PATH = "./src/main/resources/ReportEngine/" ;
	private final static String LOG_PATH = "./logs" ;
	private final static String OUTPUT_PATH = "./outputPath" ;
	private final static String OUTPUT_FILENAME = "/output-birt" ;
	private final static String REPORT_PATH = "./src/main/resources/reports" ;
	
	private final static String REPORT_FILENAME = "monPremierRapport.rptdesign" ;
	
	/**
	 * @param args
	 * @throws BirtException 
	 * @throws MissingConstructorParamBirtRenderException 
	 */
	public static void main(String[] args) throws MissingConstructorParamBirtRenderException, BirtException {
		HashMap<Object, Object> paramValues = new HashMap<Object, Object>() ;
		paramValues.put("monParametreTitreDePage", "Titre de ma page");
		paramValues.put("CustomerNumberParam", 200);
		
		BirtRender birtRender = new BirtRender( 
				REPORTENGINE_PATH, 
				LOG_PATH, 
				OUTPUT_PATH, 
				OUTPUT_FILENAME );
		birtRender.doChangeLogLevel( Level.INFO );
		birtRender.doRender( REPORT_PATH + "/" + REPORT_FILENAME, 1, paramValues );
	}

}
