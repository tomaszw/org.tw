/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.serializers;

import org.tw.persistence.xml.ILoadSession;
import org.w3c.dom.Element;

public class CharSerializer extends PrimitiveSerializer<Character> {
  public Character load(ILoadSession ses, Element elem) {
    return Character.valueOf(elem.getTextContent().charAt(0));
  }

}
