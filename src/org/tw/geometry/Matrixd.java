package org.tw.geometry;

import java.text.NumberFormat;
import java.text.DecimalFormat;

/**
 * Author: tomek Date: 2004-05-27
 */
public class Matrixd {
  public double[] d;
  public int w, h;

  public Matrixd(Matrixd m) {
    w = m.w;
    h = m.h;
    d = m.d.clone();
  }

  public Matrixd(int i, int j) {
    d = new double[i*j];
    w = j;
    h = i;
  }

  public Matrixd(int i, int j, double[] d) {
    this.d = d;
    w = j;
    h = i;
  }

  public Matrixd copy() {
    return new Matrixd(h, w, (double[]) d.clone());
  }

  public Matrixd crossOut(int i, int j) {
    Matrixd r = new Matrixd(h - 1, w - 1);
    int si = 0, sj = 0;
    int di = 0, dj = 0;
    while (di < r.h) {
      sj = 0;
      dj = 0;
      if (si == i)
        ++si;
      while (dj < r.w) {
        if (sj == j)
          ++sj;
        r.set(di, dj, get(si, sj));
        ++dj;
        ++sj;
      }
      ++di;
      ++si;
    }
    return r;
  }

  public double det() {
    if (!isSquare())
      throw new RuntimeException("Mat.det");
    final int dim = w;
    if (dim == 1)
      return d[0];
    else if (dim == 2)
      return d[0]*d[3] - d[1]*d[2];
    else if (dim == 3)
      return d[0]*d[4]*d[8] + d[1]*d[5]*d[6] + d[2]*d[3]*d[7] - d[2]*d[4]
        *d[6] - d[5]*d[7]*d[0] - d[8]*d[1]*d[3];
    else {
      double sum = 0;
      for (int j = 0; j < dim; ++j) {
        double factor = get(0, j)*crossOut(0, j).det();
        if (j%2 == 0)
          sum += factor;
        else
          sum -= factor;
      }
      return sum;
    }
  }

  public double get(int i, int j) {
    return d[j + w*i];
  }

  public void getColumn(int col, double[] dst) {
    int p = 0;
    for (int i = 0; i < h; ++i)
      dst[p++] = get(i, col);
  }

  public double[] getColumn(int col) {
    double[] d = new double[h];
    getColumn(col, d);
    return d;
  }

  public double[] getColumnMajor() {
    double[] r = new double[w*h];
    getColumnMajor(r);
    return r;
  }
  
  public void getColumnMajor(double[] dst) {
    int p = 0;
    for (int j = 0; j < w; ++j)
      for (int i = 0; i < h; ++i)
        dst[p++] = get(i, j);
  }

  public double[] getRow(int row) {
    double[] d = new double[w];
    getRow(row, d);
    return d;
  }

  public void getRow(int row, double[] dst) {
    int p = 0;
    for (int j = 0; j < w; ++j)
      dst[p++] = get(row, j);
  }

  public Matrixd getSubmat(int i, int j, int h, int w) {
    Matrixd r = new Matrixd(h, w);
    for (int y = i; y < i + h; ++y)
      for (int x = j; x < j + w; ++x)
        r.set(y - i, x - j, get(y, x));
    return r;
  }

  public Matrixd inverse() {
    if (!isSquare())
      throw new RuntimeException("Mat.inverse");
    double det = det();
    Matrixd r = new Matrixd(h, w);
    if (det == 0) {
      r.setIdentity();
    } else {
      for (int i = 0; i < h; ++i)
        for (int j = 0; j < w; ++j)
          r
            .set(i, j, (((i + j)%2 == 0) ? 1 : -1)*(1.0f/det)
            *crossOut(i, j).det());
    }
    r.transposeInplace();
    return r;
  }

  public boolean isSquare() {
    return w == h;
  }

  public Matrixd mul(Matrixd rhs) {
    if (w != rhs.h)
      throw new RuntimeException("Mat.mul");

    Matrixd r = new Matrixd(h, rhs.w);
    for (int i = 0; i < h; ++i)
      for (int j = 0; j < rhs.w; ++j) {
        double dot = 0;
        for (int p = 0; p < w; ++p)
          dot += get(i, p)*rhs.get(p, j);
        r.set(i, j, dot);
      }
    return r;
  }

  public void set(int i, int j, double f) {
    d[j + w*i] = f;
  }

  public void setIdentity() {
    for (int i = 0; i < h; ++i)
      for (int j = 0; j < w; ++j)
        set(i, j, i == j ? 1 : 0);
  }

  public void setSubmat(int i, int j, Matrixd m) {
    for (int y = i; y < i + m.h; ++y)
      for (int x = j; x < j + m.w; ++x)
        set(y, x, m.get(y - i, x - j));
  }

  public Matrixd transpose() {
    Matrixd m = new Matrixd(h, w);
    for (int i = 0; i < h; ++i)
      for (int j = 0; j < w; ++j)
        m.set(i, j, get(j, i));
    return m;
  }

  public void transposeInplace() {
    if (!isSquare())
      throw new RuntimeException("Mat.transposeInplace");
    double tmp;
    for (int i = 0; i < h; ++i)
      for (int j = i + 1; j < w; ++j) {
        tmp = get(i, j);
        set(i, j, get(j, i));
        set(j, i, tmp);
      }
  }

  public String format() {
    NumberFormat f = new DecimalFormat("0.00");
    String s = "";
    for (int i=0; i<4; ++i) {
      for (int j=0; j<4; ++j) {
        s += f.format(get(i,j));
        if (j != 4) s += " ";
      }
      s += "\n";
    }
    return s;
  }
}