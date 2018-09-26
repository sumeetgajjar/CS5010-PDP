package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.command.Command;

/**
 * This class provides a skeletal implementation of the {@link Calculator} interface. It minimize
 * the effort required to implement this interface. It has following protected fields.
 * <ul>
 * <li><code>currentExpression</code> : It stores the current algebraic expression as {@link
 * List}</li>
 * <li><code>anticipatedCommandNames</code> : It is a {@link Set} of valid {@link
 * CommandName} the next Input can have</li>
 * <li><code>result</code> : It stores the result of the calculator as {@link String}</li>
 * </ul>
 *
 * <p>To implement a {@link Calculator} the programmer needs to extend this class and provide
 * implementations for following methods.
 *
 * <ul>
 * <li>getSupportedOperators</li>
 * <li>getSupportedDigits</li>
 * <li>getSupportedCommands</li>
 * <li>getCalculatorInstance</li>
 * </ul></p>
 *
 * <p>The programmer should also provide a constructor, of following signature
 * <code>Calculator({@link List} currentExpression, {@link Set}
 * anticipatedCommandNames, {@link String} result)</code></p>
 */
public abstract class AbstractCalculator implements Calculator {

  /**
   * Set of valid characters for "Clear Input" command.
   */
  protected static final Set<Character> CLEAR_CHARACTER_SET = Collections.singleton('C');

  /**
   * Set of valid characters for "Equal To" command.
   */
  protected static final Set<Character> EQUAL_TO_CHARACTER_SET = Collections.singleton('=');

  /**
   * Set of default supported Digits.
   */
  protected static final Set<Character> SUPPORTED_DIGITS = Collections.unmodifiableSet(
          new LinkedHashSet<>(
                  Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
                  )));

  /**
   * Set of default supported operators.
   */
  protected static final Set<Character> SUPPORTED_OPERATOR = Collections.unmodifiableSet(
          new LinkedHashSet<>(Arrays.asList('+', '-', '*')));

  /**
   * stores the current algebraic expression.
   */
  protected final List<String> currentExpression;

  /**
   * stores the valid CommandName, the next input can have.
   */
  protected final Set<CommandName> anticipatedCommandNames;

  /**
   * stores the current result of the calculator.
   */
  protected final String result;

  /**
   * Protected Constructor for invocation by subclass constructors. It initializes the fields to
   * given values.
   *
   * @param currentExpression       current algebraic expression
   * @param anticipatedCommandNames set of valid command names next input can have
   * @param result                  current result of the calculator
   */
  protected AbstractCalculator(List<String> currentExpression,
                               Set<CommandName> anticipatedCommandNames,
                               String result) {

    this.currentExpression = currentExpression;
    this.anticipatedCommandNames = anticipatedCommandNames;
    this.result = result;
  }

  /**
   * Returns a {@link Set} of operators as character, which this Calculator supports.
   *
   * @return a {@link Set} of operators as character, which this Calculator supports
   */
  protected abstract Set<Character> getSupportedOperators();

  /**
   * Returns a {@link Set} of digits as characters, which this Calculator supports.
   *
   * @return a {@link Set} of digits as characters, which this Calculator supports
   */
  protected abstract Set<Character> getSupportedDigits();

  /**
   * Returns a {@link List} of {@link Command}, which this Calculator supports.
   *
   * @return a {@link List} of {@link Command}, which this Calculator supports
   */
  protected abstract List<Command> getSupportedCommands();

  /**
   * Returns a new Calculator Object with the given values.
   *
   * @param expression              current algebraic expression
   * @param result                  the current result of calculator
   * @param anticipatedCommandNames set of valid command names next input can have
   * @return a new Calculator Object with the given values
   */
  protected abstract Calculator getCalculatorInstance(
          List<String> expression,
          String result,
          Set<CommandName> anticipatedCommandNames);

  /**
   * Takes an input char and returns a new {@link Calculator} object. The new {@link Calculator} is
   * result of successful processing of the given char. It throws an IllegalArgumentException for
   * all invalid inputs and sequences, and a RuntimeException if a valid input causes an operand to
   * overflow.
   *
   * @param input the input for the {@link Calculator}
   * @return a new {@link Calculator} object as a result of correct processing of given input
   * @throws IllegalArgumentException if the given character is invalid or leads to an invalid input
   *                                  sequence
   * @throws RuntimeException         if a valid input causes an operand to overflow
   */
  @Override
  public Calculator input(char input) throws IllegalArgumentException, RuntimeException {
    Command currentCommand = mapInputToCommand(input);

    isCurrentInputValid(input, currentCommand.getCommandName());

    List<String> newExpression = currentCommand.performAction(input);

    String newResult = generateResultString(newExpression);

    Set<CommandName> nextAnticipatedCommandNames =
            currentCommand.getNextValidCommands();

    return getCalculatorInstance(newExpression, newResult, nextAnticipatedCommandNames);
  }

  @Override
  public String getResult() {
    return this.result;
  }

  /**
   * Checks if current command is a valid anticipated command of the calculator.
   *
   * @param input              the character for which command is to be checked
   * @param currentCommandName name of the command associated with the given character
   * @throws IllegalArgumentException if current command is not a valid anticipated command of the
   *                                  calculator
   */
  private void isCurrentInputValid(char input, CommandName currentCommandName)
          throws IllegalArgumentException {

    if (!this.anticipatedCommandNames.contains(currentCommandName)) {
      throw new IllegalArgumentException(String.format("Input: '%s' is illegal", input));
    }
  }

  /**
   * Returns the concatenated String of given algebraic expression.
   *
   * @param expression algebraic expression
   * @return the concatenated String of given algebraic expression
   */
  private String generateResultString(List<String> expression) {
    return String.join("", expression);
  }

  /**
   * Return the Command which maps to given input character.
   *
   * @param input character for which command is to be mapped
   * @return the Command which maps to given input character
   * @throws IllegalArgumentException if the Command corresponding to given input is not found
   */
  private Command mapInputToCommand(char input)
          throws IllegalArgumentException {

    for (Command command : getSupportedCommands()) {
      if (command.doesInputCharacterBelongToCommand(input)) {
        return command;
      }
    }
    throw new IllegalArgumentException(String.format("Input: '%s' is illegal", input));
  }
}
