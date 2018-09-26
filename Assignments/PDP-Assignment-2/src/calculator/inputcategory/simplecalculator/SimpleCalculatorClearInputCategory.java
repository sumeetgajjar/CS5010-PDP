package calculator.inputcategory.simplecalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.inputcategory.AbstractInputCategory;
import calculator.util.Utils;

public class SimpleCalculatorClearInputCategory extends AbstractInputCategory {

  public SimpleCalculatorClearInputCategory(List<String> expression,
                                            Set<Character> clearInputCharacterSet) {

    super(expression, clearInputCharacterSet);
  }

  @Override
  public CommandName getInputCategory() {
    return CommandName.CLEAR;
  }

  @Override
  public List<String> performAction(char input) throws RuntimeException {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);
    expressionDeque.clear();
    return Utils.getExpressionList(expressionDeque);
  }

  @Override
  public Set<CommandName> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandName.OPERAND, CommandName.CLEAR)));
  }
}
