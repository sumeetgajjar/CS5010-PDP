package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategoryName;
import calculator.inputcategory.InputCategoryInterface;
import calculator.inputcategory.simplecalculator.SimpleCalculatorClearInputCategory;
import calculator.inputcategory.simplecalculator.SimpleCalculatorEqualToInputCategory;
import calculator.inputcategory.simplecalculator.SimpleCalculatorOperandInputCategory;
import calculator.inputcategory.simplecalculator.SimpleCalculatorOperatorInputCategory;

import static calculator.bean.InputCategoryName.CLEAR;
import static calculator.bean.InputCategoryName.OPERAND;

public class SimpleCalculator extends AbstractCalculator {

  private static final Set<InputCategoryName> INITIAL_VALID_INPUT_CATEGORY_SET =
          Collections.unmodifiableSet(
                  new HashSet<>(Arrays.asList(OPERAND, CLEAR
                  )));

  private SimpleCalculator(List<String> currentExpression,
                           Set<InputCategoryName> anticipatedInputCategoryNames,
                           String result) {
    super(currentExpression, anticipatedInputCategoryNames, result);
  }

  public SimpleCalculator() {
    this(Collections.emptyList(), INITIAL_VALID_INPUT_CATEGORY_SET, "");
  }

  @Override
  protected Calculator getCalculatorInstance(
          List<String> expression,
          String result,
          Set<InputCategoryName> anticipatedInputCategoryNames) {
    return new SimpleCalculator(expression, anticipatedInputCategoryNames, result);
  }

  @Override
  protected List<InputCategoryInterface> getSupportedInputCategoryInterface() {
    return Arrays.asList(
            new SimpleCalculatorOperandInputCategory(
                    this.currentExpression,
                    getSupportedDigits(),
                    getSupportedOperators()),

            new SimpleCalculatorOperatorInputCategory(
                    this.currentExpression,
                    getSupportedOperators()),

            new SimpleCalculatorEqualToInputCategory(
                    this.currentExpression,
                    EQUAL_TO_CHARACTER_SET),

            new SimpleCalculatorClearInputCategory(
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
