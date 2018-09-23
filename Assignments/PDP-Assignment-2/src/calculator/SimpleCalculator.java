package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleCalculator extends AbstractCalculator {

  private static final List<Character> SUPPORTED_DIGITS =
          Collections.unmodifiableList(
                  Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'));

  private static final List<Character> SUPPORTED_OPERATORS =
          Collections.unmodifiableList(Arrays.asList('+', '-', '*'));

  private static final List<Character> SUPPORTED_MISC_CHARACTERS =
          Collections.unmodifiableList(Arrays.asList('=', 'C'));


  @Override
  public String getResult() {
    return "";
  }

  @Override
  public Calculator input(char input) throws IllegalArgumentException {
    isInputCharacterLegal(input);

//    InputType inputType = getInputType(input);


    return new SimpleCalculator();
  }

  private void isInputCharacterLegal(char input) throws IllegalArgumentException {
    boolean isInputLegal = false;

    List<Character> legalInputs = getSupportedInputs();

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

  private List<Character> getSupportedInputs() {
    return Stream.of(getSupportedDigits(), getSupportedOperators(), getSupportedMiscCharacters())
            .flatMap(List::stream)
            .collect(Collectors.toList());
  }

  private List<Character> getSupportedDigits() {
    return SUPPORTED_DIGITS;
  }

  private List<Character> getSupportedOperators() {
    return SUPPORTED_OPERATORS;
  }

  private List<Character> getSupportedMiscCharacters() {
    return SUPPORTED_MISC_CHARACTERS;
  }
}
