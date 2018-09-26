package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.command.Command;
import calculator.command.simplecalculator.SimpleCalculatorClearInputCommand;
import calculator.command.simplecalculator.SimpleCalculatorEqualToCommand;
import calculator.command.simplecalculator.SimpleCalculatorOperandInputCommand;
import calculator.command.simplecalculator.SimpleCalculatorOperatorInputCommand;

import static calculator.bean.CommandName.CLEAR;
import static calculator.bean.CommandName.OPERAND;

/**
 * This class represents a SimpleCalculator. It extends {@link AbstractCalculator} and it can only
 * work with 32-bit whole numbers.
 *
 * The valid operand characters are 0-9.
 *
 * The valid operators are +, - and *.
 *
 * The SimpleCalculator does not support negative numbers as input, although it can handle negative
 * results.
 *
 * The {@link SimpleCalculator} does not “infer” any missing inputs. For e.g.,11+=, will reported as
 * an error.
 *
 * The calculator does allow inputting “=” multiple times and the operation will return same result
 * every time.
 *
 * The input ’C’ will clear the calculator inputs.
 *
 * The input method throws an IllegalArgumentException for all invalid inputs and sequences, and a
 * RuntimeException if a valid input causes an operand to overflow. If the result of the arithmetic
 * overflows, then the result is reported as 0. For e.g., p + q - 1 0 will result in -10 if p+q
 * overflows.
 *
 * Following is the list of valid input sequences for {@link SimpleCalculator}.
 * <ul>
 * <li>12+20=</li>
 * <li>12+20-40=</li>
 * <li>12+20=-40=</li>
 * <li>12+20=-40=====</li>
 * </ul>
 */
public class SimpleCalculator extends AbstractCalculator {

  /**
   * Initial Set of Commands that are supported by the {@link SimpleCalculator}.
   */
  private static final Set<CommandName> INITIAL_VALID_INPUT_CATEGORY_SET =
          Collections.unmodifiableSet(
                  new HashSet<>(Arrays.asList(OPERAND, CLEAR
                  )));

  /**
   * Private Constructor which constructs a Object of {@link SimpleCalculator} with given values.
   *
   * @param currentExpression       current algebraic expression
   * @param anticipatedCommandNames the current result of calculator
   * @param result                  set of valid command names next input can have
   */
  private SimpleCalculator(List<String> currentExpression,
                           Set<CommandName> anticipatedCommandNames,
                           String result) {
    super(currentExpression, anticipatedCommandNames, result);
  }

  /**
   * Public constructor which constructs a Object of {@link SimpleCalculator} with default values.
   * The default values of fields are as follows.
   * <ul>
   * <li><code>currentExpression</code>: empty list</li>
   * <li><code>anticipatedCommandNames</code>: {@link CommandName#OPERAND}, {@link
   * CommandName#CLEAR}</li>
   * <li><code>result</code>: ""</li>
   * </ul>
   */
  public SimpleCalculator() {
    this(Collections.emptyList(), INITIAL_VALID_INPUT_CATEGORY_SET, "");
  }

  /**
   * Returns a new Object of {@link SimpleCalculator} with given values.
   *
   * @param expression              current algebraic expression
   * @param result                  the current result of calculator
   * @param anticipatedCommandNames set of valid command names next input can have
   */
  @Override
  protected Calculator getCalculatorInstance(
          List<String> expression,
          String result,
          Set<CommandName> anticipatedCommandNames) {
    return new SimpleCalculator(expression, anticipatedCommandNames, result);
  }

  /**
   * Returns the list of supported {@link Command} by {@link SimpleCalculator}. The list includes
   * following commands.
   * <ul>
   * <li>{@link SimpleCalculatorOperandInputCommand}</li>
   * <li>{@link SimpleCalculatorOperatorInputCommand}</li>
   * <li>{@link SimpleCalculatorEqualToCommand}</li>
   * <li>{@link SimpleCalculatorClearInputCommand}</li>
   * </ul>
   *
   * @return the list of supported {@link Command} by {@link SimpleCalculator}
   */
  @Override
  protected List<Command> getSupportedCommands() {
    return Arrays.asList(
            new SimpleCalculatorOperandInputCommand(
                    this.currentExpression,
                    getSupportedDigits(),
                    getSupportedOperators()),

            new SimpleCalculatorOperatorInputCommand(
                    this.currentExpression,
                    getSupportedOperators()),

            new SimpleCalculatorEqualToCommand(
                    this.currentExpression,
                    EQUAL_TO_CHARACTER_SET),

            new SimpleCalculatorClearInputCommand(
                    this.currentExpression,
                    CLEAR_CHARACTER_SET)
    );
  }

  /**
   * Returns the list of supported operators by {@link SimpleCalculator}. The list includes
   * following.
   * <ul>
   * <li>+</li>
   * <li>-</li>
   * <li>*</li>
   * </ul>
   *
   * @return the list of supported operators by {@link SimpleCalculator}
   */
  @Override
  protected Set<Character> getSupportedOperators() {
    return SUPPORTED_OPERATOR;
  }

  /**
   * Returns the list of supported digits by {@link SimpleCalculator} The list includes following.
   * <ul>
   * <li>1</li>
   * <li>2</li>
   * <li>3</li>
   * <li>4</li>
   * <li>5</li>
   * <li>6</li>
   * <li>7</li>
   * <li>8</li>
   * <li>9</li>
   * <li>0</li>
   * </ul>
   *
   * @return the list of supported digits by {@link SimpleCalculator}
   */
  @Override
  protected Set<Character> getSupportedDigits() {
    return SUPPORTED_DIGITS;
  }
}
