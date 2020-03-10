/*
 * Created on 2005-08-30
 */

package org.tw.persistence.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassidBindings {
  private Map<String, Class> id2type = new HashMap<String, Class>();
  private Map<Class, String> type2id = new HashMap<Class, String>();
  
  public Set<Class> classes() {
    return type2id.keySet();
  }
  
  public Class<?> classFor(String classid) {
    return id2type.get(classid);
  }
  
  public String classidFor(Class<?> type) {
    return type2id.get(type);
  }
  
  public void add(String id, Class type) {
    id2type.put(id, type);
    type2id.put(type, id);
  }
  
//  public static ClassidBindings fromAnnotations(Class... types) {
//    ClassidBindings binds = new ClassidBindings();
//    for (Class<?> t : types) {
//      Classid id = t.getAnnotation(Classid.class);
//      if (id != null)
//        binds.add(id.value(), t);
//    }
//    return binds;
//  }
  
  public static ClassidBindings fromMap(Map<String, Class> map) {
    ClassidBindings binds = new ClassidBindings();
    for (String k : map.keySet())
      binds.add(k, map.get(k));
    return binds;
  }
}
