//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.27 at 07:35:32 AM CET 
//


package org.iptc.std.iptc4xmpext._2008_02_29;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.iptc.std.iptc4xmpext._2008_02_29 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AOCircaDateCreated_QNAME = new QName("http://iptc.org/std/Iptc4xmpExt/2008-02-29/", "AOCircaDateCreated");
    private final static QName _CountryName_QNAME = new QName("http://iptc.org/std/Iptc4xmpExt/2008-02-29/", "CountryName");
    private final static QName _City_QNAME = new QName("http://iptc.org/std/Iptc4xmpExt/2008-02-29/", "City");
    private final static QName _LocationId_QNAME = new QName("http://iptc.org/std/Iptc4xmpExt/2008-02-29/", "LocationId");
    private final static QName _ProvinceState_QNAME = new QName("http://iptc.org/std/Iptc4xmpExt/2008-02-29/", "ProvinceState");
    private final static QName _Sublocation_QNAME = new QName("http://iptc.org/std/Iptc4xmpExt/2008-02-29/", "Sublocation");
    private final static QName _DigitalSourceType_QNAME = new QName("http://iptc.org/std/Iptc4xmpExt/2008-02-29/", "DigitalSourceType");
    private final static QName _WorldRegion_QNAME = new QName("http://iptc.org/std/Iptc4xmpExt/2008-02-29/", "WorldRegion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.iptc.std.iptc4xmpext._2008_02_29
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iptc.org/std/Iptc4xmpExt/2008-02-29/", name = "AOCircaDateCreated")
    public JAXBElement<String> createAOCircaDateCreated(String value) {
        return new JAXBElement<String>(_AOCircaDateCreated_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iptc.org/std/Iptc4xmpExt/2008-02-29/", name = "CountryName")
    public JAXBElement<String> createCountryName(String value) {
        return new JAXBElement<String>(_CountryName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iptc.org/std/Iptc4xmpExt/2008-02-29/", name = "City")
    public JAXBElement<String> createCity(String value) {
        return new JAXBElement<String>(_City_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iptc.org/std/Iptc4xmpExt/2008-02-29/", name = "LocationId")
    public JAXBElement<String> createLocationId(String value) {
        return new JAXBElement<String>(_LocationId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iptc.org/std/Iptc4xmpExt/2008-02-29/", name = "ProvinceState")
    public JAXBElement<String> createProvinceState(String value) {
        return new JAXBElement<String>(_ProvinceState_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iptc.org/std/Iptc4xmpExt/2008-02-29/", name = "Sublocation")
    public JAXBElement<String> createSublocation(String value) {
        return new JAXBElement<String>(_Sublocation_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iptc.org/std/Iptc4xmpExt/2008-02-29/", name = "DigitalSourceType")
    public JAXBElement<String> createDigitalSourceType(String value) {
        return new JAXBElement<String>(_DigitalSourceType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iptc.org/std/Iptc4xmpExt/2008-02-29/", name = "WorldRegion")
    public JAXBElement<String> createWorldRegion(String value) {
        return new JAXBElement<String>(_WorldRegion_QNAME, String.class, null, value);
    }

}
