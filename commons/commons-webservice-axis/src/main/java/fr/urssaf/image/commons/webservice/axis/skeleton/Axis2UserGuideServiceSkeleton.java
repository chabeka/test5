/**
 * Axis2UserGuideServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
package fr.urssaf.image.commons.webservice.axis.skeleton;

import org.apache.axis2.axis2userguide.DoInOnlyRequest;
import org.apache.axis2.axis2userguide.MultipleParametersAddItemRequest;
import org.apache.axis2.axis2userguide.MultipleParametersAddItemResponse;
import org.apache.axis2.axis2userguide.NoParametersRequest;
import org.apache.axis2.axis2userguide.NoParametersResponse;
import org.apache.axis2.axis2userguide.TwoWayOneParameterEchoRequest;
import org.apache.axis2.axis2userguide.TwoWayOneParameterEchoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.urssaf.image.commons.webservice.axis.service.UserGuideService;

/**
 * Axis2UserGuideServiceSkeleton java skeleton for the axisService
 */
@Component
public class Axis2UserGuideServiceSkeleton {

   private final UserGuideService service;
   
   @Autowired
   public Axis2UserGuideServiceSkeleton(UserGuideService service) {
      this.service = service;
   }

   /**
    * Auto generated method signature
    * 
    * @param doInOnlyRequest
    */

   public void doInOnly(DoInOnlyRequest request) {
     
      service.doInOnly(request);

   }

   /**
    * Auto generated method signature
    * 
    * @param twoWayOneParameterEchoRequest
    */

   public TwoWayOneParameterEchoResponse twoWayOneParameterEcho(
         TwoWayOneParameterEchoRequest request) {

      return service.twoWayOneParameterEcho(request);
   }

   /**
    * Auto generated method signature
    * 
    * @param noParametersRequest
    */

   public NoParametersResponse noParameters(
         NoParametersRequest request) {

      return service.noParameters(request);
   }

   /**
    * Auto generated method signature
    * 
    * @param multipleParametersAddItemRequest
    */

   public MultipleParametersAddItemResponse multipleParametersAddItem(
         MultipleParametersAddItemRequest request) {

      return service
            .multipleParametersAddItem(request);
   }

}