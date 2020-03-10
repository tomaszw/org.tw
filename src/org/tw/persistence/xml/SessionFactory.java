/*
 * Created on 2005-10-10
 */

package org.tw.persistence.xml;

import java.util.*;

import org.tw.persistence.xml.serializers.*;
import org.w3c.dom.Document;

public class SessionFactory {
  private ClassidBindings            m_classBinds  = new ClassidBindings();
  private Map<Class, IXmlSerializer> m_serializers = new HashMap<Class, IXmlSerializer>();

  public SessionFactory() {
    registerDefaultSerializers();
  }

  public void addBindings(ClassidBindings binds) {
    for (Class<?> type : binds.classes()) {
      m_classBinds.add(binds.classidFor(type), type);
    }
  }
  public ClassidBindings bindings() {
    return m_classBinds;
  }

  public IStoreSession newSaveSession(Document doc) {
    Session ses = new Session(m_classBinds, m_serializers);
    ses.sessionDoc = doc;
    return ses;
  }
  
  public ILoadSession newLoadSession() {
    Session ses = new Session(m_classBinds, m_serializers);
    return ses;
  }
  
  public void addCustomSerializer(Class<?> type, IXmlSerializer<?> s) {
    m_serializers.put(type, s);
  }

  private void registerDefaultSerializers() {
    addCustomSerializer(Integer.class, new IntegerSerializer());
    addCustomSerializer(Boolean.class, new BooleanSerializer());
    addCustomSerializer(Byte.class, new ByteSerializer());
    addCustomSerializer(Character.class, new CharSerializer());
    addCustomSerializer(Double.class, new DoubleSerializer());
    addCustomSerializer(Float.class, new FloatSerializer());
    addCustomSerializer(Integer.class, new IntegerSerializer());
    addCustomSerializer(Long.class, new LongSerializer());
    addCustomSerializer(Short.class, new ShortSerializer());
    addCustomSerializer(String.class, new StringSerializer());
    addCustomSerializer(HashMap.class, new MapSerializer());
    addCustomSerializer(ArrayList.class, new CollectionSerializer());
    addCustomSerializer(LinkedList.class, new CollectionSerializer());
    addCustomSerializer(HashSet.class, new CollectionSerializer());
  }
}
