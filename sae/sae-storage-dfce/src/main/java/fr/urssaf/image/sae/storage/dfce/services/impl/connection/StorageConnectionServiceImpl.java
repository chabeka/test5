package fr.urssaf.image.sae.storage.dfce.services.impl.connection;

import java.net.MalformedURLException;
import java.net.URL;
import net.docubase.toolkit.service.Authentication;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import fr.urssaf.image.sae.storage.dfce.contants.Constants;
import fr.urssaf.image.sae.storage.dfce.messages.StorageMessageHandler;
import fr.urssaf.image.sae.storage.dfce.model.AbstractServiceProvider;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.connection.StorageHost;
import fr.urssaf.image.sae.storage.model.connection.StorageUser;
import fr.urssaf.image.sae.storage.services.connection.StorageConnectionService;

/**
 * Fournit l’implémentation des services de connexion à la base de stockage
 * 
 * @author akenore, rhofir
 * 
 */
@Service
@Qualifier("storageConnectionService")
public class StorageConnectionServiceImpl extends AbstractServiceProvider
      implements StorageConnectionService {
   /**
    * {@inheritDoc}
    */
   public final void openConnection() throws ConnectionServiceEx {
      StorageUser user = getStorageConnectionParameter().getStorageUser();
      Authentication.openSession(user.getLogin(), user.getPassword(),
            buildUrlForConnection(getStorageConnectionParameter()));
   }

   /**
    * {@inheritDoc}
    */
   public final void closeConnexion() {
      if (Authentication.isSessionActive()) {
         Authentication.closeSession();
      }
   }

   /**
    * Construit un {@link StorageConnectionServiceImpl}
    */
   public StorageConnectionServiceImpl() {
      super();
   }

   /**
    * 
    * @param storageConnectionParameter
    *           : Les paramètres de connexion à la base de stockage
    */
   @SuppressWarnings("PMD.LongVariable")
   public StorageConnectionServiceImpl(
         final StorageConnectionParameter storageConnectionParameter) {
      super(storageConnectionParameter);
   }

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("PMD.LongVariable")
   public final void setStorageConnectionServiceParameter(
         final StorageConnectionParameter storageConnectionParameter) {
      setStorageConnectionParameter(storageConnectionParameter);

   }

   /**
    * Permet de construire l'url de connection.
    * 
    * @param storageConnectionParameter
    *           : Les paramètres de connexion à la base de stockage
    * @return l'url de connexion à la base de stockage
    * @throws ConnectionServiceEx
    *            Exception lorsque la construction de l'url n'aboutie pas.
    */
   @SuppressWarnings("PMD.LongVariable")
   private String buildUrlForConnection(
         final StorageConnectionParameter storageConnectionParameter)
         throws ConnectionServiceEx {
      String url = Constants.BLANK;
      String protocol = Constants.HTTP;
      StorageHost storageHost = storageConnectionParameter.getStorageHost();
      try {
         if (storageConnectionParameter.getStorageHost().isSecure()) {
            protocol = Constants.HTTPS;
         }
         URL urlConnection = new URL(protocol, storageHost.getHostName(),
               storageHost.getHostPort(), storageHost.getContextRoot());
         url = urlConnection.toString();
      } catch (MalformedURLException except) {
         throw new ConnectionServiceEx(StorageMessageHandler
               .getMessage(Constants.CNT_CODE_ERROR), except.getMessage(),
               except);
      } catch (Exception except) {
         throw new ConnectionServiceEx(StorageMessageHandler
               .getMessage(Constants.CNT_CODE_ERROR), except.getMessage(),
               except);
      }
      return url;
   }

}
