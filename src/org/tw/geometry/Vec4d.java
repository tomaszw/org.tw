/*
 * Created on 2004-07-21
 */

package org.tw.geometry;

public class Vec4d {
  public double x, y, z, w;

  public Vec4d() {
  }

  public Vec4d(double x, double y, double z, double w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public Vec4d(Vec4d p) {
    this(p.x, p.y, p.z, p.w);
  }

  public Vec4d add(Vec4d b) {
    return new Vec4d(x + b.x, y + b.y, z + b.z, w + b.w);
  }

  public void assign(double x, double y, double z, double w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public void assign(Vec4d p) {
    x = p.x;
    y = p.y;
    z = p.z;
    w = p.w;
  }

  public Vec4d copy() {
    return new Vec4d(x, y, z, w);
  }

  public double distanceTo(Vec4d b) {
    double dx = b.x - x;
    double dy = b.y - y;
    double dz = b.z - z;
    double dw = b.w - w;
    return (double) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
  }

  public Vec4d div(double f) {
    return new Vec4d(x / f, y / f, z / f, w / f);
  }

  public double dot(Vec4d b) {
    return x * b.x + y * b.y + z * b.z + w * b.w;
  }

  public double get(int i) {
    switch (i) {
      case 0 :
        return x;
      case 1 :
        return y;
      case 2 :
        return z;
      case 3 :
        return w;
    }
    throw new IndexOutOfBoundsException();
  }

  public double length() {
    return (double) Math.sqrt(x * x + y * y + z * z + w * w);
  }

  public Vec4d mul(double f) {
    return new Vec4d(x * f, y * f, z * f, w * f);
  }

  public Vec4d normalize() {
    double l = length();
    return new Vec4d(x / l, y / l, z / l, w / l);
  }

  public double squaredLength() {
    return x * x + y * y + z * z + w * w;
  }

  public Vec4d sub(Vec4d b) {
    return new Vec4d(x - b.x, y - b.y, z - b.z, w - b.w);
  }
}