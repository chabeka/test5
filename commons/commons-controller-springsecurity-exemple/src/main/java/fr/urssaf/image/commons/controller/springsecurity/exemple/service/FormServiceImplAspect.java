package fr.urssaf.image.commons.controller.springsecurity.exemple.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("aspect")
public class FormServiceImplAspect implements FormService{

   private static final Logger LOG = Logger.getLogger(FormServiceImplAspect.class);

   @Override
   public void display(String title, String text) {

      LOG.info("ASPECT DISPLAY title:"+title+" text:"+text);
   }

   @Override
   public void save(String title, String text) {
     
      LOG.info("ASPECT SAVE title:"+title+" text:"+text);
      
   }

}
