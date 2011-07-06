package fr.urssaf.image.sae.storage.services.storagedocument;

import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;

/**
 * Fournit l’ensemble des services d’insertion, de recherche, de récupération.
 * 
 */
public interface StorageDocumentService extends InsertionService,
      SearchingService, RetrievalService,DeletionService {
	 /**
	    * Permet d'initialiser les paramètres dont le service aura besoin
	    * 
	    * @param storageConnectionParameter
		 *            : Les paramètres de connexion à la base de stockage
	    */
	@SuppressWarnings("PMD.LongVariable")
	   void setStorageDocumentServiceParameter(final StorageConnectionParameter storageConnectionParameter);
	   
	 
}
