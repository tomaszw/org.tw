/*
 * Created on 2005-09-10
 */

package org.tw.console;

import java.nio.CharBuffer;
import java.nio.IntBuffer;

public interface Surface {
  int bgat(int p);
  char chat(int p);
  void clear();
  void clearflag(int x, int y, int flag);
  void clearRect(int x, int y, int w, int h);
  int fgat(int p);
  int flagsat(int p);
  IntBuffer getBgbuf();
  CharBuffer getChbuf();
  IntBuffer getFgbuf();
  IntBuffer getFlagsbuf();
  int getHeight();
  int getScanW();
  int getWidth();
  boolean inbounds(int y, int x);
  void print(String s);
  void printCh(char ch);
  void printSurface(Surface s);
  void printSymbol(Symbol s);
  void putBuf(char ch, int bg, int fg, int fl);
  void putBufAt(int x, int y, char ch, int bg, int fg, int flags);
  void setBg(Color c);
  void setBufPos(int p);
  void setFg(Color c);
  void setFlags(int flags);
  void setPos(int x, int y);
}