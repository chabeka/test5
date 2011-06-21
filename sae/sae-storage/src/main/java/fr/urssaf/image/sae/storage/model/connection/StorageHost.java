package fr.urssaf.image.sae.storage.model.connection;

/**
 * Classe concrète contenant les caractéristiques de la machine où se trouve la
 * base de stockage<BR />
 * 
 * <li>
 * Attribut contextRoot : Représente le chemin d'accès à l'application web</li>
 * <li>
 * Attribut hostName : Représente le nom de la machine</li> <li>
 * Attribut hostPort : Représente le port de la machine</li> <li>
 * Attribut secure : Booléen permettant d'indiquer s'il faut utiliser http ou
 * https</li>
 */
public class StorageHost {

   private String contextRoot;

   private String hostName;

   private int hostPort;

   private boolean secure;

   /**
    * Retourne le nom de la machine où se trouve la base de stockage
    * 
    * @return Le nom de la machine hote
    */
   public final String getHostName() {
      return hostName;
   }

   /**
    * Initalise le nom de la machine de stockage
    * 
    * @param hostName
    *           : Le nom du serveur de stockage
    */
   public final void setHostName(final String hostName) {
      this.hostName = hostName;
   }

   /**
    * Retourne le port de la machine de stockage
    * 
    * @return Le port
    */
   public final int getHostPort() {
      return hostPort;
   }

   /**
    * Initialise le port de la machine où se trouve la base de stockage
    * 
    * @param hostPort
    *           : Le port de la machine de stockage
    */
   public final void setHostPort(final int hostPort) {
      this.hostPort = hostPort;
   }

   /**
    * Retourne le contexte root de l'application web
    * 
    * @return Le contexte root de l'application web
    */
   public final String getContextRoot() {
      return contextRoot;
   }

   /**
    * Initialise le context root de l'application web
    * 
    * @param contextRoot
    *           : Le contexte root de l'application web
    */
   public final void setContextRoot(final String contextRoot) {
      this.contextRoot = contextRoot;
   }

   /**
    * Retourne le fait que la connexion utilise https ou non
    * 
    * @return Vrai si https faux si http
    */
   public final boolean isSecure() {
      return secure;
   }

   /**
    * Initialise le fait que la connexion soit sécurisée ou non
    * 
    * @param secure
    *           : Vrai si https, faux si http
    */
   public final void setSecure(final boolean secure) {
      this.secure = secure;
   }


   /**
    * Constructeur
    * 
    * @param hostName
    *           : Le nom de la machine de stockage
    * @param hostPort
    *           : Le port de la machine de stockage
    * @param contextRoot
    *           : Le context root de l'application web
    * @param isSecure
    *           : Vrai si https, faux si http
    * 
    */
   public StorageHost(final String hostName, final int hostPort,
         final String contextRoot, final boolean isSecure) {
      this.hostName = hostName;
      this.hostPort = hostPort;
      this.contextRoot = contextRoot;
      secure = isSecure;
   }
}
