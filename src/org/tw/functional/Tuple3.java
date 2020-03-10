/*
 * Created on 2007-01-06
 */

package org.tw.functional;

public class Tuple3<A,B,C> {
    public Tuple3() {
    }
    
    public Tuple3(A v1, B v2, C v3) {
      this.v1 = v1;
      this.v2 = v2;
      this.v3 = v3;
    }
    
    public A v1;
    public B v2;
    public C v3;
}
