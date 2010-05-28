package fr.urssaf.image.commons.dao.spring.service;

public interface EntityCountDao {

	/**
     * Compte le nombre d'objets persistants
     * 
     * @return nombre d'objets persistants
     */
    public int count();
}
