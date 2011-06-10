package fr.urssaf.image.sae.storage.model.connection;

/**
 * Classe concrète contenant les caractéristiques de la machine où se trouve la
 * base de stockage<BR />
 * 
 * <li>
 * Attribut hostIp : Représente l’adresse ip de la machine</li> <li>
 * Attribut hostName : Représente le nom de la machine</li> <li>
 * Attribut hostPort : Représente le port de la machine</li> <li>
 * Attribut hostDomain : Represente l'identifiant de Domaine de la machine</li>
 */
public class StorageHost {

   private String hostIp;

   private String hostName;

   private int hostPort;

   private int hostDomain;

   /**
    * Retourne l’adresse IP de la machine où se trouve la base de stockage
    * 
    * @return Adresse IP
    */
   public final String getHostIp() {
      return hostIp;
   }

   /**
    * Initialise l'adresse IP de l'hote
    * 
    * @param hostIp
    *           L'adresse IP de l'hote
    */
   public final void setHostIp(String hostIp) {
      this.hostIp = hostIp;
   }

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
    *           Le nom du serveur de stockage
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
    *           Le port de la machine de stockage
    */
   public final void setHostPort(int hostPort) {
      this.hostPort = hostPort;
   }

   /**
    * Retourne l’identifant du domaine où se trouve la base de stockage
    * 
    * @return L'identifiant du domaine
    */
   public final int getHostDomain() {
      return hostDomain;
   }

   /**
    * Initialise l’identifant du domaine où se trouve la base de stockage
    * 
    * @param hostDomain
    *           L'identifiant du domaine
    */
   public final void setHostDomain(int hostDomain) {
      this.hostDomain = hostDomain;
   }

   /**
    * Constructeur
    * 
    * @param hostName
    *           Le nom de la machine de stockage
    * @param hostIp
    *           L'adresse IP de la machine de stockage
    * @param hostPort
    *           Le port de la machine de stockage
    * @param hostDomain
    *           L'identifiant du domaine de la machine de stockage
    */
   public StorageHost(final String hostName, final String hostIp,
         final int hostPort, final int hostDomain) {
      this.hostName = hostName;
      this.hostIp = hostIp;
      this.hostPort = hostPort;
      this.hostDomain = hostDomain;
   }
}
