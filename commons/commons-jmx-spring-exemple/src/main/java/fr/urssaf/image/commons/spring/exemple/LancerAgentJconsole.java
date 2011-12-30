package fr.urssaf.image.commons.spring.exemple;


/**
 *  Classe permettant de consomer les différents Managed Bean 
 *  dans le client JMX, Jconsole. 
 *
 */
public class LancerAgentJconsole {

  public static void main(String[] args) {
     try {
        int valeur = 1550;
        while (valeur < 1580) {
           valeur ++;
           System.out.println(valeur);
           Thread.sleep(1500);
        }
        
     } catch (Exception e) {
         e.printStackTrace();
     } 
  }
}