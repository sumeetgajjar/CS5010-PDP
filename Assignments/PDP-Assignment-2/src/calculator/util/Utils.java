package calculator.util;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Utils {

  public static Deque<String> getExpressionDeque(List<String> expressions) {
    return new LinkedList<>(expressions);
  }

  public static List<String> getExpressionList(Deque<String> expressionDeque) {
    return new LinkedList<>(expressionDeque);
  }
}
