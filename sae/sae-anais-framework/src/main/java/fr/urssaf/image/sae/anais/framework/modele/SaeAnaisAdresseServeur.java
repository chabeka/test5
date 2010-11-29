package fr.urssaf.image.sae.anais.framework.modele;

/**
 * classe de paramétrage d'adressage du serveur ANAIS
 * 
 * @see SaeAnaisProfilConnexion#getServeurs()
 * @see SaeAnaisService
 */
public class SaeAnaisAdresseServeur {

   private String hote;

   private Integer port;

   private boolean tls;

   private int timeout;

   /**
    * 
    * @return L’adresse IP ou le nom DNS
    */
   public final String getHote() {
      return hote;
   }

   /**
    * 
    * @param hote
    *           L’adresse IP ou le nom DNS
    */
   public final void setHote(String hote) {
      this.hote = hote;
   }

   /**
    * 
    * @return port Le numéro de port TCP sur lequel se serveur écoute
    */
   public final Integer getPort() {
      return port;
   }

   /**
    * 
    * @param port
    *           Le numéro de port TCP sur lequel se serveur écoute
    */
   public final void setPort(Integer port) {
      this.port = port;
   }

   /**
    * 
    * @return Flag indiquant si TLS doit être activé pour la communication avec
    *         le serveur
    */
   public final boolean isTls() {
      return tls;
   }

   /**
    * 
    * @param tls
    *           Flag indiquant si TLS doit être activé pour la communication
    *           avec le serveur
    */
   public final void setTls(boolean tls) {
      this.tls = tls;
   }

   /**
    * 
    * @return Le timeout de connexion au serveur, en millisecondes
    */
   public final int getTimeout() {
      return timeout;
   }

   /**
    * 
    * @param timeout
    *           Le timeout de connexion au serveur, en millisecondes
    */
   public final void setTimeout(int timeout) {
      this.timeout = timeout;
   }

}
