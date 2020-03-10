/*
 * Created on 2004-08-31
 */

package org.tw.web;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XmlKit {
  public static Element[] childElems(Element root, String tag) {
    NodeList children = root.getChildNodes();
    List<Element> res = new ArrayList<Element>();
    for (int i = 0; i < children.getLength(); ++i) {
      Node n = children.item(i);
      if (n instanceof Element && ((Element)n).getTagName().equals(tag)) {
        res.add((Element) n);
      }
    }
    return (Element[]) res.toArray(new Element[res.size()]);
  }

  public static Document createDoc() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();
      return document;
    } catch (FactoryConfigurationError e) {
    } catch (ParserConfigurationException e) {
    }
    return null;
  }

  public static Element firstChild(Element root, String tag) {
    Element[] elems = XmlKit.childElems(root, tag);
    if (elems.length < 1)
      return null;
    return elems[0];
  }

  public static String firstChildTextContent(Element root, String tag) {
    return firstChild(root,tag).getTextContent();
  }
  
  public static Document loadDoc(File file) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(file);
      return doc;

    } catch (ParserConfigurationException e) {
      //e.printStackTrace();
    } catch (SAXException e) {
      //e.printStackTrace();
    } catch (IOException e) {
      //e.printStackTrace();
    }
    return null;
  }

  public static Document loadDoc(String filename) {
    return loadDoc(new File(filename));
  }

  public static boolean saveDoc(File file, Document document) {
    try {
      Element root = document.getDocumentElement();

      TransformerFactory transFactory = TransformerFactory.newInstance();
      Transformer trans = transFactory.newTransformer();
      OutputStream out = new FileOutputStream(file);
      try {
        trans.transform(new DOMSource(root), new StreamResult(out));
      } finally {
        out.close();
      }
      return true;
    } catch (DOMException e) {
    } catch (TransformerConfigurationException e) {
    } catch (FileNotFoundException e) {
    } catch (FactoryConfigurationError e) {
    } catch (TransformerFactoryConfigurationError e) {
    } catch (TransformerException e) {
    } catch (IOException e) {
    }
    return false;
  }

  public static boolean saveDoc(String filename, Document document) {
    return saveDoc(new File(filename), document);
  }
}
