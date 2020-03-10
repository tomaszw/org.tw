/*
 * Created on 2004-07-21
 */

package org.tw.geometry;

import java.io.Serializable;

public class Vec3d implements Serializable {
  public double x, y, z;

  public static Vec3d ZERO = new Vec3d(0,0,0);
  public static Vec3d UNIT_X = new Vec3d(1,0,0);
  public static Vec3d UNIT_Y = new Vec3d(0,1,0);
  public static Vec3d UNIT_Z = new Vec3d(0,0,1);
  
  public Vec3d() {
  }

  public Vec3d(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vec3d(Vec3d p) {
    this(p.x, p.y, p.z);
  }

  public void set(int i, double f) {
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

  public double get(int i) {
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

  public Vec3d add(Vec3d b) {
    return new Vec3d(x + b.x, y + b.y, z + b.z);
  }

  public void assign(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public void assign(Vec3d p) {
    x = p.x;
    y = p.y;
    z = p.z;
  }

  public Vec3d copy() {
    return new Vec3d(x, y, z);
  }

  public double distanceTo(Vec3d b) {
    double dx = b.x - x;
    double dy = b.y - y;
    double dz = b.z - z;
    return (double) Math.sqrt(dx * dx + dy * dy + dz * dz);
  }

  public Vec3d div(double f) {
    return new Vec3d(x / f, y / f, z / f);
  }

  public Vec3d cross(Vec3d v) {
    return new Vec3d(y * v.z - v.y * z,
      z * v.x - v.z * x,
      x * v.y - v.x * y);
  }

  public double dot(Vec3d b) {
    return x * b.x + y * b.y + z * b.z;
  }

  public double length() {
    return (double) Math.sqrt(x * x + y * y + z * z);
  }

  public Vec3d mul(double f) {
    return new Vec3d(x * f, y * f, z * f);
  }

  public Vec3d normalize() {
    double l = length();
    return new Vec3d(x / l, y / l, z / l);
  }

  public Vec3d negate() {
    return new Vec3d(-x, -y, -z);
  }

  public double squaredLength() {
    return x * x + y * y + z * z;
  }

  public Vec3d sub(Vec3d b) {
    return new Vec3d(x - b.x, y - b.y, z - b.z);
  }

  public String toString() {
    return x + " " + y + " " + z;
  }
}