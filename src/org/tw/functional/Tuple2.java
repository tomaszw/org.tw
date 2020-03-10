/*
 * Created on 2007-01-06
 */

package org.tw.functional;

public class Tuple2<A,B> {
  public Tuple2() {
  }
  
  public Tuple2(A v1, B v2) {
    this.v1 = v1;
    this.v2 = v2;
  }
  
  public A v1;
  public B v2;
}
