/*
 * Created on 2007-07-05
 */

package org.tw.console;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SwingConsole implements Console {
  int paintNo = 1;
  private int m_w;
  private int m_h;
  private AbstractFont m_font;
  private MemorySurface m_surface;
  private JPanel m_panel;
  private BlockingQueue<KeyEvent> m_keyEvents = new LinkedBlockingQueue<KeyEvent>();
  private Graphics2D m_graphics;

  //private BufferStrategy m_bstrategy;

  private BufferedImage m_image;

  public SwingConsole(final Window window, JPanel p, int w, int h) {
    m_w = w;
    m_h = h;
    m_panel = p;
    m_panel.setBackground(java.awt.Color.BLACK);
    m_panel.setForeground(java.awt.Color.WHITE);
    m_surface = new MemorySurface(m_w, m_h);
    //window.setIgnoreRepaint(true);
    //m_panel.setIgnoreRepaint(true);
    SwingUtilities.getWindowAncestor(m_panel).addWindowListener(new WindowAdapter() {

      public void windowOpened(WindowEvent e) {
        m_font = createDefaultFont();
        m_panel.setPreferredSize(new Dimension(m_font.getCharWidth() * m_w, m_font
            .getCharHeight()
            * m_h));
        m_panel.setMinimumSize(new Dimension(m_font.getCharWidth() * m_w, m_font
            .getCharHeight()
            * m_h));
        //m_panel.getParent().validate();
        SwingUtilities.getWindowAncestor(m_panel).pack();
        //window.createBufferStrategy(2);
        //m_bstrategy = window.getBufferStrategy();
      }

    });

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(
        new KeyEventPostProcessor() {

          public boolean postProcessKeyEvent(KeyEvent e) {
            try {
              if (e.getID() == KeyEvent.KEY_PRESSED)
                if (m_keyEvents.size() <= 1)
                  m_keyEvents.put(e);
            } catch (InterruptedException e1) {}
            return false;
          }

        });
  }

  public void addOverlay(Overlay o) {
  }

  public void dispose() {
  }

  public AbstractFont font() {
    return m_font;
  }

  public Graphics2D getGraphics() {
    //assert m_panel.getGraphics() != null;
    return (Graphics2D) (m_graphics != null ? m_graphics : m_panel.getGraphics());
  }

  public KeyEvent readKey() {
    //    System.out.println("total " + Runtime.getRuntime().totalMemory()/1000000.0 + " free " + 
    //        Runtime.getRuntime().freeMemory()/1000000.0);
    try {
      return m_keyEvents.take();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void removeOverlay(Overlay o) {
  }

  public void repaint() {
    //System.out.println("repaint no. " + paintNo++);
    if (m_font == null)
      m_font = createDefaultFont();
    if (m_image == null || m_image.getWidth() != m_panel.getWidth()
        || m_image.getHeight() != m_panel.getHeight()) {
      //      m_image = new BufferedImage(m_panel.getWidth(), m_panel.getHeight(),
      //          BufferedImage.TYPE_3BYTE_BGR);
      m_image = (BufferedImage) m_panel.createImage(m_panel.getWidth(), m_panel
          .getHeight());
      //System.out.println("zonk");
    }
    Graphics2D g = m_image.createGraphics();
    //    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
    //        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    m_graphics = g;
    g.setColor(java.awt.Color.WHITE);
    g.setBackground(java.awt.Color.BLACK);
    int bg = -1, fg = -1;
    for (int y = 0; y < surface().getHeight(); ++y) {
      for (int x = 0; x < surface().getWidth(); ++x) {
        int sx = x * m_font.getCharWidth();
        int sy = y * m_font.getCharHeight();
        int newfg = surface().fgat(y * m_w + x);
        int newbg = surface().bgat(y * m_w + x);
        if (newfg != fg) {
          fg = newfg;
          g.setColor(ColorCache.getAwtColor(fg));
        }
        if (newbg != bg) {
          bg = newbg;
          g.setBackground(ColorCache.getAwtColor(bg));
        }
        g.clearRect(sx, sy, m_font.getCharWidth(), m_font.getCharHeight());
        if (y < surface().getHeight() && x < surface().getWidth())
          m_font.paintCh(this, surface().chat(y * m_w + x), sx, sy);
      }
    }
    m_panel.getGraphics().drawImage(m_image, 0, 0, null);
    //g.dispose();
    //m_bstrategy.show();
  }

  public Surface surface() {
    return m_surface;
  }

  private AbstractFont createDefaultFont() {
    return new AWTFontWrapper(this, Font.decode("Lucida Console-bold-12"));
  }

}
