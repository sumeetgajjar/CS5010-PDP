package question.bean;

/**
 * This enum represents the valid option choices an {@link question.YesNoQuestion} can have.
 */
public enum YesNoQuestionAnswer {

  YES("Yes"),
  NO("No");

  private final String answerString;

  YesNoQuestionAnswer(String answerString) {
    this.answerString = answerString;
  }

  /**
   * Returns the string associated with YesNoQuestionAnswer.
   *
   * @return the string associated with YesNoQuestionAnswer
   */
  public String getAnswerString() {
    return answerString;
  }

  /**
   * Returns a {@link YesNoQuestionAnswer} associated with the given answerString. The case of the
   * given answerString is ignored while associating it with {@link YesNoQuestionAnswer}.
   *
   * @param answerString answerString of which {@link YesNoQuestionAnswer} is to be found
   * @return YesNoQuestionAnswer associated with the given string
   * @throws IllegalArgumentException if no YesNoQuestionAnswer is associated with given string
   */
  public static YesNoQuestionAnswer getYesNoQuestionAnswer(String answerString)
          throws IllegalArgumentException {

    for (YesNoQuestionAnswer yesNoQuestionAnswer : YesNoQuestionAnswer.values()) {
      if (yesNoQuestionAnswer.getAnswerString().equalsIgnoreCase(answerString)) {
        return yesNoQuestionAnswer;
      }
    }
    throw new IllegalArgumentException(String.format("Invalid answerString: %s", answerString));
  }
}
