package calculator;

import java.util.Deque;
import java.util.List;
import java.util.Objects;

import calculator.bean.InputCategory;
import calculator.bean.Operation;

public class SmartCalculator extends SimpleCalculator {

  private Operation lastOperation;

  @Override
  protected List<String> performActionForInputCategoryOperator(char input) {
    Deque<String> currentExpressionDeque = getCurrentExpressionDeque();

    String lastElement = currentExpressionDeque.peekLast();
    if (Objects.nonNull(lastElement)) {
      InputCategory lastInputCategory = getInputCategory(lastElement.charAt(lastElement.length() - 1));
      if (lastInputCategory == InputCategory.OPERAND) {
        currentExpressionDeque.addLast(String.valueOf(input));
      } else if (lastInputCategory == InputCategory.OPERATOR) {
        currentExpressionDeque.removeLast();
        currentExpressionDeque.addLast(String.valueOf(input));
      } else {
        throw new IllegalStateException(String.format("Cannot Handle InputCategory: %s", lastInputCategory));
      }
    }

    return getListFromDeque(currentExpressionDeque);
  }

  @Override
  protected List<String> performActionForInputCategoryEqualTo() {
    Deque<String> currentExpressionDeque = getCurrentExpressionDeque();

    while (!currentExpressionDeque.isEmpty()) {
      int n1 = Integer.parseInt(currentExpressionDeque.removeFirst());
      char operator = currentExpressionDeque.removeFirst().charAt(0);
      int n2 = Integer.parseInt(currentExpressionDeque.removeFirst());

      int result = performOperation(operator, n1, n2);

      currentExpressionDeque.addFirst(String.valueOf(result));
      if (currentExpressionDeque.size() == 1) {
        break;
      }
    }

    return getListFromDeque(currentExpressionDeque);
  }
}
