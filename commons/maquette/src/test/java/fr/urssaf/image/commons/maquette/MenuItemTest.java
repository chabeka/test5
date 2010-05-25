package fr.urssaf.image.commons.maquette;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.maquette.exception.ReferentialIntegrityException;
import fr.urssaf.image.commons.maquette.tool.MenuItem;

public class MenuItemTest {
	public static Logger log = Logger.getLogger( MenuItemTest.class );
	
	MenuItem aParent ;
	MenuItem firstChild ;
	MenuItem secondChild ;
	
	@Before
    public void setUp(){
		aParent = new MenuItem();
		aParent.setLink("google.com");
		aParent.setTitle("google");
		
		firstChild = new MenuItem();
		firstChild.setLink("microsoft.com");
		firstChild.setTitle("microsoft");
		
		secondChild = new MenuItem();
		secondChild.setLink("microsoft.com");
		secondChild.setTitle("microsoft");
    }
 
    @After
    public void tearDown(){
        aParent = null ;
        firstChild = null ;
    }
	
	@Test
	public void simpleAddChild()
	{
		try {
			aParent.addChild( firstChild ) ;
			assertEquals( firstChild, aParent.getChildren().get(0) ) ;
			assertEquals( aParent, firstChild.getParent() ) ;
			
		} catch (ReferentialIntegrityException e) {
			e.printStackTrace();
			fail("L'ajout d'un enfant a échoué") ;
		}
	}
	
	@Test
	public void simpleAddParent()
	{
		try {
			firstChild.addParent( aParent ) ;
			assertEquals( firstChild, aParent.getChildren().get(0) ) ;
			assertEquals( aParent, firstChild.getParent() ) ;
			
		} catch (ReferentialIntegrityException e) {
			e.printStackTrace();
			fail("L'ajout d'un parent a échoué") ;
		}
	}
	
	@Test
	public void simpleSetParent()
	{
		try {
			firstChild.setParent( aParent ) ;
			assertFalse(aParent.hasChildren()) ;
			
		} catch (ReferentialIntegrityException e) {
			e.printStackTrace();
			fail("Le positionnement d'un parent a échoué") ;
		}
	}
	
	@Test
	public void twoAddChild()
	{
		try {
			MenuItem aNewChild = new MenuItem();
			aNewChild.setLink("microsoft.com");
			aNewChild.setTitle("microsoft");
			
			aParent.addChild( firstChild ) ;
			aParent.addChild( aNewChild ) ;
			
			assertEquals( firstChild, aParent.getChildren().get(0) ) ;
			assertEquals( aParent, firstChild.getParent() ) ;
			
			assertEquals( aNewChild, aParent.getChildren().get(1) ) ;
			assertEquals( aParent, aNewChild.getParent() ) ;
			
		} catch (ReferentialIntegrityException e) {
			fail("L'ajout d'un enfant a échoué") ;
		}
	}
	
	@Test
	public void existingAddChild()
	{
		try {
			aParent.addChild( firstChild ) ;
			aParent.addChild( firstChild ) ;
			
			fail("L'ajout d'un enfant aurait du échouer") ;
		} catch (ReferentialIntegrityException e) {
			
		}
	}
	
	@Test
	public void addParentToTwoChild()
	{
		try {
			firstChild.addParent( aParent ) ;
			secondChild.addParent( aParent ) ;
			
			assertEquals( firstChild, aParent.getChildren().get(0) ) ;
			assertEquals( aParent, firstChild.getParent() ) ;
			
			assertEquals( secondChild, aParent.getChildren().get(1) ) ;
			assertEquals( aParent, secondChild.getParent() ) ;
			
		} catch (ReferentialIntegrityException e) {
			e.printStackTrace();
			fail("L'ajout d'un parent a échoué") ;
		}
	}
	
	@Test
	public void addChildWhereasIsParent()
	{
		try {
			aParent.addChild( firstChild ) ;
			
			firstChild.addChild( aParent ) ;
			fail("L'ajout d'un enfant aurait du échouer car l'enfant est déjà parent") ;
		} catch (ReferentialIntegrityException e) {
		}
	}
	
	@Test
	public void addParentWhereasIsChild()
	{
		try {
			firstChild.addParent( aParent ) ;
			
			aParent.addParent( firstChild ) ;		
			fail("L'ajout d'un parent aurait du échouer car le parent est déjà enfant") ;
		} catch (ReferentialIntegrityException e) {
		}
	}
	
