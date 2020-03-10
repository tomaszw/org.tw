/*
 * Created on 2005-08-21
 */

package org.tw.lang;

public class LanguageKit {
  public static <T> T cast(Object obj) {
    T r;
    try {
      r = (T) obj;
    } catch (ClassCastException e) {
      return null;
    }
    return r;
  }
}
