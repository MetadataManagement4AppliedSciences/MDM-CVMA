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
import com.thebuzzmedia.exiftool.Tag;
import java.util.regex.Pattern;


/**
 * Enumeration defining all tags used by the CVMA community. The defined tags
 * are used inside the exif tool. Only defined tags are available via the tool.
 * For further information see
 * <a href="http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html">
 * http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html
 * </a>
 *
 * @author hartmann-v
 */
public enum CvmaTags implements Tag {
  // **************************************************************************
  // Very first test with all fields defined as STRING and safe, not avoided and 
  // not protected.
  // **************************************************************************
  TITLE("Title", false, false, false, DataType.STRING),
  TYPE("Type", false, false, false, DataType.STRING),
  RELATION("Relation", false, false, false, DataType.STRING),
  VOLUME("Volume", false, false, false, DataType.STRING),
  FIGURE("Figure", false, false, false, DataType.STRING),
  IDENTIFIER("Identifier", false, false, false, DataType.STRING),
  PHOTOGRAPHICTYPE("PhotographicType", false, false, false, DataType.STRING),
  DIGITALSOURCETYPE("DigitalSourceType", false, false, false, DataType.STRING),
  PHOTOGRAPHICCONTEXT("PhotographicContext", false, false, false, DataType.STRING),
  CREATEDATE("CreateDate", false, false, false, DataType.STRING),
  LOCATIONCREATEDWORLDREGION("LocationCreatedWorldRegion", false, false, false, DataType.STRING),
  LOCATIONCREATEDCOUNTRYNAME("LocationCreatedCountryName", false, false, false, DataType.STRING),
  LOCATIONCREATEDPROVINCESTATE("LocationCreatedProvinceState", false, false, false, DataType.STRING),
  LOCATIONCREATEDCITY("LocationCreatedCity", false, false, false, DataType.STRING),
  LOCATIONCREATEDSUBLOCATION("LocationCreatedSublocation", false, false, false, DataType.STRING),
  LOCATIONPARTOFBUILDING("LocationPartOfBuilding", false, false, false, DataType.STRING),
  GPSLATITUDE("GPSLatitude", false, false, false, DataType.STRING),
  GPSLONGITUDE("GPSLongitude", false, false, false, DataType.STRING),
  LOCATIONCREATEDLOCATIONID("LocationcreatedLocationId", false, false, false, DataType.STRING),
  DIRECTION("Direction", false, false, false, DataType.STRING),
  PANE("Pane", false, false, false, DataType.STRING),
  ROW("Row", false, false, false, DataType.STRING),
  COLUMN("Column", false, false, false, DataType.STRING),
  FORMERLOCATION("FormerLocation", false, false, false, DataType.STRING),
  FORMERLOCATIONIDS("FormerLocationIds", false, false, false, DataType.STRING),
  OBJECTHEIGHT("ObjectHeight", false, false, false, DataType.STRING),
  OBJECTWIDTH("ObjectWidth", false, false, false, DataType.STRING),
  OBJECTDIAMETER("ObjectDiameter", false, false, false, DataType.STRING),
  AOCIRCADATECREATED("AOCircaDateCreated", false, false, false, DataType.STRING),
  AGEDETERMINATIONSTART("AgeDeterminationStart", false, false, false, DataType.STRING),
  AGEDETERMINATIONEND("AgeDeterminationEnd", false, false, false, DataType.STRING),
  RESTORATIONHISTORY("RestorationHistory", false, false, false, DataType.STRING),
  ENTITYNAME("EntityName", false, false, false, DataType.STRING),
  ENTITYIDENTIFIER("EntityIdentifier", false, false, false, DataType.STRING),
  ENTITYROLE("EntityRole", false, false, false, DataType.STRING),
  ICONCLASS_NOTATION("IconclassNotation", false, false, false, DataType.STRING),
  ICONCLASS_DESCRIPTION("IconclassDescription", false, false, false, DataType.STRING),
  MARKED("Marked", false, false, false, DataType.STRING),
  CREATOR("Creator", false, false, false, DataType.STRING),
  OWNER("Owner", false, false, false, DataType.STRING),
  PUBLISHER("Publisher", false, false, false, DataType.STRING),
  CREDIT("Credit", false, false, false, DataType.STRING),
  USAGETERMS("UsageTerms", false, false, false, DataType.STRING),
  WEBSTATEMENT("WebStatement", false, false, false, DataType.STRING),
  PUBLISHINGSTATUS("PublishingStatus", false, false, false, DataType.STRING),
  INSTRUCTION("Instruction", false, false, false, DataType.STRING);
//  ,
//  DOCUMENTID("DocumentID", false, false, false, DataType.STRING),
//  CAPTION_ABSTRACT("Caption-Abstract", false, false, false, DataType.STRING),
//  STRIPBYTECOUNTS("StripByteCounts", false, false, false, DataType.STRING),
//  FILESIZE("FileSize", false, false, false, DataType.STRING),
//  OBJECTNAME("ObjectName", false, false, false, DataType.STRING),
//  FILEINODECHANGEDATE("FileInodeChangeDate", false, false, false, DataType.STRING),
//  GLOBALANGLE("GlobalAngle", false, false, false, DataType.STRING),
//  BY_LINE("By-line", false, false, false, DataType.STRING),
//  PHOTOSHOPTHUMBNAIL("PhotoshopThumbnail", false, false, false, DataType.STRING),
//  STRIPOFFSETS("StripOffsets", false, false, false, DataType.STRING),
//  FILEACCESSDATE("FileAccessDate", false, false, false, DataType.STRING),
//  EXIFBYTEORDER("ExifByteOrder", false, false, false, DataType.STRING),
//  IMAGESIZE("ImageSize", false, false, false, DataType.STRING),
//  FILEPERMISSIONS("FilePermissions", false, false, false, DataType.STRING),
//  COPYRIGHTFLAG("CopyrightFlag", false, false, false, DataType.STRING),
//  FILEMODIFYDATE("FileModifyDate", false, false, false, DataType.STRING),
//  XMPTOOLKIT("XMPToolkit", false, false, false, DataType.STRING),
//  URL("URL", false, false, false, DataType.STRING),
//  DESCRIPTION("Description", false, false, false, DataType.STRING),
//  GPSPOSITION("GPSPosition", false, false, false, DataType.STRING),
//  CURRENTIPTCDIGEST("CurrentIPTCDigest", false, false, false, DataType.STRING),
//  EXIFTOOLVERSION("ExifToolVersion", false, false, false, DataType.STRING),
//  FILENAME("FileName", false, false, false, DataType.STRING),
//  DIRECTORY("Directory", false, false, false, DataType.STRING);
  /**
   * For further information see
   * <a href="http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html">
   * http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html
   * </a>
   */
  private final boolean avoided;
  /**
   * For further information see
   * <a href="http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html">
   * http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html
   * </a>
   */
  private final boolean unsafe;
  /**
   * For further information see
   * <a href="http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html">
   * http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html
   * </a>
   */
  private final boolean protectedField;
  /**
   * For further information see
   * <a href="http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html">
   * http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html
   * </a>
   */
  private final String name;
  /**
   * For further information see
   * <a href="http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html">
   * http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/index.html
   * </a>
   */
  private final DataType type;

