package fr.urssaf.image.sae.vi.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.sae.vi.service.WebServiceVIService;

public class WebServiceVIServiceValidateTest {

   private static final String ISSUER = "issuer";

   private static final String ID_UTILISATEUR = "id_utilisateur";

   private static final String ALIAS = "alias";

   private static final String PASSWORD = "password";

   private static WebServiceVIService service;

   @BeforeClass
   public static void beforeClass() {

      service = EasyMock.createMock(WebServiceVIService.class);
   }

   private KeyStore keystore;

   private List<String> pagm;

   @Before
   public void before() throws KeyStoreException {

      keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      pagm = Arrays.asList("PAGM_1", "", "   ", "PAGM_2", null);
   }

   @Test
   public void creerVIpourServiceWebFailure_pagm() {

      creerVIpourServiceWebFailure_pagm(null);
      creerVIpourServiceWebFailure_pagm(Arrays.asList("", " ", null));

   }

   private void creerVIpourServiceWebFailure_pagm(List<String> pagm) {

      try {
         service.creerVIpourServiceWeb(pagm, ISSUER, ID_UTILISATEUR, keystore,
               ALIAS, PASSWORD);
         fail("IllegalArgumentException attendue");
      } catch (IllegalArgumentException e) {

         assertEquals("Il faut spécifier au moins un PAGM", e.getMessage());
      }

   }

   @Test
   public void creerVIpourServiceWebFailure_issuer() {

      creerVIpourServiceWebFailure("issuer", pagm, null, ID_UTILISATEUR,
            keystore, ALIAS, PASSWORD);

   }

   @Test
   public void creerVIpourServiceWebFailure_keystore() {

      creerVIpourServiceWebFailure("keystore", pagm, ISSUER, ID_UTILISATEUR,
            null, ALIAS, PASSWORD);

   }

   @Test
   public void creerVIpourServiceWebFailure_alias() {

      creerVIpourServiceWebFailure("alias", pagm, ISSUER, ID_UTILISATEUR,
            keystore, null, PASSWORD);

   }

   @Test
   public void creerVIpourServiceWebFailure_password() {

      creerVIpourServiceWebFailure("password", pagm, ISSUER, ID_UTILISATEUR,
            keystore, ALIAS, null);

   }

   private void creerVIpourServiceWebFailure(String param, List<String> pagm,
         String issuer, String idUtilisateur, KeyStore keystore, String alias,
         String password) {

      try {
         service.creerVIpourServiceWeb(pagm, issuer, idUtilisateur, keystore,
               alias, password);
         fail("IllegalArgumentException attendue");
      } catch (IllegalArgumentException e) {

         assertEquals("Le paramètre [" + param
               + "] n'est pas renseigné alors qu'il est obligatoire", e
               .getMessage());
      }

   }

}
