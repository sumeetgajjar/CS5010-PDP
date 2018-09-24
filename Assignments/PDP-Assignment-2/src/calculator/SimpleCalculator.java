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

  private final String inputSequence;

  private SimpleCalculator(String inputSequence) {
    this.inputSequence = inputSequence;
  }

  public SimpleCalculator() {
    this("");
  }

  @Override
  public String getResult() {
    return "";
  }

  @Override
  public Calculator input(char input) throws IllegalArgumentException {
    isInputCharacterLegal(input);
    InputCategory paramInputCategory = getInputType(input);
    isInputCharacterValid(paramInputCategory);
    String newInputSequence = inputSequence + input;

    return null;
  }

  private void isInputCharacterValid(InputCategory paramInputCategory) throws IllegalArgumentException {
    if (inputSequence.isEmpty()) {
      if (paramInputCategory == InputCategory.EQUAL_TO || paramInputCategory == InputCategory.OPERATOR) {
        throw new IllegalArgumentException("Invalid Input");
      }
    } else {
      InputCategory currentInputCategory = getInputType(inputSequence.charAt(inputSequence.length() - 1));
      Set<InputCategory> illegalInputCategorySet = getIllegalInputCategory(currentInputCategory);
      if (illegalInputCategorySet.contains(paramInputCategory)) {
        throw new IllegalArgumentException("Invalid Input");
      }
    }
  }

  private Set<InputCategory> getIllegalInputCategory(InputCategory inputCategory) {
    switch (inputCategory) {
      case OPERAND:
        return Collections.emptySet();
      case OPERATOR:
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(InputCategory.OPERATOR, InputCategory.EQUAL_TO)));
      case EQUAL_TO:
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(InputCategory.EQUAL_TO, InputCategory.OPERAND)));
      case CLEAR:
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(InputCategory.OPERATOR, InputCategory.EQUAL_TO)));
      default:
        throw new IllegalStateException("Invalid InputCategory");
    }
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
