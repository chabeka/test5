package fr.urssaf.image.tests.dfcetest;

import static java.lang.System.out;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.Base.DocumentCreationDateConfiguration;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.base.CategoryDataType;
import net.docubase.toolkit.model.reference.Category;
import net.docubase.toolkit.service.ServiceProvider;
import net.docubase.toolkit.service.administration.StorageAdministrationService;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import fr.urssaf.image.tests.dfcetest.helpers.NcotiHelper;

/**
 * Création/Suppression d'une base de type NCOTI.
 * 
 */
//@RunWith(Parameterized.class)
public class CreateNcotiTest extends AbstractNcotiTest {

   /*private int domainId;
   
   @Parameters
   public static List<Integer[]> domainIds() {
      return Arrays.asList(new Integer[][] { { 1, 0 }, { 2, 0 } });
   }

   public CreateNcotiTest(int input, int expected) {
      this.domainId = input;
      // pas besoin d'expected dans cette classe
   }*/
   
   @Test
   public void create() throws Exception {
      Base base = NcotiHelper.createOrReplaceBase(BASE_ID);
      assertNotNull(base);
      assertNotNull(base.getBaseCategories());
      assertEquals(53, base.getBaseCategories().size());
      // FIXME : comment récupérer la description ? 
      // Il y a un setter mais pas de getter
      //assertEquals("Base des cotisants", base.);
   }

   @Test
   public void drop() throws Exception {
      DocubaseHelper.dropBase(BASE_ID);
      try {
         DocubaseHelper.getBase(BASE_ID);
         fail("La base " + BASE_ID + "aurait du être supprimée");
      } catch (RuntimeException e) {
         if (!StringUtils.contains(e.getMessage(), "introuvable")) {
            throw e;
         } else {
            // OK, la base a bien été supprimée
         }
      }
   }
}
