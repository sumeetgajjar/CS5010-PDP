package calculator;

import java.util.Deque;
import java.util.List;

import calculator.bean.InputCategory;

public class SmartCalculator extends SimpleCalculator {
  @Override
  protected List<String> performActionForInputCategoryOperator(char input) {
    Deque<String> currentExpressionDeque = getCurrentExpressionDeque();

    String lastElement = currentExpressionDeque.peekLast();
    InputCategory lastInputCategory = getInputCategory(lastElement.charAt(lastElement.length() - 1));
    if (lastInputCategory == InputCategory.OPERAND) {
      currentExpressionDeque.addLast(String.valueOf(input));
    } else if (lastInputCategory == InputCategory.OPERATOR) {
      currentExpressionDeque.removeLast();
      currentExpressionDeque.addLast(String.valueOf(input));
    } else {
      throw new IllegalStateException(String.format("Cannot Handle InputCategory: %s", lastInputCategory));
    }

    return getListFromDeque(currentExpressionDeque);
  }
}
