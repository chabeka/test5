package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.PingRequest;
import fr.cirtil.www.saeservice.PingResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml"})
public class SaeServiceSkeletonTest {

   @Autowired
   private SaeServiceSkeleton skeleton;
  
   @Test
   public void ping(){
      
      PingRequest request = new PingRequest();
     
      
      PingResponse response = skeleton.ping(request);
      
      assertEquals("Test du ping","Les services SAE sont en ligne",response.getPingString());
   }
}
