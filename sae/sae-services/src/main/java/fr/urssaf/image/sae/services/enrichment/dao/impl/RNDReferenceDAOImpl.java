package fr.urssaf.image.sae.services.enrichment.dao.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.metadata.messages.MetadataMessageHandler;
import fr.urssaf.image.sae.services.enrichment.dao.RNDReferenceDAO;
import fr.urssaf.image.sae.services.enrichment.xml.model.TypeDocument;
import fr.urssaf.image.sae.services.enrichment.xml.services.XmlRndDataService;
import fr.urssaf.image.sae.services.exception.enrichment.ReferentialRndException;
import fr.urssaf.image.sae.services.exception.enrichment.UnknownCodeRndEx;
import fr.urssaf.image.sae.services.util.ResourceMessagesUtils;
/**
 * Classe représentant le DAO du référentiel des codes RND.
 * @author Rhofir
 *
 */
@Service
@Qualifier("rndReferenceDAO")
public class RNDReferenceDAOImpl implements RNDReferenceDAO {
   @Autowired
   @Qualifier("xmlRndDataService")
   private XmlRndDataService xmlDataService;

   @Autowired
   private ApplicationContext context;

   /**
    * @return Le service Xml.
    */
   public final XmlRndDataService getXmlDataService() {
      return xmlDataService;
   }

   /**
    * @param xmlDataService
    *           :Le service Xml.
    */
   public final void setXmlDataService(XmlRndDataService xmlDataService) {
      this.xmlDataService = xmlDataService;
   }

   /**
    * @return Le context.
    */
   public final ApplicationContext getContext() {
      return context;
   }

   /**
    * @param context
    *           . Le context Spring.
    */
   public final void setContext(ApplicationContext context) {
      this.context = context;
   }

   /**
    * {@inheritDoc}
    * 
    * @throws ReferentialRndException
    *            Exception lever lorsque la récupération des métadonnées ne sont
    *            pas disponibles.
    */
   @Override
   public final Map<String, TypeDocument> getAllRndCodes()
         throws ReferentialRndException {
      final Resource referentiel = getContext().getResource("classpath:xml/RCND.xml");
      try {
         return xmlDataService.rndReferenceReader(referentiel.getInputStream());

      } catch (IOException e) {
         throw new ReferentialRndException(MetadataMessageHandler.getMessage(
               "rcnd.file.notfound", referentiel.getFilename()), e);
      }
   }

   @Override
   public final String getActivityCodeByRnd(String codeRnd)
         throws ReferentialRndException, UnknownCodeRndEx {
      String activityCode = StringUtils.EMPTY;
      TypeDocument typeDoc = getAllRndCodes().get(codeRnd);
      if (typeDoc == null) {
         throw new UnknownCodeRndEx(ResourceMessagesUtils.loadMessage("capture.code.rnd.interdit",
               codeRnd));
      }
      activityCode = typeDoc.getActivityCode();
      if (StringUtils.isEmpty(activityCode)) {
         throw new ReferentialRndException(ResourceMessagesUtils.loadMessage(
               "capture.code.activite.nonvalide", codeRnd));
      }
      return activityCode;
   }
   @Override
   public final TypeDocument getTypeDocument(String codeRnd)  
         throws ReferentialRndException, UnknownCodeRndEx {
      TypeDocument typeDoc = getAllRndCodes().get(codeRnd);
      if (typeDoc == null) {
         throw new UnknownCodeRndEx(ResourceMessagesUtils.loadMessage("capture.code.rnd.interdit",
               codeRnd));
      }
      return typeDoc;
   }
   @Override
   public final String getFonctionCodeByRnd(String codeRnd)
         throws ReferentialRndException, UnknownCodeRndEx {
      String fonctionCode = StringUtils.EMPTY;
      TypeDocument typeDoc = getAllRndCodes().get(codeRnd);
      if (typeDoc == null) {
         throw new UnknownCodeRndEx(ResourceMessagesUtils.loadMessage("capture.code.rnd.interdit",
               codeRnd));
      }
      fonctionCode = typeDoc.getFonctionCode();
      if (StringUtils.isEmpty(fonctionCode)) {
         throw new UnknownCodeRndEx(ResourceMessagesUtils.loadMessage("capture.code.fonction.nonvalide",
               codeRnd));
      }
      return fonctionCode;
   }

   @Override
   public final int getStorageDurationByRnd(String codeRnd)
         throws ReferentialRndException, UnknownCodeRndEx {
      int storageDuration = -1;
      TypeDocument typeDoc = getAllRndCodes().get(codeRnd);
      if (typeDoc == null) {
         throw new UnknownCodeRndEx(ResourceMessagesUtils.loadMessage("capture.code.rnd.interdit",
               codeRnd));
      }
      storageDuration = typeDoc.getStorageDuration();
      if (storageDuration == -1) {
         throw new UnknownCodeRndEx(ResourceMessagesUtils.loadMessage("capture.dureeconservation.nonvalide",
               codeRnd));
      }
      return storageDuration;
   }
}
