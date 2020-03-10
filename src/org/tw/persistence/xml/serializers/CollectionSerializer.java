/*
 * Created on 2005-10-13
 */

package org.tw.persistence.xml.serializers;

import java.util.Collection;

import org.tw.persistence.xml.ILoadSession;
import org.tw.persistence.xml.IStoreSession;
import org.tw.persistence.xml.IXmlSerializer;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CollectionSerializer implements IXmlSerializer<Collection> {

  public Collection load(ILoadSession ses, Element elem) {
    NodeList nl = elem.getChildNodes();
    Collection c = (Collection) ses.newObjectFromMetadata(elem);
    for (int i=0; i<nl.getLength(); ++i) {
      c.add(ses.load((Element)nl.item(i)));
    }
    return c;
  }

  public void store(IStoreSession ses, Element elem, Collection obj) {
    for (Object o : obj) {
      elem.appendChild(ses.store(o));
    }
  }

}
