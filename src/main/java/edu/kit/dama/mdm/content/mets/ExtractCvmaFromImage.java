/**
 * Copyright (C) 2014 Karlsruhe Institute of Technology (support@kitdatamanager.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.kit.dama.mdm.content.mets;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import com.thebuzzmedia.exiftool.Tag;
import edu.kit.dama.commons.exceptions.PropertyValidationException;
import edu.kit.dama.mdm.content.impl.exceptions.MetaDataExtractionException;
import edu.kit.dama.mdm.dataorganization.entity.core.ICollectionNode;
import edu.kit.dama.mdm.dataorganization.entity.core.IDataOrganizationNode;
import edu.kit.dama.mdm.dataorganization.entity.core.IFileNode;
import edu.kit.dama.mdm.dataorganization.impl.util.Util;
import edu.kit.dama.rest.staging.types.TransferTaskContainer;
import edu.kit.dama.util.Constants;
import edu.kit.masi.CvmaTags;
import edu.kit.masi.CvmaXml;
import edu.kit.masi.XmlTags;
import https.lod_academy.cvma.ns.xmp.CvmaRootType;
import https.lod_academy.cvma.ns.xmp.ObjectFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;
import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * Extract exif metadata from first image found in data set.
 *
 * @author hartmann-v
 */
public class ExtractCvmaFromImage extends MetsMetadataExtractor {

  /**
   * The logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ExtractCvmaFromImage.class);
  /**
   * The version configuration property
   */
  private static final String VERSION_PROPERTY = "version";
  /**
   * The exiftool configuration property
   */
  private static final String PATH_TO_EXIFTOOL = "Path to exiftool";
  /**
   * The most actual version.
   */
  private static final String VERSION_1_0 = "Version 1.0";
  /**
   * file name of the measurement report. Tab separated file containing some
   * useful values.
   */
  private static final String REGEX_TIF_IMAGES = ".*(TIF|tif|TIFF|tiff)";

  private static final String ENV_EXIFTOOL_PATH = "exiftool.path";

  private String pathToExifTool = null;
  /**
   * Namespace for CVMA.
   */
  private static final String CVMA_NAMESPACE = "https://lod.academy/cvma/ns/xmp/";
  /**
   * Prefix for CVMA.
   */
  private static final String CVMA_PREFIX = "cvma";
  /**
   * Root node of CVMA.
   */
  private static final String CVMA_ROOT = "cvma:cvma";
  /**
   * Namespace for Dublin Core.
   */
  private static final String OAI_NAMESPACE = "http://www.openarchives.org/OAI/2.0/oai_dc/";
  /**
   * Prefix for Dublin Core.
   */
  private static final String OAI_PREFIX = "oai_dc";
  /**
   * Root node of Dublin Core.
   */
  private static final String OAI_ROOT = "oai_dc:dc";
  /**
   * Prefix for Dublin Core.
   */
  private static final String DC_PREFIX = "dc";
  /**
   * Namespace for Dublin Core.
   */
  private static final String DC_NAMESPACE = "http://purl.org/dc/elements/1.1/";
  /**
   * Prefix for xmpRights.
   */
  private static final String XMP_RIGHTS_PREFIX = "xmpRights";
  /**
   * Namespace for xmpRights.
   */
  private static final String XMP_RIGHTS_NAMESPACE = "http://ns.adobe.com/xap/1.0/rights/";

  @Override
  public String[] getUserPropertyKeys() {
    LOGGER.trace("getUserPropertyKeys - Nothing to do");
    return null;
  }

  @Override
  public String getUserPropertyDescription(String pKey) {
    LOGGER.trace("getUserPropertyDescription - Nothing to do");
    return null;
  }

  /**
   * Enumeration of all known versions.
   */
  public enum VersionString {

    /**
     * Version 1.0
     */
    VERSION10(VERSION_1_0);

    /**
     * Version as String
     */
    String version;

    /**
     * Hidden constructor.
     *
     * @param label Version as string.
     */
    private VersionString(String label) {
      version = label;
    }

