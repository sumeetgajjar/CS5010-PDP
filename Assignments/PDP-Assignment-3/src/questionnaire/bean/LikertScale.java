package questionnaire.bean;

public enum LikertScale {

  STRONGLY_AGREE("Strongly Agree"),
  AGREE("Agree"),
  NEITHER_AGREE_NOR_DISAGREE("Neither Agree nor Disagree"),
  DISAGREE("Disagree"),
  STRONGLY_DISAGREE("Strongly Disagree");

  private final String likerScaleString;

  LikertScale(String likerScaleString) {
    this.likerScaleString = likerScaleString;
  }

  public String getLikerScaleString() {
    return likerScaleString;
  }

  public static LikertScale getLikertScale(String likerScaleString)
          throws IllegalArgumentException {

    for (LikertScale likertScale : LikertScale.values()) {
      if (likertScale.getLikerScaleString().equals(likerScaleString)) {
        return likertScale;
      }
    }

    throw new IllegalArgumentException(
            String.format("Invalid LikertScaleString: %s", likerScaleString));
  }
}
