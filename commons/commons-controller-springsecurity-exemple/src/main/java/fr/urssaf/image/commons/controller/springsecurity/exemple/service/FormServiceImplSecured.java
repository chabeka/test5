package fr.urssaf.image.commons.controller.springsecurity.exemple.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@Qualifier("secured")
public class FormServiceImplSecured implements FormService{

   private static final Logger LOG = Logger.getLogger(FormServiceImplSecured.class);

   @Override
   public void display(String title, String text) {

      LOG.info("SECURED DISPLAY title:"+title+" text:"+text);
   }

   @Override
   @Secured({"ROLE_ADMIN"})
   public void save(String title, String text) {
     
      LOG.info("SECURED SAVE title:"+title+" text:"+text);
      
   }

}
