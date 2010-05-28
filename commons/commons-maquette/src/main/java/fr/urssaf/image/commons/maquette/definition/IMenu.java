package fr.urssaf.image.commons.maquette.definition;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.urssaf.image.commons.maquette.tool.MenuItem;

public interface IMenu {
	public List<MenuItem> getMenu( HttpServletRequest hsr ) ;
	
	public String getBreadcrumb( HttpServletRequest hsr ) ;
}
