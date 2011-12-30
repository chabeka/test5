package fr.urssaf.image.commons.jmx.jconsole.notifications.mbean;

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

  void rafraichir();
  
  MBeanNotificationInfo[] getNotificationInfo();

  int add(int a, int b);

}