    @Override
    public String toString() {
      return version;
    }

    /**
     * Alternative implementation select enumeration by string representation
     * instead of variable declaration.
     *
     * @param value The value.
     *
     * @return The enum.
     */
    public static VersionString getEnum(String value) {
      for (VersionString item : VersionString.values()) {
        if (item.version.compareTo(value) == 0) {
          return item;
        }
      }
      throw new IllegalArgumentException("Invalid VersionString value: " + value);
    }
  }

  /**
   * Default constructor
   *
   * @param pUniqueIdentifier The unique identifier of this OP. This identifier
   * should be used to name generated output files associated with this OP.
   */
  public ExtractCvmaFromImage(String pUniqueIdentifier) {
    super(pUniqueIdentifier);
  }

  @Override
  public final String getName() {
    return "CVMA_Metadata_Extractor";
  }

  @Override
  protected void validateExtractorProperties(Properties pProperties) throws PropertyValidationException {
    LOGGER.debug("valideateExtractorProperties() -- validate properties");
    if (pProperties == null) {
      throw new IllegalArgumentException("Argument pProperties must not be null");

    }
    String version = pProperties.getProperty(VERSION_PROPERTY);
    try {
      if (version == null) {
        //no version set
        throw new IllegalArgumentException("Property VERSION_PROPERTY not provided.");
      }
      //check version...throws IllegalArgumentException if invalid
      LOGGER.debug("Validated parser version {}", VersionString.getEnum(version));
    } catch (IllegalArgumentException iae) {
      throw new PropertyValidationException("Failed to parse version from property value " + version + ". Value not part of " + Arrays.toString(VersionString.values()) + ".", iae);
    }
    // <editor-fold defaultstate="collapsed" desc="Check and set path for exiftool">
    String path2Exiftool = pProperties.getProperty(PATH_TO_EXIFTOOL, "/usr/bin/exiftool");
    if (!new File(path2Exiftool).exists()) {
      LOGGER.error("Failed to set path to exiftool. The path '{}' seems to be invalid.", path2Exiftool);
      throw new PropertyValidationException("Failed to set path to exiftool. The path '" + path2Exiftool + "' seems to be invalid.");

    }
    // </editor-fold>
  }

  @Override
  protected String[] getExtractorPropertyKeys() {
    return new String[]{VERSION_PROPERTY, PATH_TO_EXIFTOOL};
  }

  @Override
  protected String getExtractorPropertyDescription(String pProperty) {
    if (VERSION_PROPERTY.equals(pProperty)) {
      return "The version which is used to parse the files.\nPossible values: " + Arrays.toString(VersionString.values());
    }
    if (PATH_TO_EXIFTOOL.equals(pProperty)) {
      return "The path to the exiftool (e.g.: /usr/bin/exiftool).";
    }
    return null;
  }

  @Override
  protected void configureExtractor(Properties pProperties) {
    LOGGER.debug("configureExtractor() -- configure properties");
    // <editor-fold defaultstate="collapsed" desc="Check and set version property">
    String version = pProperties.getProperty(VERSION_PROPERTY, VERSION_1_0);
    VersionString actualVersion = VersionString.VERSION10;
    try {
      actualVersion = VersionString.getEnum(version);
    } catch (IllegalArgumentException iae) {
      LOGGER.warn("Failed to parse version from property value {}. Using default value '{}'", version, actualVersion);
    }
    // Test for valid entry
    switch (actualVersion) {
      case VERSION10:
        break;
//      case VERSION20:
      default:
        LOGGER.error("'" + actualVersion + "' is not a supported version!");
        throw new UnknownError("'" + actualVersion + "' is not a supported version!");
    }
    // </editor-fold>
    LOGGER.debug("Chosen version: " + actualVersion);
    pathToExifTool = pProperties.getProperty(PATH_TO_EXIFTOOL);
    LOGGER.debug("Use " + pathToExifTool + " for metadata extraction.");
//    System.setProperty(ExecutionConstant.EXIFTOOLPATH, pProperties.getProperty(PATH_TO_EXIFTOOL));
    System.setProperty(ENV_EXIFTOOL_PATH, pathToExifTool);
  }

