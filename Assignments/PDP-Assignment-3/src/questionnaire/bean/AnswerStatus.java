package questionnaire.bean;

public enum AnswerStatus {

  CORRECT("Correct"),
  INCORRECT("Incorrect");

  private final String answerStatusString;

  AnswerStatus(String answerStatusString) {
    this.answerStatusString = answerStatusString;
  }

  public String getAnswerStatusString() {
    return answerStatusString;
  }

  public static AnswerStatus getAnswerStatus(String answerStatusString)
          throws IllegalArgumentException {

    for (AnswerStatus answerStatus : AnswerStatus.values()) {
      if (answerStatus.getAnswerStatusString().equals(answerStatusString)) {
        return answerStatus;
      }
    }

    throw new IllegalArgumentException(
            String.format("Invalid AnswerStatusString: %s", answerStatusString));
  }
}
