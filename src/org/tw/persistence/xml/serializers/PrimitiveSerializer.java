/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.serializers;

import org.tw.persistence.xml.IStoreSession;
import org.tw.persistence.xml.IXmlSerializer;
import org.w3c.dom.Element;

public abstract class PrimitiveSerializer<T> implements IXmlSerializer<T> {
  public void store(IStoreSession ses, Element elem, T obj) {
    elem.setTextContent(obj.toString());
  }
}
