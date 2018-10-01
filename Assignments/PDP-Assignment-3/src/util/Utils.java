package util;

import java.util.Objects;

public class Utils {

  public static boolean isStringNotSet(String text) {
    return !isStringSet(text);
  }

  public static boolean isStringSet(String text) {
    return Objects.nonNull(text) && text.length() > 0;
  }

  public static Object[] merge(Object[] array, Object... objects) {
    Object[] mergedArray = new Object[array.length + objects.length];
    int i = 0;
    for (Object obj : array) {
      mergedArray[i++] = obj;
    }

    for (Object obj : objects) {
      mergedArray[i++] = obj;
    }

    return mergedArray;
  }
}
