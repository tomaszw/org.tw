/*
 * Created on 2004-07-21
 */

package org.tw.geometry;

public class Vec4f {
  public float x, y, z, w;

  public Vec4f() {
  }

  public Vec4f(float x, float y, float z, float w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public Vec4f(Vec4f p) {
    this(p.x, p.y, p.z, p.w);
  }

  public Vec4f add(Vec4f b) {
    return new Vec4f(x + b.x, y + b.y, z + b.z, w + b.w);
  }

  public void assign(float x, float y, float z, float w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public void assign(Vec4f p) {
    x = p.x;
    y = p.y;
    z = p.z;
    w = p.w;
  }

  public Vec4f copy() {
    return new Vec4f(x, y, z, w);
  }

  public float distanceTo(Vec4f b) {
    float dx = b.x - x;
    float dy = b.y - y;
    float dz = b.z - z;
    float dw = b.w - w;
    return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
  }

  public Vec4f div(float f) {
    return new Vec4f(x / f, y / f, z / f, w / f);
  }

  public float dot(Vec4f b) {
    return x * b.x + y * b.y + z * b.z + w * b.w;
  }

  public float get(int i) {
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

  public float length() {
    return (float) Math.sqrt(x * x + y * y + z * z + w * w);
  }

  public Vec4f mul(float f) {
    return new Vec4f(x * f, y * f, z * f, w * f);
  }

  public Vec4f normalize() {
    float l = length();
    return new Vec4f(x / l, y / l, z / l, w / l);
  }

  public float squaredLength() {
    return x * x + y * y + z * z + w * w;
  }

  public Vec4f sub(Vec4f b) {
    return new Vec4f(x - b.x, y - b.y, z - b.z, w - b.w);
  }
}