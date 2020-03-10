/*
 * Created on 2004-07-21
 */

package org.tw.geometry;

public class Vec2d {
  public double x, y;

  public Vec2d() {
  }

  public Vec2d(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vec2d(Vec2d p) {
    this(p.x, p.y);
  }

  public Vec2d add(Vec2d b) {
    return new Vec2d(x + b.x, y + b.y);
  }

  public void assign(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void assign(Vec2d p) {
    x = p.x;
    y = p.y;
  }

  public Vec2d copy() {
    return new Vec2d(x, y);
  }

  public double distanceTo(Vec2d b) {
    double dx = b.x - x;
    double dy = b.y - y;
    return (double) Math.sqrt(dx * dx + dy * dy);
  }

  public Vec2d div(double f) {
    return new Vec2d(x / f, y / f);
  }

  public double dot(Vec2d b) {
    return x * b.x + y * b.y;
  }

  public double get(int i) {
    switch (i) {
      case 0 :
        return x;
      case 1 :
        return y;
    }
    throw new IndexOutOfBoundsException();
  }

  public double length() {
    return (double) Math.sqrt(x * x + y * y);
  }

  public Vec2d mul(double f) {
    return new Vec2d(x * f, y * f);
  }

  public Vec2d normalize() {
    double l = length();
    return new Vec2d(x / l, y / l);
  }

  public Vec2d rotate(Vec2d about, double angle) {
    Vec2d p = sub(about);
    double radius = length();
    double alpha = (double) Math.atan2(y, x);
    if (alpha < 0)
      alpha += Math.PI * 2;
    angle += alpha;
    Vec2d r = new Vec2d();
    r.x = radius * (double) (Math.cos(angle));
    r.y = radius * (double) (Math.sin(angle));
    return r.add(about);
  }

  public double squaredLength() {
    return x * x + y * y;
  }

  public Vec2d sub(Vec2d b) {
    return new Vec2d(x - b.x, y - b.y);
  }
}