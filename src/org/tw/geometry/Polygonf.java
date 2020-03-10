/*
 * Created on 2004-08-10
 */

package org.tw.geometry;

import java.util.ArrayList;
import java.util.List;

public class Polygonf {
  private List<Vec2f> m_points = new ArrayList<Vec2f>();

  public Polygonf() {
  }

  public void addPoint(Vec2f p) {
    m_points.add(new Vec2f(p));
  }

  public Rectanglef getBoundingRect() {
    Vec2f min = new Vec2f();
    Vec2f max = new Vec2f();
    min.assign((Vec2f) m_points.get(0));
    max.assign((Vec2f) m_points.get(0));

    for (int i = 1; i < m_points.size(); ++i) {
      Vec2f p = (Vec2f) m_points.get(i);
      if (p.x < min.x)
        min.x = p.x;
      else if (p.x > max.x)
        max.x = p.x;
      if (p.y < min.y)
        min.y = p.y;
      else if (p.y > max.y)
        max.y = p.y;
    }
    return new Rectanglef(min.x, min.y, max.x - min.x, max.y - min.y);
  }

  public int getNumSides() {
    return m_points.size();
  }

  public Vec2f getPoint(int num) {
    return (Vec2f) m_points.get(num);
  }

  public boolean isInside(Vec2f p) {
    if (getNumSides() < 3)
      throw new IllegalStateException();
    for (int i = 0, n = getNumSides(); i < n; ++i) {
      Vec2f a = getPoint(i);
      Vec2f b = getPoint((i + 1) % n);
      Vec2f c = getPoint((i + n - 1) % n);

      Vec2f ab = b.sub(a).normalize();
      Vec2f ac = c.sub(a).normalize();
      Vec2f ap = p.sub(a).normalize();

      if (ap.dot(ab) < ac.dot(ab))
        return false;
    }
    return true;
  }

  public void setPoint(int num, Vec2f p) {
    m_points.set(num, new Vec2f(p));
  }
}