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
 * This class represents Operator Input {@link Command} for {@link SimpleCalculator}. It extends
 * {@link AbstractCommand}.
 */
public class SimpleCalculatorOperatorInputCommand extends AbstractCommand {

  /**
   * Constructs the object of {@link SimpleCalculatorOperatorInputCommand}. Its initializes it to
   * the given values
   *
   * @param expression       current algebraic expression
   * @param supportOperators set of operators supported by the command
   */
  public SimpleCalculatorOperatorInputCommand(List<String> expression,
                                              Set<Character> supportOperators) {

    super(expression, supportOperators);
  }

  /**
   * returns the command name.
   *
   * @return the command name
   */
  @Override
  public CommandName getCommandName() {
    return CommandName.OPERATOR;
  }

  /**
   * Appends the input character operator to the expression and returns the new expression.
   *
   * @param input input character
   * @return the new expression after appending the input character operator
   */
  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);
    expressionDeque.addLast(String.valueOf(input));
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
