/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.serializers;

import org.tw.persistence.xml.ILoadSession;
import org.w3c.dom.Element;

public class DoubleSerializer extends PrimitiveSerializer<Double> {
  public Double load(ILoadSession ses, Element elem) {
    return Double.parseDouble(elem.getTextContent());
  }

}
