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
 * This class represents a SimpleCalculator. It can only work with 32-bit whole numbers.
 */
public class SimpleCalculator extends AbstractCalculator {

  private static final Set<CommandName> INITIAL_VALID_INPUT_CATEGORY_SET =
          Collections.unmodifiableSet(
                  new HashSet<>(Arrays.asList(OPERAND, CLEAR
                  )));

  private SimpleCalculator(List<String> currentExpression,
                           Set<CommandName> anticipatedCommandNames,
                           String result) {
    super(currentExpression, anticipatedCommandNames, result);
  }

  public SimpleCalculator() {
    this(Collections.emptyList(), INITIAL_VALID_INPUT_CATEGORY_SET, "");
  }

  @Override
  protected Calculator getCalculatorInstance(
          List<String> expression,
          String result,
          Set<CommandName> anticipatedCommandNames) {
    return new SimpleCalculator(expression, anticipatedCommandNames, result);
  }

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

  @Override
  protected Set<Character> getSupportedOperators() {
    return SUPPORTED_OPERATOR;
  }

  @Override
  protected Set<Character> getSupportedDigits() {
    return SUPPORTED_DIGITS;
  }
}
