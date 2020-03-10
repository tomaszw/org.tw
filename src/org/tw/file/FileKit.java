/*
 * Created on 2004-08-31
 */

package org.tw.file;

import java.io.*;

public class FileKit {
  public static boolean exists(String path) {
    boolean exists = true;
    try {
      (new FileInputStream(path)).close();
    } catch (FileNotFoundException e) {
      exists = false;
    } catch (IOException e) {
      exists = false;
    }
    return exists;
  }

  public static File join(String... path) {
    File res = new File(path[0]);
    for (int i = 1; i < path.length; ++i)
      res = new File(res, path[i]);
    return res;
  }
}