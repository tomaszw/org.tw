package org.tw.functional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: tomek
 * Date: 2004-09-16
 */
public class LambdaKit {
  public static <T> List<T> filter(Iterable<T> c, Lambda1<Boolean, T> lambda) {
    List<T> r = new LinkedList<T>();
    for (T x : c) {
      if (lambda.eval(x) == true) {
        r.add(x);
      }
    }
    return r;
  }

  public static <T> List<T> filter(T[] c, Lambda1<Boolean, T> lambda) {
    return filter(Arrays.asList(c), lambda);
  }

  public static <T> List<T> map(Iterable<T> c, Lambda1<T, T> lambda) {
    List<T> r = new LinkedList<T>();
    for (T x : c) {
      r.add(lambda.eval(x));
    }
    return r;
  }

  public static <T> List<T> map(T[] c, Lambda1<T, T> lambda) {
    return map(Arrays.asList(c), lambda);
  }
}
