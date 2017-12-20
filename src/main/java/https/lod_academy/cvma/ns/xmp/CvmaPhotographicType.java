//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.27 at 07:35:32 AM CET 
//


package https.lod_academy.cvma.ns.xmp;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cvmaPhotographicType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="cvmaPhotographicType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Durchlicht"/>
 *     &lt;enumeration value="Durchlicht Vorderseite Einzelaufnahme"/>
 *     &lt;enumeration value="Durchlicht Vorderseite Gesamtaufnahme"/>
 *     &lt;enumeration value="Durchlicht Vorderseite Detailnahme"/>
 *     &lt;enumeration value="Auflicht"/>
 *     &lt;enumeration value="Auflicht Vorderseite"/>
 *     &lt;enumeration value="Auflicht Vorderseite Einzelaufnahme"/>
 *     &lt;enumeration value="Auflicht Vorderseite Gesamtaufnahme"/>
 *     &lt;enumeration value="Auflicht Vorderseite Detailaufnahme"/>
 *     &lt;enumeration value="Auflicht Rückseite"/>
 *     &lt;enumeration value="Auflicht Rückseite Einzelaufnahme"/>
 *     &lt;enumeration value="Auflicht Rückseite Gesamtaufnahme"/>
 *     &lt;enumeration value="Auflicht Rückseite Detailaufnahme"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "cvmaPhotographicType")
@XmlEnum
public enum CvmaPhotographicType {

    @XmlEnumValue("Durchlicht")
    DURCHLICHT("Durchlicht"),
    @XmlEnumValue("Durchlicht Vorderseite Einzelaufnahme")
    DURCHLICHT_VORDERSEITE_EINZELAUFNAHME("Durchlicht Vorderseite Einzelaufnahme"),
    @XmlEnumValue("Durchlicht Vorderseite Gesamtaufnahme")
    DURCHLICHT_VORDERSEITE_GESAMTAUFNAHME("Durchlicht Vorderseite Gesamtaufnahme"),
    @XmlEnumValue("Durchlicht Vorderseite Detailnahme")
    DURCHLICHT_VORDERSEITE_DETAILNAHME("Durchlicht Vorderseite Detailnahme"),
    @XmlEnumValue("Auflicht")
    AUFLICHT("Auflicht"),
    @XmlEnumValue("Auflicht Vorderseite")
    AUFLICHT_VORDERSEITE("Auflicht Vorderseite"),
    @XmlEnumValue("Auflicht Vorderseite Einzelaufnahme")
    AUFLICHT_VORDERSEITE_EINZELAUFNAHME("Auflicht Vorderseite Einzelaufnahme"),
    @XmlEnumValue("Auflicht Vorderseite Gesamtaufnahme")
    AUFLICHT_VORDERSEITE_GESAMTAUFNAHME("Auflicht Vorderseite Gesamtaufnahme"),
    @XmlEnumValue("Auflicht Vorderseite Detailaufnahme")
    AUFLICHT_VORDERSEITE_DETAILAUFNAHME("Auflicht Vorderseite Detailaufnahme"),
    @XmlEnumValue("Auflicht R\u00fcckseite")
    AUFLICHT_RÜCKSEITE("Auflicht R\u00fcckseite"),
    @XmlEnumValue("Auflicht R\u00fcckseite Einzelaufnahme")
    AUFLICHT_RÜCKSEITE_EINZELAUFNAHME("Auflicht R\u00fcckseite Einzelaufnahme"),
    @XmlEnumValue("Auflicht R\u00fcckseite Gesamtaufnahme")
    AUFLICHT_RÜCKSEITE_GESAMTAUFNAHME("Auflicht R\u00fcckseite Gesamtaufnahme"),
    @XmlEnumValue("Auflicht R\u00fcckseite Detailaufnahme")
    AUFLICHT_RÜCKSEITE_DETAILAUFNAHME("Auflicht R\u00fcckseite Detailaufnahme");
    private final String value;

    CvmaPhotographicType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CvmaPhotographicType fromValue(String v) {
        for (CvmaPhotographicType c: CvmaPhotographicType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}