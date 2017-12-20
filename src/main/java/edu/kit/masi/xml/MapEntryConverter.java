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
package edu.kit.masi.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import edu.kit.masi.XmlTags;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Converter for Maps. Only entries defined in enumeration XmlTags will be
 * handled.
 *
 * @author hartmann-v
 */
public class MapEntryConverter implements Converter {

  /**
   * Separator working place Potsdam.
   */
  private static String SEPARATOR_1 = ",";
  /**
   * Separator working place Freiburg
   */
  private static String SEPARATOR_2 = "*";

  /**
   * Regular expression for splitting fields with multiple values.
   */
  private static String REGEX = "[" + SEPARATOR_1 + SEPARATOR_2 + "]";

  @Override
  public boolean canConvert(Class clazz) {
    return AbstractMap.class.isAssignableFrom(clazz);
  }

  @Override
  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

    AbstractMap map = (AbstractMap) value;
    for (Object obj : map.entrySet()) {
      Map.Entry entry = (Map.Entry) obj;
      XmlTags xmlTag = XmlTags.fromExifLabel(entry.getKey().toString());
      switch (xmlTag) {
        case TYPE:
        case ICONCLASSNOTATION:
        case FORMERLOCATIONIDS:
        case RELATEDENTITIESENTITYIDENTIFIER:
        case RELATEDENTITIESENTITYROLE:
        case RELATEDENTITIESENTITYNAME:
          // Split be separator
          for (String fieldValue : entry.getValue().toString().split(REGEX)) {
            if (fieldValue != null) {
              writeNode(writer, xmlTag, fieldValue);
            }
          }
          break;
        case MARKED:
        case PUBLISHINGSTATUS:
          if (entry.getValue() != null) {
            writeNode(writer, xmlTag, entry.getValue().toString().toLowerCase());
          }
          break;
        default:
          if (entry.getValue() != null) {
            writeNode(writer, xmlTag, entry.getValue().toString());
          }
      }
    }
  }

  private void writeNode(HierarchicalStreamWriter writer, XmlTags xmlTag, String value) {
    writer.startNode(xmlTag.getElementName());
    writer.addAttribute("xmlns:" + xmlTag.getPrefix(), xmlTag.getUri());
    if (value != null) {
      writer.setValue(value);
    }
    writer.endNode();

  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    Map<String, String> map = new HashMap<>();

    while (reader.hasMoreChildren()) {
      reader.moveDown();

      String key = XmlTags.fromElementName(reader.getNodeName()).getExifLabel(); // nodeName aka element's name
      String value = reader.getValue();
      if (map.containsKey(key)) {
        map.put(key, map.get(key) + SEPARATOR_1 + value);
      } else {
        map.put(key, value);
      }
      reader.moveUp();
    }
    return map;
  }

}
