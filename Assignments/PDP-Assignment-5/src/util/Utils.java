package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gajjar.s, on 12:43 PM, 10/17/18
 */
public class Utils {

  public static List<Character> convertStringToCharacterArray(String code) {
    List<Character> path = new ArrayList<>(code.length());
    char[] chars = code.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      path.add(i, chars[i]);
    }
    return path;
  }
}
