/*
 * Created on 2005-09-10
 */

package org.tw.console;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

public class MemorySurface implements Surface {
  int m_curbg;
  int m_curfg;
  int m_curflags;

  // private Color m_curBg, m_curFg;
  CharBuffer m_chbuf;
  IntBuffer m_fgbuf;
  IntBuffer m_bgbuf;
  IntBuffer m_flagsbuf;

  int m_marky;
  int m_markx;
  int m_w;
  int m_h;
  int m_scanw;
  private int m_savedFlags;
  private int m_savedFg;
  private int m_savedBg;
  private boolean m_override;

  public MemorySurface(int w, int h) {
    m_chbuf = ByteBuffer.allocateDirect(w * h * 2).asCharBuffer();
    m_fgbuf = ByteBuffer.allocateDirect(w * h * 4).asIntBuffer();
    m_bgbuf = ByteBuffer.allocateDirect(w * h * 4).asIntBuffer();
    m_flagsbuf = ByteBuffer.allocateDirect(w * h * 4).asIntBuffer();
    m_w = w;
    m_h = h;
    m_scanw = w;
    clear();
  }
  public void copyRect(int x, int y, int w, int h, Surface dst, int dx, int dy) {
    MemorySurface ms = (MemorySurface) dst;
    for (int cy = 0; cy < h; ++cy) {
      if (dy+cy < ms.getHeight() && y+cy < getHeight()) {
        ms.setBufPos((dy + cy) * ms.m_scanw + dx);
        setBufPos((y + cy) * m_scanw + x);
        for (int cx = 0; cx < w; ++cx) {
          if (dx+cx < ms.getWidth() && x+cx < getWidth()) {
            ms.m_chbuf.put(m_chbuf.get());
            ms.m_fgbuf.put(m_fgbuf.get());
            ms.m_bgbuf.put(m_bgbuf.get());
            ms.m_flagsbuf.put(m_flagsbuf.get());
          }
        }
      }
    }
  }

  public int bgat(int p) {
    return m_bgbuf.get(p);
  }
  public char chat(int p) {
    return m_chbuf.get(p);
  }

  public void clear() {
    clearRect(0, 0, m_w, m_h);
  }
  public void clearRect(int x, int y, int w, int h) {
    int y2 = y + h;
    int x2 = x + w;
    for (int cy = y; cy < y2; ++cy) {
      setBufPos(cy * m_scanw + x);
      for (int cx = x; cx < x2; ++cx) {
        m_chbuf.put(' ');
        m_bgbuf.put(m_curbg);
        m_fgbuf.put(m_curfg);
        m_flagsbuf.put(m_curflags);
      }
    }
  }

  public void clearflag(int x, int y, int flag) {
    int p = y * m_scanw + x;
    m_flagsbuf.put(p, m_flagsbuf.get(p) & (~flag));
  }

  public int fgat(int p) {
    return m_fgbuf.get(p);
  }

  public int flagsat(int p) {
    return m_flagsbuf.get(p);
  }

  public IntBuffer getFlagsbuf() {
    return m_flagsbuf;
  }

  public IntBuffer getBgbuf() {
    return m_bgbuf;
  }

  public CharBuffer getChbuf() {
    return m_chbuf;
  }

  public IntBuffer getFgbuf() {
    return m_fgbuf;
  }

  public int getHeight() {
    return m_h;
  }

  public int getScanW() {
    return m_scanw;
  }

  public int getWidth() {
    return m_w;
  }

  public boolean inbounds(int y, int x) {
    return y >= 0 && x >= 0 && y < m_h && x < m_w;
  }

