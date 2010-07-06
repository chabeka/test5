
package fr.urssaf.image.commons.dao.spring.service;


/**
 * @author Bertrand BARAULT
 * 
 * Fonctions typiques d'une classe persistante M
 *
 * @param <M> classe persistante
 */
public interface EntityModifyDao<P> {

    /**
     * Insère un objet persistant
     * 
     * @param obj objet persistant à insérer
     */
    void save(P obj);

    /**
     * Supprime un objet persistant
     * 
     * @param obj objet persistant à supprimer
     */
    void delete(P obj);

    /**
     * Met à jour un objet persistant
     * 
     * @param obj objet persistant à mettre à jour
     */
    void update(P obj);

}
