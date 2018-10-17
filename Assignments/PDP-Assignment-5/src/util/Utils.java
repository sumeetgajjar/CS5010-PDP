package util;

/**
 * Created by gajjar.s, on 12:43 PM, 10/17/18
 */
public class Utils {

  public static <T> T[] getSubArray(T[] array, int from, int to) {
    T[] subarray = (T[]) (new Object[to - from]);
    for (int i = from, j = 0; i < to; i++, j++) {
      subarray[j] = array[i];
    }
    return subarray;
  }

  public static Character[] convertStringToCharacterArray(String code) {
    Character[] path = new Character[code.length()];
    char[] chars = code.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      path[i] = chars[i];
    }
    return path;
  }
}