  /**
   * Constructor
   *
   * @param name Name
   * @param unsafe unsafe
   * @param avoided avoided
   * @param protectedField protected field
   * @param type type
   */
  private CvmaTags(final String name, final boolean unsafe, final boolean avoided, final boolean protectedField, final DataType type) {
    this.avoided = avoided;
    this.unsafe = unsafe;
    this.protectedField = protectedField;
    this.name = name;
    this.type = type;
  }

//  @Override
  public boolean isAvoided() {
    return avoided;
  }

//  @Override
  public boolean isUnsafe() {
    return unsafe;
  }

//  @Override
  public boolean isProtectedField() {
    return protectedField;
  }

  @Override
  public String getName() {
    return name;
  }
//
//  public DataType getType() {
//    return type;
//  }

  @Override
  public <T> T parse(String value) {
    return type.parse(value);
  }
@SuppressWarnings("unchecked")
	private enum DataType {
		INTEGER {
			@Override
			public <T> T parse(String value) {
				return (T) Integer.valueOf(Integer.parseInt(value));
			}
		},

		LONG {
			@Override
			public <T> T parse(String value) {
				return (T) Long.valueOf(Long.parseLong(value));
			}
		},

		DOUBLE {
			@Override
			public <T> T parse(String value) {
				return (T) Double.valueOf(Double.parseDouble(value));
			}
		},

		STRING {
			@Override
			public <T> T parse(String value) {
				return (T) value;
			}
		},

		ARRAY {
			@Override
			public <T> T parse(String value) {
				return (T) value.split(Pattern.quote(Constants.SEPARATOR)); }
		};
    
   	public abstract <T> T parse(String value);
	}
 }
