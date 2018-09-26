package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.bean.Operation;
import calculator.command.Command;
import calculator.command.smartcalculator.SmartCalculatorClearInputCommand;
import calculator.command.smartcalculator.SmartCalculatorEqualToCommand;
import calculator.command.smartcalculator.SmartCalculatorOperandInputCommand;
import calculator.command.smartcalculator.SmartCalculatorOperatorInputCommand;
import calculator.util.Utils;

public class SmartCalculator extends AbstractCalculator {

  /**
   * Initial Set of Commands that are supported by the {@link SmartCalculator}.
   */
  private static final Set<CommandName> INITIAL_VALID_INPUT_CATEGORY_SET =
          Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                  CommandName.CLEAR,
                  CommandName.OPERATOR,
                  CommandName.OPERAND)
          ));

  /**
   * last operation in the previous algebraic expression
   */
  private final Operation lastOperation;

  /**
   * last operand in the previous algebraic expression
   */
  private final int lastOperand;

  /**
   * Private Constructor which constructs a Object of {@link SmartCalculator} with given values.
   *
   * @param newExpression               current algebraic expression
   * @param nextAnticipatedCommandNames the current result of calculator
   * @param result                      set of valid command names next input can have
   * @param lastOperation               last operation in the previous algebraic expression
   * @param lastOperand                 last operand in the previous algebraic expression
   */
  private SmartCalculator(List<String> newExpression,
                          Set<CommandName> nextAnticipatedCommandNames,
                          String result,
                          Operation lastOperation,
                          int lastOperand) {

    super(newExpression, nextAnticipatedCommandNames, result);
    this.lastOperand = lastOperand;
    this.lastOperation = lastOperation;
  }

  /**
   * Public constructor which constructs a Object of {@link SmartCalculator} with default values.
   * The default values of fields are as follows.
   * <ul>
   * <li><code>currentExpression</code>: empty list</li>
   * <li><code>anticipatedCommandNames</code>: {@link CommandName#OPERAND},
   * {@link CommandName#CLEAR}, {@link CommandName#OPERATOR}</li>
   * <li><code>result</code>: ""</li>
   * <li><code>lastOperation</code>: {@link Operation#ADD}</li>
   * <li><code>lastOperand</code>: 0"</li>
   * </ul>
   */
  public SmartCalculator() {
    this(
            Collections.emptyList(),
            INITIAL_VALID_INPUT_CATEGORY_SET,
            "",
            Operation.ADD,
            0);
  }

  /**
   * Returns the list of supported operators by {@link SmartCalculator}. The list includes
   * following.
   * <ul>
   * <li>+</li>
   * <li>-</li>
   * <li>*</li>
   * </ul>
   *
   * @return the list of supported operators by {@link SmartCalculator}
   */
  @Override
  protected Set<Character> getSupportedOperators() {
    return SUPPORTED_OPERATOR;
  }

  /**
   * Returns the list of supported digits by {@link SmartCalculator} The list includes following.
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
   * @return the list of supported digits by {@link SmartCalculator}
   */
  @Override
  protected Set<Character> getSupportedDigits() {
    return SUPPORTED_DIGITS;
  }

  /**
   * Returns the list of supported {@link Command} by {@link SmartCalculator}. The list includes
   * following commands.
   * <ul>
   * <li>{@link SmartCalculatorOperandInputCommand}</li>
   * <li>{@link SmartCalculatorOperatorInputCommand}</li>
   * <li>{@link SmartCalculatorEqualToCommand}</li>
   * <li>{@link SmartCalculatorClearInputCommand}</li>
   * </ul>
   *
   * @return the list of supported {@link Command} by {@link SmartCalculator}
   */
  @Override
  protected List<Command> getSupportedCommands() {
    return Arrays.asList(
            new SmartCalculatorOperandInputCommand(
                    this.currentExpression,
                    getSupportedDigits(),
                    getSupportedOperators()),

            new SmartCalculatorOperatorInputCommand(
                    this.currentExpression,
                    getSupportedOperators(),
                    getSupportedDigits()),

            new SmartCalculatorEqualToCommand(
                    this.currentExpression,
                    EQUAL_TO_CHARACTER_SET,
                    lastOperation,
                    lastOperand),

            new SmartCalculatorClearInputCommand(
                    this.currentExpression,
                    CLEAR_CHARACTER_SET)
    );
  }

  /**
   * Returns a new Object of {@link SmartCalculator} with given values.
   *
   * @param expression              current algebraic expression
   * @param result                  the current result of calculator
   * @param anticipatedCommandNames set of valid command names next input can have
   * @return a new Object of {@link SmartCalculator} with given values.
   */
  @Override
  protected Calculator getCalculatorInstance(
          List<String> expression,
          String result,
          Set<CommandName> anticipatedCommandNames) {

    int newLastOperand = getLastOperand();
    Operation newLastOperator = getLastOperator();

    return new SmartCalculator(
            expression,
            anticipatedCommandNames,
            result,
            newLastOperator,
            newLastOperand);
  }

  /**
   * Returns the last operation in the current algebraic expression. If the size of the current
   * expression is 0 return {@link Operation#ADD}.
   *
   * @return the last operation in the current algebraic expression
   */
  private Operation getLastOperator() {
    if (this.currentExpression.size() == 0) {
      return Operation.ADD;
    } else if (this.currentExpression.size() == 1) {
      return this.lastOperation;
    } else {
      Deque<String> currentExpressionDeque = Utils.getExpressionDeque(this.currentExpression);
      while (!currentExpressionDeque.isEmpty()) {
        String lastElement = currentExpressionDeque.removeLast();
        char operatorSymbol = lastElement.charAt(lastElement.length() - 1);
        if (getSupportedOperators().contains(operatorSymbol)) {
          return Operation.getOperation(operatorSymbol);
        }
      }
    }
    throw new IllegalStateException("Cannot reach here");
  }

  /**
   * Returns the last operand in the current algebraic expression. If the size of the current
   * expression is 0 returns 0.
   *
   * @return the last operand in the current algebraic expression
   */
  private int getLastOperand() {
    if (this.currentExpression.size() == 0) {
      return 0;
    } else if (this.currentExpression.size() == 1) {
      return this.lastOperand;
    } else {
      Deque<String> currentExpressionDeque = Utils.getExpressionDeque(this.currentExpression);
      while (!currentExpressionDeque.isEmpty()) {
        String lastElement = currentExpressionDeque.removeLast();
        char digit = lastElement.charAt(lastElement.length() - 1);
        if (getSupportedDigits().contains(digit)) {
          return Integer.parseInt(lastElement);
        }
      }
    }
    throw new IllegalStateException("Cannot reach here");
  }
}
