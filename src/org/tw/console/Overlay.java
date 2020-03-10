/*
 * Created on 2006-07-12
 */

package org.tw.console;


public abstract class Overlay {
  private double m_t0 = 0;
  private double m_t1 = 0;
  private double m_dt = 0;
  private boolean m_init = true;
  private Console m_console;

  public Overlay(Console c) {
    m_console = c;
  }
  public double dt() {
    return m_dt;
  }

  public abstract void paint();

  //  public void setGL(GL gl) {
  //    m_gl = gl;
  //  }

  public double t() {
    return m_t1;
  }
  
  public Console getConsole() {
    return m_console;
  }

  public void update() {
    if (m_init) {
      m_t0 = System.nanoTime() / 10e8;
      m_t1 = m_t0;
      m_init = false;
    } else {
      double t1 = System.nanoTime() / 10e8;
      m_dt = t1 - m_t0;
      m_t0 = m_t1;
      m_t1 = t1;
    }
    paint();
  }
}
