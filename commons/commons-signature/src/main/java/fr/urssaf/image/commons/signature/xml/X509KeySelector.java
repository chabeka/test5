package fr.urssaf.image.commons.signature.xml;

import java.security.Key;
import java.security.PublicKey;
import java.util.Iterator;

import java.security.cert.X509Certificate;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;

class X509KeySelector extends KeySelector {
   
   @SuppressWarnings("unchecked")
   public KeySelectorResult select(KeyInfo keyInfo,
         KeySelector.Purpose purpose, AlgorithmMethod method,
         XMLCryptoContext context) throws KeySelectorException {
      Iterator keyInfoIterator = keyInfo.getContent().iterator();
      while (keyInfoIterator.hasNext()) {
         XMLStructure info = (XMLStructure) keyInfoIterator.next();
         if (!(info instanceof X509Data)) {
            continue;
         }
         X509Data x509Data = (X509Data) info;
         Iterator x509iterator = x509Data.getContent().iterator();
         while (x509iterator.hasNext()) {
            Object obj = x509iterator.next();
            if (!(obj instanceof X509Certificate)) {
               continue;
            }
            final PublicKey key = ((X509Certificate) obj).getPublicKey();
            // Make sure the algorithm is compatible
            // with the method.
            if (algEquals(method.getAlgorithm(), key.getAlgorithm())) {
               return new KeySelectorResult() {
                  public Key getKey() {
                     return key;
                  }
               };
            }
         }
      }
      throw new KeySelectorException("No key found!");
   }

   private static boolean algEquals(String algURI, String algName) {
      return ((algName.equalsIgnoreCase("DSA") && algURI
            .equalsIgnoreCase(SignatureMethod.DSA_SHA1))
            || (algName.equalsIgnoreCase("RSA") && algURI
                  .equalsIgnoreCase(SignatureMethod.RSA_SHA1))) ;
   }

}
