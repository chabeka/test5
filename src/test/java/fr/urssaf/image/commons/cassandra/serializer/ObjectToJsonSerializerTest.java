package fr.urssaf.image.commons.cassandra.serializer;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;

public class ObjectToJsonSerializerTest {

   @SuppressWarnings("unchecked")
   @Test
   public void test() throws UnsupportedEncodingException {
      ObjectToJsonSerializer os = new ObjectToJsonSerializer();
      
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("element1", 1);
      map.put("element2", "deux");
      
      byte[] bytes = os.toBytes(map);
      String json = new String(bytes, "UTF8");
      
      System.out.println(json);
      
      HashMap<String, Object> map2 = (HashMap<String, Object>) os.fromBytes(bytes);
      Assert.assertEquals(1, map2.get("element1"));
      Assert.assertEquals("deux", map2.get("element2"));
   }

}
