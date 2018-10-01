package questionnaire.bean;

public enum Result {

  CORRECT("Correct"),
  INCORRECT("Incorrect");

  private final String resultString;

  Result(String resultString) {
    this.resultString = resultString;
  }

  public String getResultString() {
    return resultString;
  }

  public static Result getAnswerStatus(String answerStatusString)
          throws IllegalArgumentException {

    for (Result result : Result.values()) {
      if (result.getResultString().equals(answerStatusString)) {
        return result;
      }
    }

    throw new IllegalArgumentException(
            String.format("Invalid AnswerStatusString: %s", answerStatusString));
  }
}
