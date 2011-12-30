package fr.urssaf.image.commons.spring.exemple.mbean;

/**
 * Premier MBean pour tester JMX.
 * 
 *
 */
public interface IPremierBean {
  
  public int add (int x, int y);
  
  public String getHelloWord();
  
  public void setHelloWord(String hello);
  
  public void sayHello();
  
  public int getValeur();
  
  public void setValeur(int valeur);
}
