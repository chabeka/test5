package fr.urssaf.image.sae.storage.services.connection;

import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;

/**
 * Fournit le service de connexion à la base de stockage
 */
public interface StorageConnectionService {

   /**
    * Permet d'ouvrir une connexion
    * 
    * @throws ConnectionServiceEx Exception liée à la connection
    */
 //CHECKSTYLE:OFF
   void openConnection() throws ConnectionServiceEx;
   
   /**
    * Permet la fermeture d'une connection
    */
   void closeConnexion();
}
