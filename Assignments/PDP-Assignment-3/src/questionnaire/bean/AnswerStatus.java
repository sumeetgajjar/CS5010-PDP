package questionnaire.bean;

public enum AnswerStatus {

  CORRECT("Correct"),
  INCORRECT("Incorrect");

  private final String answerStatusString;

  private AnswerStatus(String answerStatusString) {
    this.answerStatusString = answerStatusString;
  }

  public String getAnswerStatusString() {
    return answerStatusString;
  }
}
