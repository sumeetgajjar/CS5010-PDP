package calculator.inputcategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import calculator.bean.InputCategory;
import calculator.util.Utils;

public class SmartOperatorInputCategory extends OperatorInputCategory {

  private final Set<Character> supportedDigits;

  public SmartOperatorInputCategory(List<String> expression, Set<Character> supportedOperators, Set<Character> supportedDigits) {
    super(expression, supportedOperators);
    this.supportedDigits = supportedDigits;
  }

  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);

    String lastElement = expressionDeque.peekLast();
    if (Objects.nonNull(lastElement)) {

      char ch = lastElement.charAt(lastElement.length() - 1);
      if (this.supportedDigits.contains(ch)) {
        expressionDeque.addLast(String.valueOf(input));
      } else if (this.supportedOperators.contains(ch)) {
        expressionDeque.removeLast();
        expressionDeque.addLast(String.valueOf(input));
      } else {
        throw new IllegalStateException(String.format("Cannot Handle Input: %s", input));
      }
    }

    return Utils.getExpressionList(expressionDeque);
  }

  @Override
  public Set<InputCategory> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InputCategory.OPERAND, InputCategory.OPERATOR, InputCategory.EQUAL_TO, InputCategory.CLEAR)));
  }
}
