package fr.urssaf.image.commons.webservice.rpc.ged.modele;

import static org.junit.Assert.assertEquals;

import javax.xml.rpc.ServiceException;

import org.junit.Test;

public class ConsommationTest {

   @Test
   /**
    * Test du Ping() directement à partir des classes modèles
    * Uniquement pour des besoins d'explication dans la fiche de développement F011
    */
   public void testPing() throws java.rmi.RemoteException, ServiceException
   {
      GedImageServiceLocator serviceLocator = new GedImageServiceLocator();
      String urlWsdl = "http://cer69imageint4.cer69.recouv:9021/services/gedimageserveur.php?WSDL";
      serviceLocator.setGedImagePortEndpointAddress(urlWsdl);
      GedImagePortType port = serviceLocator.getGedImagePort();
      String resultatPing = port.ping();
      assertEquals(resultatPing,"GedImage en ligne !");
   }

   
}
