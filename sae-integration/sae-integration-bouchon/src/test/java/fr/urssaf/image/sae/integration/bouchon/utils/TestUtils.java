package fr.urssaf.image.sae.integration.bouchon.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;

import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ListeMetadonneeType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ListeResultatRechercheType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.MetadonneeType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ResultatRechercheType;
import fr.urssaf.image.sae.integration.bouchon.security.VIHandler;

public class TestUtils {

   private static final String SECURITY_PATH = "src/main/resources/META-INF";
   
   
   public static SaeServiceStub getServiceStub(
         Configuration config,
         String fichierRessourceVi)
   throws AxisFault {
      
      ConfigurationContext configContext = 
         ConfigurationContextFactory.createConfigurationContextFromFileSystem(
            SECURITY_PATH,
            SECURITY_PATH + "/axis2.xml");
      
      configContext.setProperty(
            VIHandler.PROP_FICHIER_VI, 
            fichierRessourceVi);

      SaeServiceStub service = new SaeServiceStub(
            configContext,
            config.getString("urlServiceWeb"));
      
      return service;
      
   }
   
   
   public static void checkMetadonneesCount(
         ListeMetadonneeType metadonnees,
         int nombreAttendu) {
      
      MetadonneeType[] tabMetaTypes = metadonnees.getMetadonnee();
      
      assertNotNull(
            "La liste des métadonnées ne doit pas être null",
            tabMetaTypes);
      
      assertEquals(
            "Vérifie le nombre de métadonnées",
            nombreAttendu,
            tabMetaTypes.length);
      
   }
   
   
   public static void checkMetadonneesPresenceUnique(
         ListeMetadonneeType metadonnees,
         String code,
         String valeur) {
      
      MetadonneeType[] tabMetaTypes = metadonnees.getMetadonnee();
      
      assertNotNull(
            "La liste des métadonnées ne doit pas être null",
            tabMetaTypes);
      
      Boolean trouve = false;
      
      for (MetadonneeType metaType: tabMetaTypes) {
         if (metaType.getCode().getMetadonneeCodeType().equals(code)) {
            if (trouve) {
               
               fail("La métadonnée \"" + code + "\" est présente plusieurs fois");
               
            } else {

               trouve = true;
               
               assertEquals(
                     "Vérification de la valeur de la métadonnée",
                     valeur,
                     metaType.getValeur().getMetadonneeValeurType());
            }
            
         }
      }
      
      if (!trouve) {
         fail("La métadonnée \"" + code + "\" n'a pas été retrouvée");
      }
      
   }
   
   
   public static void checkResultatsRechercheCount(
         ListeResultatRechercheType resultatsRecherche,
         int nombreAttendu) {
      
      
      ResultatRechercheType[] tabResRechTypes = resultatsRecherche.getResultat();
      
      assertNotNull(
            "La liste des résultats de recherche ne doit pas être null",
            tabResRechTypes);
      
      assertEquals(
            "Vérifie le nombre de résultat de recherche",
            nombreAttendu,
            tabResRechTypes.length);
      
   }
   
   
   
   public static ResultatRechercheType checkResultatRecherchePresenceUnique(
         ListeResultatRechercheType resultatsRecherche,
         String uuidAttendu) {
      
      String uuidAttenduClone = uuidAttendu;
      
      ResultatRechercheType[] tabResRechTypes = resultatsRecherche.getResultat();
      
      assertNotNull(
            "La liste des résultats de recherche ne doit pas être null",
            tabResRechTypes);
      
      Boolean trouve = false;
      
      String uuid;
      ResultatRechercheType resultat = null;
      
      for (ResultatRechercheType resRechType: tabResRechTypes) {
         
         uuid = WsTypeUtils.extractUuid(resRechType.getIdArchive());
         
         if (uuid.equals(uuidAttenduClone)) {

            if (trouve) {

               fail("Le résultat de recherche avec l'UUID \"" + uuidAttendu
                     + "\" est présent plusieurs fois");

            } else {
               
               trouve = true;
               
               resultat = resRechType;
               
            }

         }
         
      }
      
      if (!trouve) {
         
         fail(
               "La résultat de recherche avec l'UUID \"" + 
               uuidAttendu + 
               "\" n'a pas été retrouvé");
         
      }
      
      return resultat;
      
   }
   
   
   public static void ecritDataHandlerSurDisque(
         DataHandler dataHandler,
         String filePath) throws IOException {
      
      File file = new File(filePath);
      FileOutputStream fos = null;
      try {
         
         fos = new FileOutputStream(file);
         
         IOUtils.copy(dataHandler.getInputStream(), fos);
         
      }
      finally {
         if (fos!=null) {
            fos.close();
         }
      }
      
      
   }
   
   
}
