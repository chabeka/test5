package fr.urssaf.image.commons.controller.springsecurity.exemple.service;

import javax.annotation.security.RolesAllowed;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("jsr250")
public class FormServiceImplJSR250 implements FormService{

   private static final Logger LOG = Logger.getLogger(FormServiceImplJSR250.class);

   @Override
   public void display(String title, String text) {

      LOG.info("JSR250 DISPLAY title:"+title+" text:"+text);
   }

   @Override
   @RolesAllowed({"ROLE_ADMIN"})
   public void save(String title, String text) {
     
      LOG.info("JSR250 SAVE title:"+title+" text:"+text);
      
   }

}
