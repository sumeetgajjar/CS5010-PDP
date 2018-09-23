package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import calculator.bean.InputType;

public class SimpleCalculator extends AbstractCalculator {

  private static final Set<Character> SUPPORTED_DIGITS =
          Collections.unmodifiableSet(new LinkedHashSet<>(
                  Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0')));

  private static final Set<Character> SUPPORTED_OPERATORS =
          Collections.unmodifiableSet(new LinkedHashSet<>(
                  Arrays.asList('+', '-', '*')
          ));

  private static final char CLEAR_INPUT_CHARACTER = 'C';
  private static final char EQUAL_TO_CHARACTER = '=';


  @Override
  public String getResult() {
    return "";
  }

  @Override
  public Calculator input(char input) throws IllegalArgumentException {
    isInputCharacterLegal(input);
    InputType inputType = getInputType(input);


    return null;
  }

  private InputType getInputType(char input) throws IllegalArgumentException {
    if (getSupportedDigits().contains(input)) {
      return InputType.OPERAND;
    } else if (getSupportedOperators().contains(input)) {
      return InputType.OPERATOR;
    } else if (CLEAR_INPUT_CHARACTER == input) {
      return InputType.CLEAR;
    } else if (EQUAL_TO_CHARACTER == input) {
      return InputType.EQUAL_TO;
    } else {
      throw new IllegalArgumentException(String.format("Cannot Identify the InputType for:%s", input));
    }
  }

  private void isInputCharacterLegal(char input) throws IllegalArgumentException {
    Set<Character> legalInputSet = getSupportedInputs();
    if (!legalInputSet.contains(input)) {
      throw new IllegalArgumentException(String.format("Input: %s is illegal", input));
    }
  }

  private Set<Character> getSupportedInputs() {
    return Stream.of(getSupportedDigits(),
            getSupportedOperators(),
            Collections.singleton(CLEAR_INPUT_CHARACTER),
            Collections.singleton(EQUAL_TO_CHARACTER))
            .flatMap(Set::stream)
            .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private Set<Character> getSupportedDigits() {
    return SUPPORTED_DIGITS;
  }

  private Set<Character> getSupportedOperators() {
    return SUPPORTED_OPERATORS;
  }
}
