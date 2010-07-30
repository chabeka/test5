package fr.urssaf.image.commons.birt.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EngineException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.birt.BirtEngine;
import fr.urssaf.image.commons.birt.BirtRender;
import fr.urssaf.image.commons.birt.BirtTools;
import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtException;
import fr.urssaf.image.commons.birt.exception.MissingParamBirtRenderException;
import fr.urssaf.image.commons.birt.exception.NoEngineBirtEngineException;
import fr.urssaf.image.commons.birt.exception.NoInstanceBirtEngineException;

@SuppressWarnings({"PMD.TooManyMethods","PMD.AvoidDuplicateLiterals"})
public class BirtRenderTest {
	
   private BirtEngine birtEngine = null ;
   private BirtRender birtRender = null ;
	
   private final String logsPath = System.getProperty("java.io.tmpdir") ;
   private final String outputPath = System.getProperty("java.io.tmpdir") ;
   
	private static String reportEngineHome = null ;
	
	private static final String OUTPUT_FILENAME = "monRapportGenereEn" ;
	private static final String REPORT_FILE_PATH = "./src/test/resources/reports/monPremierRapport.rptdesign" ;
	private static final String EXTENSION_PDF = ".pdf";
	private static final String EXTENSION_HTML = ".html";
	
	private static Map<Object, Object> paramValues ;
	
	@Before
	public void setUp() throws Exception {	   
	   
	   // setup reportEngineHome
      reportEngineHome = BirtTools.getBirtHomeFromEnvVar() ;
	   
	   birtEngine = BirtEngine.getInstance( reportEngineHome, logsPath, null ) ;
	   birtRender = new BirtRender(outputPath, OUTPUT_FILENAME);
	   
	   paramValues = new HashMap<Object, Object>() ;
      paramValues.put("monParametreTitreDePage", "Titre de ma page - Test unitaire");
      paramValues.put("CustomerNumberParam", 200);
	}

	@After
	public void tearDown() throws Exception {
	   
		File fPdf = new File( FilenameUtils.concat(outputPath, OUTPUT_FILENAME) + EXTENSION_PDF );
		if ( fPdf.exists() ) { 
			fPdf.delete();
		}
		
		File fHtml = new File( FilenameUtils.concat(outputPath,OUTPUT_FILENAME) + EXTENSION_HTML );
		if ( fHtml.exists() ) {
			fHtml.delete();
		}
		
		if( birtRender != null )
		{
		   birtRender = null ; //NOPMD
		}
		
      killBirtEngineInstance();
      
   }
   
   private void killBirtEngineInstance()
   {
      if( birtEngine != null )
      {
         BirtEngine.killInstance();
         birtEngine = null ; //NOPMD
      }
   }

	/**
    * Vérification des valeurs passées au constructeur
    */
   @Test
   public void testBirtRender() {
      assertTrue( outputPath + " attendu, " + birtRender.getOutputPath() + "obtenu", 
            birtRender.getOutputPath().equals( outputPath ) ) ;
      assertTrue( OUTPUT_FILENAME + " attendu, " + birtRender.getOutputFilename() + "obtenu", 
            birtRender.getOutputFilename().equals( OUTPUT_FILENAME ) );
      assertTrue( OUTPUT_FILENAME + " attendu, " + birtRender.getDefaultOutputFilename() + "obtenu", 
            birtRender.getDefaultOutputFilename().equals( OUTPUT_FILENAME ) );
   }
   
	/**
	 * On démarre le moteur de rendu alors que le serveur est arrété
	 * @throws BirtException 
	 * @throws MissingConstructorParamBirtException 
	 * @throws NoInstanceBirtEngineException 
	 * @throws NoEngineBirtEngineException 
	 */
	@Test(expected = NoEngineBirtEngineException.class )
	public void testNewRenderWhenEngineIsStopped() 
	throws MissingConstructorParamBirtException, 
	   BirtException, 
	   NoEngineBirtEngineException, 
	   NoInstanceBirtEngineException {
	   
		assertFalse("Le serveur ne semble pas démarré", birtEngine.isStopped()) ;
		birtEngine.stopEngine();
		assertTrue("Le serveur ne semble pas stoppé", birtEngine.isStopped()) ;
      
		@SuppressWarnings("unused")
      BirtRender newBirtRender = new BirtRender(outputPath, OUTPUT_FILENAME);
	}
	
	/**
	 * On arrête le serveur Birt puis on appelle la méthode doRender
	 * @throws NoEngineBirtEngineException 
	 * @throws NoInstanceBirtEngineException 
	 * @throws MissingParamBirtRenderException 
	 * @throws EngineException 
	 */
	@Test(expected = NoEngineBirtEngineException.class)
	public void testDoRenderWhenEngineIsStopped() 
	throws EngineException, 
	   MissingParamBirtRenderException, 
	   NoInstanceBirtEngineException, 
	   NoEngineBirtEngineException {
	   
	   assertFalse("Le serveur ne semble pas démarré", birtEngine.isStopped()) ;
      birtEngine.stopEngine();
      assertTrue("Le serveur ne semble pas stoppé", birtEngine.isStopped()) ;
      
      birtRender.doRender(REPORT_FILE_PATH, BirtRender._MODE_PDF_, paramValues) ;

	}
	
	/**
	 * Test de l'exception récupérée par un mauvais passage de paramètre
	 * @throws BirtException 
	 * @throws NoInstanceBirtEngineException 
	 * @throws NoEngineBirtEngineException 
	 * @throws MissingConstructorParamBirtRenderException 
	 * @throws Exception 
	 */
	@Test(expected = MissingConstructorParamBirtException.class)
   public void testBirtRender2() 
	throws MissingConstructorParamBirtException, 
	   BirtException, 
	   NoEngineBirtEngineException, 
	   NoInstanceBirtEngineException {
	   
      new BirtRender( null, null ) ;
   }
	
