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

public class SimpleOperandInputCategory extends AbstractInputCategory {

  private final Set<Character> supportedOperators;

  public SimpleOperandInputCategory(List<String> expression, Set<Character> supportCharacters, Set<Character> supportedOperators) {
    super(expression, supportCharacters);
    this.supportedOperators = Collections.unmodifiableSet(supportedOperators);
  }

  @Override
  public InputCategory getInputCategory() {
    return InputCategory.OPERAND;
  }

  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);

    String lastElement = expressionDeque.peekLast();
    if (Objects.nonNull(lastElement)) {

      char lastInput = lastElement.charAt(lastElement.length() - 1);

      if (this.supportedCharacters.contains(lastInput)) {
        lastElement = expressionDeque.pollLast();
        String newNumber = appendDigit(lastElement, input);
        expressionDeque.addLast(newNumber);
      } else if (this.supportedOperators.contains(lastInput)) {
        expressionDeque.addLast(String.valueOf(input));
      } else {
        throw new IllegalStateException(String.format("Cannot Handle Input: %s", input));
      }
    } else {
      expressionDeque.addLast(String.valueOf(input));
    }

    return Utils.getExpressionList(expressionDeque);
  }

  private String appendDigit(String numberString, char digitToAppend) {
    try {
      int currentNumber = Integer.parseInt(numberString);
      int newNumber = Math.addExact(Math.multiplyExact(currentNumber, 10), digitToAppend - '0');
      return String.valueOf(newNumber);
    } catch (ArithmeticException e) {
      throw new RuntimeException("Operand overflow: operand is greater than 32 bits", e);
    }
  }

  @Override
  public Set<InputCategory> getNextValidInputCategorySet() {
    Set<InputCategory> validInputCategorySet = new HashSet<>(Arrays.asList(
            InputCategory.OPERAND, InputCategory.OPERATOR, InputCategory.CLEAR));

    if (this.expression.size() >= 2) {
      validInputCategorySet.add(InputCategory.EQUAL_TO);
    }
    return Collections.unmodifiableSet(validInputCategorySet);
  }
}
