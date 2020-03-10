/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.serializers;

import org.tw.persistence.xml.ILoadSession;
import org.tw.persistence.xml.IStoreSession;
import org.tw.persistence.xml.IXmlSerializer;
import org.w3c.dom.Element;

public class BooleanSerializer implements IXmlSerializer<Boolean> {
  public Boolean load(ILoadSession ses, Element elem) {
    return new Boolean(elem.getTextContent().equals("1"));
  }

  public void store(IStoreSession ses, Element elem, Boolean obj) {
    elem.setTextContent(obj == Boolean.TRUE ? "1" : "0");
  }

}