	@Test
	public void addSubChild()
	{
		try {
			aParent.addChild(firstChild) ;
			firstChild.addChild( secondChild );
			
			assertEquals( aParent, firstChild.getParent() ) ;
			assertEquals( firstChild, secondChild.getParent() ) ;
			
			assertEquals( firstChild, aParent.getChildren().get(0) ) ;
			assertEquals( secondChild, firstChild.getChildren().get(0) ) ;
		} catch (ReferentialIntegrityException e) {
			fail("L'ajout d'un enfant C du parent B qui est lui même enfant de A a échoué") ;
		}
	}
	
	@Test
	public void anotherTest()
	{
		List<MenuItem> listMenuItem = new ArrayList<MenuItem>();
		
		MenuItem miGoogle = new MenuItem();
		miGoogle.setLink("google.com");
		miGoogle.setTitle("google");
		
		MenuItem miGooglePortal = new MenuItem();
		miGooglePortal.setLink("igoogle.com");
		miGooglePortal.setTitle("igoogle");
		try {
			miGoogle.addChild( miGooglePortal ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miGoogleDocs = new MenuItem();
		miGoogleDocs.setLink("google.com");
		miGoogleDocs.setTitle("google docs");
		try {
			miGoogle.addChild( miGoogleDocs ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miGooglePicasa = new MenuItem();
		miGooglePicasa.setLink("picasa.google.com");
		miGooglePicasa.setTitle("picasa");
		try {
			miGoogle.addChild( miGooglePicasa ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miGoogleReader = new MenuItem();
		miGoogleReader.setLink("reader.google.com");
		miGoogleReader.setTitle("google reader");
		try {
			miGoogle.addChild( miGoogleReader ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listMenuItem.add(miGoogle);
		
		MenuItem miKrosoft = new MenuItem();
		miKrosoft.setLink("microsoft.com");
		miKrosoft.setTitle("Microsoft");
		
		MenuItem miKrosoftOS = new MenuItem();
		miKrosoftOS.setLink("windows.com");
		miKrosoftOS.setTitle("Système d'exploitation");
		try {
			miKrosoft.addChild( miKrosoftOS ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miWin95 = new MenuItem();
		miWin95.setLink("w95.com");
		miWin95.setTitle("windows 95");
		try {
			miKrosoftOS.addChild( miWin95 ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miWin98 = new MenuItem();
		miWin98.setLink("w98.com");
		miWin98.setTitle("windows 98");
		try {
			miKrosoftOS.addChild( miWin98) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miWinMe = new MenuItem();
		miWinMe.setLink("wME.com");
		miWinMe.setTitle("windows Millenium");
		try {
			miKrosoftOS.addChild( miWinMe ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miWinXP = new MenuItem();
		miWinXP.setLink("wXP.com");
		miWinXP.setTitle("windows XP");
		try {
			miKrosoftOS.addChild( miWinXP ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miWin7 = new MenuItem();
		miWin7.setLink("w7.com");
		miWin7.setTitle("windows 7");
		try {
			miKrosoftOS.addChild( miWin7 ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miKrosoftMsn = new MenuItem();
		miKrosoftMsn.setLink("msn.com");
		miKrosoftMsn.setTitle("MSN");
		try {
			miKrosoft.addChild( miKrosoftMsn ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miKrosoftHardware = new MenuItem();
		miKrosoftHardware.setLink("krosoftHardware.com");
		miKrosoftHardware.setTitle("microsoft hardware");
		try {
			miKrosoft.addChild( miKrosoftHardware ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MenuItem miKrosoftXBox = new MenuItem();
		miKrosoftXBox.setLink("xbox.com");
		miKrosoftXBox.setTitle("Xbox");
		try {
			miKrosoftHardware.addChild( miKrosoftXBox ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MenuItem miKrosoftXBox360 = new MenuItem();
		miKrosoftXBox360.setLink("xbox360.com");
		miKrosoftXBox360.setTitle("Xbox 360");
		try {
			miKrosoftHardware.addChild( miKrosoftXBox360 ) ;
		} catch (ReferentialIntegrityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listMenuItem.add(miKrosoft);
		
		MenuItem miDivers = new MenuItem();
		miDivers.setLink("euh.com");
		miDivers.setTitle("rien à voir");
		
		listMenuItem.add(miDivers);
	}
}
