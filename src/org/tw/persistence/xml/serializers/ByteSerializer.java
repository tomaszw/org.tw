/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.serializers;

import org.tw.persistence.xml.ILoadSession;
import org.tw.persistence.xml.IStoreSession;
import org.tw.persistence.xml.IXmlSerializer;
import org.w3c.dom.Element;

public class ByteSerializer implements IXmlSerializer<Byte> {
  public Byte load(ILoadSession ses, Element elem) {
    return Byte.parseByte(elem.getTextContent());
  }

  public void store(IStoreSession ses, Element elem, Byte obj) {
    elem.setTextContent(obj.toString());
  }

}
