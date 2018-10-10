package util;

import polynomial.bean.Term;

/**
 * This class represents set of util functions which can be used by any class.
 */
public class Utils {

  /**
   * Returns the given array.
   *
   * @param objects the objects to be returned
   * @param <T>     the type of the objects array
   * @return the given array
   */
  @SafeVarargs
  public static <T> T[] getArray(T... objects) {
    return objects;
  }

  /**
   * Checks if the term is a zero term. A term is a zero term is its coefficient is zero.
   *
   * @param term the term to check
   * @return true if the term is zero, false otherwise
   */
  public static boolean isTermZero(Term term) {
    return term.getCoefficient() == 0;
  }

  /**
   * Checks if the term is non-zero. A term is a non zero term is its coefficient is non zero.
   *
   * @param term the term to check
   * @return true if the term is non-zero, false otherwise
   */
  public static boolean isTermNonZero(Term term) {
    return !isTermZero(term);
  }
}
