package calculator.command.smartcalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import calculator.SmartCalculator;
import calculator.bean.CommandName;
import calculator.command.Command;
import calculator.command.simplecalculator.SimpleCalculatorOperatorInputCommand;
import calculator.util.Utils;

/**
 * This class represents Operator Input {@link Command} for {@link SmartCalculator}. It extends
 * {@link SimpleCalculatorOperatorInputCommand}.
 */
public class SmartCalculatorOperatorInputCommand extends SimpleCalculatorOperatorInputCommand {

  private final Set<Character> supportedDigits;

  /**
   * Constructs the object of {@link SimpleCalculatorOperatorInputCommand}. Its initializes it to
   * the given values
   *
   * @param expression         current algebraic expression
   * @param supportedOperators set of operators supported by the command
   * @param supportedDigits    set of digits  supported by the command
   */
  public SmartCalculatorOperatorInputCommand(List<String> expression,
                                             Set<Character> supportedOperators,
                                             Set<Character> supportedDigits) {

    super(expression, supportedOperators);
    this.supportedDigits = supportedDigits;
  }

  /**
   * Appends the input character operator to the expression if the last element of the expression is
   * operand and returns the new expression. If the last element of the expression is operator then
   * it removes the lastElement from the expression, appends the given operator character to the
   * expression and returns the new expression.
   *
   * @param input input character
   * @return the new expression after appending the input character operator
   */
  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);

    String lastElement = expressionDeque.peekLast();
    if (Objects.nonNull(lastElement)) {

      /* If last element in the expression is operator then
       * ignore the operator by remove it from the expression.
       * If last element in the expression is operand then
       * append the operator in the expression.
       */

      char ch = lastElement.charAt(lastElement.length() - 1);
      if (this.supportedDigits.contains(ch)) {
        expressionDeque.addLast(String.valueOf(input));
      } else if (this.supportedCharacters.contains(ch)) {
        expressionDeque.removeLast();
        expressionDeque.addLast(String.valueOf(input));
      } else {
        throw new IllegalStateException(String.format("Cannot Handle Input: %s", input));
      }
    }

    return Utils.getExpressionList(expressionDeque);
  }

  /**
   * Returns a {@link Set} of valid {@link CommandName} that the next input character can represent
   * after execution of the current command.
   *
   * @return a set of valid CommandName that the next input character can belong to
   */
  @Override
  public Set<CommandName> getNextValidCommands() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandName.OPERAND,
            CommandName.OPERATOR,
            CommandName.EQUAL_TO,
            CommandName.CLEAR)));
  }
}
