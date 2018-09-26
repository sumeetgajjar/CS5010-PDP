package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategoryName;
import calculator.bean.Operation;
import calculator.inputcategory.InputCategoryInterface;
import calculator.inputcategory.SmartCalculatorClearInputCategory;
import calculator.inputcategory.SmartCalculatorEqualToInputCategory;
import calculator.inputcategory.SmartCalculatorOperandInputCategory;
import calculator.inputcategory.SmartCalculatorOperatorInputCategory;
import calculator.util.Utils;

public class SmartCalculator extends AbstractCalculator {

  private static final Set<InputCategoryName> INITIAL_VALID_INPUT_CATEGORY_SET =
          Collections.unmodifiableSet(new HashSet<>(
                  Arrays.asList(InputCategoryName.CLEAR, InputCategoryName.OPERATOR, InputCategoryName.OPERAND)
          ));

  private final Operation lastOperation;
  private final int lastOperand;


  private SmartCalculator(List<String> newExpression, Set<InputCategoryName> nextAnticipatedInputCategoryNames, String result, Operation lastOperation, int lastOperand) {
    super(newExpression, nextAnticipatedInputCategoryNames, result);
    this.lastOperand = lastOperand;
    this.lastOperation = lastOperation;
  }

  public SmartCalculator() {
    this(Collections.emptyList(), INITIAL_VALID_INPUT_CATEGORY_SET, "", Operation.ADD, 0);
  }

  @Override
  protected Set<Character> getSupportedOperators() {
    return SUPPORTED_OPERATOR;
  }

  @Override
  protected Set<Character> getSupportedDigits() {
    return SUPPORTED_DIGITS;
  }

  @Override
  protected List<InputCategoryInterface> getSupportedInputCategoryInterface() {
    return Arrays.asList(
            new SmartCalculatorOperandInputCategory(this.currentExpression, getSupportedDigits(), getSupportedOperators()),
            new SmartCalculatorOperatorInputCategory(this.currentExpression, getSupportedOperators(), getSupportedDigits()),
            new SmartCalculatorEqualToInputCategory(this.currentExpression, EQUAL_TO_CHARACTER_SET, lastOperation, lastOperand),
            new SmartCalculatorClearInputCategory(this.currentExpression, CLEAR_CHARACTER_SET)
    );
  }

  @Override
  protected Calculator getCalculatorInstance(List<String> expression, String result, Set<InputCategoryName> anticipatedInputCategoryNames) {
    int newLastOperand = getLastOperand();
    Operation newLastOperator = getLastOperator();
    return new SmartCalculator(expression, anticipatedInputCategoryNames, result, newLastOperator, newLastOperand);
  }

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
