package fr.urssaf.image.sae.webdemo.service.dao;

import java.util.List;

import fr.urssaf.image.sae.webdemo.modele.Log;
import fr.urssaf.image.sae.webdemo.resource.Dir;

/**
 * Interface du service des traces d'exploitation
 * 
 * 
 */
public interface LogDAO {

   /**
    * Renvoie le liste des traces paginée et triée
    * 
    * @param firstResult
    *           premier index de la liste
    * @param maxResults
    *           nombre de résultats maximum dans la liste
    * @param order
    *           colonne triée
    * @param dir
    *           sens du tri (DESC/ASC)
    * @return liste des traces
    */
   List<Log> find(int firstResult, int maxResults, String order, Dir dir);

   /**
    * 
    * @return nombre total des traces
    */
   int count();

}
