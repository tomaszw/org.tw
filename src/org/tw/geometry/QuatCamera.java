/*
 * Created on 2007-05-12
 */

package org.tw.geometry;

/**
 * quaternion-based camera
 */
public class QuatCamera {
  // position
  public Vec3f P = new Vec3f();
  // Rotation along view vector
  public Quatf ViewQ = new Quatf();

  //
  public QuatCamera() {
    setHeadingPitchRoll(0, 0, 0);
  }
  public void setHeadingPitchRoll(float h, float p, float r) {
    ViewQ = Quatf.fromEulerHPR(h, p, r);
  }
  public Matrixf getMatrix() {
    Matrixf t = MatrixKit.translation4x4(P.x, P.y, P.z);
    return t.mul(ViewQ.toMatrix4());
  }
  public Vec3f getViewVector() {
    return ViewQ.mul(new Quatf(0, new Vec3f(0, 0, -1))).mul(ViewQ.conjugate()).p
        .normalize();
  }
  public Vec3f getUpVector() {
    return ViewQ.mul(new Quatf(0, new Vec3f(0, 1, 0))).mul(ViewQ.conjugate()).p
        .normalize();
  }
  public Vec3f getRightVector() {
    return ViewQ.mul(new Quatf(0, new Vec3f(1, 0, 0))).mul(ViewQ.conjugate()).p
        .normalize();
  }
}
