package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategoryName;
import calculator.inputcategory.InputCategoryInterface;

public abstract class AbstractCalculator implements Calculator {

  protected static final Set<Character> CLEAR_CHARACTER_SET = Collections.singleton('C');
  protected static final Set<Character> EQUAL_TO_CHARACTER_SET = Collections.singleton('=');
  protected static final Set<Character> SUPPORTED_DIGITS = Collections.unmodifiableSet(
          new LinkedHashSet<>(
                  Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
                  )));
  protected static final Set<Character> SUPPORTED_OPERATOR = Collections.unmodifiableSet(
          new LinkedHashSet<>(Arrays.asList('+', '-', '*')));
  protected final List<String> currentExpression;
  protected final Set<InputCategoryName> anticipatedInputCategoryNames;
  protected final String result;

  protected AbstractCalculator(List<String> currentExpression,
                               Set<InputCategoryName> anticipatedInputCategoryNames,
                               String result) {

    this.currentExpression = currentExpression;
    this.anticipatedInputCategoryNames = anticipatedInputCategoryNames;
    this.result = result;
  }

  //todo check usages and need of abstract methods
  protected abstract Set<Character> getSupportedOperators();

  protected abstract Set<Character> getSupportedDigits();

  protected abstract List<InputCategoryInterface> getSupportedInputCategoryInterface();

  protected abstract Calculator getCalculatorInstance(
          List<String> expression,
          String result,
          Set<InputCategoryName> anticipatedInputCategoryNames);

  @Override
  public Calculator input(char input) throws IllegalArgumentException {
    InputCategoryInterface currentInputCategoryInterface = getInputCategoryInterface(input);

    isCurrentInputValid(input, currentInputCategoryInterface.getInputCategory());

    List<String> newExpression = currentInputCategoryInterface.performAction(input);

    String newResult = generateResultString(newExpression);

    Set<InputCategoryName> nextAnticipatedInputCategoryNames =
            currentInputCategoryInterface.getNextValidInputCategorySet();

    return getCalculatorInstance(newExpression, newResult, nextAnticipatedInputCategoryNames);
  }

  @Override
  public String getResult() {
    return this.result;
  }

  private void isCurrentInputValid(char input, InputCategoryName currentInputCategoryName)
          throws IllegalArgumentException {

    if (!this.anticipatedInputCategoryNames.contains(currentInputCategoryName)) {
      throw new IllegalArgumentException(String.format("Input: '%s' is illegal", input));
    }
  }

  private String generateResultString(List<String> expression) {
    return String.join("", expression);
  }

  private InputCategoryInterface getInputCategoryInterface(char input)
          throws IllegalArgumentException {

    for (InputCategoryInterface inputCategoryInterface : getSupportedInputCategoryInterface()) {
      if (inputCategoryInterface.belongToInputCategory(input)) {
        return inputCategoryInterface;
      }
    }
    throw new IllegalArgumentException(String.format("Input: '%s' is illegal", input));
  }
}
