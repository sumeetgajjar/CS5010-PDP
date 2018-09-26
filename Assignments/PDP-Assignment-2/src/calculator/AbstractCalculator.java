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
 *
 * <ul>
 * <li><code>currentExpression</code> : It stores the current algebraic expression as {@link
 * List}</li>
 * <li><code>anticipatedCommandNames</code> : It is a {@link Set} of valid {@link
 * CommandName} the next Input can have</li>
 * <li><code>result</code> : It stores the result of the calculator as {@link String}</li>
 * </ul>
 *
 * To implement a {@link Calculator} the programmer needs to extend this class and provide
 * implementations for following methods.
 *
 * <ul>
 * <li>getSupportedOperators</li>
 * <li>getSupportedDigits</li>
 * <li>getSupportedCommands</li>
 * <li>getCalculatorInstance</li>
 * </ul>
 *
 * The programmer should also provide a constructor, of following signature
 * <code>Calculator({@link List<String>} currentExpression, {@link Set<CommandName>}
 * anticipatedCommandNames, {@link String} result)</code>
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

  protected AbstractCalculator(List<String> currentExpression,
                               Set<CommandName> anticipatedCommandNames,
                               String result) {

    this.currentExpression = currentExpression;
    this.anticipatedCommandNames = anticipatedCommandNames;
    this.result = result;
  }

  protected abstract Set<Character> getSupportedOperators();

  protected abstract Set<Character> getSupportedDigits();

  protected abstract List<Command> getSupportedCommands();

  protected abstract Calculator getCalculatorInstance(
          List<String> expression,
          String result,
          Set<CommandName> anticipatedCommandNames);

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

  private void isCurrentInputValid(char input, CommandName currentCommandName)
          throws IllegalArgumentException {

    if (!this.anticipatedCommandNames.contains(currentCommandName)) {
      throw new IllegalArgumentException(String.format("Input: '%s' is illegal", input));
    }
  }

  private String generateResultString(List<String> expression) {
    return String.join("", expression);
  }

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
