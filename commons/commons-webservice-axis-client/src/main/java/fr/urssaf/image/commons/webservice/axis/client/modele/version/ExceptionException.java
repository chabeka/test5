
/**
 * ExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */

package fr.urssaf.image.commons.webservice.axis.client.modele.version;

// CHECKSTYLE:OFF
@SuppressWarnings("all")
public class ExceptionException extends java.lang.Exception{
    
    private fr.urssaf.image.commons.webservice.axis.client.modele.version.VersionStub.ExceptionE faultMessage;

    
        public ExceptionException() {
            super("ExceptionException");
        }

        public ExceptionException(java.lang.String s) {
           super(s);
        }

        public ExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(fr.urssaf.image.commons.webservice.axis.client.modele.version.VersionStub.ExceptionE msg){
       faultMessage = msg;
    }
    
    public fr.urssaf.image.commons.webservice.axis.client.modele.version.VersionStub.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    