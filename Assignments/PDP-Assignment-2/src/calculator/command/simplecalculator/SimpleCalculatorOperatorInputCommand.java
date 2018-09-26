package calculator.command.simplecalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.command.AbstractCommand;
import calculator.util.Utils;

public class SimpleCalculatorOperatorInputCommand extends AbstractCommand {

  public SimpleCalculatorOperatorInputCommand(List<String> expression,
                                              Set<Character> supportCharacters) {

    super(expression, supportCharacters);
  }

  @Override
  public CommandName getCommandName() {
    return CommandName.OPERATOR;
  }

  @Override
  public List<String> performAction(char input) throws RuntimeException {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);
    expressionDeque.addLast(String.valueOf(input));
    return Utils.getExpressionList(expressionDeque);
  }

  @Override
  public Set<CommandName> getNextValidCommands() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandName.OPERAND, CommandName.CLEAR)));
  }
}
