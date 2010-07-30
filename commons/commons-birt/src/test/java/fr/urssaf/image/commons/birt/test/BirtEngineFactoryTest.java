package fr.urssaf.image.commons.birt.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.birt.core.exception.BirtException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.birt.BirtEngine;
import fr.urssaf.image.commons.birt.BirtEngineFactory;
import fr.urssaf.image.commons.birt.BirtEngineFactoryKeys;
import fr.urssaf.image.commons.birt.BirtTools;
import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtException;
import fr.urssaf.image.commons.birt.exception.MissingKeyInHashMapBirtEngineFactoryException;
import fr.urssaf.image.commons.birt.exception.NullFactoryBirtEngineException;


@SuppressWarnings({"PMD.TooManyMethods","PMD.AvoidDuplicateLiterals"})
public class BirtEngineFactoryTest {
	
   private BirtEngine birtEngine = null ;
	
	private String reportEngineHome = null ;
	private final String logsPath = System.getProperty("java.io.tmpdir") ;
	
	@Before
	public void setUp() throws Exception {

	   // setup reportEngineHome
	   reportEngineHome = BirtTools.getBirtHomeFromEnvVar() ;
	}

	@After
	public void tearDown() throws Exception {
	
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
	 * @throws NullFactoryBirtEngineException 
	 * @throws BirtException 
	 * @throws MissingConstructorParamBirtException 
	 * @throws MissingKeyInHashMapBirtEngineFactoryException 
	 */
	@Test
	public void testgetBirtEngineInstance1() 
	throws MissingKeyInHashMapBirtEngineFactoryException, 
   	MissingConstructorParamBirtException, 
   	BirtException, 
   	NullFactoryBirtEngineException {
	   
	   // Démarrage du serveur Birt
      Map<String, Object> factoryParams = new HashMap<String,Object>();
      factoryParams.put(BirtEngineFactoryKeys.REPORT_ENGINE_HOME , reportEngineHome ) ;
      factoryParams.put(BirtEngineFactoryKeys.LOG_PATH, logsPath);
      
      birtEngine = BirtEngineFactory.getBirtEngineInstance( BirtEngineFactory.JAVA_APP, factoryParams ) ;
      assertNotNull("testgetBirtEngineInstance1",birtEngine);
      
	}
	
	/**
    * @throws NullFactoryBirtEngineException 
    * @throws BirtException 
    * @throws MissingConstructorParamBirtException 
    * @throws MissingKeyInHashMapBirtEngineFactoryException 
    */
   @Test
   public void testgetBirtEngineInstance2() 
   throws MissingKeyInHashMapBirtEngineFactoryException, 
      MissingConstructorParamBirtException, 
      BirtException, 
      NullFactoryBirtEngineException {
      
      // Démarrage du serveur Birt
      Map<String, Object> factoryParams = new HashMap<String,Object>();
      factoryParams.put(BirtEngineFactoryKeys.REPORT_ENGINE_HOME , "" ) ;
      factoryParams.put(BirtEngineFactoryKeys.LOG_PATH, logsPath);
      
      try {
         birtEngine = BirtEngineFactory.getBirtEngineInstance( BirtEngineFactory.JAVA_APP, factoryParams ) ;
      } catch( MissingConstructorParamBirtException e ) {
         if( e.getMessage().contains( ": context" ) )
         {
            fail("Le message d'erreur ne devrait pas contenir ':context', mais ': reportEnginePath'") ;
         }
      }

   }

}
