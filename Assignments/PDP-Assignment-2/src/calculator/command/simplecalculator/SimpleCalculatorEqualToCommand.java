package calculator.command.simplecalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.SimpleCalculator;
import calculator.bean.CommandName;
import calculator.bean.Operation;
import calculator.command.AbstractCommand;
import calculator.command.Command;
import calculator.util.Utils;

/**
 * This class represents EqualTo {@link Command} for {@link SimpleCalculator}. It extends {@link
 * AbstractCommand}.
 */
public class SimpleCalculatorEqualToCommand extends AbstractCommand {

  /**
   * Constructs the object of {@link SimpleCalculatorEqualToCommand}. Its initializes it to the
   * given values
   *
   * @param expression        current algebraic expression
   * @param supportCharacters set of characters supported by the command
   */
  public SimpleCalculatorEqualToCommand(List<String> expression,
                                        Set<Character> supportCharacters) {

    super(expression, supportCharacters);
  }

  /**
   * returns the command name.
   *
   * @return the command name
   */
  @Override
  public CommandName getCommandName() {
    return CommandName.EQUAL_TO;
  }

  /**
   * Returns the algebraic expression generated after evaluating the current expression.
   *
   * @param input input character
   * @return the algebraic expression generated after evaluating the current expression
   * @throws RuntimeException if the input character causes operand overflow
   */
  @Override
  public List<String> performAction(char input) {
    return evaluateExpression(this.expression);
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
            CommandName.OPERATOR, CommandName.EQUAL_TO, CommandName.CLEAR)));
  }

  /**
   * Returns the evaluated form of given algebraic expression.
   *
   * @param expression algebraic expression
   * @return the evaluated form of given algebraic expression
   */
  protected List<String> evaluateExpression(List<String> expression) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(expression);
    int n1 = Integer.parseInt(expressionDeque.removeFirst());

    while (!expressionDeque.isEmpty()) {
      char operator = expressionDeque.removeFirst().charAt(0);
      int n2 = Integer.parseInt(expressionDeque.removeFirst());
      n1 = performOperation(operator, n1, n2);
    }
    return Collections.singletonList(String.valueOf(n1));
  }

  /**
   * Performs arithmetic operation associated with given symbol on the given two numbers. If the
   * result of the arithmetic operation causes a 32-bit overflow, it returns 0 as result.
   *
   * @param operatorSymbol operator symbol
   * @param n1             number1
   * @param n2             number2
   * @return result of arithmetic operation associated with given symbol on the given two numbers.
   */
  private int performOperation(char operatorSymbol, int n1, int n2) {
    try {
      Operation operation = Operation.getOperation(operatorSymbol);
      return operation.perform(n1, n2);
    } catch (ArithmeticException e) {
      return 0;
    }
  }
}
