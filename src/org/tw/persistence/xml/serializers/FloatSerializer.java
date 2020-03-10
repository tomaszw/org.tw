/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.serializers;

import org.tw.persistence.xml.ILoadSession;
import org.w3c.dom.Element;

public class FloatSerializer extends PrimitiveSerializer<Float> {
  public Float load(ILoadSession ses, Element elem) {
    return Float.parseFloat(elem.getTextContent());
  }
}
