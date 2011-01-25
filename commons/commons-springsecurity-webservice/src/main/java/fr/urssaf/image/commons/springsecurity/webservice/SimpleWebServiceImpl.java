package fr.urssaf.image.commons.springsecurity.webservice;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.commons.springsecurity.service.SimpleService;
import fr.urssaf.image.commons.springsecurity.service.modele.Modele;
import fr.urssaf.image.commons.springsecurity.webservice.authenticate.AuthenticateService;

@Service
@WebService(serviceName = "SimpleService", endpointInterface = "fr.urssaf.image.commons.springsecurity.webservice.SimpleWebService", portName = "SimpleServicePort")
public class SimpleWebServiceImpl implements SimpleWebService {

   @Autowired
   private SimpleService service;

   @Autowired
   private AuthenticateService authService;

   @Override
   public Modele load(String role) {

      authService.authenticate(role);

      return service.load();
   }

   @Override
   public void save(String role, String title, String text) {

      authService.authenticate(role);

      Modele modele = new Modele();
      modele.setText(text);
      modele.setTitle(title);

      service.save(modele);

   }

}
