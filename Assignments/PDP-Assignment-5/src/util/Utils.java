package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gajjar.s, on 12:43 PM, 10/17/18
 */
public class Utils {

  /**
   * Returns the List of characters from the given string.
   *
   * @param message the given string
   * @return the List of characters from the given string
   */
  public static List<Character> convertStringToCharacterArray(String message) {
    List<Character> path = new ArrayList<>(message.length());
    char[] chars = message.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      path.add(i, chars[i]);
    }
    return path;
  }
}
