package fr.urssaf.image.sae.anais.framework.component;

/**
 * data source de connexion à ANAIS<br>
 * un data source est nécessaire pour instancier ConnectionFactory
 * 
 * @see ConnectionFactory
 * 
 */
public class DataSource {

   private String hostname;
   private Integer port;
   private boolean usetls;
   private String appdn;
   private String passwd;
   private String codeapp;
   private String codeenv;
   private String timeout;

   /**
    * @return adresse IP du serveur ANAIS
    */
   public String getHostname() {
      return hostname;
   }

   /**
    * 
    * @param hostname
    *           adresse IP du serveur ANAIS
    */
   public void setHostname(String hostname) {
      this.hostname = hostname;
   }

   /**
    * 
    * @return port TCP du serveur ANAIS
    */
   public Integer getPort() {
      return port;
   }

   /**
    * 
    * @param port
    *           port TCP du serveur ANAIS
    */
   public void setPort(Integer port) {
      this.port = port;
   }

   /**
    * 
    * @return activation de TLS pour la connexion au serveur ANAIS
    */
   public boolean isUsetls() {
      return usetls;
   }

   /**
    * 
    * @param usetls
    *           activation de TLS pour la connexion au serveur ANAIS
    */
   public void setUsetls(boolean usetls) {
      this.usetls = usetls;
   }

   /**
    * 
    * @return DN du compte applicatif
    */
   public String getAppdn() {
      return appdn;
   }

   /**
    * 
    * @param appdn
    *           DN du compte applicatif
    */
   public void setAppdn(String appdn) {
      this.appdn = appdn;
   }

   /**
    * 
    * @return mot de passe du compte applicatif
    */
   public String getPasswd() {
      return passwd;
   }

   /**
    * 
    * @param passwd
    *           mot de passe du compte applicatif
    */
   public void setPasswd(String passwd) {
      this.passwd = passwd;
   }

   /**
    * 
    * @return code de l'application
    */
   public String getCodeapp() {
      return codeapp;
   }

   /**
    * 
    * @param codeapp
    *           code de l'application
    */
   public void setCodeapp(String codeapp) {
      this.codeapp = codeapp;
   }

   /**
    * 
    * @return Code environnement
    */
   public String getCodeenv() {
      return codeenv;
   }

   /**
    * 
    * @param codeenv
    *           Code environnement
    */
   public void setCodeenv(String codeenv) {
      this.codeenv = codeenv;
   }

   /**
    * 
    * @return délai de la connexion
    */
   public String getTimeout() {
      return timeout;
   }

   /**
    * 
    * @param timeout
    *           délai de la connexion
    */
   public void setTimeout(String timeout) {
      this.timeout = timeout;
   }

}
