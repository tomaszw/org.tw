/*
 * Created on 2007-08-10
 */

package org.tw.console;

import java.util.HashMap;
import java.util.Map;

public class ColorCache {
  static Map<Integer, java.awt.Color> awtcolors = new HashMap<Integer,java.awt.Color>();
  
  public static java.awt.Color getAwtColor(int rgba) {
    Integer ix = Integer.valueOf(rgba);
    java.awt.Color c = awtcolors.get(ix);
    if(c==null) {
      c = new java.awt.Color(rgba);
      awtcolors.put(ix,c);
    }
    return c;
  }
}
