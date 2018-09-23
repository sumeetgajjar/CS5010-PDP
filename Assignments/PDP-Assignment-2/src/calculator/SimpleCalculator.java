package calculator;

import calculator.bean.InputType;

public class SimpleCalculator extends AbstractCalculator {

  private int firstOperand;
  private int secondOperand;
  private String result = "";


  @Override
  public Calculator input(char input) throws IllegalArgumentException {
    isInputCharacterLegal(input);

    InputType inputType = getInputType(input);


    return new SimpleCalculator();
  }

  private void isInputCharacterLegal(char input) throws IllegalArgumentException {
    boolean isInputLegal = false;

    char[] legalInputs = getSupportedInputs();
    for (char legalInput : legalInputs) {
      if (input == legalInput) {
        isInputLegal = true;
        break;
      }
    }

    if (!isInputLegal) {
      throw new IllegalArgumentException(String.format("Input: %s is illegal", input));
    }
  }

  private char[] getSupportedInputs() {
    return new char[]{
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            '+', '-', '*',
            'C'
    };
  }

  @Override
  public String getResult() {
    return "";
  }
}
