package calculator.command.simplecalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import calculator.SimpleCalculator;
import calculator.bean.CommandName;
import calculator.command.AbstractCommand;
import calculator.command.Command;
import calculator.util.Utils;

/**
 * This class represents Operand Input {@link Command} for {@link SimpleCalculator}. It extends
 * {@link AbstractCommand}. It throws an Runtime exception if the given input causes a Operand
 * Overflow.
 */
public class SimpleCalculatorOperandInputCommand extends AbstractCommand {

  private final Set<Character> supportedOperators;

  /**
   * Constructs the object of {@link SimpleCalculatorOperandInputCommand}. Its initializes it to the
   * given values
   *
   * @param expression         current algebraic expression
   * @param supportCharacters  set of characters supported by the command
   * @param supportedOperators set of supported operators by the command
   */
  public SimpleCalculatorOperandInputCommand(
          List<String> expression,
          Set<Character> supportCharacters,
          Set<Character> supportedOperators) {

    super(expression, supportCharacters);
    this.supportedOperators = Collections.unmodifiableSet(supportedOperators);
  }

  /**
   * returns the command name.
   *
   * @return the command name
   */
  @Override
  public CommandName getCommandName() {
    return CommandName.OPERAND;
  }

  /**
   * Appends the input character digit to the expression and returns the new expression. It throws
   * RuntimeException if the given input causes an operand overflow. It throws IllegalStateException
   * if the last element in the expression is neither a operand nor a operator.
   *
   * @param input input character
   * @return the new expression after appending the input character digit
   * @throws RuntimeException if the input character causes an operand overflow
   */
  @Override
  public List<String> performAction(char input) throws RuntimeException, IllegalStateException {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);

    String lastElement = expressionDeque.peekLast();
    if (Objects.nonNull(lastElement)) {

      char lastInput = lastElement.charAt(lastElement.length() - 1);

      /*
        checks if the last element is the operand or operator.
        If it is operand then appends the given input digit to the operand and
        then appends it to the expression.
        If it is operator the appends the given input digit directly to the expression.
        If it is neither operand nor operator then is throws IllegalStateException.
       */
      if (this.supportedCharacters.contains(lastInput)) {
        lastElement = expressionDeque.pollLast();
        String newNumber = appendDigit(lastElement, input);
        expressionDeque.addLast(newNumber);
      } else if (this.supportedOperators.contains(lastInput)) {
        expressionDeque.addLast(String.valueOf(input));
      } else {
        throw new IllegalStateException(String.format("Cannot Handle Input: %s", input));
      }
    } else {
      expressionDeque.addLast(String.valueOf(input));
    }

    return Utils.getExpressionList(expressionDeque);
  }

  /**
   * Appends the given character digit to the given number string. It throws an exception if the
   * appending of input character causes an operand overflow.
   *
   * @param numberString  numberString
   * @param digitToAppend digitToAppend
   * @return the number string with digit appended
   * @throws RuntimeException if the appending of the input character causes an operand overflow
   */
  private String appendDigit(String numberString, char digitToAppend) throws RuntimeException {
    try {
      int currentNumber = Integer.parseInt(numberString);
      int newNumber = Math.addExact(Math.multiplyExact(currentNumber, 10), digitToAppend - '0');
      return String.valueOf(newNumber);
    } catch (ArithmeticException e) {
      throw new RuntimeException("Operand overflow: operand is greater than 32 bits", e);
    }
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
    Set<CommandName> validCommandNames = new HashSet<>(Arrays.asList(
            CommandName.OPERAND, CommandName.OPERATOR, CommandName.CLEAR));

    if (this.expression.size() >= 2) {
      validCommandNames.add(CommandName.EQUAL_TO);
    }
    return Collections.unmodifiableSet(validCommandNames);
  }
}
