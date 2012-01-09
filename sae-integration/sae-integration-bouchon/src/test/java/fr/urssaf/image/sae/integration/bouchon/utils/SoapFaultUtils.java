package fr.urssaf.image.sae.integration.bouchon.utils;

import static org.junit.Assert.assertEquals;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;

public class SoapFaultUtils {

   
   public static final String NAMESPACE_WSSE = 
      "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

   public static final String NAMESPACE_VI = "urn:iops:vi:faultcodes";

   public static final String NAMESPACE_SAE = "urn:sae:faultcodes";
   
   
   public static void checkSoapFault(
         AxisFault fault,
         String faultMessageAttendu,
         QName faultCodeAttendu) {
      
      // Vérifie le message de la Soap Fault
      assertEquals(
            "Vérification du message de la Soap Fault",
            faultMessageAttendu,
            fault.getReason());
      
      // Vérifie le code de la Soap Fault
      // Le namespace
      assertEquals(
            "Vérification du code de la Soap Fault - Partie Namespace",
            faultCodeAttendu.getNamespaceURI(),
            fault.getFaultCode().getNamespaceURI());
      // Le préfixe
      assertEquals(
            "Vérification du code de la Soap Fault - Partie Prefix",
            faultCodeAttendu.getPrefix(),
            fault.getFaultCode().getPrefix());
      // La partie locale
      assertEquals(
            "Vérification du code de la Soap Fault - Partie LocalPart",
            faultCodeAttendu.getLocalPart(),
            fault.getFaultCode().getLocalPart());

   }
   
   
}
