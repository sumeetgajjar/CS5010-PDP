package questionnaire.bean;

public enum Result {

  CORRECT("Correct"),
  INCORRECT("Incorrect");

  private final String answerStatusString;

  Result(String answerStatusString) {
    this.answerStatusString = answerStatusString;
  }

  public String getAnswerStatusString() {
    return answerStatusString;
  }

  public static Result getAnswerStatus(String answerStatusString)
          throws IllegalArgumentException {

    for (Result result : Result.values()) {
      if (result.getAnswerStatusString().equals(answerStatusString)) {
        return result;
      }
    }

    throw new IllegalArgumentException(
            String.format("Invalid AnswerStatusString: %s", answerStatusString));
  }
}
