package fr.urssaf.image.sae.anais.framework.modele;

/**
 * classe de paramétrage d'adressage du serveur ANAIS
 * 
 * 
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
   public String getHote() {
      return hote;
   }

   /**
    * 
    * @param hote
    *           L’adresse IP ou le nom DNS
    */
   public void setHote(String hote) {
      this.hote = hote;
   }

   /**
    * 
    * @return port Le numéro de port TCP sur lequel se serveur écoute
    */
   public Integer getPort() {
      return port;
   }

   /**
    * 
    * @param port
    *           Le numéro de port TCP sur lequel se serveur écoute
    */
   public void setPort(Integer port) {
      this.port = port;
   }

   /**
    * 
    * @return Flag indiquant si TLS doit être activé pour la communication avec
    *         le serveur
    */
   public boolean isTls() {
      return tls;
   }

   /**
    * 
    * @param tls
    *           Flag indiquant si TLS doit être activé pour la communication
    *           avec le serveur
    */
   public void setTls(boolean tls) {
      this.tls = tls;
   }

   /**
    * 
    * @return Le timeout de connexion au serveur, en millisecondes
    */
   public int getTimeout() {
      return timeout;
   }

   /**
    * 
    * @param timeout
    *           Le timeout de connexion au serveur, en millisecondes
    */
   public void setTimeout(int timeout) {
      this.timeout = timeout;
   }

}
