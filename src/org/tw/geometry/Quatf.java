/*
 * Created on 2007-05-12
 */

package org.tw.geometry;

public class Quatf {
  public float w = 1;
  public Vec3f p = new Vec3f();

  public static Quatf fromEulerHPR(float heading, float pitch, float roll) {
    return fromXrot(pitch).mul(fromYrot(heading)).mul(fromZrot(roll));
  }
  public static Quatf fromEulerXYZ(float x, float y, float z) {
    return fromXrot(x).mul(fromYrot(y)).mul(fromZrot(z));
  }
  public static Quatf fromXrot(float angle) {
    return new Quatf((float) Math.cos(angle / 2), new Vec3f((float) Math.sin(angle / 2),
        0, 0));
  }

  public String toString() {
    return String.format("[w = %.2f   x = %.2f  y = %.2f  z = %.2f]", w, p.x, p.y, p.z);
  }
  public static Quatf fromYrot(float angle) {
    return new Quatf((float) Math.cos(angle / 2), new Vec3f(0, (float) Math
        .sin(angle / 2), 00));
  }

  public static Quatf fromZrot(float angle) {
    return new Quatf((float) Math.cos(angle / 2), new Vec3f(0, 0, (float) Math
        .sin(angle / 2)));
  }

  public Quatf() {
  }

  public Vec3f rotate(Vec3f v) {
    return mul(new Quatf(0, v)).mul(conjugate()).p;
  }

  public Matrixf toMatrix4() {
    Matrixf m = new Matrixf(4, 4);
    float sqw = w * w;
    float sqx = p.x * p.x;
    float sqy = p.y * p.y;
    float sqz = p.z * p.z;

    // invs (inverse square length) is only required if quaternion is not already normalised
    float invs = 1.0f / (sqx + sqy + sqz + sqw);
    m.set(0, 0, (sqx - sqy - sqz + sqw) * invs); // since sqw + sqx + sqy + sqz =1/invs*invs
    m.set(1, 1, (-sqx + sqy - sqz + sqw) * invs);
    m.set(2, 2, (-sqx - sqy + sqz + sqw) * invs);

    float tmp1 = p.x * p.y;
    float tmp2 = p.z * w;
    m.set(1, 0, 2 * (tmp1 + tmp2) * invs);
    m.set(0, 1, 2 * (tmp1 - tmp2) * invs);

    tmp1 = p.x * p.z;
    tmp2 = p.y * w;
    m.set(2, 0, 2 * (tmp1 - tmp2) * invs);
    m.set(0, 2, 2 * (tmp1 + tmp2) * invs);
    tmp1 = p.y * p.z;
    tmp2 = p.x * w;
    m.set(2, 1, 2 * (tmp1 + tmp2) * invs);
    m.set(1, 2, 2 * (tmp1 - tmp2) * invs);
    m.set(3, 3, 1);
    return m;
  }

  public Quatf(float w, Vec3f p) {
    this.p = p.copy();
    this.w = w;
  }

  public Quatf(Vec3f p, float w) {
    this.p = p.copy();
    this.w = w;
  }

  public Quatf conjugate() {
    Quatf q = copy();
    q.p.x = -q.p.x;
    q.p.y = -q.p.y;
    q.p.z = -q.p.z;
    return q;
  }

  public Quatf copy() {
    return new Quatf(p, w);
  }

  public float length() {
    return (float) Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z + w * w);
  }

  public Quatf mul(Quatf B) {
    Quatf C = new Quatf();
//    C.w = this.w * B.w - this.p.dot(B.p);
//    C.p = B.p.mul(this.w).add(this.p.mul(B.w)).add(this.p.cross(B.p));
    C.p.x = this.w * B.p.x + this.p.x * B.w + this.p.y * B.p.z - this.p.z * B.p.y;
    C.p.y = this.w * B.p.y - this.p.x * B.p.z + this.p.y * B.w + this.p.z * B.p.x;
    C.p.z = this.w * B.p.z + this.p.x * B.p.y - this.p.y * B.p.x + this.p.z * B.w;
    C.w = this.w * B.w - this.p.x * B.p.x - this.p.y * B.p.y - this.p.z * B.p.z;
    return C;
  }

  public void normalize() {
    float l = length();
    w /= l;
    p.x /= l;
    p.y /= l;
    p.z /= l;
  }

  public Quatf normalized() {
    Quatf q = copy();
    q.normalize();
    return q;
  }
}
