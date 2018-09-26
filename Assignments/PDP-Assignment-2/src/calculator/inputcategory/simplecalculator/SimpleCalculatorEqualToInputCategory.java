package calculator.inputcategory.simplecalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.bean.Operation;
import calculator.inputcategory.AbstractInputCategory;
import calculator.util.Utils;

public class SimpleCalculatorEqualToInputCategory extends AbstractInputCategory {

  public SimpleCalculatorEqualToInputCategory(List<String> expression,
                                              Set<Character> supportCharacters) {

    super(expression, supportCharacters);
  }

  @Override
  public CommandName getInputCategory() {
    return CommandName.EQUAL_TO;
  }

  @Override
  public List<String> performAction(char input) throws RuntimeException {
    return evaluateExpression(this.expression);
  }

  @Override
  public Set<CommandName> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandName.OPERATOR, CommandName.EQUAL_TO, CommandName.CLEAR)));
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
