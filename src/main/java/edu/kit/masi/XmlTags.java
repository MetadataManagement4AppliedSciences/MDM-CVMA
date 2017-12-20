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

import org.jdom.Namespace;

/**
 * All tags which should be available inside the exif. This may only a small
 * part of the complete metadata.
 *
 * @author hartmann-v
 */
public enum XmlTags {
  TITLE(CvmaXml.NS_DC, "title", "Title"),
  TYPE(CvmaXml.NS_CVMA, "Type", "Type"),
  RELATION(CvmaXml.NS_DC, "relation", "Relation"),
  VOLUME(CvmaXml.NS_CVMA, "Volume", "Volume"),
  FIGURE(CvmaXml.NS_CVMA, "Figure", "Figure"),
  IDENTIFIER(CvmaXml.NS_DC, "identifier", "Identifier"),
  PHOTOGRAPHICTYPE(CvmaXml.NS_CVMA, "PhotographicType", "PhotographicType"),
  DIGITALSOURCETYPE(CvmaXml.NS_IPTC, "DigitalSourceType", "DigitalSourceType"),
  PHOTOGRAPHICCONTEXT(CvmaXml.NS_CVMA, "PhotographicContext", "PhotographicContext"),
  CREATEDATE(CvmaXml.NS_XMP, "CreateDate", "CreateDate"),
  LOCATIONCREATEDWORLDREGION(CvmaXml.NS_IPTC, "WorldRegion", "LocationCreatedWorldRegion"),
  LOCATIONCREATEDCOUNTRYNAME(CvmaXml.NS_IPTC, "CountryName", "LocationCreatedCountryName"),
  LOCATIONCREATEDPROVINCESTATE(CvmaXml.NS_IPTC, "ProvinceState", "LocationCreatedProvinceState"),
  LOCATIONCREATEDCITY(CvmaXml.NS_IPTC, "City", "LocationCreatedCity"),
  LOCATIONCREATEDSUBLOCATION(CvmaXml.NS_IPTC, "Sublocation", "LocationCreatedSublocation"),
  LOCATIONPARTOFBUILDING(CvmaXml.NS_CVMA, "PartOfBuilding", "PartOfBuilding"),
  GPSLATITUDE(CvmaXml.NS_CVMA, "GPSLatitude", "GPSLatitude"),
  GPSLONGITUDE(CvmaXml.NS_CVMA, "GPSLongitude", "GPSLongitude"),
  LOCATIONID(CvmaXml.NS_IPTC, "LocationId", "LocationCreatedLocationId"),
  DIRECTION(CvmaXml.NS_CVMA, "Direction", "Direction"),
  PANE(CvmaXml.NS_CVMA, "Pane", "Pane"),
  ROW(CvmaXml.NS_CVMA, "Row", "Row"),
  COLUMN(CvmaXml.NS_CVMA, "Column", "Column"),
  FORMERLOCATION(CvmaXml.NS_CVMA, "FormerLocation", "FormerLocation"),
  FORMERLOCATIONIDS(CvmaXml.NS_CVMA, "FormerLocationIds", "FormerLocationIds"),
  OBJECTHEIGHT(CvmaXml.NS_CVMA, "ObjectHeight", "ObjectHeight"),
  OBJECTWIDTH(CvmaXml.NS_CVMA, "ObjectWidth", "ObjectWidth"),
  OBJECTDIAMETER(CvmaXml.NS_CVMA, "ObjectDiameter", "ObjectDiameter"),
  AOCIRCADATECREATED(CvmaXml.NS_IPTC, "AOCircaDateCreated", "ArtworkDateCreated"),
  AGEDETERMINATIONSTART(CvmaXml.NS_CVMA, "AgeDeterminationStart", "AgeDeterminationStart"),
  AGEDETERMINATIONEND(CvmaXml.NS_CVMA, "AgeDeterminationEnd", "AgeDeterminationEnd"),
  RESTORATIONHISTORY(CvmaXml.NS_CVMA, "RestorationHistory", "RestorationHistory"),
  RELATEDENTITIESENTITYNAME(CvmaXml.NS_CVMA, "EntityName", "EntityName"),
  RELATEDENTITIESENTITYIDENTIFIER(CvmaXml.NS_CVMA, "EntityIdentifier", "EntityIdentifier"),
  RELATEDENTITIESENTITYROLE(CvmaXml.NS_CVMA, "EntityRole", "EntityRole"),
  ICONCLASSNOTATION(CvmaXml.NS_CVMA, "IconclassNotation", "IconclassNotation"),
  ICONCLASSDESCRIPTION(CvmaXml.NS_CVMA, "IconclassDescription", "IconclassDescription"),
  MARKED(CvmaXml.NS_XMP_RIGHTS, "Marked", "Marked"),
  CREATOR(CvmaXml.NS_DC, "creator", "Creator"),
  OWNER(CvmaXml.NS_XMP_RIGHTS, "Owner", "Owner"),
  PUBLISHER(CvmaXml.NS_DC, "publisher", "Publisher"),
  CREDIT(CvmaXml.NS_PHOTO, "Credit", "Credit"),
  USAGETERMS(CvmaXml.NS_XMP_RIGHTS, "UsageTerms", "UsageTerms"),
  WEBSTATEMENT(CvmaXml.NS_XMP_RIGHTS, "WebStatement", "WebStatement"),
  PUBLISHINGSTATUS(CvmaXml.NS_CVMA, "PublishingStatus", "PublishingStatus"),
  PHOTOSHOPINSTRUCTIONS(CvmaXml.NS_PHOTO, "Instructions", "Instructions");
  /**
   * Label used inside the XML. See CVMA-XMP-Specification.
   */
  String xmlLabel;
  /**
   * Linked namespace to the element.
   */
  Namespace namespace;
  /**
   * Label used in exif.
   */
  String exifLabel;

