package fr.urssaf.image.commons.maquette.tool;

import java.util.ArrayList;
import java.util.List;

import fr.urssaf.image.commons.maquette.exception.ReferentialIntegrityException;

public class MenuItem{

	protected static int counter = 0 ;
	
	protected int id ;
	private String link = "" ;
	private String title = ""  ;
	private String description = "" ;
	private MenuItem parent = null ;
	public List<MenuItem> children ;
	
	/**
	 * 
	 */
	public MenuItem() {
		super();
		// J'affecte l'identifiant unique
		id = MenuItem.counter ;
		
		// J'incrémente le compteur pour la prochaine instance
		MenuItem.counter++ ;
		
		//
		parent = null ;
		
		// Création du contenu des enfants
		children = new ArrayList<MenuItem>() ;
	}
	
	protected int getId() {
		return id;
	}

	public String getLink() {
		return link;
	}

	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public MenuItem getParent() {
		return parent;
	}
	
	public Boolean hasParent()
	{
		Boolean result = false ;
		
		if( parent != null )
			result = true ;
		
		return result ;
	}
	
	public void addParent( MenuItem menuItem ) throws ReferentialIntegrityException {
		// Vérification intégrité référentielle
		if( menuItem.getId() == id )
			throw new ReferentialIntegrityException( "(" + menuItem.getId() + ") " + menuItem.getTitle() + " ne peut être parent de lui même" ) ;
		if( isAChild( this, menuItem ) )
			throw new ReferentialIntegrityException( "(" + menuItem.getId() + ") " + menuItem.getTitle() + " ne peut être parent car il est déjà enfant" ) ;
		
		// Ajout du parent
		parent = menuItem ;
		
		// Synchronisation du parent
		menuItem.addChild(this);			
	}
	
	public List<MenuItem> getChildren() {
		return children;
	}
	
	public Boolean hasChildren()
	{
		Boolean result = false ;
		
		if( children.size() > 0 )
			result = true ;
		
		return result ;
	}
	
	public void addChild( MenuItem implMenuItem )throws ReferentialIntegrityException {
		// vérification de l'intégrité
		if( implMenuItem == parent )
			throw new ReferentialIntegrityException( "(" + implMenuItem.getId() + ") " + implMenuItem.getTitle() + " ne peut être enfant car il est déjà parent" ) ;
		if( isAChild(this, implMenuItem) )
			throw new ReferentialIntegrityException( "(" + implMenuItem.getId() + ") " + implMenuItem.getTitle() + " ne peut être enfant car il est déjà enfant" ) ;
		
		MenuItem c = (MenuItem) implMenuItem;
		
		// ajout de l'item à la liste
		children.add( implMenuItem ) ;
		
		// synchronisation avec l'item en lui affectant le parent
		c.setParent( this ) ;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setParent( MenuItem implMenuItem ) throws fr.urssaf.image.commons.maquette.exception.ReferentialIntegrityException {
		// Vérification intégrité référentielle
		if( implMenuItem.getId() == id )
			throw new fr.urssaf.image.commons.maquette.exception.ReferentialIntegrityException( "(" + implMenuItem.getId() + ") " + implMenuItem.getTitle() + " ne peut être parent de lui même" ) ;
		if( isAChild( this, implMenuItem ) )
			throw new fr.urssaf.image.commons.maquette.exception.ReferentialIntegrityException( "(" + implMenuItem.getId() + ") " + implMenuItem.getTitle() + " ne peut être parent car il est déjà enfant" ) ;

		parent = implMenuItem ;
	}
	
	@SuppressWarnings("unused")
	private static Boolean isNotAChild( MenuItem menuItem, MenuItem expectedChild )
	{
		return !MenuItem.isAChild( menuItem, expectedChild );
	}
	
	private static Boolean isAChild( MenuItem implMenuItem, MenuItem menuItem )
	{
		Boolean result = false ;
		
		// on parcours les enfants de menuItem pour chercher si expectedChild y est déjà référencé
		for( int i = 0 ; i < implMenuItem.getChildren().size() ; i++ )
		{
			if( implMenuItem.getChildren().get(i).getId() == menuItem.getId() )
			{
				result = true ;
				break ;
			}
			else if( implMenuItem.getChildren().get(i).hasChildren() )
			{
				for( int j = 0 ; j < implMenuItem.getChildren().get(i).getChildren().size(); j++ )
				{
					result = isAChild(implMenuItem.getChildren().get(i).getChildren().get(j), menuItem) ;
					if( result )
						break ;
				}
			}
		}
		
		return result ;
	}


}
