/*
 * Created on 2004-08-27
 */

package org.tw.patterns.observer;

public interface IUpdateable {
  public static final int NO_HINT = -1;
  void addUpdateListener(IUpdateListener l);
  void removeUpdateListener(IUpdateListener l);
  void fireUpdated(int hint, Object data);
}