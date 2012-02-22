package fr.urssaf.image.commons.cassandra.spring.batch.serializer;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.batch.item.ExecutionContext;

import fr.urssaf.image.commons.cassandra.serializer.ObjectToJsonSerializer;

import me.prettyprint.cassandra.serializers.AbstractSerializer;

/**
 * Classe de sérialisation/désérialisation des ExecutionContext
 * Elle utilise un sérialiser json.
 */
public class ExecutionContextSerializer extends
      AbstractSerializer<ExecutionContext> {
   
   private static final ExecutionContextSerializer INSTANCE = new ExecutionContextSerializer();

   @SuppressWarnings("unchecked")
   @Override
   /** {@inheritDoc} */
   public final ExecutionContext fromByteBuffer(ByteBuffer byteBuffer) {
      Map<String, Object> map = (Map<String, Object>) ObjectToJsonSerializer
            .get().fromByteBuffer(byteBuffer);
      return new ExecutionContext(map);
   }

   @Override
   /** {@inheritDoc} */
   public final ByteBuffer toByteBuffer(ExecutionContext executionContext) {
      Map<String, Object> map = new HashMap<String, Object>();
      for (Entry<String, Object> me : executionContext.entrySet()) {
         map.put(me.getKey(), me.getValue());
      }
      return ObjectToJsonSerializer.get().toByteBuffer(map);
   }

   /**
    * Renvoie un singleton
    * @return  singleton
    */
   public static ExecutionContextSerializer get() {
      return INSTANCE;
   }

}
