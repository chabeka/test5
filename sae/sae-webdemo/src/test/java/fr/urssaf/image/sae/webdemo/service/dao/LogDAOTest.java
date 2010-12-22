package fr.urssaf.image.sae.webdemo.service.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webdemo.modele.Log;
import fr.urssaf.image.sae.webdemo.resource.Dir;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-servlet.xml",
      "/applicationContext.xml" })
public class LogDAOTest {

   @Autowired
   private LogDAO service;
   
   @Test
   public void find(){
      
      List<Log> logs = service.find(10,100,"idseq",Dir.ASC);
      assertEquals(100,logs.size());
   }
}
