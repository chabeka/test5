package fr.urssaf.image.commons.maquette.template.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.maquette.tool.MenuItem;

public final class MenuGenerator {

	public static Logger logger = Logger.getLogger( MenuGenerator.class.getName() );
	
	private static MenuItem selectedMenu;
	
	/**
	 * @desc processus de construction du menu
	 * @return la chaîne html du menu
	 */
	public static StringBuilder buildMenu( List<MenuItem> listMenu, String requestURL )
	{
		StringBuilder html = new StringBuilder() ;
		for( int i = 0 ; i < listMenu.size() ; i++ )
		{
			// début de ul
			if( i==0 || !listMenu.get(i).hasParent() )
				html.append( "<ul>" ) ;
			
			html.append( MenuGenerator.addRowToMenu( listMenu.get(i), requestURL ) ) ;
			
			// fin de ul
			if( i == ( listMenu.size() -1 ) || !listMenu.get(i).hasParent() )
				html.append( "</ul>" );
		}
		
		return html ;
	}
	
	/**
	 * @return une partie du code html du menu
	 */
	private static StringBuilder addRowToMenu( MenuItem menuItem, String requestURL )
	{
logger.debug( "Ajout ligne au menu : " + requestURL + " -- " + requestURL );
		// Test du breadcrumb
		if( requestURL.compareTo( menuItem.getLink() ) == 0 )
		{
			logger.info( "URI found : " + requestURL );
			MenuGenerator.selectedMenu = menuItem ;
		}
		// Traitement du menu
		StringBuilder html = new StringBuilder() ;
		
		html.append( "<li><a href='" + menuItem.getLink() + "' class='" ) ;
		
		// mise en évidence de la première ligne
		if( !menuItem.hasParent() )
			html.append( "firstrow" );
		
		html.append( "' title='" + menuItem.getDescription() + "' tabindex='" ) ;
		
		// différenciation comportementale de la première ligne (0) des autres (9999)
		if( !menuItem.hasParent() )
			html.append( "0" ) ;
		else
			html.append( "9999" ) ;
		
		html.append( "'>" + menuItem.getTitle() + "</a>" ) ;
		
		// recherche des enfants 
		if( menuItem.hasChildren() )
		{
			html.append( MenuGenerator.buildMenu( menuItem.getChildren(), requestURL ) ) ;
		}
		
		html.append( "</li>" ) ;
		
		return html ;
	}
	
	// TODO selectedMenu est static => la variable est partagée sur tout le serveur d'appli => à repenser
	public static String buildBreadcrumb()
	{
		String breadCrumb = "" ;
logger.debug( "selected menu : " + MenuGenerator.selectedMenu );
		if( MenuGenerator.selectedMenu != null )
		{
			MenuItem mi = MenuGenerator.selectedMenu ;
			List<MenuItem> listMenuItem = new ArrayList<MenuItem>();
			int nbLoop = 0 ;
			do{
				listMenuItem.add( mi ) ;
				mi = mi.getParent() ;
				nbLoop ++ ;
			}while( mi.hasParent() ) ;
			// Ajout du menu root si besoin
			if( nbLoop > 1 )
				listMenuItem.add( mi ) ;
			
			for( int i = (listMenuItem.size()-1); i>=0 ; i-- )
			{
				breadCrumb += listMenuItem.get(i).getTitle() ;
				if( i!= 0 )
					breadCrumb += " &gt; " ;
			}
			
			// nettoyage de la variable static (je sais c'est foireux comme implémentation, et c'est à repenser...)
			MenuGenerator.selectedMenu = null ;
		}
		
		return breadCrumb ;
	}
	
}
