package fr.urssaf.image.commons.dao.spring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

;

@RunWith(Suite.class)
@SuiteClasses( { DocumentFindDaoTest.class, DocumentModifyDaoTest.class,
		DocumentServiceTest.class, DocumentLazyLoadingTest.class})
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class DaoAllTests {

}
