package question.bean;

public enum LikertScale {

  STRONGLY_AGREE("1", "Strongly Agree"),
  AGREE("2", "Agree"),
  NEITHER_AGREE_NOR_DISAGREE("3", "Neither Agree nor Disagree"),
  DISAGREE("4", "Disagree"),
  STRONGLY_DISAGREE("5", "Strongly Disagree");

  private final String optionNumber;
  private final String likerScaleString;

  LikertScale(String optionNumber, String likerScaleString) {
    this.optionNumber = optionNumber;
    this.likerScaleString = likerScaleString;
  }

  public String getOptionNumber() {
    return optionNumber;
  }

  public String getLikerScaleString() {
    return likerScaleString;
  }

  public static LikertScale getLikertScale(String optionNumber)
          throws IllegalArgumentException {

    for (LikertScale likertScale : LikertScale.values()) {
      if (likertScale.getOptionNumber().equals(optionNumber)) {
        return likertScale;
      }
    }

    throw new IllegalArgumentException(
            String.format("Invalid LikertScaleString: %s", optionNumber));
  }
}
