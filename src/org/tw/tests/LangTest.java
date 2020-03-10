/*
 * Created on 2005-08-30
 */

package org.tw.tests;

import static org.tw.lang.LanguageKit.cast;

public class LangTest {
  public static void main(String[] args) {
    Object obj = new Integer(100);
  
    
    Double i = cast(obj);
    System.out.println(i);
  }
}