  /**
   * Extract metadata from tif file using exiftool.
   *
   * @param pImageFile image file containing metadata in exif format.
   * @return metadata as XML string.
   */
  private String extractMetadataFromImageToString(File pImageFile) throws IOException {
    LOGGER.debug("Extract metadata from file '{}'", pImageFile.getAbsolutePath());
    String returnValue = "<cvma xmlns=\"https://lod.academy/cvma/ns/xmp/\" xmlns:Iptc4xmpExt=\"http://iptc.org/std/Iptc4xmpExt/2008-02-29/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:photoshop=\"http://ns.adobe.com/photoshop/1.0/\" xmlns:xmp=\"http://ns.adobe.com/xap/1.0/\" xmlns:xmprights=\"http://ns.adobe.com/xap/1.0/rights/\" xmlns:gps=\"http://www.topografix.com/GPX/1/1/\">"
            + "<dc:title>title</dc:title>  <Type>Glasmalerei</Type>  <dc:relation>some relation</dc:relation>  <Volume>some volume</Volume>  <Figure>some figure</Figure>  <dc:identifier>identifier</dc:identifier>"
            + "<PhotographicType>Durchlicht Vorderseite Einzelaufnahme</PhotographicType>  <Iptc4xmpExt:DigitalSourceType>Originaldigitalaufnahme</Iptc4xmpExt:DigitalSourceType>  <PhotographicContext>ausgebaut</PhotographicContext>"
            + "</cvma>";

    CvmaXml cvmaXml = new CvmaXml();
    try {
      // set path property for the configuration file used by exif tool.
      System.setProperty("args.path", System.getProperty("java.io.tmpdir") + File.separator + "exif");
      ExifTool exifTool = new ExifToolBuilder().withPath(pathToExifTool).build();
      CvmaTags[] values = CvmaTags.values();
      Map<Tag, String> imageMeta = exifTool.getImageMeta(pImageFile, new ArrayList<Tag>(Arrays.asList(values)));
      // Conversion to support old implementation based on be.pw999.jexif
      Map<String, String> allTagsValues = new HashMap<>();
      for (Tag tag : imageMeta.keySet()) {
        allTagsValues.put(tag.getName(), imageMeta.get(tag));
      }
//      TagUtil.register(CvmaTags.class);
//      Map<String, String> allTagsValues;
//      synchronized (this) {
//      JExifTool tool = new JExifTool(); //starts exiftool process
//      JExifInfo info1 = tool.getInfo(pImageFile); // create proxy
//      allTagsValues = info1.getAllTagsValues();
////      tool.stop();
//      }
      LOGGER.debug("Found {} properties!", allTagsValues.size());
      if (LOGGER.isTraceEnabled()) {
        for (String key : allTagsValues.keySet()) {
          LOGGER.trace("Key: {}    -- value: {}", key, allTagsValues.get(key));
        }
      }

      for (XmlTags xmlTag : XmlTags.values()) {
        String description = allTagsValues.get(xmlTag.toString());
        if (description != null) {
          if (!description.trim().isEmpty()) {
            description = description.replace(com.thebuzzmedia.exiftool.Constants.SEPARATOR, ", ");
            LOGGER.trace("Add {}={} to content metadata.", xmlTag.toString(), description);
            cvmaXml.addNode(xmlTag.toString(), description);
          }
        }

      }
// <editor-fold defaultstate="collapsed" desc="Validation of xml (example code)">
      // Validation should be done in super class using registered schemas.
//    try {      
//      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//      Schema schema = sf.newSchema(new File("/tmp/CVMA.xsd"));
//      JAXBContext jaxbContext = JAXBContext.newInstance(Cvma.class);
//      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//      unmarshaller.setSchema(schema);
//      Cvma cvma = (Cvma) unmarshaller.unmarshal(new StringReader(cvmaXml.toXml()));
//      LOGGER.debug("XML is valid!");
//    } catch (SAXException ex) {
//      java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//    } catch (JAXBException ex) {
//      java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//    }
// </editor-fold>

      // make xml more readable
      JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      CvmaRootType cvma = ((JAXBElement<CvmaRootType>) unmarshaller.unmarshal(new StringReader(cvmaXml.toXml()))).getValue();
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", cvmaXml);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectFactory of = new ObjectFactory();
      marshaller.marshal(of.createCvma(cvma), baos);
      returnValue = baos.toString();

    } catch (JAXBException ex) {
//      LOGGER.error("Error parsing xml file", ex);
//    }catch (JExifException ex) {
//      LOGGER.error("Exception parsing image file", ex);
//    } catch (ExifError ex) {
//      LOGGER.error("Error parsing image file", ex);
//    } catch (JAXBException ex) {
//      LOGGER.error("Error parsing xml file", ex);
    }
    return returnValue;
  }

