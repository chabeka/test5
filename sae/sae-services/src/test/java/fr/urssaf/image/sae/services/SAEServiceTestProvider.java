package fr.urssaf.image.sae.services;

import java.util.UUID;

import junit.framework.Assert;

import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;

/**
 * Service pour fournir des méthodes communes pour les tests de l'artefact<br>
 * <br>
 * La classe peut-être injecté par {@link Autowired}
 * 
 * 
 */
@Component
public class SAEServiceTestProvider {

   private final StorageServiceProvider serviceProvider;

   private final StorageConnectionParameter connection;

   /**
    * initialise la façade des services de sae-storage
    * 
    * @param serviceProvider
    *           façade des services de sae-storage
    * @param connection
    *           connection à DFCE
    */
   @Autowired
   public SAEServiceTestProvider(
         @Qualifier("storageServiceProvider") StorageServiceProvider serviceProvider,
         @Qualifier("storageConnectionParameter") StorageConnectionParameter connection) {

      Assert.assertNotNull(serviceProvider);
      Assert.assertNotNull(connection);

      this.serviceProvider = serviceProvider;
      this.connection = connection;

      this.serviceProvider.setStorageServiceProviderParameter(connection);
   }

   /**
    * Permet de retrouver un document dans le SAE à partir de son identifiant
    * unique d'archivage<br>
    * <br>
    * Cette méthode peut s'avérer utile pour les tests unitaires simplement pour
    * vérifier qu'un document a bien été inséré dans le SAE
    * 
    * @param uuid
    *           identifiant unique du document à retrouver dans le SAE
    * @return instance du {@link Document} correspond au paramètre
    *         <code>uuid</code>
    * @throws ConnectionServiceEx
    *            une exception est levée lors de l'ouverture de la connexion
    */
   public final Document searchDocument(UUID uuid) throws ConnectionServiceEx {

      serviceProvider.getStorageConnectionService().openConnection();

      Base base = ServiceProvider.getBaseAdministrationService().getBase(
            connection.getStorageBase().getBaseName());
      Document document = ServiceProvider.getSearchService().getDocumentByUUID(
            base, uuid);

      serviceProvider.getStorageConnectionService().closeConnexion();

      return document;
   }

   /**
    * Permet de supprimer un document dans le SAE à partir de son identifiant
    * unique d'archivage<br>
    * <br>
    * Cette méthode peut s'avérer utile pour les tests unitaires simplement pour
    * supprimer un document du SAE qui vient d'être inséré et n'est plus utile
    * 
    * @param uuid
    *           identifiant unique du document à supprimer SAE
    * @throws ConnectionServiceEx
    *            une exception est levée lors de l'ouverture de la connexion
    */
   public final void deleteDocument(UUID uuid) throws ConnectionServiceEx {

      serviceProvider.getStorageConnectionService().openConnection();

      ServiceProvider.getStoreService().deleteDocument(uuid);

      serviceProvider.getStorageConnectionService().closeConnexion();

   }
}
