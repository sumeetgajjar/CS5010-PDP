package question.bean;

/**
 * This enum represents the Types of Result a Question can have for the given answer.
 */
public enum Result {

  /**
   * Represents the answer is correct.
   */
  CORRECT("Correct"),

  /**
   * Represents the answer is incorrect.
   */
  INCORRECT("Incorrect");

  private final String resultString;

  Result(String resultString) {
    this.resultString = resultString;
  }

  /**
   * Returns the string value associated with the type.
   *
   * @return the string value associated with the type
   */
  public String getResultString() {
    return resultString;
  }
}