  /**
   * Extract metadata from tif file using exiftool.
   *
   * @param pImageFile file containing image including metadata in exif format.
   * @return document of metadata (W3C element)
   */
  private Document extractMetadataFromImage(File pImageFile) throws IOException {
    Document w3CDocument = null;
    try {
      org.jdom.Document pDocument = JaxenUtil.getDocument(extractMetadataFromImageToString(pImageFile));
      patchCvmaFields(pDocument);
      XMLOutputter xo;
      xo = new XMLOutputter();
      w3CDocument = JaxenUtil.getW3CDocument(IOUtils.toInputStream(xo.outputString(pDocument)));
    } catch (Exception ex) {
      LOGGER.error("Error creating XML", ex);
    }

    return w3CDocument;
  }

  @Override
  protected Document createCommunitySpecificDocument(TransferTaskContainer pContainer) throws MetaDataExtractionException {
    LOGGER.debug("{}: createCommunitySpecificElement", this.getClass().toString());
    Document returnValue = null;

    // <editor-fold defaultstate="collapsed" desc="check for and read metadata.xml ">
    ICollectionNode root = pContainer.getFileTree().getRootNode();
    //initialize hashes list
    LOGGER.debug("Searching for image file.");
    //obtain "generated" folder node
    IDataOrganizationNode dataSubTree = Util.getNodeByName(root, Constants.STAGING_DATA_FOLDER_NAME);

    Set<IDataOrganizationNode> imageFiles = Util.getNodesByRegex((ICollectionNode) dataSubTree, REGEX_TIF_IMAGES);

    if (imageFiles.isEmpty()) {
      LOGGER.warn("No image file found. Skipping meta data extraction.");
    } else {
      if (imageFiles.size() > 1) {
        LOGGER.warn("More than one image file found. Extract metadata only from the first one!");
      }
      IFileNode imageFile = (IFileNode) imageFiles.iterator().next();
      try {
        File theFile = new File(new URL(imageFile.getLogicalFileName().asString()).toURI());

        returnValue = extractMetadataFromImage(theFile);

      } catch (MalformedURLException | URISyntaxException ex) {
        LOGGER.error("Failed to extract metadata from URL " + imageFile.getLogicalFileName().asString() + ".", ex);
      } catch (IOException ex) {
        LOGGER.error("Error reading file from URL " + imageFile.getLogicalFileName().asString() + ".", ex);
      }
    }
    // </editor-fold>

    return returnValue;
  }

