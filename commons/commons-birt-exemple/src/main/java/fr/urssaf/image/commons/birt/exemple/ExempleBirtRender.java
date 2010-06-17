package fr.urssaf.image.commons.birt.exemple;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EngineException;

import fr.urssaf.image.commons.birt.BirtRender;
import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtRenderException;
import fr.urssaf.image.commons.birt.exception.MissingParamBirtRenderException;

public class ExempleBirtRender {
	/**
	 * chemin relatif au projet
	 */
	private static String _REPORT_ENGINE_PATH_ = "./src/main/resources/ReportEngine/" ;
	private static String _LOG_PATH_ = "./logs" ;
	private static String _OUTPUT_PATH_ = "./outputPath" ;
	private static String _OUTPUT_FILENAME_ = "/output-birt" ;
	private static String _REPORT_PATH_ = "./src/main/resources/reports" ;
	
	private static String _reportFileName = "monPremierRapport.rptdesign" ;
	
	/**
	 * @param args
	 * @throws BirtException 
	 * @throws MissingConstructorParamBirtRenderException 
	 */
	public static void main(String[] args) throws MissingConstructorParamBirtRenderException, BirtException {
		HashMap<Object, Object> paramValues = new HashMap<Object, Object>() ;
		paramValues.put("monParametreTitreDePage", "Titre de ma page");
		paramValues.put("CustomerNumberParam", 200);
		
		BirtRender br = new BirtRender( 
				_REPORT_ENGINE_PATH_, _LOG_PATH_, _OUTPUT_PATH_, 
				_OUTPUT_FILENAME_ );
		br.doChangeLogLevel( Level.INFO );
		br.doRender( _REPORT_PATH_ + "/" + _reportFileName, 1, paramValues );
	}

}
