package question.bean;

/**
 * This class represents the legal NumericChoices an {@link question.Question}n can have.
 */
public enum NumericChoice {

  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8);

  private final int value;

  NumericChoice(int value) {
    this.value = value;
  }

  /**
   * Returns the integer value associated with the choice.
   *
   * @return the integer value associated with the choice
   */
  public int getValue() {
    return value;
  }

  /**
   * Returns the String of the integer value associated with the choice.
   *
   * @return the String of the integer value associated with the choice
   */
  public String getStringValue() {
    return String.valueOf(this.value);
  }

  /**
   * Returns a {@link NumericChoice} associated with the given stringValue.
   *
   * @param stringValue string of which NumericChoice is to be found
   * @return the NumericChoice associated with the given stringValue
   * @throws IllegalArgumentException if no NumericChoice is associated with the given stringValue
   */
  public static NumericChoice getChoice(String stringValue) throws IllegalArgumentException {
    for (NumericChoice numericChoice : NumericChoice.values()) {
      if (numericChoice.getStringValue().equals(stringValue)) {
        return numericChoice;
      }
    }

    throw new IllegalArgumentException(String.format("Invalid NumericChoice value: %s", stringValue));
  }

  /**
   * Returns a {@link NumericChoice} associated with the given integer value.
   *
   * @param value integer value of which NumericChoice is to be found
   * @return the NumericChoice associated with the given stringValue
   * @throws IllegalArgumentException if no NumericChoice is associated with the given integerValue
   */
  public static NumericChoice getChoice(int value) throws IllegalArgumentException {
    for (NumericChoice numericChoice : NumericChoice.values()) {
      if (numericChoice.getValue() == value) {
        return numericChoice;
      }
    }

    throw new IllegalArgumentException(String.format("Invalid NumericChoice value: %s", value));
  }
}
