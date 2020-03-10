package org.tw.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: tomek
 * Date: 2004-09-21
 */
public class Clipper3 {
  public static List<Vec3f> clipBackParts(Plane3 clipPlane, List<Vec3f> input, float epsilon) {
    List<Vec3f> r = new ArrayList<Vec3f>();
    if (input.size() == 0)
      return input;
    Vec3f pa;
    float da,db;
    pa = input.get(input.size() - 1);
    da = clipPlane.distanceTo(pa);
    for (Vec3f pb : input) {
      db = clipPlane.distanceTo(pb);
      if (db < -epsilon) {
        if (da < -epsilon) {
          r.add(pb.copy());
        } else {
          Vec3f dir = pb.sub(pa);
          float s = -da/dir.dot(clipPlane.n);
          Vec3f ins = pa.add(dir.mul(s));
          r.add(ins);
          r.add(pb.copy());
        }
      } else if (da < -epsilon) {
        Vec3f dir = pb.sub(pa);
        float s = -da/dir.dot(clipPlane.n);
        Vec3f ins = pa.add(dir.mul(s));
        r.add(ins);
      }
      da = db;
      pa = pb;
    }
    return r;
  }

  public static List<Vec3f> clipFrontParts(Plane3 clipPlane, List<Vec3f> input, float epsilon) {
    Plane3 p = new Plane3();
    p.n = clipPlane.n.negate();
    p.d = clipPlane.d;
    return clipBackParts(p, input, epsilon);
  }

//  public static void split(Plane3 clipPlane, List<Vec3f> input, List<Vec3f> front, List<Vec3f> back, float epsilon) {
//    perform(ClipMode.SPLIT, clipPlane, input, front, back, epsilon);
//  }

  private static void perform(ClipMode m, Plane3 clipPlane, List<Vec3f> input, List<Vec3f> front, List<Vec3f> back, float epsilon) {
    Vec3f beg, pa;
    float da, db;

    if (input.size() == 0)
      return;
    pa = input.get(input.size() - 1);
    da = clipPlane.distanceTo(pa);
    for (Vec3f pb : input) {
      db = clipPlane.distanceTo(pb);

      if (da < -epsilon) {
        if (db > epsilon) {
          Vec3f dir = pb.sub(pa);
          float s = -da/dir.dot(clipPlane.n);
          Vec3f ins = pa.add(dir.mul(s));
          if (m != ClipMode.FRONT) front.add(ins);
          if (m != ClipMode.BACK) back.add(ins);
        } else if (m != ClipMode.BACK) back.add(pb.copy());
        if (m != ClipMode.FRONT) front.add(pb.copy());
      } else if (da > epsilon) {
        if (db < -epsilon) {
          Vec3f dir = pb.sub(pa);
          float s = -da/dir.dot(clipPlane.n);
          Vec3f ins = pa.add(dir.mul(s));
          if (m != ClipMode.FRONT) front.add(ins);
          if (m != ClipMode.BACK) back.add(ins);
        } else if (m != ClipMode.FRONT) front.add(pb.copy());
        if (m != ClipMode.BACK) back.add(pb.copy());
      } else {
        if (m != ClipMode.FRONT) front.add(pb.copy());
        if (m != ClipMode.BACK) back.add(pb.copy());
      }
      pa = pb;
      da = db;
    }
  }
}
