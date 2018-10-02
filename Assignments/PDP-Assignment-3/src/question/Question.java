package question;

/**
 * This class represents a Question. A Question has its own text which can be retrieved using {@link
 * Question#getText()} method. For a given {@link Question}, in order to check whether a given
 * answer is correct or incorrect, {@link Question#evaluateAnswer(String)} method can be used. In
 * order to get the answer choices for the {@link Question} <code>{@link
 * Question#getOptions()}</code> method can be used.
 */
public interface Question extends Comparable<Question> {

  /**
   * Returns the text of the Question as String.
   *
   * @return the text of the {@link Question} as {@link String}
   */
  String getText();

  /**
   * Returns the String "Correct" if the given answer is correct, else returns "Incorrect". For all
   * invalid answers it will return "Incorrect".
   *
   * @param answer answer for the given {@link Question}
   * @return the {@link String} "Correct" if given answer is correct, else return {@link String}
   * "Incorrect" for all incorrect and invalid answers
   */
  String evaluateAnswer(String answer);

  /**
   * Returns the options associated with the question.
   *
   * @return the option associated with the question as array of {@link String}
   */
  String[] getOptions();
}
