package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.MetadonneeType;
import fr.cirtil.www.saeservice.Recherche;
import fr.cirtil.www.saeservice.RechercheResponseType;
import fr.cirtil.www.saeservice.ResultatRechercheType;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service.xml",
      "/applicationContext-security-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class RechercheTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   private MessageContext ctx;

   @Before
   public void before() {

      ctx = new MessageContext();
      MessageContext.setCurrentMessageContext(ctx);

   }

   // private static final String UUID_META = "UUID";

   private static final String CODE_RND_META = "CodeRND";

   private static final String COTISANT_META = "NumeroCotisant";

   private static final String SIRET_META = "Siret";

   private static final String COMPTE_META = "DenominationCompte";

   private static final String ORGANISME_META = "CodeOrganisme";

   @Test
   public void recherche_success() throws AxisFault {

      Axis2Utils.initMessageContext(ctx,
            "src/test/resources/request/recherche_success.xml");

      Recherche request = new Recherche();

      RechercheResponseType response = skeleton.rechercheSecure(request)
            .getRechercheResponse();

      ResultatRechercheType[] resultats = response.getResultats().getResultat();

      assertEquals("nombre de resultats inattendu", 3, resultats.length);

      Map<String, String> metas1 = new HashMap<String, String>();
      // metas1.put(UUID_META, "110E8400-E29B-11D4-A716-446655440000");
      metas1.put(CODE_RND_META, "3.1.3.1.1");
      metas1.put(COTISANT_META, "704815");
      metas1.put(SIRET_META, "49980055500017");
      metas1.put(COMPTE_META, "SPOHN ERWAN MARIE MAX");
      metas1.put(ORGANISME_META, "UR030");
      // metas1.put("DateOrigine", "2011-06-03");

      assertResultatRechercheType(resultats[0],
            "110E8400-E29B-11D4-A716-446655440000", metas1);

      Map<String, String> metas2 = new HashMap<String, String>();
      // metas2.put(UUID_META, "510E8200-E29B-18C4-A716-446677440120");
      metas2.put(CODE_RND_META, "1.A.X.X.X");
      metas2.put(COTISANT_META, "723804");
      metas2.put(SIRET_META, "07413151710009");
      metas2.put(COMPTE_META, "CHEVENIER ANDRE");
      metas2.put(ORGANISME_META, "UR030");

      assertResultatRechercheType(resultats[1],
            "510E8200-E29B-18C4-A716-446677440120", metas2);

      Map<String, String> metas3 = new HashMap<String, String>();
      // metas3.put(UUID_META, "48758200-A29B-18C4-B616-455677840120");
      metas3.put(CODE_RND_META, "1.2.3.3.1");
      metas3.put(COTISANT_META, "719900");
      metas3.put(SIRET_META, "07412723410007");
      metas3.put(COMPTE_META, "COUTURIER GINETTE");
      metas3.put(ORGANISME_META, "UR030");

      assertResultatRechercheType(resultats[2],
            "48758200-A29B-18C4-B616-455677840120", metas3);

   }

   private static void assertResultatRechercheType(
         ResultatRechercheType resultat, String uuid,
         Map<String, String> metadonnees) {

      assertEquals("nombre de resultats inattendu", uuid, resultat
            .getIdArchive().getUuidType());

      MetadonneeType[] metas = resultat.getMetadonnees().getMetadonnee();

      for (MetadonneeType metadonnee : metas) {

         String code = metadonnee.getCode().getMetadonneeCodeType();

         assertTrue("la métadonnée '" + code + "' n'est pas présente dans "
               + uuid, metadonnees.containsKey(code));
         assertEquals("la valeur de la métadonnée '" + code
               + "' est incorrecte dans " + uuid, metadonnees.get(code),
               metadonnee.getValeur().getMetadonneeValeurType());
      }

      assertEquals("nombre de metadonnees inattendu", metadonnees.size(),
            metas.length);

   }
}
