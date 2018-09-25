package calculator.inputcategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;
import calculator.util.Utils;

public class SimpleOperatorInputCategory implements InputCategoryInterface {

  protected final List<String> expression;
  protected final Set<Character> supportedOperators;

  public SimpleOperatorInputCategory(List<String> expression, Set<Character> supportedOperators) {
    this.expression = expression;
    this.supportedOperators = Collections.unmodifiableSet(supportedOperators);
  }

  @Override
  public boolean belongToInputCategory(char input) {
    return this.supportedOperators.contains(input);
  }

  @Override
  public InputCategory getInputCategory() {
    return InputCategory.OPERATOR;
  }

  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);
    expressionDeque.addLast(String.valueOf(input));
    return Utils.getExpressionList(expressionDeque);
  }

  @Override
  public Set<InputCategory> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InputCategory.OPERAND, InputCategory.CLEAR)));
  }
}
