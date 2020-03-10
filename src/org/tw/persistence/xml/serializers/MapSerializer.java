/*
 * Created on 2005-09-11
 */

package org.tw.persistence.xml.serializers;

import java.util.HashMap;
import java.util.Map;

import org.tw.persistence.xml.ILoadSession;
import org.tw.persistence.xml.IStoreSession;
import org.tw.persistence.xml.IXmlSerializer;
import org.tw.web.XmlKit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MapSerializer implements IXmlSerializer<Map> {
  public Map load(ILoadSession ses, Element elem) {
    Map map = (Map) ses.newObjectFromMetadata(elem);
    Element[] entryElems = XmlKit.childElems(elem, "entry");
    for (Element e : entryElems) {
      NodeList nodes = e.getChildNodes();
      Object key = ses.load((Element) nodes.item(0));
      Object val = ses.load((Element) nodes.item(1));
      map.put(key, val);
    }
    return map;
  }

  public void store(IStoreSession ses, Element elem, Map obj) {
    Document sessionDoc = elem.getOwnerDocument();
    for (Object key : obj.keySet()) {
      Object val = obj.get(key);
      Element e = ses.sessionDoc().createElement("entry");
      elem.appendChild(e);
      elem.appendChild(ses.store(key));
      elem.appendChild(ses.store(val));
    }
  }
}
