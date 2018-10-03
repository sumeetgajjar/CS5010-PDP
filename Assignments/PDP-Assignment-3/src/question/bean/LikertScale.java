package question.bean;

/**
 * This enum represents the valid Option choices an {@link question.LikertQuestion} can have.
 */
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

  /**
   * Returns the string associated with LikertScale option choice.
   *
   * @return the string associated with LikertScale option choice
   */
  public String getLikertScaleString() {
    return likertScaleString;
  }
}
