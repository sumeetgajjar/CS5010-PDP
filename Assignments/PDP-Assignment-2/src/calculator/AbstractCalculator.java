package calculator;

import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;
import calculator.bean.Operator;

public abstract class AbstractCalculator implements Calculator {


  protected static final char CLEAR_INPUT_CHARACTER = 'C';
  protected static final char EQUAL_TO_CHARACTER = '=';

  protected abstract Set<Character> getSupportedInputs();

  protected abstract Set<Character> getSupportedDigits();

  protected abstract Set<Character> getSupportedOperatorSymbols();

  protected InputCategory getInputType(char input) throws IllegalArgumentException {
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

  protected void isInputCharacterLegal(char input) throws IllegalArgumentException {
    Set<Character> legalInputSet = getSupportedInputs();
    if (!legalInputSet.contains(input)) {
      throw new IllegalArgumentException(String.format("Input: '%s' is illegal", input));
    }
  }

  protected String generateResultString(List<String> expression) {
    return String.join("", expression);
  }

  protected int performOperation(char operatorSymbol, int n1, int n2) {
    try {
      Operator operator = Operator.getOperator(operatorSymbol);
      return operator.performOperation(n1, n2);
    } catch (ArithmeticException e) {
      return 0;
    }
  }

  protected String appendDigit(String numberString, char digitToAppend) {
    try {
      int currentNumber = Integer.parseInt(numberString);
      int newNumber = Math.addExact(Math.multiplyExact(currentNumber, 10), digitToAppend - '0');
      return String.valueOf(newNumber);
    } catch (ArithmeticException e) {
      throw new RuntimeException("Operand overflow: operand is greater than 32 bits", e);
    }
  }
}
