package fr.urssaf.image.commons.controller.spring;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateArrayTest;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateCheckTest;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateCollectionTest;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateFormTest;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateMapTest;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateRuleTest;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateValidTest;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ResetFormTest;

@RunWith(Suite.class)
@SuiteClasses( { PopulateCollectionTest.class, PopulateCheckTest.class,
		PopulateMapTest.class, PopulateArrayTest.class, PopulateRuleTest.class,
		PopulateValidTest.class,PopulateFormTest.class,ResetFormTest.class })
public class ObjectAllTests {

}
