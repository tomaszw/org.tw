/*
 * Created on 2007-07-05
 */

package org.tw.console;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;

public class AWTFontWrapper extends AbstractFont {
  private char[] array = new char[1];
  private Font m_awtFont;
  private GlyphVector[] m_glyphs = new GlyphVector[1024];
  private Shape[] m_shapes = new Shape[1024];
  private LineMetrics m_lm;

  public AWTFontWrapper(SwingConsole c, Font f) {
    super(f.getStringBounds("@", c.getGraphics().getFontRenderContext()));
    m_awtFont = f;
    for (int i = 0; i < 1024; ++i) {
      m_glyphs[i] = m_awtFont.createGlyphVector(c.getGraphics().getFontRenderContext(),
          "" + (char) i);
      m_shapes[i] = m_glyphs[i].getOutline();
    }
    m_lm = m_awtFont.getLineMetrics("@", c.getGraphics().getFontRenderContext());
  }
  public void paintCh(Console c, char ch, float x, float y) {
    SwingConsole sc = (SwingConsole) c;
    Graphics2D g = sc.getGraphics();
    //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    //    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
    //        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR);
    int chw = getCharWidth();
    int chh = getCharHeight();
    int px = (int) x;
    int py = (int) y;
    if (ch == SpecChars.HLINE) {
      g.drawLine(px, py + chh / 2, px + chw - 1, py + chh / 2);
    } else if (ch == SpecChars.VLINE) {
      g.drawLine(px + chw / 2, py, px + chw / 2, py + chh - 1);
    } else if (ch == SpecChars.LTCORNER) {
      g.drawLine(px + chw / 2, py + chh - 1, px + chw / 2, py + chh / 2);
      g.drawLine(px + chw / 2, py + chh / 2, px + chw - 1, py + chh / 2);
    } else if (ch == SpecChars.RTCORNER) {
      g.drawLine(px + chw / 2, py + chh - 1, px + chw / 2, py + chh / 2);
      g.drawLine(px + chw / 2, py + chh / 2, px, py + chh / 2);

    } else if (ch == SpecChars.LBCORNER) {
      g.drawLine(px + chw / 2, py, px + chw / 2, py + chh / 2);
      g.drawLine(px + chw / 2, py + chh / 2, px + chw - 1, py + chh / 2);
    } else if (ch == SpecChars.RBCORNER) {
      g.drawLine(px + chw / 2, py, px + chw / 2, py + chh / 2);
      g.drawLine(px + chw / 2, py + chh / 2, px, py + chh / 2);
    } else {
//            if (ch != ' ')
//              g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                  RenderingHints.VALUE_ANTIALIAS_ON);
      if (g.getFont() != m_awtFont)
        g.setFont(m_awtFont);
      array[0] = ch;
      g.drawGlyphVector(m_glyphs[(int) ch], (int) x, (int) y + getCharHeight()
          - m_lm.getDescent());
      //      AffineTransform trans = g.getTransform();
      //      g.translate(x, y + getCharHeight() - m_lm.getDescent());
      //      g.fill(m_shapes[(int) ch]);
      //      g.setTransform(trans);
//      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//          RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    //g.dispose();
  }
}
