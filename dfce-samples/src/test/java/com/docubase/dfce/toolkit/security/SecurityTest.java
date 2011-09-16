package com.docubase.dfce.toolkit.security;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.service.ServiceProvider;
import net.docubase.toolkit.service.ged.SearchService.DateFormat;

import org.junit.Test;

import com.docubase.dfce.toolkit.base.AbstractBaseTestCase;
import com.docubase.dfce.toolkit.base.AbstractTestCaseCreateAndPrepareBase;

public class SecurityTest extends AbstractTestCaseCreateAndPrepareBase {
    private ServiceProvider serviceProvider = ServiceProvider
	    .newServiceProvider();

    @Test(expected = RuntimeException.class)
    public void testWrongLogin() {
	serviceProvider.connect(AbstractBaseTestCase.ADM_LOGIN + "wrongLogin",
		AbstractBaseTestCase.ADM_PASSWORD,
		AbstractBaseTestCase.SERVICE_URL);
    }

    @Test(expected = RuntimeException.class)
    public void testMalformedURL() {
	serviceProvider.connect(AbstractBaseTestCase.ADM_LOGIN,
		AbstractBaseTestCase.ADM_PASSWORD, "malformed"
			+ AbstractBaseTestCase.SERVICE_URL);
    }

    @Test(expected = RuntimeException.class)
    public void testRoleUserOperationAdmin() {
	serviceProvider.connect(AbstractBaseTestCase.SIMPLE_USER_NAME,
		AbstractBaseTestCase.SIMPLE_USER_PASSWORD,
		AbstractBaseTestCase.SERVICE_URL);
	serviceProvider.getBaseAdministrationService().getAllBases();
    }

    @Test
    public void testRoleAdminOperationAdmin() {
	serviceProvider.connect(AbstractBaseTestCase.ADM_LOGIN,
		AbstractBaseTestCase.ADM_PASSWORD,
		AbstractBaseTestCase.SERVICE_URL);
	List<Base> allBases = serviceProvider.getBaseAdministrationService()
		.getAllBases();
	assertNotNull(allBases);
    }

    @Test
    public void testRoleUserOperationUser() {
	serviceProvider.connect(AbstractBaseTestCase.SIMPLE_USER_NAME,
		AbstractBaseTestCase.SIMPLE_USER_PASSWORD,
		AbstractBaseTestCase.SERVICE_URL);
	String formatDate = serviceProvider.getSearchService().formatDate(
		new Date(), DateFormat.DATE);
	assertNotNull(formatDate);
    }
}
