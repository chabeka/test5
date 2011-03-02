/**
 * Axis2UserGuideServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
package fr.urssaf.image.commons.webservice.axis.skeleton;

import org.apache.axis2.axis2userguide.MultipleParametersAddItemResponse;
import org.apache.axis2.axis2userguide.NoParametersResponse;
import org.apache.axis2.axis2userguide.TwoWayOneParameterEchoResponse;
import org.apache.log4j.Logger;

/**
 * Axis2UserGuideServiceSkeleton java skeleton for the axisService
 */
public class Axis2UserGuideServiceSkeleton implements
      Axis2UserGuideServiceSkeletonInterface {

   private static final Logger LOG = Logger
         .getLogger(Axis2UserGuideServiceSkeleton.class);

   /**
    * Auto generated method signature
    * 
    * @param doInOnlyRequest0
    */

   public void doInOnly(
         org.apache.axis2.axis2userguide.DoInOnlyRequest doInOnlyRequest0) {

      LOG.info(doInOnlyRequest0.getMessageString());

   }

   /**
    * Auto generated method signature
    * 
    * @param twoWayOneParameterEchoRequest1
    */

   public org.apache.axis2.axis2userguide.TwoWayOneParameterEchoResponse twoWayOneParameterEcho(
         org.apache.axis2.axis2userguide.TwoWayOneParameterEchoRequest twoWayOneParameterEchoRequest1) {

      LOG.info(twoWayOneParameterEchoRequest1.getEchoString());

      TwoWayOneParameterEchoResponse res = new TwoWayOneParameterEchoResponse();

      res.setEchoString(twoWayOneParameterEchoRequest1.getEchoString());

      return res;
   }

   /**
    * Auto generated method signature
    * 
    * @param noParametersRequest3
    */

   public org.apache.axis2.axis2userguide.NoParametersResponse noParameters(
         org.apache.axis2.axis2userguide.NoParametersRequest noParametersRequest3) {

      LOG.info(noParametersRequest3);

      NoParametersResponse res = new NoParametersResponse();

      return res;

   }

   /**
    * Auto generated method signature
    * 
    * @param multipleParametersAddItemRequest5
    */

   public org.apache.axis2.axis2userguide.MultipleParametersAddItemResponse multipleParametersAddItem(
         org.apache.axis2.axis2userguide.MultipleParametersAddItemRequest multipleParametersAddItemRequest5) {

      LOG.info(multipleParametersAddItemRequest5.getPrice());
      LOG.info(multipleParametersAddItemRequest5.getItemId());
      LOG.info(multipleParametersAddItemRequest5.getDescription());
      LOG.info(multipleParametersAddItemRequest5.getItemName());

      MultipleParametersAddItemResponse res = new MultipleParametersAddItemResponse();

      res.setSuccessfulAdd(true);
      res.setItemId(multipleParametersAddItemRequest5.getItemId());

      return res;

   }

}
