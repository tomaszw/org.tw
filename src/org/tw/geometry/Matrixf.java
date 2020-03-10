package org.tw.geometry;

import java.text.NumberFormat;
import java.text.DecimalFormat;

/**
 * Author: tomek Date: 2004-05-27
 */
public class Matrixf {
  public float[] d;
  public int w, h;

  public Matrixf(Matrixf m) {
    w = m.w;
    h = m.h;
    d = m.d.clone();
  }

  public Matrixf(int i, int j) {
    d = new float[i*j];
    w = j;
    h = i;
  }

  public Matrixf(int i, int j, float[] d) {
    this.d = d;
    w = j;
    h = i;
  }

  public Matrixf copy() {
    return new Matrixf(h, w, (float[]) d.clone());
  }

  public Matrixf crossOut(int i, int j) {
    Matrixf r = new Matrixf(h - 1, w - 1);
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

  public float det() {
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
      float sum = 0;
      for (int j = 0; j < dim; ++j) {
        float factor = get(0, j)*crossOut(0, j).det();
        if (j%2 == 0)
          sum += factor;
        else
          sum -= factor;
      }
      return sum;
    }
  }

  public float get(int i, int j) {
    return d[j + w*i];
  }

  public void getColumn(int col, float[] dst) {
    int p = 0;
    for (int i = 0; i < h; ++i)
      dst[p++] = get(i, col);
  }

  public float[] getColumn(int col) {
    float[] d = new float[h];
    getColumn(col, d);
    return d;
  }

  public float[] getColumnMajor() {
    float[] r = new float[w*h];
    getColumnMajor(r);
    return r;
  }
  
  public void getColumnMajor(float[] dst) {
    int p = 0;
    for (int j = 0; j < w; ++j)
      for (int i = 0; i < h; ++i)
        dst[p++] = get(i, j);
  }

  public float[] getRow(int row) {
    float[] d = new float[w];
    getRow(row, d);
    return d;
  }

  public void getRow(int row, float[] dst) {
    int p = 0;
    for (int j = 0; j < w; ++j)
      dst[p++] = get(row, j);
  }

  public Matrixf getSubmat(int i, int j, int h, int w) {
    Matrixf r = new Matrixf(h, w);
    for (int y = i; y < i + h; ++y)
      for (int x = j; x < j + w; ++x)
        r.set(y - i, x - j, get(y, x));
    return r;
  }

  public Matrixf inverse() {
    if (!isSquare())
      throw new RuntimeException("Mat.inverse");
    float det = det();
    Matrixf r = new Matrixf(h, w);
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

  public Matrixf mul(Matrixf rhs) {
    if (w != rhs.h)
      throw new RuntimeException("Mat.mul");

    Matrixf r = new Matrixf(h, rhs.w);
    for (int i = 0; i < h; ++i)
      for (int j = 0; j < rhs.w; ++j) {
        float dot = 0;
        for (int p = 0; p < w; ++p)
          dot += get(i, p)*rhs.get(p, j);
        r.set(i, j, dot);
      }
    return r;
  }

  public void set(int i, int j, float f) {
    d[j + w*i] = f;
  }

  public void setIdentity() {
    for (int i = 0; i < h; ++i)
      for (int j = 0; j < w; ++j)
        set(i, j, i == j ? 1 : 0);
  }

  public void setSubmat(int i, int j, Matrixf m) {
    for (int y = i; y < i + m.h; ++y)
      for (int x = j; x < j + m.w; ++x)
        set(y, x, m.get(y - i, x - j));
  }

  public Matrixf transpose() {
    Matrixf m = new Matrixf(h, w);
    for (int i = 0; i < h; ++i)
      for (int j = 0; j < w; ++j)
        m.set(i, j, get(j, i));
    return m;
  }

  public void transposeInplace() {
    if (!isSquare())
      throw new RuntimeException("Mat.transposeInplace");
    float tmp;
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