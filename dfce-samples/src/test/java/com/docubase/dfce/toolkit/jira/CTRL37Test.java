package com.docubase.dfce.toolkit.jira;

import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.service.ServiceProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.docubase.dfce.toolkit.base.AbstractBaseTestCase;

@RunWith(JUnit4.class)
public class CTRL37Test {
    public static final String BASE_ID = "RICHGED";

    private static final String ADM_LOGIN = "_ADMIN";
    private static final String ADM_PASSWORD = "DOCUBASE";

    private static ServiceProvider serviceProvider = ServiceProvider
	    .newServiceProvider();

    @Test
    public void getBaseMultiThread() {
	serviceProvider.connect(ADM_LOGIN, ADM_PASSWORD,
		AbstractBaseTestCase.SERVICE_URL);
	Base base = serviceProvider.getBaseAdministrationService().getBase(
		BASE_ID);

	System.out.println(base + " " + Thread.currentThread().getName());

	Thread t = new Thread() {
	    @Override
	    public void run() {
		System.out.println(Thread.currentThread().getName());
		serviceProvider.getBaseAdministrationService().getBase(BASE_ID);
	    };
	};
	t.start();
	serviceProvider.disconnect();
    }

    @Test
    public void getBaseMultiThreadWithAuth() {
	serviceProvider.connect(ADM_LOGIN, ADM_PASSWORD,
		AbstractBaseTestCase.SERVICE_URL);
	Base base = serviceProvider.getBaseAdministrationService().getBase(
		BASE_ID);
	System.out.println(base + " " + Thread.currentThread().getName());

	Thread t = new Thread() {
	    @Override
	    public void run() {
		System.out.println(Thread.currentThread().getName());
		serviceProvider.getBaseAdministrationService().getBase(BASE_ID);
	    };
	};
	t.start();
	serviceProvider.disconnect();
    }
}
