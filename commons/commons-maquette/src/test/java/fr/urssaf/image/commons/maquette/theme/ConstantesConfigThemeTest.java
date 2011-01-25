package fr.urssaf.image.commons.maquette.theme;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.urssaf.image.commons.util.exceptions.TestConstructeurPriveException;
import fr.urssaf.image.commons.util.tests.TestsUtils;


/**
 * Tests unitaires de la classe {@link ConstantesConfigTheme}
 *
 */
@SuppressWarnings("PMD")
public class ConstantesConfigThemeTest {

   
   /**
    * Test du constructeur privé, pour le code coverage
    * 
    * @throws TestConstructeurPriveException 
    */
   @Test
   public void constructeurPrive() throws TestConstructeurPriveException {
      Boolean result = TestsUtils.testConstructeurPriveSansArgument(ConstantesConfigTheme.class);
      assertTrue("Le constructeur privé n'a pas été trouvé",result);

   }
   
}
