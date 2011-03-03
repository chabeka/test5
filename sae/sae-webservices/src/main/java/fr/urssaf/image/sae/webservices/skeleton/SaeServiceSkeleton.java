/**
 * SaeServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
package fr.urssaf.image.sae.webservices.skeleton;

import fr.cirtil.www.saeservice.PingRequest;
import fr.cirtil.www.saeservice.PingResponse;

/**
 * SaeServiceSkeleton java skeleton for the axisService
 */
public class SaeServiceSkeleton implements SaeServiceSkeletonInterface {

   /**
    * Auto generated method signature
    * 
    * @param pingRequest0
    */

   public PingResponse ping(PingRequest pingRequest) {

      PingResponse response = new PingResponse();

      response.setPingString("Les services SAE sont en ligne");

      return response;
   }

}
