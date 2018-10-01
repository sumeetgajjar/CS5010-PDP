package question.bean;

public enum LikertScale {

  STRONGLY_AGREE("Strongly Agree"),
  AGREE("Agree"),
  NEITHER_AGREE_NOR_DISAGREE("Neither Agree nor Disagree"),
  DISAGREE("Disagree"),
  STRONGLY_DISAGREE("Strongly Disagree");

  private final String likertScaleString;

  LikertScale(String likertScaleString) {
    this.likertScaleString = likertScaleString;
  }

  public String getLikertScaleString() {
    return likertScaleString;
  }
}
