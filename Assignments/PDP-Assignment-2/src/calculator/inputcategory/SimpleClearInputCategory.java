package calculator.inputcategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategoryName;
import calculator.util.Utils;

public class SimpleClearInputCategory extends AbstractInputCategory {

  public SimpleClearInputCategory(List<String> expression, Set<Character> clearInputCharacterSet) {
    super(expression, clearInputCharacterSet);
  }

  @Override
  public InputCategoryName getInputCategory() {
    return InputCategoryName.CLEAR;
  }

  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);
    expressionDeque.clear();
    return Utils.getExpressionList(expressionDeque);
  }

  @Override
  public Set<InputCategoryName> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InputCategoryName.OPERAND, InputCategoryName.CLEAR)));
  }
}
