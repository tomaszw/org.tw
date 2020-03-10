/*
 * Created on 2006-07-08
 */

package org.tw.console;

import java.awt.event.KeyEvent;

public interface Console {
  void addOverlay(Overlay o);
  void dispose();
  AbstractFont font();
  KeyEvent readKey();
  void removeOverlay(Overlay o);
  void repaint();
  Surface surface();
}