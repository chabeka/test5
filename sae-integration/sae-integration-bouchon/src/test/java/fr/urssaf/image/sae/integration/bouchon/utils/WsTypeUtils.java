package fr.urssaf.image.sae.integration.bouchon.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;

import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ConsultationResponseType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.EcdeUrlType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ListeMetadonneeCodeType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ListeMetadonneeType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ListeResultatRechercheType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.MetadonneeCodeType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.MetadonneeType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.MetadonneeValeurType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ObjetNumeriqueType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ObjetNumeriqueTypeChoice_type0;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.RequeteRechercheType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ResultatRechercheType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.UuidType;

/**
 * Méthodes utilitaires pour les types du WSDL/XSD
 */
public class WsTypeUtils {
   

   public static EcdeUrlType buildEcdeUrl(String urlEcde) throws MalformedURIException {
      EcdeUrlType ecdeUrlType = new EcdeUrlType();
      ecdeUrlType.setEcdeUrlType(new URI(urlEcde));
      return ecdeUrlType;
   }
   
   
   public static ObjetNumeriqueType buildObjetNumeriqueAvecUrlEcde(
         String urlEcde)
      throws MalformedURIException {
      
      ObjetNumeriqueType objetNumeriqueType = new ObjetNumeriqueType();
      
      ObjetNumeriqueTypeChoice_type0 urlOuContenu = new ObjetNumeriqueTypeChoice_type0() ;
      objetNumeriqueType.setObjetNumeriqueTypeChoice_type0(urlOuContenu);
      
      EcdeUrlType ecdeUrlType = buildEcdeUrl(urlEcde);
      urlOuContenu.setUrl(ecdeUrlType);
      
      // fin
      return objetNumeriqueType;
      
   }
   
   
   public static ListeMetadonneeType buildListeMetadonnes(Map<String,String> metadonnees) {
      
      ListeMetadonneeType listeMetadonneeType = new ListeMetadonneeType();
      
      MetadonneeType[] tabMetadonneeType = new MetadonneeType[metadonnees.size()];
      listeMetadonneeType.setMetadonnee(tabMetadonneeType);
      
      int indice = 0 ;
      MetadonneeType metadonneeType;
      MetadonneeCodeType codeType;
      MetadonneeValeurType valeurType;
      for(Map.Entry<String, String> entry: metadonnees.entrySet()) {
         
         metadonneeType = new MetadonneeType();
         tabMetadonneeType[indice] = metadonneeType; 
         indice++;
         
         // Le code
         codeType = new MetadonneeCodeType();
         codeType.setMetadonneeCodeType(entry.getKey());
         metadonneeType.setCode(codeType);
         
         // La valeur
         valeurType = new MetadonneeValeurType();
         valeurType.setMetadonneeValeurType(entry.getValue());
         metadonneeType.setValeur(valeurType);
         
      }
      
      return listeMetadonneeType;
      
   }
   
   
   public static RequeteRechercheType buildRequeteLucene(String requeteLucene) {
      
      RequeteRechercheType requeteRechercheType = new RequeteRechercheType() ;
      
      requeteRechercheType.setRequeteRechercheType(requeteLucene);
      
      return requeteRechercheType;
      
   }
   
   
   
   public static ListeMetadonneeCodeType buildListeCodesMetadonnes(
         List<String> codesMetadonnees) {
      
      ListeMetadonneeCodeType listeMetadonneeCodeType = new ListeMetadonneeCodeType();
      
      MetadonneeCodeType[] tabMetadonneeCodeType = 
         new MetadonneeCodeType[codesMetadonnees.size()];
      listeMetadonneeCodeType.setMetadonneeCode(tabMetadonneeCodeType);
      
      int indice = 0 ;
      MetadonneeCodeType metadonneeCodeType;
      
      Iterator<String> iterator = codesMetadonnees.iterator();
      while ( iterator.hasNext() ){
         
         metadonneeCodeType = new MetadonneeCodeType();
         tabMetadonneeCodeType[indice] = metadonneeCodeType; 
         indice++;
         
         metadonneeCodeType.setMetadonneeCodeType(iterator.next());
         
      }
      
      return listeMetadonneeCodeType;
      
   }
   
   
   public static UuidType buildUuid(String uuid) {
      
      UuidType uuidType = new UuidType();
      
      uuidType.setUuidType(uuid);
      
      return uuidType;
      
   }
   
   
   public static String extractUuid(UuidType uuid) {
      
      return uuid.getUuidType();
      
   }
   
   
   public static void addMetadonnee(
         ListeMetadonneeType metadonnees,
         String code,
         String valeur) {
      
      MetadonneeType metaType = new MetadonneeType();
      
      MetadonneeCodeType metaCodeType = new MetadonneeCodeType() ;
      metaCodeType.setMetadonneeCodeType(code);
      metaType.setCode(metaCodeType);
      
      MetadonneeValeurType metaValeurType = new MetadonneeValeurType();
      metaValeurType.setMetadonneeValeurType(valeur);
      metaType.setValeur(metaValeurType);
      
      metadonnees.addMetadonnee(metaType);
      
   }
   
   
   public static void displayMetadonnees(
         ListeMetadonneeType metadonnees) {
      
      if (metadonnees==null) {
         System.out.println("La liste des métadonnées est null");
      } else {
         
         MetadonneeType[] tabMetaTypes = metadonnees.getMetadonnee();
         
         if (tabMetaTypes==null) {
            System.out.println("La liste des métadonnées est null");
         } else  {
         
            System.out.println("Nombre de métadonnées : " + tabMetaTypes.length);
            System.out.println("Liste des métadonnées :");
            
            String code;
            String valeur;
            for(MetadonneeType metaType: tabMetaTypes) {
               code = metaType.getCode().getMetadonneeCodeType();
               valeur = metaType.getValeur().getMetadonneeValeurType();
               System.out.println(code + " = " + valeur);
            }
         
         }
         
      }
      
   }
   
   public static void displayResultatRecherche(
         ListeResultatRechercheType resultatsRecherche) {
      
      if (resultatsRecherche==null) {
         System.out.println("Les résultats de recherche sont null");
      } else {
         
         ResultatRechercheType[] tabResRechTypes = resultatsRecherche.getResultat();
         
         if (tabResRechTypes==null) {
            System.out.println("Les résultats de recherche sont null");
         }
         else {
         
            System.out.println("Nombre de résultats de recherche : " + tabResRechTypes.length);
            System.out.println("Liste des résultats de recherche :");
            System.out.println();
            
            String uuid;
            for(ResultatRechercheType resRechType: tabResRechTypes) {
               
               uuid = WsTypeUtils.extractUuid(resRechType.getIdArchive());
               System.out.println("IdArchive = " + uuid);
               
               displayMetadonnees(resRechType.getMetadonnees());
               
               System.out.println();
               
            }
         
         }
         
      }
      
   }
   
   
   public static void addResultatRecherche(
         ListeResultatRechercheType resultatsRecherche,
         String idArchive,
         ListeMetadonneeType metadonnees) {
      
      
      ResultatRechercheType resRechType = new ResultatRechercheType(); 
      
      resRechType.setIdArchive(WsTypeUtils.buildUuid(idArchive));
      
      resRechType.setMetadonnees(metadonnees);
      
      resultatsRecherche.addResultat(resRechType);
      
   }
   
   
   public static DataHandler getObjetNumeriqueAsContenu(
         ConsultationResponseType consultationResponse) {
      
      return consultationResponse.getObjetNumerique()
            .getObjetNumeriqueConsultationTypeChoice_type0().getContenu();
      
   }
   

}
