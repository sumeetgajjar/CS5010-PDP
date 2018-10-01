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

  public static YesNoQuestionAnswer getYesNoQuestionAnswer(String answerString)
          throws IllegalArgumentException {

    for (YesNoQuestionAnswer yesNoQuestionAnswer : YesNoQuestionAnswer.values()) {
      if (yesNoQuestionAnswer.getAnswerString().equals(answerString)) {
        return yesNoQuestionAnswer;
      }
    }
    throw new IllegalArgumentException(String.format("Invalid answerString: %s", answerString));
  }
}
