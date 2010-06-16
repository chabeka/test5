package fr.urssaf.image.commons.file.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.xml.sax.SAXException;

import fr.urssaf.image.commons.file.xml.XSDValidator.SAXParseExceptionType;

public class XSDValidatorTest {

	private static final Logger log = Logger.getLogger(XSDValidatorTest.class);

	private static final String xsdFile = "C:/info.xsd";

	@Test
	public void validatorInfoByDOMSuccess() {
		String xmlFile = "src/test/resources/info_success.xml";
		assertSuccess(xmlFile, xsdFile);
	}

	@Test
	public void validatorInfoByDOMFailure() {
		String xmlFile = "src/test/resources/info_failure.xml";
		try {
			List<SAXParseExceptionType> exceptions = XSDValidator
					.validXMLWithDOM(xmlFile, xsdFile);

			SAXParseExceptionType exception = exceptions.get(0);

			assertEquals("erreur de type", "ERROR", exception.type.getLabel());
			assertEquals("erreur de ligne", 10, exception.exception
					.getLineNumber());
			assertEquals(
					"erreur de message",
					"cvc-complex-type.2.4.d: Invalid content starting with element 'baliseinconnue'. No child element is expected at this point.",
					exception.exception.getMessage());

			assertEquals(1, exceptions.size());

		} catch (ParserConfigurationException e) {
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		} catch (SAXException e) {
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}

	}

	private void assertSuccess(String xmlFile, String xsdFile) {

		try {
			assertTrue(XSDValidator.validXMLWithDOM(xmlFile, xsdFile).isEmpty());
		} catch (ParserConfigurationException e) {

			log.error(e.getMessage(), e);
			fail(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		} catch (SAXException e) {
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}

}
