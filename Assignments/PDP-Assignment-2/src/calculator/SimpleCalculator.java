package calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import calculator.bean.InputCategory;

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

  private String inputSequence;
  private boolean firstInput;
  private InputCategory inputSequenceCategory;
  private String result;
  private int number1;
  private int number2;
  private int signal = 1;

  private SimpleCalculator(String inputSequence, boolean firstInput) {
    this.inputSequence = inputSequence;
    this.firstInput = firstInput;
  }

  public SimpleCalculator() {
    this("", true);
  }

  @Override
  public String getResult() {
    return this.result;
  }

  @Override
  public Calculator input(char input) throws IllegalArgumentException {
    isInputCharacterLegal(input);
    InputCategory currentInputCategory = getInputType(input);
    isCurrentInputValid(currentInputCategory);


    return null;
  }

  private void isCurrentInputValid(InputCategory currentInputCategory) throws IllegalArgumentException {
    Set<InputCategory> validInputCategorySet;
    if (firstInput) {
      validInputCategorySet = getInitialValidInputCategory();
    } else {
      validInputCategorySet = getNextValidInputCategory(this.inputSequenceCategory);
    }

    if (!validInputCategorySet.contains(currentInputCategory)) {
      throw new IllegalArgumentException("Invalid Input");
    }
  }

  private Set<InputCategory> getInitialValidInputCategory() {
    return Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList(InputCategory.OPERAND, InputCategory.CLEAR)));
  }

  private Set<InputCategory> getNextValidInputCategory(InputCategory inputCategory) {
    Set<InputCategory> nextLegalInputCategory;

    if (inputCategory == InputCategory.OPERAND) {
      nextLegalInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERAND, InputCategory.OPERATOR));
    } else if (inputCategory == InputCategory.OPERATOR) {
      nextLegalInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERAND));
    } else if (inputCategory == InputCategory.EQUAL_TO) {
      nextLegalInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERATOR));
    } else if (inputCategory == InputCategory.CLEAR) {
      nextLegalInputCategory = new HashSet<>(Arrays.asList(InputCategory.OPERAND));
    } else {
      throw new IllegalStateException("Invalid InputCategory");
    }

    nextLegalInputCategory.add(InputCategory.CLEAR);
    return Collections.unmodifiableSet(nextLegalInputCategory);
  }

  private InputCategory getInputType(char input) throws IllegalArgumentException {
    if (getSupportedDigits().contains(input)) {
      return InputCategory.OPERAND;
    } else if (getSupportedOperators().contains(input)) {
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
