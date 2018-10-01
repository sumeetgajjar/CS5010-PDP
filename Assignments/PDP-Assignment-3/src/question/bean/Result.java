package question.bean;

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
}
