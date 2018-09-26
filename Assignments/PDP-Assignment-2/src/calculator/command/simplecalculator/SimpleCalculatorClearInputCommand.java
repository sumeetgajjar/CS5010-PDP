package calculator.command.simplecalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.SimpleCalculator;
import calculator.bean.CommandName;
import calculator.command.AbstractCommand;
import calculator.command.Command;
import calculator.util.Utils;

/**
 * This class represents ClearInput {@link Command} for {@link SimpleCalculator}. It extends {@link
 * AbstractCommand}.
 */
public class SimpleCalculatorClearInputCommand extends AbstractCommand {

  public SimpleCalculatorClearInputCommand(List<String> expression,
                                           Set<Character> clearInputCharacterSet) {

    super(expression, clearInputCharacterSet);
  }

  @Override
  public CommandName getCommandName() {
    return CommandName.CLEAR;
  }

  @Override
  public List<String> performAction(char input) throws RuntimeException {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);
    expressionDeque.clear();
    return Utils.getExpressionList(expressionDeque);
  }

  @Override
  public Set<CommandName> getNextValidCommands() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandName.OPERAND, CommandName.CLEAR)));
  }
}
