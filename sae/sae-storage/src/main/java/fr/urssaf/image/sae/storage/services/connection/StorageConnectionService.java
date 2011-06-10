package fr.urssaf.image.sae.storage.services.connection;

import fr.urssaf.image.sae.storage.exception.ConnectionServiceRtEx;

/**
 * Fournit les services de connexion à la base de stockage
 */
public interface StorageConnectionService {

   /**
    * Permet d'ouvrir une connexion
    * 
    * @throws ConnectionServiceRtEx Exception runtime typée
    */
   void openConnection() throws ConnectionServiceRtEx;
   
   /**
    * Permet la fermeture d'une connection
    */
   void closeConnexion();
}
