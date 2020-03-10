/*
 * Created on 2007-07-05
 */

package org.tw.console;

import java.awt.geom.Rectangle2D;

public abstract class AbstractFont {
  private Rectangle2D m_chBounds;

  public AbstractFont(Rectangle2D chBounds) {
    m_chBounds = chBounds;
  }
  
  public int getCharHeight() {
    return (int) m_chBounds.getHeight();
  }

  public int getCharWidth() {
    return (int) m_chBounds.getWidth();
  }

  public abstract void paintCh(Console c, char ch, float x, float y);
}
