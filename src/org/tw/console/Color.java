/*
 * Created on 2005-09-11
 */

package org.tw.console;

import java.io.Serializable;

import org.tw.geometry.Vec3d;

public class Color implements Cloneable, Serializable {
  public static final Color BLACK = new Color(0x000000);
  public static final Color BLUE = new Color(0x0000ff);
  public static final Color GREEN = new Color(0x00ff00);
  public static final Color RED = new Color(0xff0000);
  public static final Color WHITE = new Color(0xffffff);
  public static final Color LIGHT_GRAY = new Color(0xa0a0a0);
  private static final long serialVersionUID = 1L;

  private java.awt.Color m_awtColor;
  private int m_rgb;

  public Color() {
  }

  public Color(int rgb) {
    this.m_rgb = rgb;
  }

  public java.awt.Color awtColor() {
    if (m_awtColor == null)
      m_awtColor = new java.awt.Color(m_rgb);
    return m_awtColor;
  }

  @Override
  public Color clone() {
    try {
      Color res = (Color) super.clone();
      res.m_awtColor = new java.awt.Color(m_rgb);
      return res;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Color other = (Color) obj;
    if (m_rgb != other.m_rgb)
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + m_rgb;
    return result;
  }

  public int rgb() {
    return m_rgb;
  }

  public Vec3d rgbVec() {
    double r = (m_rgb & 0xff0000) >> 16;
    double g = (m_rgb & 0x00ff00) >> 8;
    double b = m_rgb & 0xff;
    r /= 255;
    g /= 255;
    b /= 255;
    return new Vec3d(r, g, b);
  }

  public int value() {
    return m_rgb;
  }
}
