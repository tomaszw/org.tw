/*
 * Created on 2004-07-22
 */

package org.tw.geometry;

public class Rectanglef {
  public float x, y, width, height;

  public Rectanglef() {
  }

  public Rectanglef(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  
  public boolean intersects(Rectanglef r) {
    float maxx =x+width;
    float maxy=y+height;
    float maxx2 = r.x+r.width;
    float maxy2 = r.y+r.height;
    
    if (maxx < r.x)
      return false;
    if (maxy < r.y)
      return false;
    if (x > maxx2)
      return false;
    if (y > maxy2)
      return false;
    return true;
  }
}