package fr.urssaf.image.sae.anais.framework.component;

import anaisJavaApi.AnaisConnection_Application;
import anaisJavaApi.AnaisExceptionFailure;
import anaisJavaApi.AnaisExceptionServerAuthentication;
import anaisJavaApi.AnaisExceptionServerCommunication;
import fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException;

/**
 * Factory pour initialiser des connection principale à ANAIS<br>
 * Necessite une instanciation de {@link DataSource} pour spécifier les
 * paramètres de la connexion<br>
 * <br>
 * 
 */
public class ConnectionFactory {

   private final DataSource dataSource;

   /***
    * Initialisation du data source
    * 
    * @param dataSource
    *           paramétrage de la connexion
    */
   public ConnectionFactory(DataSource dataSource) {
      this.dataSource = dataSource;
   }

   /**
    * Création d'une connexion initialisée
    * 
    * @return connexion initialisée à ANAIS
    */
   public final AnaisConnection_Application createConnection() {

      AnaisConnection_Application connection = new AnaisConnection_Application();
      
      try {
         connection.init(dataSource.getHostname(), dataSource.getPort(),
               dataSource.isUsetls(), dataSource.getAppdn(),

               dataSource.getPasswd(), dataSource.getCodeapp(), dataSource
                     .getCodeenv(), dataSource.getTimeout());

         return connection;

      } catch (AnaisExceptionFailure e) {
         throw new SaeAnaisApiException(e);
      } catch (AnaisExceptionServerCommunication e) {
         throw new SaeAnaisApiException(e);
      } catch (AnaisExceptionServerAuthentication e) {
         throw new SaeAnaisApiException(e);
      }

   }
}
