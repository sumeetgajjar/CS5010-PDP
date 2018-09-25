package calculator.inputcategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;
import calculator.util.Utils;

public class ClearInputCategory implements InputCategoryInterface {

  private final List<String> expression;
  private final Set<Character> supportedClearCharacters;

  public ClearInputCategory(List<String> expression, Set<Character> clearInputCharacterSet) {
    this.expression = expression;
    supportedClearCharacters = clearInputCharacterSet;
  }

  @Override
  public boolean belongToInputCategory(char input) {
    return this.supportedClearCharacters.contains(input);
  }

  @Override
  public InputCategory getInputCategory() {
    return InputCategory.CLEAR;
  }

  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);
    expressionDeque.clear();
    return Utils.getExpressionList(expressionDeque);
  }

  @Override
  public Set<InputCategory> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InputCategory.OPERAND, InputCategory.CLEAR)));
  }
}
