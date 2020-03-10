/*
 * Created on 2004-08-24
 */

package org.tw.geometry;



public class Segmentf {
  public Vec2f a,b;
  public float length() {
    return a.distanceTo(b);
  }
}
