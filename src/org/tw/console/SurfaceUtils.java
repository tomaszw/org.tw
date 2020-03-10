/*
 * Created on 2006-07-11
 */

package org.tw.console;

public class SurfaceUtils {
  public static void paintCentered(Surface srf, Surface bg) {
    int dx = bg.getWidth() / 2 - srf.getWidth() / 2;
    int dy = bg.getHeight() / 2 - srf.getHeight() / 2;

    bg.setPos(dx, dy);
    bg.printSurface(srf);
  }

  public static void paintFrame(Surface srf) {
    int w = srf.getWidth();;
    int h = srf.getHeight();

    for (int x = 0; x < srf.getWidth(); ++x) {
      srf.setPos(x, 0);
      srf.printCh(SpecChars.HLINE);
      srf.setPos(x, srf.getHeight() - 1);
      srf.printCh(SpecChars.HLINE);
    }

    for (int y = 0; y < srf.getHeight(); ++y) {
      srf.setPos(0, y);
      srf.printCh(SpecChars.VLINE);
      srf.setPos(srf.getWidth() - 1, y);
      srf.printCh(SpecChars.VLINE);
    }

    srf.setPos(0, 0);
    srf.printCh(SpecChars.LTCORNER);
    srf.setPos(w - 1, 0);
    srf.printCh(SpecChars.RTCORNER);
    srf.setPos(0, h - 1);
    srf.printCh(SpecChars.LBCORNER);
    srf.setPos(w - 1, h - 1);
    srf.printCh(SpecChars.RBCORNER);
  }

}
