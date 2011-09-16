package com.docubase.dfce.toolkit.client.administration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.docubase.dfce.toolkit.base.AbstractBaseTestCase;
import com.docubase.dfce.toolkit.client.AbstractDFCEToolkitClientTest;

public class AccessControlClientTest extends AbstractDFCEToolkitClientTest {
    @Before
    public void before() {
	if (!serviceProvider.isSessionActive()) {
	    serviceProvider.connect(AbstractBaseTestCase.ADM_LOGIN,
		    AbstractBaseTestCase.ADM_PASSWORD,
		    AbstractBaseTestCase.SERVICE_URL);
	}
    }

    @Test
    public void testIsUp() {
	boolean serverUp = serviceProvider.isServerUp();
	Assert.assertTrue(serverUp);
    }

    @Test
    public void testIsUpClosedSession() {
	serviceProvider.disconnect();
	boolean sessionActive = serviceProvider.isServerUp();
	Assert.assertFalse(sessionActive);
    }

    @Test
    public void testIsSessionActive() throws SecurityException,
	    IllegalArgumentException {
	boolean sessionActive = serviceProvider.isSessionActive();
	Assert.assertTrue(sessionActive);
    }
}
