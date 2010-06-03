/**
 * Service1Locator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele;

public class Service1Locator extends org.apache.axis.client.Service implements fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1 {

    public Service1Locator() {
    }


    public Service1Locator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Service1Locator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for service1Port
    private java.lang.String service1Port_address = "https://cer69imageint4.cer69.recouv:9011/services/tests/webservices/test_rpc_literal.php";

    public java.lang.String getservice1PortAddress() {
        return service1Port_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String service1PortWSDDServiceName = "service1Port";

    public java.lang.String getservice1PortWSDDServiceName() {
        return service1PortWSDDServiceName;
    }

    public void setservice1PortWSDDServiceName(java.lang.String name) {
        service1PortWSDDServiceName = name;
    }

    public fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1PortType getservice1Port() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(service1Port_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getservice1Port(endpoint);
    }

    public fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1PortType getservice1Port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1BindingStub _stub = new fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1BindingStub(portAddress, this);
            _stub.setPortName(getservice1PortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setservice1PortEndpointAddress(java.lang.String address) {
        service1Port_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1BindingStub _stub = new fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1BindingStub(new java.net.URL(service1Port_address), this);
                _stub.setPortName(getservice1PortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("service1Port".equals(inputPortName)) {
            return getservice1Port();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost/", "service1");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost/", "service1Port"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("service1Port".equals(portName)) {
            setservice1PortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
