package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.docubase.toolkit.exception.ged.TagControlException;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import fr.urssaf.image.sae.storage.dfce.annotations.Loggable;
import fr.urssaf.image.sae.storage.dfce.annotations.ServiceChecked;
import fr.urssaf.image.sae.storage.dfce.contants.Constants;
import fr.urssaf.image.sae.storage.dfce.mapping.BeanMapper;
import fr.urssaf.image.sae.storage.dfce.messages.*;
import fr.urssaf.image.sae.storage.dfce.model.AbstractServices;
import fr.urssaf.image.sae.storage.dfce.utils.Utils;
import fr.urssaf.image.sae.storage.exception.*;
import fr.urssaf.image.sae.storage.model.connection.*;
import fr.urssaf.image.sae.storage.model.storagedocument.*;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
import fr.urssaf.image.sae.storage.services.storagedocument.*;

/**
 * Implémente les services de l'interface {@link InsertionService}.
 * 
 * @author Akenore, Rhofir
 * 
 */
@Service
@Qualifier("insertionService")
public class InsertionServiceImpl extends AbstractServices implements
      InsertionService {
   @Autowired
   @Qualifier("deletionService")
   private DeletionService deletionService;

   /**
    * @return : Le service de suppression
    */
   public final DeletionService getDeletionService() {
      return deletionService;
   }

   /**
    * @param deletionService
    *           : Le service de suppression.
    */
   public final void setDeletionService(final DeletionService deletionService) {
      this.deletionService = deletionService;
   }

   /**
    * Construit un {@link InsertionServiceImpl}.
    * 
    * @param storageBase
    *           : La base de stockage
    */
   public InsertionServiceImpl(final StorageBase storageBase) {
      super(storageBase);
   }

   /**
    * Construit un {@link InsertionServiceImpl} par défaut.
    **/
   public InsertionServiceImpl() {
      super();
   }

   /**
    * {@inheritDoc}
    * 
    * @throws InsertionServiceEx
    *            Exception levée lorsque l'insertion ne se déroule pas bien
    */
   @Loggable(LogLevel.TRACE)
   @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
   @ServiceChecked
   public final BulkInsertionResults bulkInsertStorageDocument(
         final StorageDocuments storageDocuments, final boolean allOrNothing)
         throws InsertionServiceEx {
      final List<StorageDocument> storageDocDone = new ArrayList<StorageDocument>();
      final List<StorageDocumentOnError> storageDocFailed = new ArrayList<StorageDocumentOnError>();

      for (StorageDocument storageDocument : Utils
            .nullSafeIterable(storageDocuments.getAllStorageDocument())) {
         try {
            storageDocument.setUuid(insertStorageDocument(storageDocument));
            storageDocDone.add(storageDocument);
         } catch (InsertionServiceEx insertExcp) {
            if (storageDocument != null) {
               storageDocFailed.add(new StorageDocumentOnError(storageDocument
                     .getMetadatas(), storageDocument.getContent(),
                     storageDocument.getFilePath(), "INSERROR : "
                           + insertExcp.getMessage()));
            }
            if (allOrNothing) {
               break;
            }
         }
      }
      if (allOrNothing) {
         for (StorageDocument strDocument : Utils
               .nullSafeIterable(storageDocDone)) {
            try {
               deletionService.deleteStorageDocument(new UUIDCriteria(
                     strDocument.getUuid(), null));
            } catch (DeletionServiceEx delSerEx) {
               throw new InsertionServiceEx(MessageHandler
                     .getMessage(Constants.INS_CODE_ERROR), delSerEx
                     .getMessage(), delSerEx);
            }
         }
      }
      return new BulkInsertionResults(new StorageDocuments(storageDocDone),
            new StorageDocumentsOnError(storageDocFailed));
   }

   /**
    * {@inheritDoc}
    */
   @Loggable(LogLevel.TRACE)
   @ServiceChecked
   public final UUID insertStorageDocument(final StorageDocument storageDocument)
         throws InsertionServiceEx {
      try {
         Document docDfce = BeanMapper.storageDocumentToDfceDocument(
               getBaseDFCE(), storageDocument);
         InputStream docContent = new ByteArrayInputStream(storageDocument
               .getContent());
         return ServiceProvider.getStoreService().storeDocument(docDfce,
               docContent).getUuid();
      } catch (TagControlException tagCtrlEx) {
         throw new InsertionServiceEx(MessageHandler
               .getMessage(Constants.INS_CODE_ERROR), tagCtrlEx.getMessage(),
               tagCtrlEx);
      } catch (Exception except) {
         throw new InsertionServiceEx(MessageHandler
               .getMessage(Constants.INS_CODE_ERROR), except.getMessage(),
               except);
      }
   }

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("PMD.LongVariable")
   public final void setInsertionServiceParameter(
         final StorageConnectionParameter storageConnectionParameter) {
      setStorageBase(storageConnectionParameter.getStorageBase());

   }

}