  public boolean patchCvmaFields(org.jdom.Document pDocument) {
    boolean isPatched = false;
    Namespace dcNamespace = Namespace.getNamespace("xap", "http://ns.adobe.com/xap/1.0/");
    Namespace cvmaNamespace = Namespace.getNamespace(CVMA_PREFIX, CVMA_NAMESPACE);
    Namespace[] allNamespaces = {dcNamespace, cvmaNamespace};
    List nodes = JaxenUtil.getNodes(pDocument, "//xap:CreateDate", allNamespaces);
    if (!nodes.isEmpty()) {
      String createDate = ((Element) nodes.get(0)).getText();
      String newString = testDate(createDate);
      if (!createDate.equalsIgnoreCase(newString)) {
        ((Element) nodes.get(0)).setText(newString);
        isPatched = true;
      }
    } else {
      LOGGER.warn("Partial node createDate not found!");
    }
    nodes = JaxenUtil.getNodes(pDocument, "//cvma:AgeDeterminationStart", allNamespaces);
    if (!nodes.isEmpty()) {
      String createDate = ((Element) nodes.get(0)).getText();
      String newString = patchDate(createDate);
      if (!createDate.equalsIgnoreCase(newString)) {
        ((Element) nodes.get(0)).setText(newString);
        isPatched = true;
      }
    } else {
      LOGGER.warn("Node AgeDeterminationStart not found!");
    }
    nodes = JaxenUtil.getNodes(pDocument, "//cvma:AgeDeterminationEnd", allNamespaces);
    if (!nodes.isEmpty()) {
      String createDate = ((Element) nodes.get(0)).getText();
      String newString = patchDate(createDate);
      if (!createDate.equalsIgnoreCase(newString)) {
        ((Element) nodes.get(0)).setText(newString);
        isPatched = true;
      }
    } else {
      LOGGER.warn("Node AgeDeterminationEnd not found!");
    }
    return isPatched;

  }

  public static String testDate(String pDate) {
    String isPatched = pDate;
    String time = "01:00:00+01:00";
    String date = null;
    String[] dateAndTime = pDate.split("T");
    if (dateAndTime.length == 2) {
      time = patchWholeTime(dateAndTime[1]);
    }
    date = patchDate(dateAndTime[0]);
    if ((time != null) || (!date.equalsIgnoreCase(dateAndTime[0]))) {
      if (time == null) {
        time = dateAndTime[1];
      }
      String newTime = String.format("%sT%s", date, time);
      isPatched = newTime;

    }
    return isPatched;

  }

  public static String patchWholeTime(String pTime) {
    String newTime = null;
    String time = null;
    String timeZone1 = "";
    String timeZone[] = pTime.split("\\+");
    switch (timeZone.length) {
      case 2:
        timeZone1 = timeZone[1];
      case 1:
        time = patchTime(timeZone[0]);
        break;
    }
    newTime = String.format("%s+%s", time, timeZone1);
    return newTime;
  }

  public static String patchTime(String pTime) {
    String newTime;
    String timeZone[] = pTime.split(":");
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    switch (timeZone.length) {
      case 3:
        try {
          seconds = Integer.parseInt(timeZone[2]);
          if ((seconds < 0) || (seconds > 59)) {
            seconds = 1;
          }
        } catch (NumberFormatException es) {
        }
      case 2:
        try {
          minutes = Integer.parseInt(timeZone[1]);
          if ((minutes < 0) || (minutes > 59)) {
            minutes = 1;
          }
        } catch (NumberFormatException es) {
        }
      case 1:
        try {
          hours = Integer.parseInt(timeZone[0]);
          if ((hours < 0) || (hours > 23)) {
            hours = 1;
          }
        } catch (NumberFormatException es) {
        }
    }
    newTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
    return newTime;
  }

