package fr.urssaf.image.commons.maquette.exemple.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.maquette.exception.ReferentialIntegrityException;
import fr.urssaf.image.commons.maquette.tool.MenuItem;

public class ImplMenuTest {
	public static Logger log = Logger.getLogger( ImplMenuTest.class );
	
	List<MenuItem> listMenuItem ;
	
	@Before
    public void setUp() throws ReferentialIntegrityException{
		getList();   
    }
 
    @After
    public void tearDown(){
     // test.
    }

	private void getList() throws ReferentialIntegrityException
	{
		listMenuItem = new ArrayList<MenuItem>() ;
		
		MenuItem miGoogle = new MenuItem();
		miGoogle.setLink("google.com");
		miGoogle.setTitle("google");
		
		listMenuItem.add(miGoogle);
		
		MenuItem miGooglePortal = new MenuItem();
		miGooglePortal.setLink("igoogle.com");
		miGooglePortal.setTitle("igoogle");
		miGoogle.addChild( miGooglePortal ) ;
		
		MenuItem miGoogleDocs = new MenuItem();
		miGoogleDocs.setLink("google.com");
		miGoogleDocs.setTitle("google docs");
		miGoogle.addChild( miGoogleDocs ) ;
		
		MenuItem miGooglePicasa = new MenuItem();
		miGooglePicasa.setLink("picasa.google.com");
		miGooglePicasa.setTitle("picasa");
		miGoogle.addChild( miGooglePicasa ) ;
		
		MenuItem miGoogleReader = new MenuItem();
		miGoogleReader.setLink("reader.google.com");
		miGoogleReader.setTitle("google reader");
		miGoogle.addChild( miGoogleReader ) ;
				
		MenuItem miKrosoft = new MenuItem();
		miKrosoft.setLink("microsoft.com");
		miKrosoft.setTitle("Microsoft");
		
		listMenuItem.add(miKrosoft);
		
		MenuItem miKrosoftOS = new MenuItem();
		miKrosoftOS.setLink("windows.com");
		miKrosoftOS.setTitle("Système d'exploitation");
		miKrosoft.addChild( miKrosoftOS ) ;
		
		MenuItem miWin95 = new MenuItem();
		miWin95.setLink("w95.com");
		miWin95.setTitle("windows 95");
		miKrosoftOS.addChild( miWin95 ) ;
		
		MenuItem miWin98 = new MenuItem();
		miWin98.setLink("w98.com");
		miWin98.setTitle("windows 98");
		miKrosoftOS.addChild( miWin98 ) ;
		
		MenuItem miWinMe = new MenuItem();
		miWinMe.setLink("wME.com");
		miWinMe.setTitle("windows Millenium");
		miKrosoftOS.addChild( miWinMe ) ;
		
		MenuItem miWinXP = new MenuItem();
		miWinXP.setLink("wXP.com");
		miWinXP.setTitle("windows XP");
		miKrosoftOS.addChild( miWinXP ) ;
		
		MenuItem miWin7 = new MenuItem();
		miWin7.setLink("w7.com");
		miWin7.setTitle("windows 7");
		miKrosoftOS.addChild( miWin7 ) ;
		
		MenuItem miKrosoftMsn = new MenuItem();
		miKrosoftMsn.setLink("msn.com");
		miKrosoftMsn.setTitle("MSN");
		miKrosoft.addChild( miKrosoftMsn ) ;
		
		MenuItem miKrosoftHardware = new MenuItem();
		miKrosoftHardware.setLink("krosoftHardware.com");
		miKrosoftHardware.setTitle("microsoft hardware");
		miKrosoft.addChild( miKrosoftHardware ) ;

		MenuItem miKrosoftXBox = new MenuItem();
		miKrosoftXBox.setLink("xbox.com");
		miKrosoftXBox.setTitle("Xbox");
		miKrosoftHardware.addChild( miKrosoftXBox ) ;
		
		MenuItem miKrosoftXBox360 = new MenuItem();
		miKrosoftXBox360.setLink("xbox360.com");
		miKrosoftXBox360.setTitle("Xbox 360");
		miKrosoftHardware.addChild( miKrosoftXBox360 ) ;
		
		MenuItem miDivers = new MenuItem();
		miDivers.setLink("euh.com");
		miDivers.setTitle("rien à voir");
		
		listMenuItem.add(miDivers);
	}
	
	@Test
	public void tree() 
	{
		assertEquals( 3, listMenuItem.size() ) ;
	}
}
