package calculator.inputcategory.simplecalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.inputcategory.AbstractCommand;
import calculator.util.Utils;

public class SimpleCalculatorClearInputCommand extends AbstractCommand {

  public SimpleCalculatorClearInputCommand(List<String> expression,
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
