/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.serializers;

import org.tw.persistence.xml.ILoadSession;
import org.w3c.dom.Element;

public class StringSerializer extends PrimitiveSerializer<String> {
  public String load(ILoadSession ses, Element elem) {
    return elem.getTextContent();
  }
}
