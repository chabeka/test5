package fr.urssaf.image.sae.webdemo.service.dao;

import java.util.List;

import fr.urssaf.image.sae.webdemo.modele.Log;

/**
 * Interface des service des traces d'exploitation
 * 
 * 
 */
public interface LogDAO {

   /**
    * Renvoie la liste complètes des traces
    * 
    * @return traces d'exploitation
    */
   List<Log> find();
}
