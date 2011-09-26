package fr.urssaf.image.sae.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import junit.framework.Assert;
import net.docubase.toolkit.exception.ged.TagControlException;
import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;

import org.apache.commons.lang.exception.NestableRuntimeException;
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

      try {

         serviceProvider.getStorageConnectionService().openConnection();

         Base base = ServiceProvider.getBaseAdministrationService().getBase(
               connection.getStorageBase().getBaseName());
         return ServiceProvider.getSearchService()
               .getDocumentByUUID(base, uuid);

      } finally {

         serviceProvider.getStorageConnectionService().closeConnexion();

      }

   }

   /**
    * Permet de retrouver le contenu d'un document archivé dans le SAE
    * 
    * @param doc
    *           document dans le SAE
    * @return contenu du document
    * @throws ConnectionServiceEx
    *            une exception est levée lors de l'ouverture de la connexion
    */
   public final InputStream loadDocumentFile(Document doc)
         throws ConnectionServiceEx {

      try {

         serviceProvider.getStorageConnectionService().openConnection();

         return ServiceProvider.getStoreService().getDocumentFile(doc);

      } finally {

         serviceProvider.getStorageConnectionService().closeConnexion();

      }

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

      try {

         serviceProvider.getStorageConnectionService().openConnection();

         ServiceProvider.getStoreService().deleteDocument(uuid);

      } finally {

         serviceProvider.getStorageConnectionService().closeConnexion();

      }

   }

   /**
    * 
    * Permet d'insérer un document dans le SAE<br>
    * <br>
    * Cette méthode peut s'avérer utile pour les tests unitaires pour consulter
    * ou recherche un document du SAE
    * 
    * @param content
    *           contenu du document à archiver
    * @param metadatas
    *           liste des métadonnées
    * @param title
    *           titre du document
    * @param type
    *           type du document
    * @param creationDate
    *           date de création du document
    * @return UUID du document dans le SAE
    * @throws ConnectionServiceEx
    *            une exception est levée lors de l'ouverture de la connexion
    */
   public final UUID captureDocument(byte[] content,
         Map<String, Object> metadatas, String title, String type,
         Date creationDate) throws ConnectionServiceEx {

      try {

         serviceProvider.getStorageConnectionService().openConnection();

         Base base = ServiceProvider.getBaseAdministrationService().getBase(
               connection.getStorageBase().getBaseName());

         Document document = ToolkitFactory.getInstance().createDocumentTag(
               base);

         document.setCreationDate(creationDate);
         document.setTitle(title);
         document.setType(type);

         for (Entry<String, Object> entry : metadatas.entrySet()) {
            BaseCategory baseCategory = base.getBaseCategory(entry.getKey());
            document.addCriterion(baseCategory, entry.getValue());

         }

         InputStream docContent = new ByteArrayInputStream(content);
         return ServiceProvider.getStoreService().storeDocument(document,
               docContent).getUuid();

      } catch (TagControlException e) {
         throw new NestableRuntimeException(e);
      } finally {

         serviceProvider.getStorageConnectionService().closeConnexion();

      }

   }
}
