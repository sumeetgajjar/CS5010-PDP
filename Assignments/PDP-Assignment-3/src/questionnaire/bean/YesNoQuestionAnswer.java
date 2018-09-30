package questionnaire.bean;

public enum YesNoQuestionAnswer {

  YES("Yes"),
  NO("No");

  private final String answerString;

  YesNoQuestionAnswer(String answerString) {
    this.answerString = answerString;
  }

  public String getAnswerString() {
    return answerString;
  }
}
