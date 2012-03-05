package fr.urssaf.image.sae.lotinstallmaj.service.impl;

import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.reference.LifeCycleRule;
import net.docubase.toolkit.service.ServiceProvider;
import net.docubase.toolkit.service.administration.BaseAdministrationService;
import net.docubase.toolkit.service.administration.StorageAdministrationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.lotinstallmaj.modele.DfceConfig;
import fr.urssaf.image.sae.lotinstallmaj.service.MajLotService;
import fr.urssaf.image.sae.lotinstallmaj.utils.ResourceMessagesUtils;


/**
 * Opérations de mise à jour du SAE.
 * 
 *
 */
@Service
public final class MajLotServiceImpl implements MajLotService {

   public static final String CODE_ACTIVITE = "CODEACTIVITENONOBLIGATOIRE";
   public static final String DUREE_CONSERVATION = "DUREECONSERVATIONDEMANDEDELAICOTISANT";
   
   public static final int DUREE_1825 = 1825;
   public static final int DUREE_1643 = 1643;
   
   private final ServiceProvider serviceProvider = ServiceProvider.newServiceProvider();
   
   // LOGGER
   private static final Logger LOG = LoggerFactory
                                       .getLogger(MajLotServiceImpl.class);
   
   @Autowired
   private DfceConfig dfceConfig;
   
   /**
    * {@inheritDoc}
    */
   @Override
   public void demarre(String[] args) {
      
      // connection à DFCE
      connectDfce();
      
      String operation = args[1];
      
      if (CODE_ACTIVITE.equalsIgnoreCase(operation) ) {
         String message = ResourceMessagesUtils.loadMessage("debut.modification.structure"); 
         LOG.info(message);
         System.out.println(message);
         // mise à jour de la metadonnée codeActivite pour la rendre non obligatoire
         updateCodeActivite();
      }
   
      if (DUREE_CONSERVATION.equalsIgnoreCase(operation) ) {
         String message = ResourceMessagesUtils.loadMessage("debut.regle.cycle.vie"); 
         LOG.info(message);
         System.out.println(message);
         // mise à jour de la durée de conservation du type de document 3.1.3.1.1
         updateDureeConservation();
      }
            
   }

   /**
    * Connexion à DFCE
    */
   private void connectDfce() {
      
      serviceProvider.connect(
            dfceConfig.getLogin(), 
            dfceConfig.getPassword(), 
            dfceConfig.getUrlToolkit());

   }
   
   /**
    * Mise à jour de la base métier pour la métadonnée CodeActivite à rendre non obligatoire.
    * 
    */
   private void updateCodeActivite() {
      
      // recupération de la metadonnee CodeActivite et verification qu'elle est bien dans l'état
      // attendu, à savoir qu'elle est obligatoire.
      BaseAdministrationService baseService = serviceProvider.getBaseAdministrationService();
      Base base = baseService.getBase(dfceConfig.getBasename());
      BaseCategory baseCategory = base.getBaseCategory("act");
      int minValues = baseCategory.getMinimumValues();
      
      // si minValues égal à 1, alors mise à jour de la structure de la base
      if (minValues == 1) { 
         baseCategory.setMinimumValues(0);
         String message = ResourceMessagesUtils.loadMessage("modification.structure"); 
         LOG.info(message);
         System.out.println(message);
         baseService.updateBase(base);
      }   
      else {
         String message = ResourceMessagesUtils.loadMessage("maj.existante.structure"); 
         LOG.info(message);
         System.out.println(message);
      }

   }
   
   /**
    * mise à jour de la durée de conservation du type de document 3.1.3.1.1
    */
   private void updateDureeConservation() {
      
      // récupération de la durée de conservation existante
      StorageAdministrationService storageAdmin = serviceProvider.getStorageAdministrationService();
      
      LifeCycleRule lifeCycleRule = storageAdmin.getLifeCycleRule("3.1.3.1.1");
            
      int dureeConservation = lifeCycleRule.getLifeCycleLength();
      
      // Si durée conservation vaut 1643 alors on met à jour la durée de conservation
      //TODO : en attente du traitement du JIRA CRTL-81
      
      if (dureeConservation == DUREE_1825) {
         String dureeConsOK = ResourceMessagesUtils.loadMessage("dureeConservationOK");
         System.out.println(dureeConsOK);
         LOG.info(dureeConsOK);
      }
      
      if(dureeConservation != DUREE_1643 ||
         dureeConservation != DUREE_1825  
         ) {
         
         String dureeConsKO = ResourceMessagesUtils.loadMessage("dureeConservationKO");
         System.out.println(dureeConsKO);
         LOG.info(dureeConsKO);
      }
      
   }

}
