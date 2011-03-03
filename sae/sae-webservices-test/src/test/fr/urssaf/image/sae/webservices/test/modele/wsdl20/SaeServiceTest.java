

/**
 * SaeServiceTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
    package fr.urssaf.image.sae.webservices.test.modele.wsdl20;

    /*
     *  SaeServiceTest Junit test case
    */

    public class SaeServiceTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testping() throws java.lang.Exception{

        fr.urssaf.image.sae.webservices.test.modele.wsdl20.SaeServiceStub stub =
                    new fr.urssaf.image.sae.webservices.test.modele.wsdl20.SaeServiceStub();//the default implementation should point to the right endpoint

           
                    
                    //There is no output to be tested!
                    stub.ping(
                        );
                    



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartping() throws java.lang.Exception{
            fr.urssaf.image.sae.webservices.test.modele.wsdl20.SaeServiceStub stub = new fr.urssaf.image.sae.webservices.test.modele.wsdl20.SaeServiceStub();
             

                stub.startping(
                         
                    new tempCallbackN65548()
                );
              


        }

        private class tempCallbackN65548  extends fr.urssaf.image.sae.webservices.test.modele.wsdl20.SaeServiceCallbackHandler{
            public tempCallbackN65548(){ super(null);}

            public void receiveResultping(
                         ) {
                
            }

            public void receiveErrorping(java.lang.Exception e) {
                fail();
            }

        }
      
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    