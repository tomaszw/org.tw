package org.tw.geometry;

/**
 * Author: tomek Date: 2004-05-27
 */
public class MatrixKit {
  public static Matrixd fromColumnMajor(int h, int w, double[] d) {
    Matrixd m = new Matrixd(h, w, d);
    return m.transpose();
  }
  public static Matrixf fromColumnMajor(int h, int w, float[] d) {
    Matrixf m = new Matrixf(h, w, d);
    return m.transpose();
  }

  public static Matrixd fromRowMajor(int h, int w, double[] d) {
    return new Matrixd(h, w, d);
  }
  public static Matrixf fromRowMajor(int h, int w, float[] d) {
    return new Matrixf(h, w, d);
  }

  public static Matrixd lookAt(Vec3d eye, Vec3d at, Vec3d up) {
    up = up.normalize();
    Matrixd m = new Matrixd(4, 4);
    Vec3d F = at.sub(eye).normalize();
    Vec3d s = F.cross(up).normalize();
    Vec3d u = up;// s.cross(F).normalize();
    for (int i = 0; i < 3; ++i) {
      m.set(i, 0, s.get(i));
      m.set(i, 1, u.get(i));
      m.set(i, 2, F.get(i));
      m.set(i, 3, eye.get(i));
    }
    m.set(3, 3, 1);
    return m.inverse();
    // return m.mul(translation4x4(eye.negate()));//.mul(m);
  }
  public static Matrixf lookAt(Vec3f eye, Vec3f at, Vec3f up) {
    up = up.normalize();
    Matrixf m = new Matrixf(4, 4);
    Vec3f F = at.sub(eye).normalize();
    Vec3f s = F.cross(up).normalize();
    Vec3f u = up;// s.cross(F).normalize();
    for (int i = 0; i < 3; ++i) {
      m.set(i, 0, s.get(i));
      m.set(i, 1, u.get(i));
      m.set(i, 2, F.get(i));
      m.set(i, 3, eye.get(i));
    }
    m.set(3, 3, 1);
    return m.inverse();
    // return m.mul(translation4x4(eye.negate()));//.mul(m);
  }
  public static Matrixd rotationX3x3(double ang) {
    Matrixd m = new Matrixd(3, 3);
    double c = (double) Math.cos(ang);
    double s = (double) Math.sin(ang);
    m.setIdentity();
    m.set(1, 1, c);
    m.set(2, 1, s);
    m.set(1, 2, -s);
    m.set(2, 2, c);
    return m;
  }
  public static Matrixf rotationX3x3(float ang) {
    Matrixf m = new Matrixf(3, 3);
    float c = (float) Math.cos(ang);
    float s = (float) Math.sin(ang);
    m.setIdentity();
    m.set(1, 1, c);
    m.set(2, 1, s);
    m.set(1, 2, -s);
    m.set(2, 2, c);
    return m;
  }

  public static Matrixd rotationXYZ3x3(double ax, double ay, double az) {
    return (rotationX3x3(ax).mul(rotationY3x3(ay))).mul(rotationZ3x3(az));
    // return (rotationZ3x3(az).mul(rotationY3x3(ay))).mul(rotationX3x3(ax));
  }

  public static Matrixf rotationXYZ3x3(float ax, float ay, float az) {
    return (rotationX3x3(ax).mul(rotationY3x3(ay))).mul(rotationZ3x3(az));
    // return (rotationZ3x3(az).mul(rotationY3x3(ay))).mul(rotationX3x3(ax));
  }

  public static Matrixd rotationY3x3(double ang) {
    Matrixd m = new Matrixd(3, 3);
    double c = (double) Math.cos(ang);
    double s = (double) Math.sin(ang);
    m.setIdentity();
    m.set(0, 0, c);
    m.set(2, 0, -s);
    m.set(0, 2, s);
    m.set(2, 2, c);
    return m;
  }

  public static Matrixf rotationY3x3(float ang) {
    Matrixf m = new Matrixf(3, 3);
    float c = (float) Math.cos(ang);
    float s = (float) Math.sin(ang);
    m.setIdentity();
    m.set(0, 0, c);
    m.set(2, 0, -s);
    m.set(0, 2, s);
    m.set(2, 2, c);
    return m;
  }

  public static Matrixd rotationZ3x3(double ang) {
    Matrixd m = new Matrixd(3, 3);
    double c = (double) Math.cos(ang);
    double s = (double) Math.sin(ang);
    m.setIdentity();
    m.set(0, 0, c);
    m.set(1, 0, s);
    m.set(0, 1, -s);
    m.set(1, 1, c);
    return m;
  }

  public static Matrixf rotationZ3x3(float ang) {
    Matrixf m = new Matrixf(3, 3);
    float c = (float) Math.cos(ang);
    float s = (float) Math.sin(ang);
    m.setIdentity();
    m.set(0, 0, c);
    m.set(1, 0, s);
    m.set(0, 1, -s);
    m.set(1, 1, c);
    return m;
  }

