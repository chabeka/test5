package fr.urssaf.image.sae.anais.framework.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;

@SuppressWarnings("PMD")
public class SaeAnaisServiceTest {

   private SaeAnaisService service;

   private static final String NOTEMPTY = "not empty";

   @Before
   public void initService() {
      service = new SaeAnaisService();
   }

   @Test
   public void environnementEmpty() {

      try {
         service.authentifierPourSaeParLoginPassword(null,
               new SaeAnaisAdresseServeur(), NOTEMPTY, NOTEMPTY, NOTEMPTY,
               NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (IllegalArgumentException e) {
         assertEquals(
               "L’environnement (Développement / Validation  / Production) doit être renseigné",
               e.getMessage());
      }

   }

   @Test
   public void loginEmpty() {

      try {
         service
               .authentifierPourSaeParLoginPassword(
                     SaeAnaisEnumCodesEnvironnement.Developpement,
                     new SaeAnaisAdresseServeur(), null, NOTEMPTY, NOTEMPTY,
                     NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (IllegalArgumentException e) {
         assertEquals("L’identifiant de l’utilisateur doit être renseigné", e
               .getMessage());
      }

   }

   @Test
   public void passwordEmpty() {

      try {
         service
               .authentifierPourSaeParLoginPassword(
                     SaeAnaisEnumCodesEnvironnement.Developpement,
                     new SaeAnaisAdresseServeur(), NOTEMPTY, null, NOTEMPTY,
                     NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (IllegalArgumentException e) {
         assertEquals("Le mot de passe de l’utilisateur doit être renseigné", e
               .getMessage());
      }

   }

   @Test
   public void HostEmpty() {

      SaeAnaisAdresseServeur serveur = new SaeAnaisAdresseServeur();
      serveur.setHote(null);
      serveur.setPort(1352);

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur, NOTEMPTY,
               NOTEMPTY, NOTEMPTY, NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (IllegalArgumentException e) {
         assertEquals(
               "L’adresse IP ou le nom d’hôte du serveur ANAIS doit être renseigné dans les paramètres de connexion",
               e.getMessage());
      }

   }

   @Test
   public void PortEmpty() {

      SaeAnaisAdresseServeur serveur = new SaeAnaisAdresseServeur();
      serveur.setHote("hostname");
      serveur.setPort(null);

      try {
         service.authentifierPourSaeParLoginPassword(
               SaeAnaisEnumCodesEnvironnement.Developpement, serveur, NOTEMPTY,
               NOTEMPTY, NOTEMPTY, NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (IllegalArgumentException e) {
         assertEquals("Le port du serveur ANAIS doit être renseigné dans les paramètres de connexion", e
               .getMessage());
      }

   }

}
