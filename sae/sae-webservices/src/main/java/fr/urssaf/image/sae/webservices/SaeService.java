package fr.urssaf.image.sae.webservices;

/**
 * Interface des services du SAE
 * 
 * 
 */
public interface SaeService {

   String PING_MESSAGE = "Les services SAE sont en ligne";

   /**
    * fonction de test d'accès au service web
    * 
    * @return "Les services SAE sont en ligne"
    */
   String ping();
}
