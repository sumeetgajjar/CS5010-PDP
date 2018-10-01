package util;

import java.util.Objects;

public class Utils {

  public static boolean isStringNotSet(String text) {
    return !isStringSet(text);
  }

  public static boolean isStringSet(String text) {
    return Objects.nonNull(text) && text.length() > 0;
  }
}
