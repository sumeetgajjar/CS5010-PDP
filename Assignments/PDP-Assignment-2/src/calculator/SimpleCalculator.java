package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;
import calculator.inputcategory.ClearInputCategory;
import calculator.inputcategory.EqualToInputCategory;
import calculator.inputcategory.InputCategoryInterface;
import calculator.inputcategory.OperandInputCategory;
import calculator.inputcategory.OperatorInputCategory;

import static calculator.bean.InputCategory.CLEAR;
import static calculator.bean.InputCategory.OPERAND;

public class SimpleCalculator extends AbstractCalculator {

  private static final Set<InputCategory> INITIAL_VALID_INPUT_CATEGORY_SET =
          Collections.unmodifiableSet(
                  new HashSet<>(Arrays.asList(OPERAND, CLEAR
                  )));

  private SimpleCalculator(List<String> currentExpression,
                           Set<InputCategory> anticipatedInputCategorySet,
                           String result) {
    super(currentExpression, anticipatedInputCategorySet, result);
  }

  public SimpleCalculator() {
    this(Collections.emptyList(), INITIAL_VALID_INPUT_CATEGORY_SET, "");
  }

  @Override
  protected Calculator getCalculatorInstance(List<String> expression, String result, Set<InputCategory> anticipatedInputCategory) {
    return new SimpleCalculator(expression, anticipatedInputCategory, result);
  }

  @Override
  protected List<InputCategoryInterface> getSupportedInputCategoryInterface() {
    return Arrays.asList(
            new OperandInputCategory(this.currentExpression, getSupportedDigits(), getSupportedOperators()),
            new OperatorInputCategory(this.currentExpression, getSupportedOperators()),
            new EqualToInputCategory(this.currentExpression, EQUAL_TO_CHARACTER_SET),
            new ClearInputCategory(this.currentExpression, CLEAR_CHARACTER_SET)
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
