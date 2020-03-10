/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml;

import org.w3c.dom.Element;

public interface IXmlSerializer<T> {
  T load(ILoadSession ses, Element elem);
  void store(IStoreSession ses, Element elem, T obj);
}
