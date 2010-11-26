package fr.urssaf.image.sae.anais.framework.component;

import anaisJavaApi.AnaisConnection_Application;
import anaisJavaApi.AnaisExceptionAuthAccountLocked;
import anaisJavaApi.AnaisExceptionAuthFailure;
import anaisJavaApi.AnaisExceptionAuthMultiUid;
import anaisJavaApi.AnaisExceptionFailure;
import anaisJavaApi.AnaisExceptionNoObject;
import anaisJavaApi.AnaisExceptionPwdExpired;
import anaisJavaApi.AnaisExceptionPwdExpiring;
import anaisJavaApi.AnaisExceptionServerCommunication;
import anaisJavaApi.AnaisHabilitationList;
import anaisJavaApi.AnaisUserInfo;
import anaisJavaApi.AnaisUserResult;
import fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException;

/**
 * La classe encapsule les méthode de la classe
 * {@link anaisJavaApi.AnaisConnection_Application} <br>
 * Le but est d'englober les exceptions du Framework Anais dans
 * {@link SaeAnaisApiException}
 * 
 */
public class AnaisConnectionSupport {

   private final AnaisConnection_Application connection;

   private final ConnectionFactory connectionFactory;

   /**
    * initialise la connection Anais
    * 
    * @param connection
    *           connection Anais initialisée
    */
   public AnaisConnectionSupport(ConnectionFactory connectionFactory) {

      this.connectionFactory = connectionFactory;
      if (this.connectionFactory == null) {
         throw new IllegalStateException("'connectionFactory' is required");
      }

      this.connection = this.connectionFactory.createConnection();
   }

   /**
    * Encapsule
    * {@link anaisJavaApi.AnaisConnection_Application#checkUserCredential(String, String)}
    * 
    * @param userLogin
    *           param login
    * @param userPassword
    *           param passwd
    * @return return checkUserCredential
    */
   public final AnaisUserResult checkUserCredential(String userLogin,
         String userPassword) {

      try {
         return connection.checkUserCredential(userLogin, userPassword);
      } catch (AnaisExceptionServerCommunication e) {
         throw new SaeAnaisApiException(e);
      } catch (AnaisExceptionAuthFailure e) {
         throw new SaeAnaisApiException(e);
      } catch (AnaisExceptionPwdExpired e) {
         throw new SaeAnaisApiException(e);
      } catch (AnaisExceptionPwdExpiring e) {
         throw new SaeAnaisApiException(e);
      } catch (AnaisExceptionAuthAccountLocked e) {
         throw new SaeAnaisApiException(e);
      } catch (AnaisExceptionAuthMultiUid e) {
         throw new SaeAnaisApiException(e);
      } catch (AnaisExceptionFailure e) {
         throw new SaeAnaisApiException(e);
      }
   }

   /**
    * Encapsule {@link anaisJavaApi.AnaisConnection_Application#close()}
    */
   public final void close() {
      try {
         connection.close();
      } catch (AnaisExceptionServerCommunication e) {
         throw new SaeAnaisApiException(e);
      }
   }

   /**
    * Encapsule
    * {@link anaisJavaApi.AnaisConnection_Application#getUserInfo(String)}
    * 
    * @param userDn
    *           param userdn
    * @return return getUserInfo
    */
   public final AnaisUserInfo getUserInfo(String userDn) {
      try {
         return connection.getUserInfo(userDn);
      } catch (AnaisExceptionNoObject e) {
         throw new SaeAnaisApiException(e);
      }
   }

   /**
    * Encapsule
    * {@link anaisJavaApi.AnaisConnection_Application#getUserHabilitations(String ,String,String)}
    * 
    * @param userDn
    *           param userdn
    * @param codeInterRegion
    *           param codeir
    * @param codeOrganisme
    *           param codeorg
    * @return return getUserHabilitations
    */
   public final AnaisHabilitationList getUserHabilitations(String userDn,
         String codeInterRegion, String codeOrganisme) {
      try {
         return connection.getUserHabilitations(userDn, codeInterRegion,
               codeOrganisme);
      } catch (AnaisExceptionNoObject e) {
         throw new SaeAnaisApiException(e);
      }
   }

}
