package questionnaire.bean;

public enum OptionNumber {

  ONE("1"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8");

  private final String optionString;

  OptionNumber(String optionString) {
    this.optionString = optionString;
  }

  public String getOptionString() {
    return optionString;
  }

  public static OptionNumber getOption(String optionString) throws IllegalArgumentException {
    for (OptionNumber optionNumber : OptionNumber.values()) {
      if (optionNumber.getOptionString().equals(optionString)) {
        return optionNumber;
      }
    }

    throw new IllegalArgumentException(String.format("Invalid OptionString: %s", optionString));
  }
}
