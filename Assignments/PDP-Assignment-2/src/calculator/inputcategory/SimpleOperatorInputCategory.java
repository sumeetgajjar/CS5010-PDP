package calculator.inputcategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategoryName;
import calculator.util.Utils;

public class SimpleOperatorInputCategory extends AbstractInputCategory {

  public SimpleOperatorInputCategory(List<String> expression, Set<Character> supportCharacters) {
    super(expression, supportCharacters);
  }

  @Override
  public InputCategoryName getInputCategory() {
    return InputCategoryName.OPERATOR;
  }

  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);
    expressionDeque.addLast(String.valueOf(input));
    return Utils.getExpressionList(expressionDeque);
  }

  @Override
  public Set<InputCategoryName> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InputCategoryName.OPERAND, InputCategoryName.CLEAR)));
  }
}