  public static String patchDate(String pDate) {
    String newDate = null;
    String dateString[] = pDate.split("[\\.ß:-]");
    int days = 1;
    int months = 1;
    int years = 1000;
    switch (dateString.length) {
      default:
      case 3:
        try {
          days = Integer.parseInt(dateString[2]);
          if ((days < 1) || (days > 31)) {
            days = 1;
          }
        } catch (NumberFormatException es) {
        }
      case 2:
        try {
          months = Integer.parseInt(dateString[1]);
          if ((months < 1) || (months > 12)) {
            months = 1;
          }
        } catch (NumberFormatException es) {
        }
      case 1:
        try {
          years = Integer.parseInt(dateString[0]);
          while (years > 2050) {
            years /= 10;
          }
        } catch (NumberFormatException es) {
        }
    }
    newDate = String.format("%04d-%02d-%02d", years, months, days);
    return newDate;
  }

//  @Override
//  protected void patchDmdSection(org.jdom.Document pMetsDocument) {
//     Namespace dcNamespace = Namespace.getNamespace(DC_PREFIX, DC_NAMESPACE);
//    Namespace cvmaNamespace = Namespace.getNamespace(CVMA_PREFIX, CVMA_NAMESPACE);
//    Namespace xmpRightsNamespace = Namespace.getNamespace(XMP_RIGHTS_PREFIX, XMP_RIGHTS_NAMESPACE);
//    Namespace[] allNamespaces = {dcNamespace, cvmaNamespace, xmpRightsNamespace};
//
//    // patch content to dublin core metadata
//    Element partialDublinCore = getPartialDocument(OAI_PREFIX, pMetsDocument, OAI_NAMESPACE, OAI_ROOT);
//    Element partialCVMA = getPartialDocument(CVMA_PREFIX, pMetsDocument, CVMA_NAMESPACE, CVMA_ROOT);
//    replaceText(partialCVMA, "dc:title", partialDublinCore, "dc:title", allNamespaces);
//    replaceText(partialCVMA, "dc:creator", partialDublinCore, "dc:creator", allNamespaces);
//    replaceText(partialCVMA, "dc:publisher", partialDublinCore, "dc:publisher", allNamespaces);
//    replaceText(partialCVMA, "xmpRights:UsageTerms", partialDublinCore, "dc:rights", allNamespaces);
//    replaceText(partialCVMA, "cvma:IconclassDescription", partialDublinCore, "dc:description", allNamespaces);
//    replaceText(partialCVMA, "cvma:Type", partialDublinCore, "dc:type", allNamespaces);
//    replaceText(partialCVMA, "dc:title", partialDublinCore, "dc:subject", allNamespaces);
//   
//  }
//
//  /**
//   * Replace text of node if not identical.
//   *
//   * @param pSource root element of source
//   * @param pSourceXPath Xpath to node
//   * @param pTarget root element of target
//   * @param pTargetXPath Xpath to node
//   * @param pNamespaces all namespaces
//   * @return value changed true or false
//   */
//  private boolean replaceText(Element pSource, String pSourceXPath, Element pTarget, String pTargetXPath, Namespace[] pNamespaces) {
//    boolean replace = false;
//    String nodeValue = JaxenUtil.getNodeValue(pSource, pSourceXPath, pNamespaces);
//    String targetValue = JaxenUtil.getNodeValue(pTarget, pTargetXPath, pNamespaces);
//    if (!nodeValue.equalsIgnoreCase(targetValue)) {
//      LOGGER.debug("Replace '{}' by '{}'", nodeValue, targetValue);
//      ((Element) (JaxenUtil.getNodes(pTarget, pTargetXPath, pNamespaces).get(0))).setText(nodeValue);
//      replace = true;
//    }
//    return replace;
//  }
//  
//  /**
//   * Get root element of partial document.
//   *
//   * @param pDigObjId Digital Object ID
//   * @param pPrefix prefix of the node
//   * @param pDocument document
//   * @param pNamespace namespace of the node
//   * @param pRootElement element name of the node
//   * @return node
//   */
//  private Element getPartialDocument(String pPrefix, org.jdom.Document pDocument, String pNamespace, String pRootElement) {
//    Element partialDocument = null;
//    Namespace namespace = Namespace.getNamespace(pPrefix, pNamespace);
//    List nodes = JaxenUtil.getNodes(pDocument, "//" + pRootElement, new Namespace[]{namespace});
//    LOGGER.debug("Prefix: {}, rootElement: {}", pPrefix, pRootElement);
//    if (!nodes.isEmpty()) {
//      partialDocument = (Element) nodes.get(0);
//    } else {
//      LOGGER.warn("Partial document not found!");
//    }
//    return partialDocument;
//  }
}
