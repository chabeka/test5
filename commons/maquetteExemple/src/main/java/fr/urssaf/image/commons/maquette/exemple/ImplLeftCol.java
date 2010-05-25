package fr.urssaf.image.commons.maquette.exemple;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.urssaf.image.commons.maquette.definition.ILeftCol;
import fr.urssaf.image.commons.maquette.tool.InfoBoxItem;

public class ImplLeftCol implements ILeftCol {

	private ArrayList<InfoBoxItem> infoBoxList ;
	
	/**
	 * 
	 */
	public ImplLeftCol() {
		super();
		infoBoxList = new ArrayList<InfoBoxItem>();
	}

	@Override
	public String getNomApplication( HttpServletRequest hsr ) {
		return "Test webapp";
	}

	@Override
	public String getVersionApplication( HttpServletRequest hsr ) {
		return "v 0.xxx";
	}

	@Override
	public String getNomUtilisateur( HttpServletRequest hsr ) {
		return "Henry T";
	}

	@Override
	public String getRoleUtilisateur( HttpServletRequest hsr ) {
		return "admin";
	}

	@Override
	public String getLienDeconnexion( HttpServletRequest hsr ) {
		return "javascript:wham.logout();";
	}

	@Override
	public List<InfoBoxItem> getInfoBox( HttpServletRequest hsr ) {
		
		if( hsr.getAttribute("PageInjectionLeftCol") != null )
		{
			InfoBoxItem otherInfo = new InfoBoxItem( "other", "Divers", "divers...." ) ;
			otherInfo.addSpan( "test", "ma description", "Contenu du premier span" ) ;
			otherInfo.addSpan( "light", "une autre description", "Contenu d'un premier span" ) ;
			infoBoxList.add( otherInfo ) ;
			hsr.removeAttribute("PageInjectionLeftCol") ;
		}
		else if( hsr.getAttribute("PageInjectionLeftColAutre") != null )
		{
			InfoBoxItem otherInfo = new InfoBoxItem( "other", "Autres", "autres...." ) ;
			otherInfo.addSpan( "test", "ma description", "Contenu du second span" ) ;
			otherInfo.addSpan( "light", "une autre description", "Contenu d'un second span" ) ;
			infoBoxList.add( otherInfo ) ;
			hsr.removeAttribute("PageInjectionLeftColAutre") ;
		}
		
		return infoBoxList;
	}

}
