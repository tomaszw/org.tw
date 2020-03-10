/*
 * Created on 2005-08-29
 */

package org.tw.persistence.xml.annotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PersistAs {
  String value();
}
