package questionnaire.bean;

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

  public int getValue() {
    return value;
  }

  public String getStringValue() {
    return String.valueOf(this.value);
  }

  public static NumericChoice getChoice(String stringValue) throws IllegalArgumentException {
    for (NumericChoice numericChoice : NumericChoice.values()) {
      if (numericChoice.getStringValue().equals(stringValue)) {
        return numericChoice;
      }
    }

    throw new IllegalArgumentException(String.format("Invalid NumericChoice value: %s", stringValue));
  }

  public static NumericChoice getChoice(int value) throws IllegalArgumentException {
    for (NumericChoice numericChoice : NumericChoice.values()) {
      if (numericChoice.getValue() == value) {
        return numericChoice;
      }
    }

    throw new IllegalArgumentException(String.format("Invalid NumericChoice value: %s", value));
  }
}
