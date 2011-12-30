package fr.urssaf.image.commons.jmx.httpadaptor.exemple.mbean;

import javax.management.MBeanNotificationInfo;

/**
 * Premier MBean pour tester JMX.
 * 
 *
 */
public interface PremierNotificationMBean {
  
  String getNom(); 

  int getValeur(); 
  void setValeur(int valeur); 
  
  int add(int a, int b);

  void rafraichir();
  
  MBeanNotificationInfo[] getNotificationInfo();

}
