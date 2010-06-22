package fr.urssaf.image.commons.maquette.definition;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.urssaf.image.commons.maquette.tool.InfoBoxItem;

public interface ILeftCol 
{
	/**
	 * @desc permet de récupérer le nom de l'application
	 * @return
	 */
	public String getNomApplication( HttpServletRequest hsr ) ;
	
	/**
	 * @desc permet de récupérer la version de l'application
	 * @return
	 */
	public String getVersionApplication( HttpServletRequest hsr ) ;
	
	/**
	 * @desc permet de récupérer le nom de l'utilisateur
	 * @return
	 */
	public String getNomUtilisateur( HttpServletRequest hsr ) ;
	
	/**
	 * @desc permet de récupérer le role de l'application
	 * @return
	 */
	public String getRoleUtilisateur( HttpServletRequest hsr ) ;
	
	/**
	 * @desc permet de récupérer la fonction ou la méthode javascript permettant de se déconnecter
	 * 		 le script javascript doit évidemment être inclus dans chaque page métier
	 * @return
	 */
	public String getLienDeconnexion( HttpServletRequest hsr ) ;
	
	/**
	 * @desc permet de récupérer la liste des infobox à ajouter aux 3 standards
	 * @return
	 */
	public List<InfoBoxItem> getInfoBox( HttpServletRequest hsr ) ;
}
