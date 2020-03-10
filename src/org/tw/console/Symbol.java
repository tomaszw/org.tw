package org.tw.console;


import java.io.Serializable;

import org.tw.geometry.Vec3d;

public class Symbol implements Cloneable, Serializable {
  private static final long serialVersionUID = 1L;
  public static final int FLASH = 1;
  public static final int FADE_IN = 1 << 1;
  public Color back;
  public char ch;

  public Color fore;
  public int flags;

  public static Symbol create(char ch, int f) {
    return new Symbol(ch, new Color(f), Color.BLACK);
  }

  public static Symbol create(char ch, int f, int b) {
    return new Symbol(ch, new Color(f), new Color(b));
  }

  public Symbol() {
    ch = '?';
    fore = Color.WHITE;
    back = Color.BLACK;
  }

  public Symbol applyLight(Vec3d lt) {
    int rgb = fore.value();
    int r = (rgb & 0xff0000) >> 16;
    int g = (rgb & 0x00ff00) >> 8;
    int b = rgb & 0xff;
    r = (int) (r * lt.x);
    g = (int) (g * lt.y);
    b = (int) (b * lt.z);
    rgb = r << 16 | g << 8 | b;

    Symbol s = clone();
    s.fore = new Color(rgb);
    return s;
  }

  public Symbol(char ch, Color f, Color b) {
    this.ch = ch;
    fore = f;
    back = b;
  }

  @Override
  public Symbol clone() {
    try {
      Symbol res = (Symbol) super.clone();
      res.back = back.clone();
      res.fore = fore.clone();
      return res;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Symbol dimmed() {
    int rgb = fore.value();
    int r = (rgb & 0xff0000) >> 16;
    int g = (rgb & 0x00ff00) >> 8;
    int b = rgb & 0xff;
    r = (int) (r* 1);
    g = (int) (g*1);
    b = (int) (b*1);
    rgb = r << 16 | g << 8 | b;

    Symbol s = clone();
    s.fore = new Color(rgb);
    return s;
    // return this;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Symbol other = (Symbol) obj;
    if (back == null) {
      if (other.back != null)
        return false;
    } else if (!back.equals(other.back))
      return false;
    if (ch != other.ch)
      return false;
    if (fore == null) {
      if (other.fore != null)
        return false;
    } else if (!fore.equals(other.fore))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((back == null) ? 0 : back.hashCode());
    result = PRIME * result + ch;
    result = PRIME * result + ((fore == null) ? 0 : fore.hashCode());
    return result;
  }
}
