/*
 * Created on 2004-08-31
 */

package org.tw.geometry;

import java.util.Arrays;

import org.tw.math.EquationKit;

public class GeometryKit {
  public static class Cube {
    public Vec3f[] points = new Vec3f[8];
    public int[] faces = new int[24];
    public Cube(float dx, float dy, float dz) {
      GeometryKit.makeCube(dx, dy, dz, points, faces);
    }
  }

  public static class SegmentP {
    public Vec2f p;
    public float  t;
  }

  public static void makeCube(float dx, float dy, float dz, Vec3f[] points, int[] faces) {
    dx/=2;
    dy/=2;
    dz/=2;
    System.arraycopy(new Vec3f[] {
        new Vec3f(-dx,-dy,dz),
        new Vec3f(dx,-dy,dz),
        new Vec3f(dx,dy,dz),
        new Vec3f(-dx,dy,dz),
        new Vec3f(-dx,-dy,-dz),
        new Vec3f(dx,-dy,-dz),
        new Vec3f(dx,dy,-dz),
        new Vec3f(-dx,dy,-dz) }, 0, points, 0, 8);
    System.arraycopy(new int[] {
        0,1,2,3,
        5,4,7,6,
        1,5,6,2,
        0,3,7,4,
        3,2,6,7,
        4,5,1,0
    }, 0, faces, 0, 6*4);
  }
  
  public static Rectanglef boundingRect(Vec2f[] points) {
    float minx = Float.POSITIVE_INFINITY, miny = Float.POSITIVE_INFINITY, maxx = Float.NEGATIVE_INFINITY, maxy = Float.NEGATIVE_INFINITY;
    for (int i = 0; i < points.length; i++) {
      Vec2f p = points[i];
      if (p.x < minx)
        minx = p.x;
      if (p.x > maxx)
        maxx = p.x;
      if (p.y < miny)
        miny = p.y;
      if (p.y > maxy)
        maxy = p.y;
    }
    return new Rectanglef(minx, miny, maxx - minx, maxy - miny);
  }

  public static float distanceOfLineToPoint(float x1, float y1, float x2, float y2,
      float px, float py) {

    Vec2f dir1 = new Vec2f(x2 - x1, y2 - y1);
    float len1 = dir1.length();
    if (len1 != 0) {
      dir1.x /= len1;
      dir1.y /= len1;
    }

    Vec2f dir2 = new Vec2f(px - x1, py - y1);
    float len2 = dir2.length();
    if (len2 != 0) {
      dir2.x /= len2;
      dir2.y /= len2;
    }

    float dot = dir1.dot(dir2);
    float t = (len2 * dot) / len1;

    if (t < 0)
      return new Vec2f(x1, y1).distanceTo(new Vec2f(px, py));
    if (t > 1)
      return new Vec2f(x2, y2).distanceTo(new Vec2f(px, py));

    Vec2f p = new Vec2f(t * len1 * dir1.x + x1, t * len1 * dir1.y + y1);
    return p.distanceTo(new Vec2f(px, py));
  }

  public static float[] intersectCircleVector(Vec2f center, float radius, Vec2f beg,
      Vec2f dir) {
    float cx = beg.x - center.x;
    float cy = beg.y - center.y;

    float a = dir.x * dir.x + dir.y * dir.y;
    float b = 2 * cx * dir.x + 2 * cy * dir.y;
    float c = cx * cx + cy * cy - radius * radius;
    return EquationKit.quadraticEquation(a, b, c);
  }

  public static boolean intersectSegments(Vec2f a, Vec2f b, Vec2f c, Vec2f d,
      float len1, float len2, SegmentP res) {
    if (len1 == 0 || len2 == 0)
      return false;
    float d1x = (b.x - a.x) / len1;
    float d1y = (b.y - a.y) / len1;
    float d2x = (d.x - c.x) / len2;
    float d2y = (d.y - c.y) / len2;

    float nom = a.x * d2y - c.x * d2y - a.y * d2x + c.y * d2x;
    float den = d1y * d2x - d1x * d2y;

    if (den == 0)
      return false;
    float t = nom / den;
    if (t < 0)
      return false;
    if (t > len1)
      return false;
    float u = 0;
    if (d2y != 0)
      u = (a.y + t * d1y - c.y) / d2y;
    else if (d2x != 0)
      u = (a.x + t * d1x - c.x) / d2x;
    else
      return false;

    if (u < 0)
      return false;
    if (u > len2)
      return false;

    res.t = t;
    res.p.x = a.x + d1x * t;
    res.p.y = a.y + d1y * t;
    return true;
  }

  public static GeometryKit.SegmentP intersectSegments(Segmentf seg1, Segmentf seg2,
      float len1, float len2) {
    if (len1 == 0 || len2 == 0)
      return null;
    Vec2f a = seg1.a;
    Vec2f b = seg1.b;
    Vec2f c = seg2.a;
    Vec2f d = seg2.b;

    float d1x = (b.x - a.x) / len1;
    float d1y = (b.y - a.y) / len1;
    float d2x = (d.x - c.x) / len2;
    float d2y = (d.y - c.y) / len2;

    float nom = a.x * d2y - c.x * d2y - a.y * d2x + c.y * d2x;
    float den = d1y * d2x - d1x * d2y;

    if (den == 0)
      return null;
    float t = nom / den;
    if (t < 0)
      return null;
    if (t > len1)
      return null;
    float u = 0;
    if (d2y != 0)
      u = (a.y + t * d1y - c.y) / d2y;
    else if (d2x != 0)
      u = (a.x + t * d1x - c.x) / d2x;
    else
      return null;

    if (u < 0)
      return null;
    if (u > len2)
      return null;

    GeometryKit.SegmentP p = new GeometryKit.SegmentP();
    p.t = t;
    p.p = new Vec2f(a.x + d1x * t, a.y + d1y * t);
    return p;
  }

  public static SegmentP intersectSegments(Segmentf seg1, Segmentf seg2) {
    float len1 = seg1.length();
    float len2 = seg2.length();
    return intersectSegments(seg1, seg2, len1, len2);
  }
}