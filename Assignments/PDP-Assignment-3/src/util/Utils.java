package util;

import java.util.Objects;
import java.util.function.Function;

/**
 * This class represents set of util functions which can be used by any class.
 */
public class Utils {

  /**
   * Returns true if the given String is null or empty, false otherwise.
   *
   * @param text string to be checked
   * @return true if the given String is null or empty, false otherwise
   */
  public static boolean isStringNotSet(String text) {
    return !isStringSet(text);
  }

  /**
   * Returns true if the given String is not null and not empty, false otherwise.
   *
   * @param text string to be checked
   * @return true if the given String is not null and not empty, false otherwise
   */
  public static boolean isStringSet(String text) {
    return Objects.nonNull(text) && text.length() > 0;
  }

  /**
   * Returns a new array containing elements of both arrays.
   *
   * @param array1 first array
   * @param array2 second array
   * @return the merged array
   */
  public static Object[] merge(Object[] array1, Object... array2) {
    Object[] mergedArray = new Object[array1.length + array2.length];
    int i = 0;
    for (Object obj : array1) {
      mergedArray[i++] = obj;
    }

    for (Object obj : array2) {
      mergedArray[i++] = obj;
    }

    return mergedArray;
  }

  /**
   * Returns true if the given array contains a element multiple times, false otherwise.
   *
   * @param array array to be check
   * @return true if the given array contains a element multiple times, false otherwise.
   */
  public static <T> boolean checkForDuplicatesInArray(T[] array) {
    for (int i = 0; i < array.length; i++) {
      for (int j = i + 1; j < array.length; j++) {
        if (array[i].equals(array[j])) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Applies the mapper function on all elements of the given array and returns the new array.
   *
   * @param mapper  mapper to be applied on each element of the given array
   * @param objects given array
   * @return the new array with mapped elements from the given array
   */
  @SafeVarargs
  public static <T> String[] mapToStringArray(Function<T, String> mapper, T... objects) {
    String[] strings = new String[objects.length];
    for (int i = 0; i < objects.length; i++) {
      strings[i] = mapper.apply(objects[i]);
    }
    return strings;
  }
}
