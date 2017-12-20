/*
 * Copyright 2016 Karlsruhe Institute of Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.kit.masi;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import edu.kit.masi.xml.MapEntryConverter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.jdom.Namespace;

/**
 * Helper class transforming metadata from exif into XML
 * @author hartmann-v
 */
public class CvmaXml extends NamespacePrefixMapper {

  // <editor-fold defaultstate="collapsed" desc="Define namespaces for corpus vitrearum">
  /**
   * Namespace for CVMA.
   */
  public static final Namespace NS_CVMA = Namespace.getNamespace("cvma", "https://lod.academy/cvma/ns/xmp/");
  /**
   * Namespace for Dublin Core.
   */
  public static final Namespace NS_DC = Namespace.getNamespace("dc", "http://purl.org/dc/elements/1.1/");
  /**
   * Namespace for Exif.
   */
  public static final Namespace NS_EXIF = Namespace.getNamespace("exif", "http://ns.adobe.com/exif/1.0/");
  /**
   * Namespace for IPTC.
   */
  public static final Namespace NS_IPTC = Namespace.getNamespace("Iptc4xmpExt", "http://iptc.org/std/Iptc4xmpExt/2008-02-29/");
  /**
   * Namespace for Photoshop.
   */
  public static final Namespace NS_PHOTO = Namespace.getNamespace("photoshop", "http://ns.adobe.com/photoshop/1.0/");
  /**
   * Namespace for XMP.
   */
  public static final Namespace NS_XMP = Namespace.getNamespace("xmp", "http://ns.adobe.com/xap/1.0/");
  /**
   * Namespace for XMP rights.
   */
  public static final Namespace NS_XMP_RIGHTS = Namespace.getNamespace("xmprights", "http://ns.adobe.com/xap/1.0/rights/");
  /**
   * Namespace for XSD.
   */
  public static final Namespace NS_XSD = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema#");
// </editor-fold>
  /**
   * Map containing all information found in image and defined in XmlTags.
   */
  Map<String, String> allNodes;
  /**
   * List of all used namespaces.
   */
  List<Namespace> allNamespaces = Arrays.asList(NS_CVMA, NS_DC, NS_EXIF, NS_IPTC, NS_PHOTO, NS_XMP_RIGHTS, NS_XSD);

  /**
   * Default constructor.
   */
  public CvmaXml() {
    allNodes = new HashMap<String, String>();
  }

  /**
   * Add element to the map.
   *
   * @param pElement Name of the element.
   * @param pValue Value of the element.
   */
  public void addNode(String pElement, String pValue) {
    allNodes.put(pElement, pValue);
  }

  /**
   * Get value of an element.
   *
   * @param pElement Name of the element.
   * @return Value of the element.
   */
  public String getNode(String pElement) {
    return allNodes.get(pElement);
  }

  /**
   * Get content of the instance as XML.
   *
   * @return String in XML format.
   */
  public String toXml() {

    // convert to XML
    //XStream xStream = new XStream(new DomDriver());
    XStream xStream = initializeXStream();

    String xml = xStream.toXML(allNodes);
    return xml;
  }
  /**
   * Parse XML file to instance.
   * @param pXmlFile File containing metadata in XML format.
   * @throws IOException Error while parsing file.
   */
  public void fromXml(File pXmlFile) throws IOException {
    String fileContent;
    fileContent = FileUtils.readFileToString(pXmlFile);
    // from XML, convert back to map
    //XStream xStream = new XStream(new DomDriver());
    XStream xStream = initializeXStream();
    allNodes = (Map<String, String>) xStream.fromXML(fileContent);
  }
  /**
   * Initialize XStream.
   * @return Instance of XStream with adopted configuration.
   */
  private XStream initializeXStream() {
    QNameMap qmap = new QNameMap();
    qmap.setDefaultNamespace(NS_CVMA.getURI());

    //   qmap.setDefaultPrefix(CVMA_NAMESPACE_PREFIX);
    StaxDriver staxDriver = new StaxDriver(qmap);

    XStream xstream = new XStream(staxDriver);
    xstream.alias("cvma", java.util.Map.class);
    xstream.registerConverter(new MapEntryConverter());
    return xstream;
  }

  @Override
  public String getPreferredPrefix(String string, String string1, boolean bln) {
    String prefix = null;
    for (Namespace item : allNamespaces) {
      if (string.equals(item.getURI())) {
        prefix = item.getPrefix();
        break;
      }
    }
    return prefix;
  }
}
