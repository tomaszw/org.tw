/*
 * Created on 2004-07-21
 */

package org.tw.geometry;

public class Pointd {
  public double x, y;

  public Pointd() {
  }

  public Pointd(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Pointd(Pointd p) {
    this(p.x, p.y);
  }

  public double get(int i) {
    switch (i) {
    case 0: return x;
    case 1: return y;
    }
    throw new IndexOutOfBoundsException();
  }
  
  public Pointd add(Pointd b) {
    return new Pointd(x + b.x, y + b.y);
  }

  public double squaredLength() {
    return x*x+y*y;
  }
  
  public void assign(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void assign(Pointd p) {
    x = p.x;
    y = p.y;
  }

  public Pointd copy() {
    return new Pointd(x, y);
  }

  public double distanceTo(Pointd b) {
    double dx=b.x-x;
    double dy=b.y-y;
    return (double) Math.sqrt(dx*dx+dy*dy);
  }

  public Pointd div(double f) {
    return new Pointd(x / f, y / f);
  }

  public double dot(Pointd b) {
    return x * b.x + y * b.y;
  }

  public double length() {
    return (double) Math.sqrt(x * x + y * y);
  }

  public Pointd mul(double f) {
    return new Pointd(x * f, y * f);
  }

  public Pointd normalize() {
    double l = length();
    return new Pointd(x / l, y / l);
  }

  public Pointd rotate(Pointd about, double angle) {
    Pointd p = sub(about);
    double radius = length();
    double alpha = (double)Math.atan2(y,x);
    if (alpha < 0) alpha += Math.PI*2;
    angle += alpha;
    Pointd r = new Pointd();
    r.x = radius * (double) (Math.cos(angle));
    r.y = radius * (double) (Math.sin(angle));
    return r.add(about);
  }

  public Pointd sub(Pointd b) {
    return new Pointd(x - b.x, y - b.y);
  }
}