package fr.urssaf.image.commons.file.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Bertrand BARAULT
 * 
 */

public final class XSDValidator {

	private XSDValidator() {

	}

	private static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	private static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	private static final Logger logger = Logger.getLogger(XSDValidator.class);

	public enum ExceptionType {
		fatalError("FATAL ERROR"), error("ERROR"), warning("WARNING");

		protected String label;

		/** Constructeur */
		ExceptionType(String pLabel) {
			this.label = pLabel;
		}

		public String getLabel() {
			return this.label;
		}
	}

	/**
	 * Méthode permettant de tester la validité d'un fichier En cas d'erreur de
	 * validation
	 * 
	 * @param xmlFile
	 *            : nom et emplacement du fichier XML à tester
	 * @param xsdFile
	 *            : nom du ficier XSD de référance
	 * @return List<SAXParseExceptionType> : renvoie une liste des
	 *         SAXParseException avec le type d'execption
	 *         error,fatalError,warning
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static List<SAXParseExceptionType> validXMLWithDOM(String xmlFile,
			String xsdFile) throws ParserConfigurationException, IOException,
			SAXException {

		final ArrayList<SAXParseExceptionType> errors = new ArrayList<SAXParseExceptionType>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		//dbf.setCoalescing(true);
		//dbf.setExpandEntityReferences(true);
		dbf.setIgnoringComments(true);
		dbf.setNamespaceAware(true);
		dbf.setValidating(true);

		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setAttribute(SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		dbf.setAttribute(SCHEMA_SOURCE, xsdFile);
		
		// Parsage du fichier XML avec DOM
		DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
		documentBuilder.setErrorHandler(new DefaultHandler() {

			@Override
			public void fatalError(SAXParseException exception) {
				errors.add(new SAXParseExceptionType(exception,
						ExceptionType.fatalError));
			}

			@Override
			public void error(SAXParseException exception) {
				errors.add(new SAXParseExceptionType(exception,
						ExceptionType.error));
			}

			@Override
			public void warning(SAXParseException exception) {
				errors.add(new SAXParseExceptionType(exception,
						ExceptionType.warning));
			}

		});
		
		documentBuilder.parse(xmlFile);

		return errors;
	}

	public static List<SAXParseExceptionType> validXMLWithSAX(String xmlFile,
			String xsdFile) throws SAXException, IOException,
			ParserConfigurationException {

		final ArrayList<SAXParseExceptionType> errors = new ArrayList<SAXParseExceptionType>();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setValidating(true);
		SAXParser saxParser = spf.newSAXParser();
		saxParser.setProperty(SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		saxParser.setProperty(SCHEMA_SOURCE, xsdFile);
		saxParser.parse(xmlFile, new DefaultHandler() {
			@Override
			public void fatalError(SAXParseException exception) {
				errors.add(new SAXParseExceptionType(exception,
						ExceptionType.fatalError));
			}

			@Override
			public void error(SAXParseException exception) {
				errors.add(new SAXParseExceptionType(exception,
						ExceptionType.error));
			}

			@Override
			public void warning(SAXParseException exception) {
				errors.add(new SAXParseExceptionType(exception,
						ExceptionType.warning));
			}
		});

		return errors;
	}

	public static class SAXParseExceptionType {

		public final SAXParseException exception;
		public final ExceptionType type;

		public SAXParseExceptionType(SAXParseException exception,
				ExceptionType type) {
			this.exception = exception;
			this.type = type;
			logger.fatal(exception.getMessage(), exception);
		}

	}

	public static void afficher(List<SAXParseExceptionType> liste) {
		Iterator<SAXParseExceptionType> iter = liste.iterator();
		while (iter.hasNext()) {
			SAXParseExceptionType type = iter.next();
			String log = type.type.getLabel() + " ligne:"
					+ type.exception.getLineNumber() + " "
					+ type.exception.getMessage();
			if (iter.hasNext()) {
				logger.info(log);
			} else {
				logger.info(log + "\n");
			}
		}

	}

}
