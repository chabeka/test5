package fr.urssaf.image.commons.xml.castor.fieldhandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

public class DateHandler extends GeneralizedFieldHandler {

   private static final String FORMAT = "dd/MM/yyyy";

   @Override
   public Object convertUponGet(Object value) {

      String obj = null;

      if (value != null) {
         SimpleDateFormat formatter = new SimpleDateFormat(FORMAT,Locale.getDefault());
         Date date = (Date) value;
         obj = formatter.format(date);
      }

      return obj;
   }

   @Override
   public Object convertUponSet(Object value) {
      SimpleDateFormat formatter = new SimpleDateFormat(FORMAT,Locale.getDefault());
      Date date = null;
      try {
         date = formatter.parse((String) value);
      } catch (ParseException e) {
         throw new IllegalArgumentException(e);
      }
      return date;
   }

   @SuppressWarnings("unchecked")
   @Override
   public Class getFieldType() {
      return Date.class;
   }

}
