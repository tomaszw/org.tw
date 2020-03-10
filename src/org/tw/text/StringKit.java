/*
 * Created on 2006-07-12
 */

package org.tw.text;

import java.util.Collection;

public class StringKit {
  public static String join(Collection<? extends Object> values, String sep) {
    StringBuffer res = new StringBuffer();
    int idx = 0;
    for (Object o : values) {
      res.append(o.toString());
      if (idx < values.size() - 1)
        res.append(sep);
      ++idx;
    }
    return res.toString();
  }

  public static String trimr(String s) {
    while (s.length() > 0 && Character.isWhitespace(s.charAt(s.length() - 1)))
      s = s.substring(0, s.length() - 1);
    return s;
  }

  public static String wordWrap(String s, int linelen) {
    String r = "";
    int p = 0;
    String word = "";

    int clen = 0;
    while (p < s.length()) {
      boolean addws = false;
      boolean addedlf = false;
      while (p < s.length() && Character.isWhitespace(s.charAt(p))) {
          addws = true;
        if (s.charAt(p) == '\n') {
          r += "\n";
          addedlf=true;
          clen = 0;
        }
        ++p;
      }

      if (addedlf)
        addws = false;
      // skip meta data
      if (p < s.length() - 1 && s.charAt(p) == '@' && s.charAt(p + 1) == '{') {
        p += 2;
        String mattr = "@{";
        while (p < s.length() && s.charAt(p) != '}') {
          mattr += s.charAt(p);
          ++p;
        }
        if (p < s.length() && s.charAt(p) == '}') {
          mattr += '}';
          r += mattr;
          ++p;
        }
      }

      word = addws?" " :"";
      while (p < s.length() && !Character.isWhitespace(s.charAt(p))
          && !(p < s.length() - 1 && s.charAt(p) == '@' && s.charAt(p + 1) == '{')) {
        word += s.charAt(p);
        ++p;
      }

      if (!word.trim().equals("")) {
        if (clen + word.length() + 1 <= linelen) {
          clen += word.length() + 1;
          r += word;// + " ";
        } else {
          r += "\n" + word.trim();// + " ";
          clen = word.length() + 1;
        }
      }
    }
    return r;
  }

  public static String capitalize(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

}
