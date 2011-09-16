package com.docubase.dfce.toolkit.client;

import net.docubase.toolkit.service.ServiceProvider;

import org.junit.BeforeClass;

import com.docubase.dfce.toolkit.base.AbstractBaseTestCase;

public abstract class AbstractDFCEToolkitClientTest {
    public static ServiceProvider serviceProvider;

    @BeforeClass
    public static void beforeClass() {
	serviceProvider = ServiceProvider.newServiceProvider();
	serviceProvider.connect(AbstractBaseTestCase.ADM_LOGIN,
		AbstractBaseTestCase.ADM_PASSWORD,
		AbstractBaseTestCase.SERVICE_URL);
    }
}
