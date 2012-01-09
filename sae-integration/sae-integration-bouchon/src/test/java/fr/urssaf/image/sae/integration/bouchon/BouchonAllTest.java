package fr.urssaf.image.sae.integration.bouchon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses( { PingTest.class,PingSecureTest.class,BouchonFinJuinTest.class})
public class BouchonAllTest {

}
