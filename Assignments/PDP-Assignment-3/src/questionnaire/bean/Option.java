package questionnaire.bean;

public enum Option {

  ONE("1"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8");

  private final String optionString;

  Option(String optionString) {
    this.optionString = optionString;
  }

  public String getOptionString() {
    return optionString;
  }

  public static Option getOption(String optionString) throws IllegalArgumentException {
    for (Option option : Option.values()) {
      if (option.getOptionString().equals(optionString)) {
        return option;
      }
    }

    throw new IllegalArgumentException(String.format("Invalid OptionString: %s", optionString));
  }
}
