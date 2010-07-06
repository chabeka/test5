
package fr.urssaf.image.commons.dao.spring.service;

import java.util.List;

/**
 * @author Bertrand BARAULT
 * 
 * Fonctions typiques d'une classe persistante M
 *
 * @param <M> classe persistante
 */
public interface EntityFindDao<M> {

    List<M> find();
    
    /**
     * Renvoie tous les objets persistants triés
     * 
     * @param order nom de la colonne à trier
     * @return liste des objets persistants
     */
    List<M> find(String order);
    
    /**
     * Renvoie tous les objets persistants triés
     * 
     * @param order nom de la colonne à trier
     * @param inverse sens du tri
     * @return liste des objets persistants
     */
    List<M> find(String order,boolean inverse);
    
    /**
     * 
     * Renvoie tous les objets persistants triés et filtrés sur un interval
     * 
     * @param firstResult index du premier objet
     * @param maxResult nombre de résultat
     * @param order nom de la colonne à trier
     * @param inverse sens du tri
     * @return liste des objets persistants
     */
    List<M> find(int firstResult, int maxResult, String order, boolean inverse);

    
}
