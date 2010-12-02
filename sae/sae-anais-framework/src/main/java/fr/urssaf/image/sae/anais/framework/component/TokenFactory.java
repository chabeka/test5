package fr.urssaf.image.sae.anais.framework.component;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import fr.urssaf.image.sae.anais.framework.modele.DroitApplicatif;
import fr.urssaf.image.sae.anais.framework.schema.DroitType;
import fr.urssaf.image.sae.anais.framework.schema.IdentiteUtilisateurType;
import fr.urssaf.image.sae.anais.framework.schema.ObjectFactory;
import fr.urssaf.image.sae.anais.framework.schema.PerimetreType;
import fr.urssaf.image.sae.anais.framework.schema.SaeJetonAuthentificationType;

/**
 * Classe de création de jeton de sécurité<br>
 * Ce jeton est un chaine de caractère identique au contenu d'un fichier XML<br>
 * <br>
 * le schéma du jeton de sécurité se situe dans META-INF/sae-anais.xsd<br>
 * <br>
 * La manipulation du XML s'appuie sur le Framework <a
 * href='https://jaxb.dev.java.net//'>JAXB2</a><br>
 * <br>
 * Il est nécessaire de générer les classes du schéma sae-anais.xsd en lançant
 * la commande <code>mvn generate-sources -PgenerateJaxb</code><br>
 * 
 * 
 */
public class TokenFactory {

   /**
    * Méthode de création d'un jeton de sécurité
    * 
    * @param lastname
    *           nom de l'utilisateur
    * @param firstname
    *           prénom de l'utilisateur
    * @param droits
    *           liste des drois applicatifs de l'utilisateur pour une
    *           application donnée
    * @return contenu d'un schéma XML
    * @throws IllegalStateException
    */
   public final String createTokenSecurity(String lastname, String firstname,
         List<DroitApplicatif> droits) {

      // utilisation de jaxb2 pour créer un fichier xml
      ObjectFactory factory = new ObjectFactory();

      SaeJetonAuthentificationType jeton = factory
            .createSaeJetonAuthentificationType();

      IdentiteUtilisateurType idUser = factory.createIdentiteUtilisateurType();
      idUser.setNom(lastname);
      idUser.setPrenom(firstname);
      jeton.setIdentiteUtilisateur(idUser);

      if (!droits.isEmpty()) {
         jeton.setDroits(factory.createDroitsType());
         for (DroitApplicatif droit : droits) {

            DroitType droitType = factory.createDroitType();
            droitType.setPerimetre(factory.createPerimetreType());
            droitType.setCode(droit.getCode());

            PerimetreType perimetre = droitType.getPerimetre();
            perimetre.setValeur(droit.getPerimetreValue());
            perimetre.setCodeType(droit.getPerimetreType());

            jeton.getDroits().getDroit().add(droitType);
         }
      }

      try {
         JAXBContext context = JAXBContext
               .newInstance(SaeJetonAuthentificationType.class);

         Marshaller marshaller = context.createMarshaller();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

         StringWriter writer = new StringWriter();

         marshaller.marshal(factory.createSaeJetonAuthentification(jeton),
               writer);

         return writer.toString();
      } catch (JAXBException e) {
         throw new IllegalStateException(e);
      }

   }
}
