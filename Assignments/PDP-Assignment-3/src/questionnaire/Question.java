package questionnaire;

/**
 * This class represents a Question. A Question has its own text which can be retrieved using {@link
 * Question#getText()} method. For a given {@link Question}, in order to check whether a given
 * answer is correct or incorrect, {@link Question#evaluateAnswer(String)} method can be used.
 */
public interface Question extends Comparable<Question> {

  /**
   * Return the text of the Question as String.
   *
   * @return the text of the {@link Question} as {@link String}
   */
  String getText();

  /**
   * Return String "Correct" if the given answer is correct, else return "Incorrect".
   *
   * @param answer answer for the given {@link Question}
   * @return the {@link String} "Correct" if given answer is correct, else return {@link String}
   * "Incorrect"
   */
  String evaluateAnswer(String answer);

  //todo do we need getOptions
}
