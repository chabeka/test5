package fr.urssaf.image.sae.storage.services;

import fr.urssaf.image.sae.storage.services.connection.StorageConnectionService;
import fr.urssaf.image.sae.storage.services.storagedocument.StorageDocumentService;

/**
 * 
 * Fournit l’ensemble des services pour Manipuler les Objets DFCE 
 *
 */
public interface StorageServiceProvider extends StorageDocumentService, StorageConnectionService{

}
