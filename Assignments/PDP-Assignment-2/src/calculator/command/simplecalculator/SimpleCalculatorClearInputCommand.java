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

  /**
   * Constructs the object of {@link SimpleCalculatorClearInputCommand}. Its initializes it to the
   * given values.
   *
   * @param expression             current algebraic expression
   * @param clearInputCharacterSet set of characters supported by the command
   */
  public SimpleCalculatorClearInputCommand(List<String> expression,
                                           Set<Character> clearInputCharacterSet) {

    super(CommandName.CLEAR, clearInputCharacterSet, expression);
  }

  /**
   * Performs the clear input action by returning an empty list.
   *
   * @param input input character
   * @return the algebraic expression generated as result of performing action on the given input
   */
  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);
    expressionDeque.clear();
    return Utils.getExpressionList(expressionDeque);
  }

  /**
   * Returns a {@link Set} of valid {@link CommandName} that the next input character can represent
   * after successful execution of the current command.
   *
   * @return a set of valid commandName that the next input character can represent after successful
   * execution of the current command.
   */
  @Override
  public Set<CommandName> getNextValidCommands() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandName.OPERAND, CommandName.CLEAR)));
  }
}
