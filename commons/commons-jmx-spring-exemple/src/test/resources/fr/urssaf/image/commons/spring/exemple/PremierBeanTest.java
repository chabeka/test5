package fr.urssaf.image.commons.spring.exemple;



import static org.junit.Assert.*;

import java.io.IOException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.commons.spring.exemple.mbean.PremierBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" 
                                     })
public class PremierBeanTest {

   @Autowired
   @Qualifier("premierBean1")
   private PremierBean premier;
   
   @Autowired
   private MBeanServerConnection mbs;
   
   @Test
   public void getValeurTestSuccess() {
      
      int attendu = 150;
      int actual = premier.getValeur();
      
      assertEquals("Valeur incorrect", attendu, actual);
      
   }
   @Test
   public void testRecuperationJmx() throws MalformedObjectNameException, NullPointerException, InstanceNotFoundException, MBeanException, ReflectionException, IOException{
      ObjectName name = new ObjectName("bean:name=premierBean");
      int valeur = (Integer) mbs.invoke(name, "getValeur",new Object[0], new String[0]);
      
      String helloWord = (String) mbs.invoke(name, "getHelloWord",new Object[0], new String[0]);
      
      assertEquals("Valeur incorrect", valeur, 150);
      
      assertEquals("Hello word non recuperer", helloWord, "Hello test");
      
   }
   
}
