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

import com.thebuzzmedia.exiftool.Constants;
import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import com.thebuzzmedia.exiftool.Tag;
import https.lod_academy.cvma.ns.xmp.CvmaRootType;
import https.lod_academy.cvma.ns.xmp.ObjectFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Just for quick testing.
 *
 * @author hartmann-v
 */
public class Main {

  /**
   * The logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

//	String property = System.getProperty(ExecutionConstant.EXIFTOOLPATH);
//		if (!Strings.isNullOrEmpty(property)) {
//			return new File(property);
//		}
//		String env = System.getenv(ExecutionConstant.EXIFTOOLENV);
//		if (!Strings.isNullOrEmpty(env)) {
//			return new File(env);
  /**
   * Main method.
   *
   * @param args no args required.
   */
  public static void main(String[] args) {
    try {
      //    try {
      System.setProperty("exiftool.path", "/tmp/exiftool/exiftool");
      test(new File("/tmp/test.tif"));
      Thread.sleep(10000);
//
//      TagUtil.register(CvmaTags.class);
//      JExifTool tool = new JExifTool(); //starts exiftool process
//      JExifInfo info1 = tool.getInfo(new File("/tmp/test.tif")); // create proxy
//      LOGGER.debug("Writing 5 to FOCAL_LENGTH for write 1 -> " + info1.setTagValue(CvmaTags.ICONCLASS_DESCRIPTION, "Volker Hartmann"));
////LOG.info("ISO value is for read 1 = {}", info1.getTag("ExifIFD.ISO)); //execute read
////LOGGER.debug("ISO value is for read 1 = " +  info1.getTagValue(new Tag("cvma:IconclassDescription"))); //execute read
//      LOGGER.debug("ISO value is for read 1 = " + info1.getTagValue(ExifIFD.CREATEDATE)); //execute read
//      Map<String, String> allTagsValues = info1.getAllTagsValues();
//      Map<Tag, String> allSupportedTagsValues = info1.getAllSupportedTagsValues();
//      for (Tag item : allSupportedTagsValues.keySet()) {
//        LOGGER.debug("  " + item.getName().toUpperCase() + "(\"" + item.getName() + "\"),");
////        LOGGER.debug(item.getClass() + "(\"" + item.getType() + "\"" + item.isProtectedField());
//      }
//
////    		ICONCLASS_DESCRIPTION("IconclassDescription", false, false, false, DataType.STRING),
////  LOGGER.debug("******************************************************************");
////      Map<Tag, String> allSupportedTagsValues = info1.getAllSupportedTagsValues();
////      for (Tag item: allSupportedTagsValues.keySet()){
////        LOGGER.debug(item.getName() + ": " + allSupportedTagsValues.get(item));
////      }
//      CvmaXml cvmaXml = new CvmaXml();
//
//      for (XmlTags xmlTag : XmlTags.values()) {
//        String description = allTagsValues.get(xmlTag.toString());
//        if (description != null) {
//          if (!description.trim().isEmpty()) {
//            cvmaXml.addNode(xmlTag.toString(), description);
//          }
//        }
//
//      }
//      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//      Schema schema = sf.newSchema(new File("/tmp/CVMA.xsd"));
//      JAXBContext jaxbContext = JAXBContext.newInstance(Cvma.class);
//      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//      unmarshaller.setSchema(schema);
//      Cvma cvma = (Cvma) unmarshaller.unmarshal(new StringReader(cvmaXml.toXml()));
//      LOGGER.debug("XML is valid!");

//    } catch (SAXException ex) {
//      LOGGER.error(null, ex);
//    } catch (JAXBException ex) {
//      LOGGER.error(null, ex);
//    } catch (JExifException ex) {
//      LOGGER.error(null, ex);
//    } catch (ExifError ex) {
//      LOGGER.error(null, ex);
//    } catch (IOException ex) {
//      LOGGER.error(null, ex);
//    }
System.exit(0);
    } catch (InterruptedException ex) {
      java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Some tests with exiftool.
   *
   * @param pImageFile File containing image with exif metadata.
   */
  public static void test(File pImageFile) {
    try {
      CvmaXml cvmaXml = new CvmaXml();
      //TagUtil.register(CvmaTags.class);
      ExifTool exifTool = new ExifToolBuilder().build();
      CvmaTags[] values = CvmaTags.values();
      Map<Tag, String> imageMeta = exifTool.getImageMeta(pImageFile, new ArrayList<Tag>(Arrays.asList(values)));
      // Conversion to support old implementation based on be.pw999.jexif
      Map<String, String> allTagsValues = new HashMap<>();
      for (Tag tag : imageMeta.keySet()) {
        allTagsValues.put(tag.getName(), imageMeta.get(tag));
      }
      
//      JExifTool tool = new JExifTool(); //starts exiftool process
//      JExifInfo info1 = tool.getInfo(pImageFile); // create proxy
//      Map<String, String> allTagsValues = info1.getAllTagsValues();
      LOGGER.debug("Found {} properties!", allTagsValues.size());
      if (LOGGER.isDebugEnabled()) {
        for (String key : allTagsValues.keySet()) {
          LOGGER.debug("Key: {}    -- value: {}", key, allTagsValues.get(key));
        }
      }

      for (XmlTags xmlTag : XmlTags.values()) {
        LOGGER.debug("XMLTAG {}/{}/{}", xmlTag, xmlTag.toString(), xmlTag.getElementName());
        String description = allTagsValues.get(xmlTag.toString());
        if (description != null) {
          if (!description.trim().isEmpty()) {
            description = description.replace(Constants.SEPARATOR, ", ");
            LOGGER.debug("Add {}={} to content metadata.", xmlTag.toString(), description);
            cvmaXml.addNode(xmlTag.toString(), description);
          }
        }
      }
      String xmlString = cvmaXml.toXml();
      System.out.println("*********************************************");
      System.out.println("*********************************************");
      System.out.println(xmlString);
      System.out.println("*********************************************");
      System.out.println("*********************************************");
      JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      CvmaRootType cvma = ((JAXBElement<CvmaRootType>) unmarshaller.unmarshal(new StringReader(xmlString))).getValue();
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", cvmaXml);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectFactory of = new ObjectFactory();
      marshaller.marshal(of.createCvma(cvma), baos);
      LOGGER.debug(baos.toString());
      LOGGER.debug(cvma.getColumn().toString());
      LOGGER.debug(cvma.getTitle().toString());
      LOGGER.debug(cvma.toString());
    } catch (IOException ex) {
      LOGGER.error(null, ex);
//    } catch (ExifError ex) {
//      LOGGER.error(null, ex);
    } catch (JAXBException ex) {
      LOGGER.error(null, ex);
//    } catch (JExifException ex) {
//      LOGGER.error(null, ex);
    }
  }

}
