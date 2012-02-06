package fr.urssaf.image.commons.spring.batch.support.stax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public final class XSDValidator {

   private XSDValidator() {

   }

   private static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

   private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

   private static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

   private static final Logger LOGGER = Logger.getLogger(XSDValidator.class);

   public enum ExceptionType {

      fatalError("FATAL ERROR"),

      error("ERROR"),

      warning("WARNING");

      private String label;

      ExceptionType(String label) {
         this.label = label;
      }

      public String getLabel() {
         return this.label;
      }
   }

   public static final class SAXParseExceptionType {

      private final SAXParseException exception;

      private final ExceptionType type;

      public SAXParseExceptionType(SAXParseException exception,
            ExceptionType type) {

         this.exception = exception;
         this.type = type;

      }

      public SAXParseException getException() {
         return this.exception;
      }

      public ExceptionType getType() {
         return this.type;
      }

   }

   private static class SaxErrorHandler extends DefaultHandler {

      private final List<SAXParseExceptionType> errors;

      SaxErrorHandler(List<SAXParseExceptionType> errors) {
         super();
         this.errors = errors;
      }

      @Override
      public void warning(SAXParseException exception) {
         errors
               .add(new SAXParseExceptionType(exception, ExceptionType.warning));
      }

      @Override
      public void error(SAXParseException exception) {
         errors.add(new SAXParseExceptionType(exception, ExceptionType.error));
      }

      @Override
      public void fatalError(SAXParseException exception) {
         errors.add(new SAXParseExceptionType(exception,
               ExceptionType.fatalError));
      }

   }

   public static List<SAXParseExceptionType> validXMLFileWithSAX(File xmlFile,
         File xsdFile) throws SAXException, IOException,
         ParserConfigurationException {

      final ArrayList<SAXParseExceptionType> errors = new ArrayList<SAXParseExceptionType>();

      SAXParser saxParser = factorySAXParser(xsdFile);

      saxParser.parse(xmlFile, new SaxErrorHandler(errors));
      return errors;

   }

   private static SAXParser factorySAXParser(File xsdFile)
         throws ParserConfigurationException, SAXException {

      SAXParserFactory spf = SAXParserFactory.newInstance();

      spf.setNamespaceAware(true);
      spf.setValidating(true);

      SAXParser saxParser = spf.newSAXParser();
      saxParser.setProperty(SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
      saxParser.setProperty(SCHEMA_SOURCE, xsdFile.getAbsolutePath());

      return saxParser;
   }

   public static void afficher(List<SAXParseExceptionType> liste) {
      Iterator<SAXParseExceptionType> iter = liste.iterator();
      while (iter.hasNext()) {
         SAXParseExceptionType type = iter.next();
         String log = type.type.getLabel() + " ligne:"
               + type.exception.getLineNumber() + " "
               + type.exception.getMessage();
         if (iter.hasNext()) {
            LOGGER.debug(log);
         } else {
            LOGGER.debug(log + "\n");
         }
      }

   }

}
