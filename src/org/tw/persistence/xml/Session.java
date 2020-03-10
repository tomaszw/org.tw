/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml;

import java.lang.reflect.*;
import java.util.*;

import org.tw.persistence.xml.annotations.PersistAs;
import org.tw.persistence.xml.annotations.Transient;
import org.tw.web.XmlKit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Session implements ILoadSession, IStoreSession {
  public static final String         CLASS_TAG        = "class";
  public static final String         ARRAY_TAG        = "array";
  public static final String         CLASSID_TAG      = "type";
  public static final String         IDREF_TAG        = "ref";
  public static final String         NULL_TAG         = "null";
  public static final String         OBJECTID_TAG     = "id";
  public Document                    sessionDoc;

  private ClassidBindings            m_baseBinds;
  private Map<Class, IXmlSerializer> m_serializers;
  private Map<Integer, Object>       m_trackingLoaded = new HashMap<Integer, Object>();
  private Map<Object, Ident>         m_trackingStored = new HashMap<Object, Ident>();

  static class Ident {
    Element       elem;
    List<Element> refelems = new ArrayList<Element>();
    int           id;
    int           numrefs;
    Object        obj;
  }

  public Document sessionDoc() {
    return sessionDoc;
  }

  public Session(ClassidBindings binds, Map<Class, IXmlSerializer> serializers) {
    m_baseBinds = binds;
    m_serializers = serializers;
  }

  public void changeTarget(Document tgt) {
    m_trackingStored.clear();
    m_trackingLoaded.clear();
    sessionDoc = tgt;
  }

  public void close() {
    int i = 1;
    for (Ident id : m_trackingStored.values()) {
      if (id.numrefs > 0) {
        id.elem.setAttribute(OBJECTID_TAG, Integer.toString(i));

        for (Element ref : id.refelems) {
          ref.setAttribute(OBJECTID_TAG, Integer.toString(i));
        }
        ++i;
      }
    }
  }

  public Object load(Element elem) {
    sessionDoc = elem.getOwnerDocument();
    return loadInternal(elem);
  }

  public Element store(Object obj) {
    return storeInternal(obj);
  }

  private Class<?> getClassForId(String classid) {
    return m_baseBinds.classFor(classid);
  }

  private void loadClassFields(Element elem, Object obj, Class<?> objClass) {
    if (objClass.equals(Object.class))
      return;
    loadClassFields(elem, obj, objClass.getSuperclass());
    Field[] fields = objClass.getDeclaredFields();
    for (Field f : fields) {
      if ((f.getModifiers() & (Modifier.TRANSIENT | Modifier.STATIC)) == 0
          && f.getAnnotation(Transient.class) == null) {
        PersistAs persName = f.getAnnotation(PersistAs.class);
        Element propElem = null;
        Element[] propElems = XmlKit.childElems(elem, "property");
        for (Element propE : propElems) {
          if (propE.hasAttribute("name") && persName != null
              && propE.getAttribute("name").equals(persName.value())) {
            propElem = propE;
            break;
          }
          if (propE.hasAttribute("fieldName")
              && propE.getAttribute("fieldName").equals(f.getName())) {
            propElem = propE;
            break;
          }
        }
        if (propElem == null)
          break;
        try {
          Element ch = (Element) propElem.getChildNodes().item(0);
          Object value = loadInternal(ch);
          f.setAccessible(true);
          f.set(obj, value);
        } catch (IllegalArgumentException e) {
          throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  private Object loadInternal(Element elem) {
    if (elem.getTagName().equals(NULL_TAG))
      return null;
    if (elem.getTagName().equals(IDREF_TAG)) {
      int objid = Integer.parseInt(elem.getAttribute(OBJECTID_TAG));
      return m_trackingLoaded.get(objid);
    }
    if (elem.getTagName().equals(ARRAY_TAG)) {
      Class<?> compType = null;
      if (elem.hasAttribute("compTypeId")) {
        compType = m_baseBinds.classFor(elem.getAttribute("compTypeId"));
      } else if (elem.hasAttribute("compTypeName")) {
        try {
          compType = Class.forName(elem.getAttribute("compTypeName"));
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }
      }
      NodeList ch = elem.getChildNodes();
      Object arr = Array.newInstance(compType, ch.getLength());
      if (elem.hasAttribute(OBJECTID_TAG))
        m_trackingLoaded.put(Integer.parseInt(elem.getAttribute(OBJECTID_TAG)), arr);
      for (int i = 0; i < ch.getLength(); ++i) {
        Array.set(arr, i, loadInternal((Element) ch.item(i)));
      }
      return arr;
    }
    if (elem.getTagName().equals("object")) {
      Class<?> type = null;
      if (elem.hasAttribute("classid")) {
        type = m_baseBinds.classFor(elem.getAttribute("classid"));
      } else if (elem.hasAttribute("class")) {
        try {
          type = Class.forName(elem.getAttribute("class"));
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }
      }
      IXmlSerializer ser = m_serializers.get(type);
      if (ser == null) {
        Object obj = newObjectFromMetadata(elem);
        if (elem.hasAttribute(OBJECTID_TAG))
          m_trackingLoaded.put(Integer.parseInt(elem.getAttribute(OBJECTID_TAG)), obj);
        loadClassFields(elem, obj, type);
        return obj;
      } else {
        Object obj = ser.load(this, elem);
        if (elem.hasAttribute(OBJECTID_TAG))
          m_trackingLoaded.put(Integer.parseInt(elem.getAttribute(OBJECTID_TAG)), obj);
        return obj;
      }
    }
    return null;
  }

  public Object newObjectFromMetadata(Element elem) {
    Class<?> type = null;
    if (elem.hasAttribute("classid")) {
      type = m_baseBinds.classFor(elem.getAttribute("classid"));
    } else if (elem.hasAttribute("class")) {
      try {
        type = Class.forName(elem.getAttribute("class"));
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
    Object obj;
    try {
      Constructor c = null;
      try {
        c = type.getConstructor(new Class[] {});
      } catch (SecurityException e) {
        throw new RuntimeException(e);
      } catch (NoSuchMethodException e) {}
      if (c != null) {
        c.setAccessible(true);
        obj = c.newInstance();
      } else
        obj = type.newInstance();
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    return obj;
  }
  
  private void storeClassFields(Element elem, Object obj, Class<?> objClass) {
    if (objClass.equals(Object.class))
      return;
    storeClassFields(elem, obj, objClass.getSuperclass());
    // System.out.println("Storing " + objClass.getName());
    Field[] fields = objClass.getDeclaredFields();
    for (Field f : fields) {
      if ((f.getModifiers() & (Modifier.STATIC | Modifier.TRANSIENT)) != 0
          || f.getAnnotation(Transient.class) != null)
        continue;

      PersistAs persistName = f.getAnnotation(PersistAs.class);
      Element propElem = elem.getOwnerDocument().createElement("property");
      if (persistName != null)
        propElem.setAttribute("name", persistName.value());
      else
        propElem.setAttribute("fieldName", f.getName());
      elem.appendChild(propElem);

      f.setAccessible(true);
      try {
        Element fieldElem = storeInternal(f.get(obj));
        propElem.appendChild(fieldElem);
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  private Element storeInternal(Object obj) {
    Element e = null;
    if (obj == null) {
      return sessionDoc.createElement(NULL_TAG);
    }

    Class<?> objClass = obj.getClass();
    if (m_trackingStored.containsKey(obj)) {
      e = sessionDoc.createElement("ref");
      Ident ident = m_trackingStored.get(obj);
      ident.refelems.add(e);
      ++ident.numrefs;
    } else {
      Ident ident = m_trackingStored.get(obj);
      if (ident == null) {
        ident = new Ident();
        ident.numrefs = 0;
        ident.obj = obj;
        m_trackingStored.put(obj, ident);
      }
      if (objClass.isArray()) {
        Class<?> compClass = objClass.getComponentType();
        e = sessionDoc.createElement("array");
        ident.elem = e;
        String clsid = m_baseBinds.classidFor(compClass);
        if (clsid != null)
          e.setAttribute("compTypeId", clsid);
        else
          e.setAttribute("compTypeName", compClass.getName());
        int len = Array.getLength(obj);
        for (int i = 0; i < len; ++i) {
          e.appendChild(storeInternal(Array.get(obj, i)));
        }
      } else {
        String clsid = m_baseBinds.classidFor(objClass);
        if (clsid != null) {
          e = sessionDoc.createElement("object");
          ident.elem = e;
          e.setAttribute("classid", clsid);
        } else {
          clsid = objClass.getName();
          e = sessionDoc.createElement("object");
          ident.elem = e;
          e.setAttribute("class", clsid);
        }
        IXmlSerializer ser = m_serializers.get(objClass);
        if (ser == null)
          storeClassFields(e, obj, objClass);
        else
          ser.store(this, e, obj);
      }
    }
    return e;
  }
}
