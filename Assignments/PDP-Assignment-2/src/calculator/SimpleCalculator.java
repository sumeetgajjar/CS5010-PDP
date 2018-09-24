package calculator;

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
import calculator.bean.Operator;

public class SimpleCalculator extends AbstractCalculator {

  private static final Set<Character> SUPPORTED_DIGITS =
          Collections.unmodifiableSet(new LinkedHashSet<>(
                  Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0')));

  private static final Set<Operator> SUPPORTED_OPERATORS =
          Collections.unmodifiableSet(new LinkedHashSet<>(
                  Arrays.asList(Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY)
          ));

  private static final char CLEAR_INPUT_CHARACTER = 'C';
  private static final char EQUAL_TO_CHARACTER = '=';
  private static final Set<InputCategory> INITIAL_VALID_INPUT_CATEGORY_SET =
          Collections.unmodifiableSet(
                  new HashSet<>(Arrays.asList(InputCategory.OPERAND, InputCategory.CLEAR)));

  private final Set<InputCategory> anticipatedInputCategorySet;
  private final List<String> currentExpression;
  private final String result;

  public SimpleCalculator() {
    anticipatedInputCategorySet = this.getInitialValidInputCategory();
    currentExpression = new LinkedList<>();
    result = "";
  }

  @Override
  public String getResult() {
    return this.result;
  }

  @Override
  public Calculator input(char input) throws IllegalArgumentException {
    isInputCharacterLegal(input);

    InputCategory currentInputCategory = getInputType(input);

    isCurrentInputValid(input, currentInputCategory);

    List<String> modifiedExpression;

    if (currentInputCategory == InputCategory.CLEAR) {
      modifiedExpression = performActionForInputCategoryClear();
    } else if (currentInputCategory == InputCategory.OPERAND) {
      modifiedExpression = performActionForInputCategoryOperand(input);
    } else if (currentInputCategory == InputCategory.OPERATOR) {
      modifiedExpression = performActionForInputCategoryOperator(input);
    } else if (currentInputCategory == InputCategory.EQUAL_TO) {
      modifiedExpression = performActionForInputCategoryEqualTo();
    }

    this.result = generateResultString();
    this.anticipatedInputCategorySet = getValidInputCategory(currentInputCategory);

    //todo: decide on what is suppose to be done
    return this;
  }

  private List<String> performActionForInputCategoryOperand(char input) {
    Deque<String> deque = new LinkedList<>(this.currentExpression);

    String lastElement = deque.peekLast();
    if (Objects.nonNull(lastElement)) {

      char lastInput = lastElement.charAt(lastElement.length() - 1);
      InputCategory lastInputCategory = getInputType(lastInput);

      if (lastInputCategory == InputCategory.OPERAND) {
        lastElement = deque.pollLast();
        String newNumber = appendDigit(lastElement, input);
        deque.addLast(newNumber);
      } else if (lastInputCategory == InputCategory.OPERATOR) {
        deque.addLast(String.valueOf(input));
      }
    } else {
      deque.addLast(String.valueOf(input));
    }

    return new LinkedList<>(deque);
  }

  private List<String> performActionForInputCategoryOperator(char input) {
    Deque<String> deque = new LinkedList<>(this.currentExpression);
    deque.addLast(String.valueOf(input));
    return new LinkedList<>(deque);
  }

  private void performActionForInputCategoryEqualTo() {
    while (!this.currentExpression.isEmpty()) {
      int n1 = Integer.parseInt(this.currentExpression.removeFirst());
      char operator = this.currentExpression.removeFirst().charAt(0);
      int n2 = Integer.parseInt(this.currentExpression.removeFirst());

      int result = performOperation(operator, n1, n2);

      this.currentExpression.addFirst(String.valueOf(result));
      if (this.currentExpression.size() == 1) {
        break;
      }
    }
  }

  private List<String> performActionForInputCategoryClear() {
    LinkedList<String> modifiedExpression = new LinkedList<>(this.currentExpression);
    modifiedExpression.clear();
    return modifiedExpression;
  }

  private int performOperation(char operatorSymbol, int n1, int n2) {
    try {
      Operator operator = Operator.getOperator(operatorSymbol);
      return operator.performOperation(n1, n2);
    } catch (ArithmeticException e) {
      return 0;
    }
  }

  private String generateResultString() {
    return String.join("", this.currentExpression);
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

  private void isCurrentInputValid(char input, InputCategory currentInputCategory) throws IllegalArgumentException {
    if (!this.anticipatedInputCategorySet.contains(currentInputCategory)) {
      throw new IllegalArgumentException(String.format("Input: '%s' is illegal", input));
    }
  }

  private Set<InputCategory> getInitialValidInputCategory() {
    return INITIAL_VALID_INPUT_CATEGORY_SET;
  }

  private Set<InputCategory> getValidInputCategory(InputCategory currentInputCategory) {
    Set<InputCategory> nextValidInputCategory;

    if (currentInputCategory == InputCategory.OPERAND) {
      nextValidInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERAND, InputCategory.OPERATOR));
      if (this.currentExpression.size() >= 2) {
        nextValidInputCategory.add(InputCategory.EQUAL_TO);
      }
    } else if (currentInputCategory == InputCategory.OPERATOR) {
      nextValidInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERAND));
    } else if (currentInputCategory == InputCategory.EQUAL_TO) {
      nextValidInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERATOR));
    } else if (currentInputCategory == InputCategory.CLEAR) {
      nextValidInputCategory = getInitialValidInputCategory();
    } else {
      throw new IllegalArgumentException(String.format("Invalid InputCategory: %s", currentInputCategory));
    }

    nextValidInputCategory.add(InputCategory.CLEAR);
    return Collections.unmodifiableSet(nextValidInputCategory);
  }

  private InputCategory getInputType(char input) throws IllegalArgumentException {
    if (getSupportedDigits().contains(input)) {
      return InputCategory.OPERAND;
    } else if (getSupportedOperatorSymbols().contains(input)) {
      return InputCategory.OPERATOR;
    } else if (CLEAR_INPUT_CHARACTER == input) {
      return InputCategory.CLEAR;
    } else if (EQUAL_TO_CHARACTER == input) {
      return InputCategory.EQUAL_TO;
    } else {
      throw new IllegalArgumentException(String.format("Cannot Identify the InputCategory for:%s", input));
    }
  }

  private void isInputCharacterLegal(char input) throws IllegalArgumentException {
    Set<Character> legalInputSet = getSupportedInputs();
    if (!legalInputSet.contains(input)) {
      throw new IllegalArgumentException(String.format("Input: '%s' is illegal", input));
    }
  }

  private Set<Character> getSupportedInputs() {
    return Stream.of(getSupportedDigits(),
            getSupportedOperatorSymbols(),
            Collections.singleton(CLEAR_INPUT_CHARACTER),
            Collections.singleton(EQUAL_TO_CHARACTER))
            .flatMap(Set::stream)
            .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private Set<Character> getSupportedDigits() {
    return SUPPORTED_DIGITS;
  }

  private Set<Character> getSupportedOperatorSymbols() {
    return SUPPORTED_OPERATORS.stream()
            .map(Operator::getSymbol)
            .collect(Collectors.toCollection(LinkedHashSet::new));
  }
}