  public void print(String s) {
    int len = s.length();
    setBufPos(m_marky * m_scanw + m_markx);
    int i = 0;
    while (i < len) {
      // mby meta attribute
      while (i < len - 1 && s.charAt(i) == '@' && s.charAt(i + 1) == '{') {
        String mattr = "";
        i += 2;
        while (i < len && s.charAt(i) != '}') {
          mattr += s.charAt(i);
          ++i;
        }
        ++i;
        handleMetaAttr(mattr);
      }
      if (i < len) {
        char ch = s.charAt(i);
        if (!markInbounds())
          return;
        putBuf(ch, m_curbg, m_curfg, m_curflags);
        advanceMark();
      }
      ++i;
    }
  }
  void handleMetaAttr(String mattr) {
    try {
      mattr = mattr.trim().toLowerCase();
      if (mattr.equals("end")) {
        m_curflags = m_savedFlags;
        m_curfg = m_savedFg;
        m_curbg = m_savedBg;
        m_override = false;
        return;
      }

      m_override = true;
      m_savedFlags = m_curflags;
      m_savedBg = m_curbg;
      m_savedFg = m_curfg;

      String prms[] = mattr.split(",");
      for (String p : prms) {
        String ass[] = p.split("=");
        if (ass.length == 2) {
          ass[0] = ass[0].trim();
          ass[1] = ass[1].trim();
          if (ass[0].equals("c")) {
            if (ass[1].substring(0, 2).equals("0x"))
              ass[1] = ass[1].substring(2);
            int rgb = Integer.parseInt(ass[1], 16);
            m_curfg = rgb;
          } else if (ass[0].equals("bg")) {
            int rgb = Integer.parseInt(ass[1], 16);
            m_curbg = rgb;
          }
        } else if (ass.length == 1) {
          if (ass[0].equals("f"))
            m_curflags |= Symbol.FLASH;
          else if (ass[0].equals("fadein"))
            m_curflags |= Symbol.FADE_IN;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void printCh(char ch) {
    setBufPos(m_marky * m_scanw + m_markx);
    putBuf(ch, m_curbg, m_curfg, m_curflags);
  }

  public void printSurface(Surface s) {
    int px = m_markx;
    int py = m_marky;
    int sw = s.getWidth();
    int sh = s.getHeight();

    MemorySurface ms = (MemorySurface) s;
    for (int y = 0; y < sh; ++y, ++py) {
      setBufPos(py * m_scanw + px);
      ms.setBufPos(y * ms.m_scanw);
      for (int x = 0; x < sw; ++x) {
        m_chbuf.put(ms.m_chbuf.get());
        m_fgbuf.put(ms.m_fgbuf.get());
        m_bgbuf.put(ms.m_bgbuf.get());
        m_flagsbuf.put(ms.m_flagsbuf.get());
      }
    }
  }

  public void printSymbol(Symbol s) {
    // if (!inbounds(m_marky, m_markx))
    // return;
    int bg = s.back.rgb();
    int fg = s.fore.rgb();
    putBufAt(m_markx, m_marky, s.ch, bg, fg, s.flags);

  }

  public void putBuf(char ch, int bg, int fg, int fl) {
    m_chbuf.put(ch);
    m_bgbuf.put(bg);
    m_fgbuf.put(fg);
    m_flagsbuf.put(fl);
  }

  public final void putBufAt(int x, int y, char ch, int bg, int fg, int flags) {
    int idx = y * m_scanw + x;
    m_chbuf.put(idx, ch);
    m_bgbuf.put(idx, bg);
    m_fgbuf.put(idx, fg);
    m_flagsbuf.put(idx, flags);
  }

  public void setBg(Color c) {
    m_curbg = c.rgb();
  }

  public void setBufPos(int p) {
    m_chbuf.position(p);
    m_fgbuf.position(p);
    m_bgbuf.position(p);
    m_flagsbuf.position(p);
  }

  public void setFg(Color c) {
    m_curfg = c.rgb();
  }

  public void setFlags(int flags) {
    m_curflags = flags;
  }

  public void setPos(int x, int y) {
    m_markx = x;
    m_marky = y;
  }

  protected final void advanceMark() {
    if (m_markx >= m_w) {
      if (m_marky >= m_h) {
        return;
      }
      m_markx = 0;
      ++m_marky;
    } else
      ++m_markx;
  }

  final boolean markInbounds() {
    return inbounds(m_marky, m_markx);
  }
}
