/*
 * Created on 2005-10-10
 */

package org.tw.persistence.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface IStoreSession {
  Document sessionDoc();
  Element store(Object obj);
  void close();
}
