package util;

import polynomial.Term;

/**
 * Created by gajjar.s,on 10:29 AM, 10/6/18
 */
public class Utils {

  @SafeVarargs
  public static <T> T[] getArray(T... objects) {
    return objects;
  }

  public static boolean isTermZero(Term term) {
    return term.getCoefficient() == 0;
  }

  public static boolean isTermNonZero(Term term) {
    return !isTermZero(term);
  }
}
