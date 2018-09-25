package calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import calculator.bean.InputCategory;
import calculator.bean.Operation;
import calculator.bean.Pair;

import static calculator.bean.InputCategory.CLEAR;
import static calculator.bean.InputCategory.EQUAL_TO;
import static calculator.bean.InputCategory.OPERAND;
import static calculator.bean.InputCategory.OPERATOR;

public class SimpleCalculator extends AbstractCalculator {

  private static final Set<Character> CLEAR_INPUT_CHARACTER_SET = Collections.singleton('C');

  private static final Set<Character> EQUAL_TO_CHARACTER_SET = Collections.singleton('=');

  private static final Set<Character> SUPPORTED_DIGITS = Collections.unmodifiableSet(
          new LinkedHashSet<>(
                  Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
                  )));

  private static final Set<Character> SUPPORTED_OPERATOR = Collections.unmodifiableSet(
          new LinkedHashSet<>(Arrays.asList('+', '-', '*')));

  private static final Set<InputCategory> INITIAL_VALID_INPUT_CATEGORY_SET =
          Collections.unmodifiableSet(
                  new HashSet<>(Arrays.asList(OPERAND, CLEAR
                  )));

  private static final List<Pair<InputCategory, Set<Character>>> VALID_INPUT_CATEGORY_LIST =
          Collections.unmodifiableList(
                  new ArrayList<>(
                          Arrays.asList(
                                  Pair.of(OPERAND, SUPPORTED_DIGITS),
                                  Pair.of(OPERATOR, SUPPORTED_OPERATOR),
                                  Pair.of(CLEAR, CLEAR_INPUT_CHARACTER_SET),
                                  Pair.of(EQUAL_TO, EQUAL_TO_CHARACTER_SET)
                          )));

  protected SimpleCalculator(List<String> currentExpression,
                             Set<InputCategory> anticipatedInputCategorySet,
                             String result) {
    super(currentExpression, anticipatedInputCategorySet, result);
  }

  public SimpleCalculator() {
    this(Collections.emptyList(), INITIAL_VALID_INPUT_CATEGORY_SET, "");
  }

  protected Calculator getNewCalculatorInstance(List<String> newExpression, String newResult, Set<InputCategory> nextAnticipatedInputCategory) {
    return new SimpleCalculator(newExpression, nextAnticipatedInputCategory, newResult);
  }

  protected List<String> performInputCategoryAction(InputCategory inputCategory, char input) {
    switch (inputCategory) {
      case OPERAND:
        return performInputCategoryOperandAction(input);
      case OPERATOR:
        return performInputCategoryOperatorAction(input);
      case CLEAR:
        return performInputCategoryClearAction();
      case EQUAL_TO:
        return performInputCategoryEqualToAction();
      default:
        throw new IllegalArgumentException("InputCategoryAction not found for InputCategory: %s");
    }
  }

  protected List<String> performInputCategoryEqualToAction() {
    return evaluateExpression(this.currentExpression);
  }

  protected List<String> evaluateExpression(List<String> expression) {
    Deque<String> expressionDeque = getExpressionDeque(expression);
    int n1 = Integer.parseInt(expressionDeque.removeFirst());

    while (!expressionDeque.isEmpty()) {
      char operator = expressionDeque.removeFirst().charAt(0);
      int n2 = Integer.parseInt(expressionDeque.removeFirst());
      n1 = performOperation(operator, n1, n2);
    }
    return Collections.singletonList(String.valueOf(n1));
  }

  protected List<String> performInputCategoryClearAction() {
    Deque<String> expressionDeque = getExpressionDeque(this.currentExpression);
    expressionDeque.clear();
    return getExpressionList(expressionDeque);
  }

  protected List<String> performInputCategoryOperatorAction(char input) {
    Deque<String> currentExpressionDeque = getExpressionDeque(this.currentExpression);
    currentExpressionDeque.addLast(String.valueOf(input));
    return getExpressionList(currentExpressionDeque);
  }

  protected List<String> performInputCategoryOperandAction(char input) {
    Deque<String> expressionDeque = getExpressionDeque(this.currentExpression);

    String lastElement = expressionDeque.peekLast();
    if (Objects.nonNull(lastElement)) {

      InputCategory lastInputCategory = getInputCategory(lastElement);

      if (lastInputCategory == InputCategory.OPERAND) {
        lastElement = expressionDeque.pollLast();
        String newNumber = appendDigit(lastElement, input);
        expressionDeque.addLast(newNumber);
      } else if (lastInputCategory == InputCategory.OPERATOR) {
        expressionDeque.addLast(String.valueOf(input));
      } else {
        throw new IllegalStateException(String.format("Cannot Handle InputCategory: %s", lastInputCategory));
      }
    } else {
      expressionDeque.addLast(String.valueOf(input));
    }

    return getExpressionList(expressionDeque);
  }

  protected Set<Character> getSupportedInputs() {
    return Stream.of(SUPPORTED_DIGITS,
            SUPPORTED_OPERATOR,
            CLEAR_INPUT_CHARACTER_SET,
            EQUAL_TO_CHARACTER_SET)
            .flatMap(Set::stream)
            .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  protected Set<InputCategory> getValidInputCategorySetForOperator() {
    return Collections.singleton(OPERAND);
  }

  protected Set<InputCategory> getValidInputCategorySetForOperand() {
    Set<InputCategory> validInputCategory = new HashSet<>(Arrays.asList(OPERAND, OPERATOR));
    if (this.currentExpression.size() >= 2) {
      validInputCategory.add(InputCategory.EQUAL_TO);
    }
    return validInputCategory;
  }

  protected Set<InputCategory> getValidInputCategorySetForEqualTo() {
    return new HashSet<>(Arrays.asList(OPERATOR, EQUAL_TO));
  }

  protected Set<InputCategory> getInitialValidInputCategorySet() {
    return INITIAL_VALID_INPUT_CATEGORY_SET;
  }

  protected InputCategory getInputCategory(String element) throws IllegalArgumentException {
    return getInputCategory(element.charAt(element.length() - 1));
  }

  protected InputCategory getInputCategory(char input) throws IllegalArgumentException {
    for (Pair<InputCategory, Set<Character>> pair : VALID_INPUT_CATEGORY_LIST) {
      InputCategory inputCategory = pair.first;
      Set<Character> validCharactersForInputCategory = pair.second;
      if (validCharactersForInputCategory.contains(input)) {
        return inputCategory;
      }
    }
    throw new IllegalArgumentException(String.format("Cannot Identify the InputCategory for:%s", input));
  }

  protected Deque<String> getExpressionDeque(List<String> expressions) {
    return new LinkedList<>(expressions);
  }

  protected List<String> getExpressionList(Deque<String> expressionDeque) {
    return new LinkedList<>(expressionDeque);
  }

  private Set<InputCategory> getValidInputCategory(InputCategory inputCategory) {
    Set<InputCategory> nextValidInputCategory = new HashSet<>();

    switch (inputCategory) {
      case OPERAND:
        nextValidInputCategory.addAll(getValidInputCategorySetForOperand());
        break;
      case OPERATOR:
        nextValidInputCategory.addAll(getValidInputCategorySetForOperator());
        break;
      case EQUAL_TO:
        nextValidInputCategory.addAll(getValidInputCategorySetForEqualTo());
        break;
      case CLEAR:
        nextValidInputCategory.addAll(getInitialValidInputCategorySet());
        break;
      default:
        throw new IllegalArgumentException(String.format("Invalid InputCategory: %s", inputCategory));
    }

    nextValidInputCategory.add(CLEAR);
    return Collections.unmodifiableSet(nextValidInputCategory);
  }

  private void isCurrentInputValid(char input, InputCategory currentInputCategory) throws IllegalArgumentException {
    if (!this.anticipatedInputCategorySet.contains(currentInputCategory)) {
      throw new IllegalArgumentException(String.format("Input: '%s' is illegal", input));
    }
  }

  private String generateResultString(List<String> expression) {
    return String.join("", expression);
  }

  private String appendDigit(String numberString, char digitToAppend) {
    try {
      int currentNumber = Integer.parseInt(numberString);
      int newNumber = Math.addExact(Math.multiplyExact(currentNumber, 10), digitToAppend - '0');
      return String.valueOf(newNumber);
    } catch (ArithmeticException e) {
      throw new RuntimeException("Operand overflow: operand is greater than 32 bits", e);
    }
  }

  private int performOperation(char operatorSymbol, int n1, int n2) {
    try {
      Operation operation = Operation.getOperation(operatorSymbol);
      return operation.perform(n1, n2);
    } catch (ArithmeticException e) {
      return 0;
    }
  }
}