  public static Matrixd rotationZYX3x3(double ax, double ay, double az) {
    return (rotationZ3x3(az).mul(rotationY3x3(ay))).mul(rotationX3x3(ax));
  }
  public static Matrixf rotationZYX3x3(float ax, float ay, float az) {
    return (rotationZ3x3(az).mul(rotationY3x3(ay))).mul(rotationX3x3(ax));
  }

  public static Matrixd rotTrans4x4(Vec3d rot, Vec3d trns) {
    Matrixd rotTmp = rotationZYX3x3(rot.x, rot.y, rot.z);
    Matrixd r = translation4x4(trns);
    r.setSubmat(0, 0, rotTmp);
    return r;
  }

  public static Matrixf rotTrans4x4(Vec3f rot, Vec3f trns) {
    Matrixf rotTmp = rotationZYX3x3(rot.x, rot.y, rot.z);
    Matrixf r = translation4x4(trns);
    r.setSubmat(0, 0, rotTmp);
    return r;
  }

  public static Matrixd scale4x4(double sx, double sy, double sz) {
    Matrixd m = new Matrixd(4, 4);
    m.setIdentity();
    m.set(0, 0, sx);
    m.set(1, 1, sy);
    m.set(2, 2, sz);
    return m;
  }

  public static Matrixf scale4x4(float sx, float sy, float sz) {
    Matrixf m = new Matrixf(4, 4);
    m.setIdentity();
    m.set(0, 0, sx);
    m.set(1, 1, sy);
    m.set(2, 2, sz);
    return m;
  }

  public static Vec3d transform(Vec3d p, Matrixd m) {
    if (m.w != 4 || m.h != 4)
      throw new IllegalArgumentException();
    return new Vec3d(p.x * m.d[0] + p.y * m.d[1] + p.z * m.d[2] + m.d[3], p.x * m.d[4]
        + p.y * m.d[5] + p.z * m.d[6] + m.d[7], p.x * m.d[8] + p.y * m.d[9] + p.z
        * m.d[10] + m.d[11]);
  }

  public static Vec3f transform(Vec3f p, Matrixf m) {
    if (m.w != 4 || m.h != 4)
      throw new IllegalArgumentException();
    return new Vec3f(p.x * m.d[0] + p.y * m.d[1] + p.z * m.d[2] + m.d[3], p.x * m.d[4]
        + p.y * m.d[5] + p.z * m.d[6] + m.d[7], p.x * m.d[8] + p.y * m.d[9] + p.z
        * m.d[10] + m.d[11]);
  }
  public static Vec4d transform(Vec4d p, Matrixd m) {
    if (m.w != 4 || m.h != 4)
      throw new IllegalArgumentException();
    return new Vec4d(p.x * m.d[0] + p.y * m.d[1] + p.z * m.d[2] + p.w * m.d[3], p.x
        * m.d[4] + p.y * m.d[5] + p.z * m.d[6] + p.w * m.d[7], p.x * m.d[8] + p.y
        * m.d[9] + p.z * m.d[10] + p.w * m.d[11], p.x * m.d[12] + p.y * m.d[13] + p.z
        * m.d[14] + p.w * m.d[15]);
  }
  public static Vec4f transform(Vec4f p, Matrixf m) {
    if (m.w != 4 || m.h != 4)
      throw new IllegalArgumentException();
    return new Vec4f(p.x * m.d[0] + p.y * m.d[1] + p.z * m.d[2] + p.w * m.d[3], p.x
        * m.d[4] + p.y * m.d[5] + p.z * m.d[6] + p.w * m.d[7], p.x * m.d[8] + p.y
        * m.d[9] + p.z * m.d[10] + p.w * m.d[11], p.x * m.d[12] + p.y * m.d[13] + p.z
        * m.d[14] + p.w * m.d[15]);
  }
  public static Matrixd translation4x4(double x, double y, double z) {
    Matrixd m = new Matrixd(4, 4);
    m.setIdentity();
    m.set(0, 3, x);
    m.set(1, 3, y);
    m.set(2, 3, z);
    return m;
  }
  public static Matrixf translation4x4(float x, float y, float z) {
    Matrixf m = new Matrixf(4, 4);
    m.setIdentity();
    m.set(0, 3, x);
    m.set(1, 3, y);
    m.set(2, 3, z);
    return m;
  }

  // public static Mat fromColumnVector(Vec4f vec) {
  // Mat m = new Mat(vec.size(), 1);
  // for (int i=0; i<vec.size(); ++i)
  // m.set(i, 0, vec.get(i));
  // return m;
  // }
  //
  // public static Mat fromRowVector(Vec vec) {
  // Mat m = new Mat(1, vec.size());
  // for (int i=0; i<vec.size(); ++i)
  // m.set(0, i, vec.get(i));
  // return m;
  // }

  public static Matrixd translation4x4(Vec3d trns) {
    return translation4x4(trns.x, trns.y, trns.z);
  }

  public static Matrixf translation4x4(Vec3f trns) {
    return translation4x4(trns.x, trns.y, trns.z);
  }
}