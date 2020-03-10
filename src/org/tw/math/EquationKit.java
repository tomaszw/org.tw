/*
 * Created on 2004-08-31
 */

package org.tw.math;


public class EquationKit {
  public static float[] quadraticEquation(float a, float b, float c) {
    float delta = b * b - 4 * a * c;
    if (delta < 0)
      return new float[0];
    if (delta == 0)
      return new float[] { -b / (2 * a) };
    float sq = (float) Math.sqrt(delta);
    return new float[] { (-b - sq) / (2 * a), (-b + sq) / (2 * a) };
  }
}
