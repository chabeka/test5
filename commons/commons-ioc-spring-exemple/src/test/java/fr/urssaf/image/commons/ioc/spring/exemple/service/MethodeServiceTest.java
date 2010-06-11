package fr.urssaf.image.commons.ioc.spring.exemple.service;

import static org.junit.Assert.*;

import org.junit.Test;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import fr.urssaf.image.commons.ioc.spring.exemple.service.MethodeService;

public class MethodeServiceTest {

	@Test
	public void byConstructeur() {

		assertEquals("ExempleByConstructeurMethodeServiceImpl",((MethodeService) (new XmlBeanFactory(new ClassPathResource(
				"applicationContext.xml"))).getBean("byConstructeur")).methode());

	}
	
	@Test
	public void bySetter() {

		assertEquals("ExempleBySetterMethodeServiceImpl",((MethodeService) (new XmlBeanFactory(new ClassPathResource(
				"applicationContext.xml"))).getBean("bySetter")).methode());

	}
	
}
