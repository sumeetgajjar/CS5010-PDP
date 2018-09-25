package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import calculator.bean.InputCategory;
import calculator.bean.Operation;

public class SmartCalculator extends SimpleCalculator {

  private static final Set<InputCategory> INITIAL_VALID_INPUT_CATEGORY_SET =
          Collections.unmodifiableSet(new HashSet<>(
                  Arrays.asList(InputCategory.CLEAR, InputCategory.OPERATOR, InputCategory.OPERAND)
          ));

  private final Operation lastOperation;
  private final int lastOperand;


  private SmartCalculator(List<String> newExpression, Set<InputCategory> nextAnticipatedInputCategory, String result, Operation lastOperation, int lastOperand) {
    super(newExpression, nextAnticipatedInputCategory, result);
    this.lastOperand = lastOperand;
    this.lastOperation = lastOperation;
  }

  public SmartCalculator() {
    this(Collections.emptyList(), INITIAL_VALID_INPUT_CATEGORY_SET, "", Operation.ADD, 0);
  }

  @Override
  protected Calculator getNewCalculatorInstance(List<String> newExpression, String newResult, Set<InputCategory> nextAnticipatedInputCategory) {
    int newLastOperand = getLastOperand();
    Operation newLastOperator = getLastOperator();
    return new SmartCalculator(newExpression, nextAnticipatedInputCategory, newResult, newLastOperator, newLastOperand);
  }

  @Override
  protected List<String> performInputCategoryEqualToAction() {
    Deque<String> expressionDeque = getExpressionDeque(this.currentExpression);

    if (expressionDeque.size() == 1) {
      expressionDeque.addLast(String.valueOf(this.lastOperation.getSymbol()));
      expressionDeque.addLast(String.valueOf(this.lastOperand));
    } else if (expressionDeque.size() == 2) {
      expressionDeque.addLast(expressionDeque.peekFirst());
    }

    return evaluateExpression(getExpressionList(expressionDeque));
  }

  @Override
  protected Set<InputCategory> getInitialValidInputCategorySet() {
    return INITIAL_VALID_INPUT_CATEGORY_SET;
  }

  @Override
  protected Set<InputCategory> getValidInputCategorySetForOperand() {
    return new HashSet<>(Arrays.asList(InputCategory.OPERAND, InputCategory.OPERATOR, InputCategory.EQUAL_TO));
  }

  @Override
  protected Set<InputCategory> getValidInputCategorySetForOperator() {
    return new HashSet<>(Arrays.asList(InputCategory.OPERAND, InputCategory.OPERATOR, InputCategory.EQUAL_TO));
  }

  @Override
  protected Set<InputCategory> getValidInputCategorySetForEqualTo() {
    return new HashSet<>(Arrays.asList(InputCategory.OPERATOR, InputCategory.EQUAL_TO));
  }

  @Override
  protected List<String> performInputCategoryOperatorAction(char input) {
    Deque<String> expressionDeque = getExpressionDeque(this.currentExpression);

    String lastElement = expressionDeque.peekLast();
    if (Objects.nonNull(lastElement)) {
      InputCategory lastInputCategory = getInputCategory(lastElement);
      if (lastInputCategory == InputCategory.OPERAND) {
        expressionDeque.addLast(String.valueOf(input));
      } else if (lastInputCategory == InputCategory.OPERATOR) {
        expressionDeque.removeLast();
        expressionDeque.addLast(String.valueOf(input));
      } else {
        throw new IllegalStateException(String.format("Cannot Handle InputCategory: %s", lastInputCategory));
      }
    }

    return getExpressionList(expressionDeque);
  }

  private Operation getLastOperator() {
    if (this.currentExpression.size() == 0) {
      return Operation.ADD;
    } else if (this.currentExpression.size() == 1) {
      return this.lastOperation;
    } else {
      Deque<String> currentExpressionDeque = getExpressionDeque(this.currentExpression);
      while (!currentExpressionDeque.isEmpty()) {
        String lastElement = currentExpressionDeque.removeLast();
        if (getInputCategory(lastElement) == InputCategory.OPERATOR) {
          return Operation.getOperation(lastElement.charAt(0));
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
      Deque<String> currentExpressionDeque = getExpressionDeque(this.currentExpression);
      while (!currentExpressionDeque.isEmpty()) {
        String lastElement = currentExpressionDeque.removeLast();
        if (getInputCategory(lastElement) == InputCategory.OPERAND) {
          return Integer.parseInt(lastElement);
        }
      }
    }
    throw new IllegalStateException("Cannot reach here");
  }
}
