package calculator;

import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;

public abstract class AbstractCalculator implements Calculator {

  protected final List<String> currentExpression;
  protected final Set<InputCategory> anticipatedInputCategorySet;
  protected final String result;

  protected AbstractCalculator(List<String> currentExpression, Set<InputCategory> anticipatedInputCategorySet, String result) {
    this.currentExpression = currentExpression;
    this.anticipatedInputCategorySet = anticipatedInputCategorySet;
    this.result = result;
  }

  @Override
  public String getResult() {
    return this.result;
  }

  @Override
  public Calculator input(char input) throws IllegalArgumentException {
    isInputCharacterLegal(input);
    InputCategory currentInputCategory = getInputCategory(input);
    isCurrentInputValid(input, currentInputCategory);
    List<String> newExpression = performInputCategoryAction(currentInputCategory, input);
    String newResult = generateResultString(newExpression);
    Set<InputCategory> nextAnticipatedInputCategory = getValidInputCategory(currentInputCategory);
    return getNewCalculatorInstance(newExpression, newResult, nextAnticipatedInputCategory);
  }
}
