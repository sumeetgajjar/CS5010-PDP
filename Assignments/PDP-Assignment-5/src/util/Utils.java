package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents set of util functions which can be used by any class.
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

  /**
   * Checks if the given string is null or empty.
   *
   * @param string string to check
   * @throws IllegalArgumentException if the given string is null or empty
   */
  public static void checkNullOrEmptyString(String string) throws IllegalArgumentException {
    if (Objects.isNull(string) || string.length() == 0) {
      throw new IllegalArgumentException(String.format("Invalid string:'%s'", string));
    }
  }

  /**
   * Checks if the given {@link Collection} is null or empty.
   *
   * @param collection the collection to check
   * @throws IllegalArgumentException if the given collection is null or empty
   */
  public static <T> void checkNullOrEmptyCollection(Collection<T> collection)
          throws IllegalArgumentException {

    if (Objects.isNull(collection) || collection.isEmpty()) {
      throw new IllegalArgumentException("Collection cannot be null or empty");
    }
  }

  /**
   * Checks if the given map is null or empty.
   *
   * @param map the map to check
   * @throws IllegalArgumentException if the given map is null or empty
   */
  public static <K, V> void checkNullOrEmptyMap(Map<K, V> map) throws IllegalArgumentException {
    if (Objects.isNull(map) || map.isEmpty()) {
      throw new IllegalArgumentException("Map cannot be null or empty");
    }
  }
}
