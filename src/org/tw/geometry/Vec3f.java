/*
 * Created on 2004-07-21
 */

package org.tw.geometry;

public class Vec3f {
  public float x, y, z;

  public static Vec3f ZERO = new Vec3f(0,0,0);
  public static Vec3f UNIT_X = new Vec3f(1,0,0);
  public static Vec3f UNIT_Y = new Vec3f(0,1,0);
  public static Vec3f UNIT_Z = new Vec3f(0,0,1);
  
  public Vec3f() {
  }

  public Vec3f(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vec3f(Vec3f p) {
    this(p.x, p.y, p.z);
  }

  public void set(int i, float f) {
    switch (i) {
    case 0:
      x = f;
      break;
    case 1:
      y = f;
      break;
    case 2:
      z = f;
      break;
    default:
      throw new IndexOutOfBoundsException();
    }

  }

  public float get(int i) {
    switch (i) {
    case 0:
      return x;
    case 1:
      return y;
    case 2:
      return z;
    }
    throw new IndexOutOfBoundsException();
  }

  public Vec3f add(Vec3f b) {
    return new Vec3f(x + b.x, y + b.y, z + b.z);
  }

  public void assign(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public void assign(Vec3f p) {
    x = p.x;
    y = p.y;
    z = p.z;
  }

  public Vec3f copy() {
    return new Vec3f(x, y, z);
  }

  public float distanceTo(Vec3f b) {
    float dx = b.x - x;
    float dy = b.y - y;
    float dz = b.z - z;
    return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
  }

  public Vec3f div(float f) {
    return new Vec3f(x / f, y / f, z / f);
  }

  public Vec3f cross(Vec3f v) {
    return new Vec3f(y * v.z - v.y * z,
      z * v.x - v.z * x,
      x * v.y - v.x * y);
  }

  public float dot(Vec3f b) {
    return x * b.x + y * b.y + z * b.z;
  }

  public float length() {
    return (float) Math.sqrt(x * x + y * y + z * z);
  }

  public Vec3f mul(float f) {
    return new Vec3f(x * f, y * f, z * f);
  }

  public Vec3f normalize() {
    float l = length();
    return new Vec3f(x / l, y / l, z / l);
  }

  public Vec3f negate() {
    return new Vec3f(-x, -y, -z);
  }

  public float squaredLength() {
    return x * x + y * y + z * z;
  }

  public Vec3f sub(Vec3f b) {
    return new Vec3f(x - b.x, y - b.y, z - b.z);
  }

  public String toString() {
    return x + " " + y + " " + z;
  }
}