/*
 * Created on 2006-07-12
 */

package org.tw.math;

import java.util.Random;

public class Rand {
  private static Random s_rand = new Random();

  public static boolean nextBoolean() {
    return s_rand.nextBoolean();
  }

  public static int dice(int sides) {
    return nextIntInRange(1,sides);
  }
  
  public static void nextBytes(byte[] bytes) {
    s_rand.nextBytes(bytes);
  }

  public static double nextDouble() {
    return s_rand.nextDouble();
  }

  public static float nextFloat() {
    return s_rand.nextFloat();
  }

  public static double nextGaussian() {
    return s_rand.nextGaussian();
  }

  public static int nextInt() {
    return s_rand.nextInt();
  }

  public static int nextIntInRange(int a, int b) {
    return nextInt(b-a+1)+a;
  }
  
  public static int nextInt(int n) {
    return s_rand.nextInt(n);
  }

  public static long nextLong() {
    return s_rand.nextLong();
  }

}
