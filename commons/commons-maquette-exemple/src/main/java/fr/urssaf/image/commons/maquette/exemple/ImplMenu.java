package fr.urssaf.image.commons.maquette.exemple;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.urssaf.image.commons.maquette.definition.IMenu;
import fr.urssaf.image.commons.maquette.exception.ReferentialIntegrityException;
import fr.urssaf.image.commons.maquette.tool.MenuItem;

public class ImplMenu implements IMenu {

	public List<MenuItem> listMenuItem = null ;
	
	/**
	 * 
	 */
	public ImplMenu() {
		super();
		listMenuItem = new ArrayList<MenuItem>(10) ;
	}

	@Override
	public List<MenuItem> getMenu( HttpServletRequest hsr ){
		
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
		miWinMe.setLink( "/ImageMaquetteWeb/filAriane.jsp" ); // "wME.com");
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
		
		return listMenuItem;
	}

	@Override
	public String getBreadcrumb(HttpServletRequest hsr) {
		return null ;
	}

}
