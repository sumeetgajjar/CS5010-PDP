package calculator.inputcategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;
import calculator.bean.Operation;
import calculator.util.Utils;

public class SimpleEqualToInputCategory implements InputCategoryInterface {

  protected final List<String> expression;
  private final Set<Character> supportedEqualToCharacters;

  public SimpleEqualToInputCategory(List<String> expression, Set<Character> supportedEqualToCharacters) {
    this.expression = expression;
    this.supportedEqualToCharacters = supportedEqualToCharacters;
  }

  @Override
  public boolean belongToInputCategory(char input) {
    return this.supportedEqualToCharacters.contains(input);
  }

  @Override
  public InputCategory getInputCategory() {
    return InputCategory.EQUAL_TO;
  }

  @Override
  public List<String> performAction(char input) {
    return evaluateExpression(this.expression);
  }

  @Override
  public Set<InputCategory> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InputCategory.OPERATOR, InputCategory.EQUAL_TO, InputCategory.CLEAR)));
  }

  protected List<String> evaluateExpression(List<String> expression) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(expression);
    int n1 = Integer.parseInt(expressionDeque.removeFirst());

    while (!expressionDeque.isEmpty()) {
      char operator = expressionDeque.removeFirst().charAt(0);
      int n2 = Integer.parseInt(expressionDeque.removeFirst());
      n1 = performOperation(operator, n1, n2);
    }
    return Collections.singletonList(String.valueOf(n1));
  }

  private int performOperation(char operatorSymbol, int n1, int n2) {
    try {
      Operation operation = Operation.getOperation(operatorSymbol);
      return operation.perform(n1, n2);
    } catch (ArithmeticException e) {
      return 0;
    }
  }
}
