package fr.urssaf.image.commons.birt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;

import org.eclipse.birt.core.exception.BirtException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.birt.BirtEngine;
import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtException;
import fr.urssaf.image.commons.birt.exception.NoInstanceBirtEngineException;
import fr.urssaf.image.commons.birt.exception.NullFactoryBirtEngineException;

@SuppressWarnings({"PMD.TooManyMethods","PMD.AvoidDuplicateLiterals"})
public class BirtEngineTest {
	
	private BirtEngine birtEngine = null ;
	
	private static final String REPORTENGINE_PATH = "C:\\birt-runtime-2_5_2\\ReportEngine" ;
	private final String logsPath = System.getProperty("java.io.tmpdir") ;
		
	@Before
	public void setUp() throws Exception {
	   birtEngine = BirtEngine.getInstance( REPORTENGINE_PATH, logsPath, null ) ;
	}

	@After
	public void tearDown() throws Exception {
	
		_killBirtEngineInstance();
		
	}
	
	private void _killBirtEngineInstance()
	{
	   if( birtEngine != null )
      {
         BirtEngine.killInstance();
         birtEngine = null ;
      }
	}
	

	/**
	 * On change le niveau de log
	 */
	@Test
	public void testDoChangeLogLevel() {
		
	   Boolean success = birtEngine.doChangeLogLevel( Level.INFO );
		
		assertTrue("Le changement de niveau de log s'est mal passé", success );
		
		assertEquals(
		      "La valeur du niveau de log n'est pas celle attendu (" + 
		      Level.INFO.intValue() + 
		      " != " + 
		      birtEngine.getLogLevel().intValue() 
		      + ")", 
		      Level.INFO.intValue(),
		      birtEngine.getLogLevel().intValue() );
	}

	/**
	 * On démarre le serveur, on l'arrête et on vérifie l'état du moteur à chaque étape
	 */
	@Test
	public void testStopEngine() {
	   
		assertFalse("Le serveur ne semble pas démarré", birtEngine.isStopped()) ;
		birtEngine.stopEngine();
		assertTrue("Le serveur ne semble pas stoppé", birtEngine.isStopped()) ;
	}
	
	/**
	 * Le statut du moteur doit être démarré : isStopped == false
	 */
	@Test
	public void testIsStopped1() {
		assertFalse("Le moteur est arrété alors qu'il devrait être démarré", 
		      birtEngine.isStopped()) ;
	}
	
	/**
	 * Le statut du moteur doit être stoppé : isStopped == true
	 */
	@Test
	public void testIsStopped2() {
	   birtEngine.stopEngine();
		assertTrue("Le moteur ne semble pas stoppé", birtEngine.isStopped()) ;
	}
	
	/**
	 * Vérification des valeurs passées au constructeur
	 */
	@Test
	public void testBirtEngine() {
		assertTrue( REPORTENGINE_PATH + " attendu, " + birtEngine.getReportEnginePath() + "obtenu", 
		      birtEngine.getReportEnginePath().equals( REPORTENGINE_PATH ) ) ;
		assertTrue( logsPath + " attendu, " + birtEngine.getLogPath() + "obtenu", 
		      birtEngine.getLogPath().equals( logsPath ) );
		assertTrue( "NULL attendu", 
		      birtEngine.getServletContext() == null ) ;
		assertFalse( "Le moteur est arrété alors qu'il devrait être démarré", 
		      birtEngine.isStopped() ) ;
	}
	
	/**
    * Test du getInstance sans instance
	 * @throws NoInstanceBirtEngineException 
    */
   @Test(expected = NoInstanceBirtEngineException.class)
   public void testGetInstance1() throws NoInstanceBirtEngineException {

      _killBirtEngineInstance();
      BirtEngine.getInstance() ;
   }
   
   /**
    * Test du getInstance avec instance
    * @throws NoInstanceBirtEngineException 
    */
   @Test
   public void testGetInstance2() throws NoInstanceBirtEngineException {
      assertTrue(BirtEngine.getInstance() != null );
   }
   
   /**
    * Test du getInstance avec de mauvais paramètre
    * @throws NullFactoryBirtEngineException 
    * @throws BirtException 
    * @throws MissingConstructorParamBirtException 
    */
   @Test(expected = MissingConstructorParamBirtException.class)
   public void testGetInstance3() 
   throws MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
      
      _killBirtEngineInstance();
      BirtEngine.getInstance(null, null, null) ;
   }
   
   /**
    * Test du getInstance avec de mauvais paramètre
    * le premier paramètre ne peut être vide si le dernier est null
    * @throws NullFactoryBirtEngineException 
    * @throws BirtException 
    * @throws MissingConstructorParamBirtException 
    */
   @Test(expected = MissingConstructorParamBirtException.class)
   public void testGetInstance4() 
   throws MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
      
      _killBirtEngineInstance();
      BirtEngine.getInstance("", null, null) ;
   }
   
   /**
    * Test du getInstance avec de mauvais paramètre
    * le dernier paramètre peut etre null si le premier n'est pas vide
    * @throws NullFactoryBirtEngineException 
    * @throws BirtException 
    * @throws MissingConstructorParamBirtException 
    */
   @Test
   public void testGetInstance5() 
   throws MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
      
      _killBirtEngineInstance();
      BirtEngine.getInstance(REPORTENGINE_PATH, null, null) ;
   }

}
