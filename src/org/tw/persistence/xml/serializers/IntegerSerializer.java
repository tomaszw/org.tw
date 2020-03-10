/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.serializers;

import org.tw.persistence.xml.ILoadSession;
import org.w3c.dom.Element;

public class IntegerSerializer extends PrimitiveSerializer<Integer> {
  public Integer load(ILoadSession ses, Element elem) {
    return Integer.parseInt(elem.getTextContent());
  }
}
