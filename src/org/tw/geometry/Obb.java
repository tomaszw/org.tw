/*
 * Created on 2004-09-01
 */

package org.tw.geometry;

public class Obb {
  private Vec2f[] m_axis   = new Vec2f[2];
  private Vec2f[] m_corner = new Vec2f[4];
  private float[]  m_origin = new float[2];

  public Obb(Vec2f center, float w, float h, float angle) {
    Vec2f X = new Vec2f((float) Math.cos(angle), (float) Math.sin(angle));
    Vec2f Y = new Vec2f(-(float) Math.sin(angle), (float) Math.cos(angle));

    X = X.mul(w / 2);
    Y = Y.mul(h / 2);

    m_corner[0] = center.sub(X).sub(Y);
    m_corner[1] = center.add(X).sub(Y);
    m_corner[2] = center.add(X).add(Y);
    m_corner[3] = center.sub(X).add(Y);

    computeAxes();
  }

  public Obb(Vec2f center, Vec2f axis1, Vec2f axis2) {
    Vec2f X = axis1.mul(0.5f);
    Vec2f Y = axis2.mul(0.5f);

    m_corner[0] = center.sub(X).sub(Y);
    m_corner[1] = center.add(X).sub(Y);
    m_corner[2] = center.add(X).add(Y);
    m_corner[3] = center.sub(X).add(Y);

    computeAxes();
  }

  public Obb(Vec2f[] corners) {
    for (int i = 0; i < 4; i++) {
      m_corner[i] = corners[i];
    }
    
    computeAxes();
  }

  public void moveTo(Vec2f center) {
    Vec2f centroid = m_corner[0].add(m_corner[1]).add(m_corner[2]).add(m_corner[3]).div(
        4);
    Vec2f translation = center.sub(centroid);
    for (int c = 0; c < 4; ++c) {
      m_corner[c] = m_corner[c].add(translation);
    }
    computeAxes();
  }

  public boolean overlaps(Obb other) {
    return overlaps1Way(other) && other.overlaps1Way(this);
  }

  private void computeAxes() {
    m_axis[0] = m_corner[1].sub(m_corner[0]);
    m_axis[1] = m_corner[3].sub(m_corner[0]);

    // Make the length of each axis 1/edge length so we know any
    // dot product must be less than 1 to fall within the edge.

    for (int a = 0; a < 2; ++a) {
      m_axis[a] = m_axis[a].div(m_axis[a].squaredLength());
      m_origin[a] = m_corner[0].dot(m_axis[a]);
    }
  }

  /** Returns true if other overlaps one dimension of this. */
  private boolean overlaps1Way(Obb other) {
    for (int a = 0; a < 2; ++a) {

      double t = other.m_corner[0].dot(m_axis[a]);

      // Find the extent of box 2 on axis a
      double tMin = t;
      double tMax = t;

      for (int c = 1; c < 4; ++c) {
        t = other.m_corner[c].dot(m_axis[a]);

        if (t < tMin) {
          tMin = t;
        } else if (t > tMax) {
          tMax = t;
        }
      }

      // We have to subtract off the origin

      // See if [tMin, tMax] intersects [0, 1]
      if ((tMin > 1 + m_origin[a]) || (tMax < m_origin[a])) {
        // There was no intersection along this dimension;
        // the boxes cannot possibly overlap.
        return false;
      }
    }

    // There was no dimension along which there is no intersection.
    // Therefore the boxes overlap.
    return true;
  }
}