/*
 * Created on 2004-08-27
 */

package org.tw.patterns.observer;

import java.util.*;

public class UpdateableObj implements IBUpdateable {
  private boolean               m_blocked         = false;
  private boolean               m_pendingUpdate   = false;
  private List<IUpdateListener> m_updateListeners = new ArrayList<IUpdateListener>();

  public void addUpdateListener(IUpdateListener l) {
    m_updateListeners.add(l);
  }

  public void blockUpdates() {
    if (m_blocked)
      return;
    m_blocked = true;
    m_pendingUpdate = false;
  }

  public void removeUpdateListener(IUpdateListener l) {
    m_updateListeners.remove(l);
  }

  public void unblockUpdates() {
    if (m_blocked) {
      if (m_pendingUpdate)
        fireUpdated();
      m_pendingUpdate = false;
      m_blocked = false;
    }
  }

  public void fireUpdated() {
    fireUpdated(IUpdateable.NO_HINT, null);
  }

  public void fireUpdated(int hint, Object data) {
    if (m_blocked) {
      m_pendingUpdate = true;
    } else {
      for (Iterator iter = m_updateListeners.iterator(); iter.hasNext();) {
        IUpdateListener l = (IUpdateListener) iter.next();
        l.onUpdate(hint, data);
      }
    }
  }
}