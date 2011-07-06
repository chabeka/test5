package fr.urssaf.image.sae.storage.model.connection;

/**
 * Classe concrète contenant les caractéristiques de l’utilisateur mit à
 * disposition pour se connecter à la base de stockage. <BR />
 * 
 * <li>
 * Attribut storageBase : Représente les paramètres de la base de stockage</li>
 * <li>
 * Attribut storageHost : Représente les paramètres de la machine où est
 * localise la base</li> <li>
 * Attribut storageUser : Représente les paramètres de l’utilisateur de
 * connexion</li>
 */
public class StorageConnectionParameter {

   private StorageBase storageBase;

   private StorageHost storageHost;

   private StorageUser storageUser;

   /**
    * Retourne la base de stockage
    * 
    * @return La base de stockage
    */
   public final StorageBase getStorageBase() {
      return storageBase;
   }

   /**
    * Initialise la base de stockage
    * 
    * @param storageBase
    *           : La base de stockage
    */
   public final void setStorageBase(final StorageBase storageBase) {
      this.storageBase = storageBase;
   }

   /**
    * Retourne la machine où se trouve la base de stockage
    * 
    * @return La machine qui héberge la base de stockage
    */
   public final StorageHost getStorageHost() {
      return storageHost;
   }

   /**
    * Initialise les paramètres d'accès à la base de stockage
    * 
    * @param storageHost
    *           : Les paramètres d'accès à la base de stockage
    */
   public final void setStorageHost(final StorageHost storageHost) {
      this.storageHost = storageHost;
   }

   /**
    * Retourne les paramètres de login à la base de stockage
    * 
    * @return L'utilisateur
    */
   public final StorageUser getStorageUser() {
      return storageUser;
   }

   /**
    * Initialise les paramètres de login à la base de stockage
    * 
    * @param storageUser
    *           : L'utilisateur de login en base de stockage
    */
   public final void setStorageUser(final StorageUser storageUser) {
      this.storageUser = storageUser;
   }

   /**
    * Constructeur
    * 
    * @param storageBase
    *           : Le nom de la base de donnée de stockage
    * @param storageHost
    *           : Les paramètres de connexion à la base de stockage
    * @param storageUser
    *           : Les paramètres de login à la base de stockage
    */
   public StorageConnectionParameter(final StorageBase storageBase,
         final StorageHost storageHost, final StorageUser storageUser) {
      this.storageBase = storageBase;
      this.storageHost = storageHost;
      this.storageUser = storageUser;
   }

}
