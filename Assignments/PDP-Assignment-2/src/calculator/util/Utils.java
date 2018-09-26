package calculator.util;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents set of util functions which can be used by any class.
 */
public class Utils {

  /**
   * Returns a {@link Deque} of the specified {@link List}.
   *
   * @param expressions the List for which the Deque is to be returned
   * @return the Deque of the specified List
   */
  public static Deque<String> getExpressionDeque(List<String> expressions) {
    return new LinkedList<>(expressions);
  }

  /**
   * Returns a {@link List} of the specified {@link Deque}.
   *
   * @param expressionDeque the Deque for which the List is to be returned
   * @return the List of the specified Deque
   */
  public static List<String> getExpressionList(Deque<String> expressionDeque) {
    return new LinkedList<>(expressionDeque);
  }
}
