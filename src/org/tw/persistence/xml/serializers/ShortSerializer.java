/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.serializers;

import org.tw.persistence.xml.ILoadSession;
import org.w3c.dom.Element;

public class ShortSerializer extends PrimitiveSerializer<Short> {
  public Short load(ILoadSession ses, Element elem) {
    return Short.parseShort(elem.getTextContent());
  }

}
