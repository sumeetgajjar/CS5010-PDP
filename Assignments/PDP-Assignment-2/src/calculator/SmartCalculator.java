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
                  Arrays.asList(InputCategory.CLEAR, InputCategory.EQUAL_TO, InputCategory.OPERATOR, InputCategory.OPERAND)
          ));

  private Operation lastOperation;
  private int lastOperand;


  public SmartCalculator(List<String> newExpression, Set<InputCategory> nextAnticipatedInputCategory, String result, Operation lastOperation, int lastOperand) {
    super(newExpression, nextAnticipatedInputCategory, result);
    this.lastOperand = lastOperand;
    this.lastOperation = lastOperation;
  }

  public SmartCalculator() {
    this(Collections.emptyList(), INITIAL_VALID_INPUT_CATEGORY_SET, "", Operation.ADD, 0);
  }

  @Override
  public Calculator input(char input) throws IllegalArgumentException {
    isInputCharacterLegal(input);

    InputCategory currentInputCategory = getInputCategory(input);

    isCurrentInputValid(input, currentInputCategory);

    List<String> newExpression;

    newExpression = getNewExpressionSequence(input, currentInputCategory);

    String newResult = generateResultString(newExpression);
    Set<InputCategory> nextAnticipatedInputCategory = getValidInputCategory(currentInputCategory);

    return new SmartCalculator(newExpression, nextAnticipatedInputCategory, newResult, lastOperation, lastOperand);
  }

  @Override
  protected List<String> performActionForInputCategoryOperator(char input) {
    Deque<String> currentExpressionDeque = getCurrentExpressionDeque();

    String lastElement = currentExpressionDeque.peekLast();
    if (Objects.nonNull(lastElement)) {
      InputCategory lastInputCategory = getInputCategory(lastElement.charAt(lastElement.length() - 1));
      if (lastInputCategory == InputCategory.OPERAND) {
        currentExpressionDeque.addLast(String.valueOf(input));
      } else if (lastInputCategory == InputCategory.OPERATOR) {
        currentExpressionDeque.removeLast();
        currentExpressionDeque.addLast(String.valueOf(input));
      } else {
        throw new IllegalStateException(String.format("Cannot Handle InputCategory: %s", lastInputCategory));
      }
    }

    return getListFromDeque(currentExpressionDeque);
  }

  @Override
  protected List<String> performActionForInputCategoryEqualTo() {
    Deque<String> currentExpressionDeque = getCurrentExpressionDeque();

    if (currentExpressionDeque.size() == 1) {
      int n1 = Integer.parseInt(currentExpressionDeque.removeFirst());
      int newResult = lastOperation.perform(n1, lastOperand);
      currentExpressionDeque.addFirst(String.valueOf(newResult));
    } else if (currentExpressionDeque.size() == 2) {
      int n1 = Integer.parseInt(currentExpressionDeque.removeFirst());
      char operator = currentExpressionDeque.removeFirst().charAt(0);
      int newResult = performOperation(operator, n1, n1);
      currentExpressionDeque.addFirst(String.valueOf(newResult));
      lastOperation = Operation.getOperation(operator);
      lastOperand = n1;
    } else {
      while (!currentExpressionDeque.isEmpty()) {
        int n1 = Integer.parseInt(currentExpressionDeque.removeFirst());
        char operator = currentExpressionDeque.removeFirst().charAt(0);
        int n2 = Integer.parseInt(currentExpressionDeque.removeFirst());

        int result = performOperation(operator, n1, n2);

        currentExpressionDeque.addFirst(String.valueOf(result));
        if (currentExpressionDeque.size() == 1) {
          lastOperation = Operation.getOperation(operator);
          lastOperand = n2;
          break;
        }
      }
    }

    return getListFromDeque(currentExpressionDeque);
  }

  protected Set<InputCategory> getValidInputCategory(InputCategory currentInputCategory) {
    Set<InputCategory> nextValidInputCategory;

    if (currentInputCategory == InputCategory.OPERAND) {
      nextValidInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERAND, InputCategory.OPERATOR, InputCategory.EQUAL_TO));
    } else if (currentInputCategory == InputCategory.OPERATOR) {
      nextValidInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERAND, InputCategory.OPERATOR, InputCategory.EQUAL_TO));
    } else if (currentInputCategory == InputCategory.EQUAL_TO) {
      nextValidInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERATOR, InputCategory.EQUAL_TO));
    } else if (currentInputCategory == InputCategory.CLEAR) {
      return getInitialValidInputCategory();
    } else {
      throw new IllegalArgumentException(String.format("Invalid InputCategory: %s", currentInputCategory));
    }

    nextValidInputCategory.add(InputCategory.CLEAR);
    return Collections.unmodifiableSet(nextValidInputCategory);
  }

  private Set<InputCategory> getInitialValidInputCategory() {
    return Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList(InputCategory.CLEAR, InputCategory.OPERATOR, InputCategory.OPERAND)
    ));
  }
}
