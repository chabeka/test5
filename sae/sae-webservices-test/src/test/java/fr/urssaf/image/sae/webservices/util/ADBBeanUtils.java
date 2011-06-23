package fr.urssaf.image.sae.webservices.util;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.databinding.ADBBean;

/**
 * Classe utilitaire pour les objets de type {@link ADBBean}
 * 
 * 
 */
public final class ADBBeanUtils {

   private ADBBeanUtils() {

   }

   /**
    * 
    * 
    * @param bean
    *           objet XML
    * @return chaine de caractère du xml
    */
   public static String print(ADBBean bean) {

      try {
         XMLStreamReader reader = bean.getPullParser(null);
         OMElement omElement = new StAXOMBuilder(reader).getDocumentElement();
         return omElement.toStringWithConsume();
      } catch (XMLStreamException e) {
         throw new IllegalStateException(e);
      }

   }
}
