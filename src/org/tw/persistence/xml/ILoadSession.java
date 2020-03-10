/*
 * Created on 2005-10-10
 */

package org.tw.persistence.xml;

import org.w3c.dom.Element;

public interface ILoadSession {
  Object load(Element root);
  Object newObjectFromMetadata(Element elem);
  void close();
}
