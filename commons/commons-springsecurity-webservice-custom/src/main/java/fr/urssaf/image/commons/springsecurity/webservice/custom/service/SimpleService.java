package fr.urssaf.image.commons.springsecurity.webservice.custom.service;

import org.springframework.stereotype.Service;

import fr.urssaf.image.commons.springsecurity.webservice.custom.modele.Modele;
import fr.urssaf.image.commons.springsecurity.webservice.custom.modele.SimpleWebService;

@Service
public class SimpleService {

   private final SimpleWebService service;

   public SimpleService() {

      fr.urssaf.image.commons.springsecurity.webservice.custom.modele.SimpleService service = new fr.urssaf.image.commons.springsecurity.webservice.custom.modele.SimpleService();
      this.service = service.getSimpleServicePort();

   }

   public void save(String role) {
      service.save(role, "title", "text");
   }

   public Modele load(String role) {
      return service.load(role);
   }

}
