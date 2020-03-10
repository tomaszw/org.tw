package org.tw.geometry;

/**
 * Author: tomek
 * Date: 2004-09-21
 */
public class Plane3 {
  public Vec3f n;
  public float d;

  public Plane3() {
  }

  public Plane3(Vec3f p0, Vec3f n) {
    this.n = n.copy();
    this.d = -n.dot(p0);
  }

  public float distanceTo(Vec3f p) {
    return n.dot(p) + d;
  }
}
