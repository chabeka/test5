package fr.urssaf.image.commons.webservice.spring.ws;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.InputStream;
import java.util.Calendar;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.spring.ws.service.HumanResourceService;

public class HolidayEndpointTest {

   private Document holidayRequest;

   private HolidayEndpoint endpoint;
   
   private HumanResourceService service;

   private Calendar startCalendar;

   private Calendar endCalendar;

   @Before
   public void setUp() throws Exception {
      
      service = createMock(HumanResourceService.class);
      SAXBuilder builder = new SAXBuilder();
      InputStream is = getClass().getResourceAsStream("/holidayRequest.xml");
      try {
         holidayRequest = builder.build(is);
      } finally {
         is.close();
      }
      endpoint = new HolidayEndpoint(service);
      startCalendar = Calendar.getInstance();
      startCalendar.clear();
      startCalendar.set(2006, Calendar.JULY, 3);
      endCalendar = Calendar.getInstance();
      endCalendar.clear();
      endCalendar.set(2006, Calendar.JULY, 7);
   }

   @Test
   public void handleHolidayRequest() throws Exception {
      service.bookHoliday(startCalendar.getTime(), endCalendar.getTime(),
            "John Doe");
      replay(service);
      endpoint.handleHolidayRequest(holidayRequest.getRootElement());
      verify(service);
   }

}
