package fr.urssaf.image.sae.webservices.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.extensions.spring.receivers.ApplicationContextHolder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.springframework.core.io.Resource;

import fr.urssaf.image.sae.vi.modele.VISignVerifParams;

/**
 * Classe utilitaires pour la mise en place du contexte de sécurité
 */
public final class SecurityUtils {

   
   private SecurityUtils() {

   }

   
   /**
    * Chargement d'un certificat X509 depuis un fichier de ressource
    *
    * @param inputStream le stream représentant le certificat X509
    * 
    * @return l'objet certificat X509
    */
   public static X509Certificate loadCertificat(
         InputStream inputStream) {
      
      try {
         
         CertificateFactory certifFactory = CertificateFactory.getInstance("X.509");
         
         X509Certificate cert = (X509Certificate) certifFactory.generateCertificate(
               inputStream);
         return cert;
      
      } catch (CertificateException e) {
         throw new SecurityException(e);
      }
   }
   
   
   /**
    * Chargement d'une CRL depuis un fichier de ressource
    *
    * @param inputStream le stream représentant la CRL
    * 
    * @return l'objet CRL
    */
   public static X509CRL loadCRL(
         InputStream inputStream) {
      
      try {
         
         CertificateFactory certifFactory = CertificateFactory.getInstance("X.509");
         
         X509CRL crl = (X509CRL) certifFactory.generateCRL(
               inputStream);
         return crl;
      
      } catch (CertificateException e) {
         throw new SecurityException(e);
      } catch (CRLException e) {
         throw new SecurityException(e);
      }
      
   }
  
   
   /**
    * Définit les certificats des AC racine d'un objet VISignVerifParams
    * en les chargeant depuis les fichiers de ressources indiqués en paramètre
    * 
    * @param signVerifParams l'objet à remplir
    * @param resources les Resources représentant les certificats de AC
    */
   public static void signVerifParamsSetCertifsAC(
         VISignVerifParams signVerifParams,
         List<Resource> resources) {
      
      List<X509Certificate> lstCertifACRacine = new ArrayList<X509Certificate>();
      
      for(Resource resource:resources) {
         try {
            lstCertifACRacine.add(loadCertificat(resource.getInputStream()));
         } catch (IOException e) {
            throw new SecurityException(e);
         }
      }
      
      signVerifParams.setCertifsACRacine(lstCertifACRacine);
      
   }
   
   
   /**
    * Définit les certificats des CRL d'un objet VISignVerifParams
    * en les chargeant depuis les fichiers de ressources indiqués en paramètre
    * 
    * @param signVerifParams l'objet à remplir
    * @param resources les Resources représentant les CRL
    */
   public static void signVerifParamsSetCRL(
         VISignVerifParams signVerifParams,
         List<Resource> resources) {
      
      List<X509CRL> crls = new ArrayList<X509CRL>();
      
      for(Resource resource:resources) {
         try {
            crls.add(loadCRL(resource.getInputStream()));
         } catch (IOException e) {
            throw new SecurityException(e);
         }
      }
      
      signVerifParams.setCrls(crls);
      
   }
   
   
   /**
    * Chargement des patterns d'issuer pour la vérification de la signature
    * électronique du VI, depuis le fichier de ressource /security/pattern_issuer.txt
    * 
    * @return les patterns d'issuer
    */
   @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
   public static List<String> loadIssuerPatterns() {
      
      Resource resource = ApplicationContextHolder.getContext().getResource(
         "classpath:security/pattern_issuer.txt");
      try {

         InputStream inStream = resource.getInputStream();
         try {
            
            List<String> patterns = IOUtils.readLines(inStream, CharEncoding.UTF_8);
            
            return patterns;
            
         }
         finally {
            if (inStream!=null) {
               inStream.close();
            }
         }
      
      } catch(IOException ex) {
         throw new RuntimeException(ex);
      }
      
   }
   

}
