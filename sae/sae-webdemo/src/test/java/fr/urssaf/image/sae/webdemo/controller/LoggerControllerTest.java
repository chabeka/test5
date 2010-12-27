package fr.urssaf.image.sae.webdemo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webdemo.ControllerTestSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@SuppressWarnings("PMD")
public class LoggerControllerTest extends
      ControllerTestSupport<LoggerController> {

   @Autowired
   private LoggerController servlet;

   private static final String START_FIELD = "start";

   private static final String LIMIT_FIELD = "limit";

   private static final String SORT_FIELD = "sort";

   private static final String DIR_FIELD = "dir";

   private static final String PARAM_START = "start";

   private static final String PARAM_END = "end";

   @Test(expected = IllegalStateException.class)
   public void loggerException() {

      new LoggerController(null);

   }

   @Test
   public void main() {

      this.initGet();

      assertEquals("registre_exploitation", this.handle(servlet).getViewName());

   }

   @Test
   public void search() throws JsonParseException, JsonMappingException,
         IOException {

      this.initPost();
      this.setURI("/registre_exploitation/search.html");

      this.initParameter(START_FIELD, "0");
      this.initParameter(LIMIT_FIELD, "25");
      this.initParameter(SORT_FIELD, "idseq");
      this.initParameter(DIR_FIELD, "DESC");

      String body = this.json(servlet);

      ObjectMapper mapper = new ObjectMapper();
      JsonNode results = mapper.readValue(body, JsonNode.class);

      assertEquals(1000, results.get("totalCount").getIntValue());
      results.get("logs").iterator();

   }

   @Test
   public void filterSuccess() throws JsonParseException, JsonMappingException,
         IOException {

      this.initPost();
      this.setURI("/registre_exploitation/filter.html");

      this.initParameter(PARAM_START, "05/12/1998");
      this.initParameter(PARAM_END, "10/01/1999");

      String body = this.json(servlet);

      ObjectMapper mapper = new ObjectMapper();
      JsonNode results = mapper.readValue(body, JsonNode.class);

      assertEquals(true, results.get("success").getBooleanValue());

   }

   @Test
   public void filterFailure_date_type() throws JsonParseException,
         JsonMappingException, IOException {

      this.initPost();
      this.setURI("/registre_exploitation/filter.html");

      this.initParameter(PARAM_START, "01-10-1990");
      this.initParameter(PARAM_END, "10/13/1998");

      String body = this.json(servlet);

      ObjectMapper mapper = new ObjectMapper();
      JsonNode results = mapper.readValue(body, JsonNode.class);

      assertEquals(false, results.get("success").getBooleanValue());
      assertEquals("form_failure_msg", results.get("errorMessage")
            .getTextValue());

      ObjectNode errors = (ObjectNode) results.get("errors");
      System.out.println(errors);

      assertTrue(errors.get("start").getTextValue(), errors.get("start")
            .getTextValue().contains(ConversionFailedException.class.getName()));
      assertTrue(errors.get("end").getTextValue(), errors.get("start")
            .getTextValue().contains(ConversionFailedException.class.getName()));

      assertEquals(2, errors.size());

   }

   @Test
   public void filterFailure_date_range() throws JsonParseException,
         JsonMappingException, IOException {

      this.initPost();
      this.setURI("/registre_exploitation/filter.html");

      this.initParameter(PARAM_START, "05/12/1998");
      this.initParameter(PARAM_END, "10/01/1997");

      String body = this.json(servlet);

      ObjectMapper mapper = new ObjectMapper();
      JsonNode results = mapper.readValue(body, JsonNode.class);

      assertEquals(false, results.get("success").getBooleanValue());
      assertEquals("form_failure_msg", results.get("errorMessage")
            .getTextValue());

      ObjectNode errors = (ObjectNode) results.get("errors");
      assertEquals("error.validDate", errors.get("end").getTextValue());

      assertEquals(1, errors.size());

   }

}
