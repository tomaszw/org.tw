/*
 * Created on 2005-09-24
 */

package org.tw.patterns.observer;

public interface IBUpdateable extends IUpdateable {
  void blockUpdates();
  void unblockUpdates();
}
