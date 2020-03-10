/*
 * Created on 2004-07-21
 */

package org.tw.geometry;

public class Vec2f {
  public float x, y;

  public Vec2f() {
  }

  public Vec2f(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public Vec2f(Vec2f p) {
    this(p.x, p.y);
  }

  public Vec2f add(Vec2f b) {
    return new Vec2f(x + b.x, y + b.y);
  }

  public void assign(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void assign(Vec2f p) {
    x = p.x;
    y = p.y;
  }

  public Vec2f copy() {
    return new Vec2f(x, y);
  }

  public float distanceTo(Vec2f b) {
    float dx = b.x - x;
    float dy = b.y - y;
    return (float) Math.sqrt(dx * dx + dy * dy);
  }

  public Vec2f div(float f) {
    return new Vec2f(x / f, y / f);
  }

  public float dot(Vec2f b) {
    return x * b.x + y * b.y;
  }

  public float get(int i) {
    switch (i) {
      case 0 :
        return x;
      case 1 :
        return y;
    }
    throw new IndexOutOfBoundsException();
  }

  public float length() {
    return (float) Math.sqrt(x * x + y * y);
  }

  public Vec2f mul(float f) {
    return new Vec2f(x * f, y * f);
  }

  public Vec2f normalize() {
    float l = length();
    return new Vec2f(x / l, y / l);
  }

  public Vec2f rotate(Vec2f about, float angle) {
    Vec2f p = sub(about);
    float radius = length();
    float alpha = (float) Math.atan2(y, x);
    if (alpha < 0)
      alpha += Math.PI * 2;
    angle += alpha;
    Vec2f r = new Vec2f();
    r.x = radius * (float) (Math.cos(angle));
    r.y = radius * (float) (Math.sin(angle));
    return r.add(about);
  }

  public float squaredLength() {
    return x * x + y * y;
  }

  public Vec2f sub(Vec2f b) {
    return new Vec2f(x - b.x, y - b.y);
  }
}