	/**
    * Test de l'exception récupérée par un mauvais passage de paramètre
    * @throws BirtException 
	 * @throws NoInstanceBirtEngineException 
	 * @throws NoEngineBirtEngineException 
    * @throws MissingConstructorParamBirtRenderException 
    */
   @Test(expected = MissingConstructorParamBirtException.class)
   public void testBirtRender3() 
   throws MissingConstructorParamBirtException, 
      BirtException, 
      NoEngineBirtEngineException, 
      NoInstanceBirtEngineException {
      
      new BirtRender( outputPath, null ) ;
   }
   
   /**
    * Test de l'exception récupérée par un mauvais passage de paramètre
    * @throws BirtException 
    * @throws MissingConstructorParamBirtException 
    * @throws BirtException 
    * @throws NoInstanceBirtEngineException 
    * @throws NoEngineBirtEngineException 
    * @throws MissingConstructorParamBirtRenderException 
    * @throws Exception 
    */
   @Test(expected = MissingConstructorParamBirtException.class)
   public void testBirtRender4() 
   throws MissingConstructorParamBirtException, 
      BirtException, 
      NoEngineBirtEngineException, 
      NoInstanceBirtEngineException {
      
      new BirtRender( null, OUTPUT_FILENAME ) ;
   }
	
	/**
	 * Test de la génération d'un rapport : pas d'exception et fichier pdf présent
	 * @throws MissingParamBirtRenderException 
	 * @throws EngineException 
	 * @throws NoEngineBirtEngineException 
	 * @throws NoInstanceBirtEngineException 
	 */
	@Test
	public void testDoRenderStringIntMap1() 
	throws EngineException, 
	   MissingParamBirtRenderException, 
	   NoInstanceBirtEngineException, 
	   NoEngineBirtEngineException {
			
		birtRender.doRender( REPORT_FILE_PATH, BirtRender._MODE_PDF_, paramValues );
		File file = new File( FilenameUtils.concat(outputPath,OUTPUT_FILENAME) + EXTENSION_PDF );
		assertTrue( "Le fichier pdf n'a pas été créé", file.exists() ) ;
		
	}
	
	/**
	 * Test de la génération d'un rapport : pas d'exception et fichier html présent
	 * @throws MissingParamBirtRenderException 
	 * @throws EngineException 
	 * @throws EngineException 
	 */
	@Test
	public void testDoRenderStringIntMap2() 
	throws EngineException,
      MissingParamBirtRenderException, 
      NoInstanceBirtEngineException, 
      NoEngineBirtEngineException {
        
      birtRender.doRender( REPORT_FILE_PATH, BirtRender._MODE_HTML_, paramValues );
      File file = new File( FilenameUtils.concat(outputPath,OUTPUT_FILENAME) + EXTENSION_HTML );
      assertTrue( "Le fichier pdf n'a pas été créé", file.exists() ) ;
   
	}
	
	/**
	 * Test de la génération d'un rapport : pas d'exception et fichier pdf (défaut) présent
	 * @throws MissingParamBirtRenderException 
	 * @throws EngineException 
	 * @throws NoEngineBirtEngineException 
	 * @throws NoInstanceBirtEngineException 
	 */
	@Test
	public void testDoRenderStringIntMap3() 
	throws EngineException, 
	   MissingParamBirtRenderException, 
	   NoInstanceBirtEngineException, 
	   NoEngineBirtEngineException {
		      
      birtRender.doRender( REPORT_FILE_PATH, 9999, paramValues );
      File file = new File( FilenameUtils.concat(outputPath,OUTPUT_FILENAME) + EXTENSION_PDF );
      assertTrue( "Le fichier pdf n'a pas été créé", file.exists() ) ;
		
	}

	/**
	 * Test de la génération d'un rapport : pas d'exception et fichier pdf présent
	 * @throws MissingParamBirtRenderException 
	 * @throws EngineException 
	 * @throws NoEngineBirtEngineException 
	 * @throws NoInstanceBirtEngineException 
	 */
	@Test
	public void testDoRenderStringStringIntMap1() 
	throws EngineException, 
	   MissingParamBirtRenderException, 
      NoInstanceBirtEngineException, 
      NoEngineBirtEngineException {
		
	   String filename = "TEST_NouveauFichierGenere" ;
	   
	   birtRender.doRender( REPORT_FILE_PATH, filename, BirtRender._MODE_PDF_, paramValues );
	   File file = new File( FilenameUtils.concat(outputPath,filename) + EXTENSION_PDF );
	   assertTrue( "Le fichier pdf n'a pas été créé", file.exists() ) ;
	   
	   file.delete() ; // nettoyage
		
	}
	
	/**
	 * Test de la génération d'un rapport : mauvais passage de paramètre, exception récupérée
	 * @throws MissingParamBirtRenderException 
	 * @throws EngineException 
	 * @throws NoEngineBirtEngineException 
	 * @throws NoInstanceBirtEngineException 
	 * @throws Exception 
	 */
	@Test
	public void testDoRenderStringStringIntMap2() 
	throws EngineException, 
	   MissingParamBirtRenderException, 
	   NoInstanceBirtEngineException, 
	   NoEngineBirtEngineException {

		birtRender.doRender( REPORT_FILE_PATH, null, 1, paramValues );
		File file = new File( FilenameUtils.concat(outputPath,OUTPUT_FILENAME) + EXTENSION_PDF );
      assertTrue( "Le fichier pdf n'a pas été créé", file.exists() ) ;
      
      file.delete() ; // nettoyage
	}

}