  /**
   * Default constructor.
   *
   * @param pNamespace Namespace of the element.
   * @param pXmlLabel Label of the element.
   * @param pExifLabel Exif label of the element.
   */
  XmlTags(Namespace pNamespace, String pXmlLabel, String pExifLabel) {
    namespace = pNamespace;
    xmlLabel = pXmlLabel;
    exifLabel = pExifLabel;
  }

  @Override
  public String toString() {
    return exifLabel;
  }

  /**
   * Get element name of the tag including namespace. e.g.: dc:title
   *
   * @return Element name.
   */
  public String getElementName() {
    return String.format("%s:%s", namespace.getPrefix(), xmlLabel);
  }

  /**
   * Get exif label of the tag. e.g.: Title
   *
   * @return Exif label.
   */
  public String getExifLabel() {
    return exifLabel;
  }

  /**
   * Get prefix of the tag.
   *
   * @return Prefix of the tag.
   */
  public String getPrefix() {
    return namespace.getPrefix();
  }

  /**
   * Get namespace of the tag.
   *
   * @return Namespace (URI) of the tag.
   */
  public String getUri() {
    return namespace.getURI();
  }

  /**
   * Get tag from exif label.
   *
   * @param pExifLabel Exif label.
   * @return Linked tag.
   */
  public static XmlTags fromExifLabel(String pExifLabel) {
    XmlTags returnValue = null;
    for (XmlTags item : XmlTags.values()) {
      if (item.toString().equals(pExifLabel)) {
        returnValue = item;
      }
    }
    return returnValue;
  }

  /**
   * Get tag from element name.
   *
   * @param elementName Element name.
   * @return Linked tag.
   */
  public static XmlTags fromElementName(String elementName) {
    XmlTags returnValue = null;
    for (XmlTags item : XmlTags.values()) {
      if (item.getElementName().equals(elementName)) {
        returnValue = item;
      }
    }
    return returnValue;
  }
}
