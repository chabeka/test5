package fr.urssaf.image.sae.vi.component;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.sae.vi.modele.DroitApplicatif;
import fr.urssaf.image.sae.vi.modele.ObjectFactory;

@SuppressWarnings("PMD")
public class TokenFactoryTest {

   private TokenFactory factory;

   @Before
   public void before() {
      factory = new TokenFactory();
   }

   @Test
   public void createTokenSecurity() throws IOException {

      List<DroitApplicatif> droits = new ArrayList<DroitApplicatif>();

      droits.add(this.createDroitApplicatif("Code 1", "Type 1", "Value 1"));
      droits.add(this.createDroitApplicatif("Code 2", "Type 2", "Value 2"));

      String token = factory.createTokenSecurity("Nom", "Prenom", droits);

      File file = new File("src/test/resources/ctd_2_rights.xml");

      assertEquals(FileUtils.readFileToString(file, "UTF-8"), token);
   }

   private DroitApplicatif createDroitApplicatif(String code, String type,
         String value) {

      DroitApplicatif droit = ObjectFactory.createDroitAplicatif();
      droit.setCode(code);
      droit.setPerimetreType(type);
      droit.setPerimetreValue(value);

      return droit;

   }
}
