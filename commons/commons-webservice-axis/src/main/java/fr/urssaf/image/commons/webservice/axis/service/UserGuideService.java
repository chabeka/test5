package fr.urssaf.image.commons.webservice.axis.service;

import org.apache.axis2.axis2userguide.DoInOnlyRequest;
import org.apache.axis2.axis2userguide.MultipleParametersAddItemRequest;
import org.apache.axis2.axis2userguide.MultipleParametersAddItemResponse;
import org.apache.axis2.axis2userguide.NoParametersRequest;
import org.apache.axis2.axis2userguide.NoParametersResponse;
import org.apache.axis2.axis2userguide.TwoWayOneParameterEchoRequest;
import org.apache.axis2.axis2userguide.TwoWayOneParameterEchoResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserGuideService {

   private static final Logger LOG = Logger.getLogger(UserGuideService.class);

   public void doInOnly(DoInOnlyRequest doInOnlyRequest) {

      LOG.debug(doInOnlyRequest.getMessageString());

   }

   public TwoWayOneParameterEchoResponse twoWayOneParameterEcho(
         TwoWayOneParameterEchoRequest twoWayOneParameterEchoRequest) {

      LOG.debug(twoWayOneParameterEchoRequest.getEchoString());

      TwoWayOneParameterEchoResponse response = new TwoWayOneParameterEchoResponse();

      response.setEchoString(twoWayOneParameterEchoRequest.getEchoString());

      return response;
   }

   public NoParametersResponse noParameters(
         NoParametersRequest noParametersRequest) {

      LOG.debug(noParametersRequest);

      NoParametersResponse response = new NoParametersResponse();

      return response;
   }

   public MultipleParametersAddItemResponse multipleParametersAddItem(
         MultipleParametersAddItemRequest multipleParametersAddItemRequest) {

      LOG.debug(multipleParametersAddItemRequest.getDescription());
      LOG.debug(multipleParametersAddItemRequest.getItemId());
      LOG.debug(multipleParametersAddItemRequest.getItemName());
      LOG.debug(multipleParametersAddItemRequest.getPrice());

      MultipleParametersAddItemResponse response = new MultipleParametersAddItemResponse();

      response.setItemId(multipleParametersAddItemRequest.getItemId());
      response.setSuccessfulAdd(true);

      return response;
   }

}
