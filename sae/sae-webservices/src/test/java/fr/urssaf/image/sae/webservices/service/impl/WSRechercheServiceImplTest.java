/**
 * 
 */
package fr.urssaf.image.sae.webservices.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.MetadonneeCodeType;

/**
 * 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service-test.xml" })
public class WSRechercheServiceImplTest {

   @Autowired
   WSRechercheServiceImpl impl;

   @Test
   public void testListeNullMDDesired_success() {
      List<String> values = WSRechercheServiceImpl.recupererListMDDesired(null);

      Assert.assertNotNull("la liste retournée est non nulle", values);
      Assert.assertEquals("la liste est vide", 0, values.size());
   }
   
   @Test
   public void testListeVideMDDesired_success() {
      List<String> values = WSRechercheServiceImpl.recupererListMDDesired(new MetadonneeCodeType[]{});

      Assert.assertNotNull("la liste retournée est non nulle", values);
      Assert.assertEquals("la liste est vide", 0, values.size());
   }

}
