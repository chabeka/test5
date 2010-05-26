package fr.urssaf.image.commons.dao.spring.service;

/**
 * Fonctions typiques d'une classe persistante M
 * 
 * @author Bertrand BARAULT
 * 
 * @param <M>  classe persistante
 * @param <I>  identifiant de la classe persistante
 */
public interface EntityIdDao<M, I> {

	/**
	 * Renvoie un objet persistant 
	 * 
	 * <p>cherche en session puis en base<p>
	 * 
	 * @param id identifiant de l'objet persistant
	 * @return objet persistant
	 */
	public M get(I id);

	/**
	 * Renvoie un objet persistant en base
	 * 
	 * <p>cherche en base<p>
	 * 
	 * @param id identifiant de l'objet persistant
	 * @return objet persistant
	 */
	public M find(I id);
	
	
}