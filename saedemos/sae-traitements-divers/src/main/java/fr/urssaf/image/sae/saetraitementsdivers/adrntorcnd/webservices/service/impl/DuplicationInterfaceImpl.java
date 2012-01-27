/**
 * 
 */
package fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.service.impl;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.exception.AdrnToRcndException;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.modele.InterfaceDuplicationLocator;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.modele.InterfaceDuplicationPort_PortType;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.modele.RNDTypeDocument;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.service.DuplicationInterface;

/**
 * Appels Webservice correspondant à duplication
 * 
 */
public class DuplicationInterfaceImpl implements DuplicationInterface {

   private String version;

   private String url;

   /**
    * {@inheritDoc}
    */
   @Override
   public RNDTypeDocument[] getDocumentTypesFromWS() throws AdrnToRcndException {

      String version = getVersionFromWS();

      if (version == null || version.isEmpty()) {
         throw new AdrnToRcndException(
               "le dernier numéro de version récupéré est null.\n "
                     + "Impossible de continuer les traitements");
      }

      RNDTypeDocument[] typeDoc = null;

      try {
         InterfaceDuplicationPort_PortType port = getPort();
         typeDoc = port.getListeTypesDocuments(version);
      } catch (RemoteException e) {
         throw new AdrnToRcndException(e);
      } catch (ServiceException e) {
         throw new AdrnToRcndException(e);
      }

      return typeDoc;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public RNDTypeDocument[] getDocumentTypesFromConfigFile()
         throws AdrnToRcndException {

      RNDTypeDocument[] typeDoc = null;

      try {
         InterfaceDuplicationPort_PortType port = getPort();
         typeDoc = port.getListeTypesDocuments(version);

      } catch (RemoteException e) {
         throw new AdrnToRcndException(e);

      } catch (ServiceException e) {
         throw new AdrnToRcndException(e);
      }

      return typeDoc;
   }

   /**
    * Retourne le port, objet permettant de réaliser les appels des méthodes
    * 
    * @return le port
    * @throws ServiceException
    *            exception d'adressage du WS
    */
   private InterfaceDuplicationPort_PortType getPort() throws ServiceException {
      InterfaceDuplicationLocator locator = new InterfaceDuplicationLocator();
      locator.setInterfaceDuplicationPortEndpointAddress(url);
      InterfaceDuplicationPort_PortType port = locator
            .getInterfaceDuplicationPort();
      return port;
   }

   /**
    * @param version
    *           the version to set
    */
   public void setVersion(String version) {
      this.version = version;
   }

   /**
    * @param url
    *           the url to set
    */
   public void setUrl(String url) {
      this.url = url;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getVersionFromConfigFile() {
      return version;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getVersionFromWS() throws AdrnToRcndException {

      String[] listVersions = null;

      try {
         InterfaceDuplicationPort_PortType port = getPort();
         listVersions = port.getLastNumVersion();

      } catch (RemoteException e) {
         throw new AdrnToRcndException(e);

      } catch (ServiceException e) {
         throw new AdrnToRcndException(e);
      }

      return listVersions[0];
   }

}
