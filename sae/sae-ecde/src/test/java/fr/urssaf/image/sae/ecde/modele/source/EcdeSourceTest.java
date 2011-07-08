package fr.urssaf.image.sae.ecde.modele.source;



import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Classe permettant de tester le modele EcdeSource
 * 
 * On verifie que les Set et les Get renvoie bien nos valeurs
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
public class EcdeSourceTest {
   
   private static File ecdeLokmen = new File("C:\test\testlokmen.txt");
   private static EcdeSource ecdeSource, ecdeSource2, ecdeSource3;
   
   private static final String LOKMEN = "lokmen";
   private static final String VAL_INNATENDU = "valeur inattendu";
   
   @BeforeClass
   public static void init() {
      ecdeSource = new EcdeSource("host", ecdeLokmen);
      ecdeSource2 = new EcdeSource("", ecdeLokmen);
      ecdeSource3 = new EcdeSource("host4", null);
   }
   
   @Test
   public void getHostNormalTest() {
      String resultatAttendu = "host";
      String resultatObtenu = ecdeSource.getHost();
      
      assertEquals(VAL_INNATENDU, resultatAttendu, resultatObtenu);
   }
   
   @Test
   public void getHostVideTest() {
      String resultatAttendu = "";
      String resultatObtenu = ecdeSource2.getHost();
      
      assertEquals(VAL_INNATENDU, resultatAttendu, resultatObtenu);
   }
   
   @Test
   public void getBasePathNulTest() {
      File resultatAttendu = null;
      File resultatObtenu = ecdeSource3.getBasePath();
      
      assertEquals(VAL_INNATENDU, resultatAttendu, resultatObtenu);
   }
   
   @Test
   public void setHostTest() {
      EcdeSource ecde = new EcdeSource("", new File(""));
      ecde.setHost(LOKMEN);
      
      String resultatAttendu = LOKMEN;
      String resultatObtenu = ecde.getHost();
      
      assertEquals(VAL_INNATENDU, resultatAttendu, resultatObtenu);
   }
   
   @Test
   public void setBasePathTest() {
      EcdeSource ecde = new EcdeSource("", new File(""));
      ecde.setBasePath(new File(LOKMEN));
      
      File resultatAttendu = new File(LOKMEN);
      File resultatObtenu = ecde.getBasePath();
      
      assertEquals(VAL_INNATENDU, resultatAttendu, resultatObtenu);
   }

}
