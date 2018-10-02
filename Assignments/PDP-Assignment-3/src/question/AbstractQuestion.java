package question;

import question.bean.Result;
import util.Utils;

/**
 * This class provides a skeletal implementation of the {@link Question} interface. It minimize the
 * effort required to implement this interface. It has following protected fields.
 * <ul>
 * <li><code>text</code> : It stores the text of the question as {@link String}</li>
 * </ul>
 *
 * <p>To implement a {@link Question} the programmer needs to extend this class and provide
 * implementations for following methods.
 * <ul>
 * <li>eval</li>
 * <li>getRankForOrdering</li>
 * </ul>
 *
 * <p>The programmer should also provide a constructor in the derived class whose signature matches
 * the signature of {@link AbstractQuestion#AbstractQuestion(String)}.
 */
public abstract class AbstractQuestion implements Question {

  /**
   * protected field to store the question text.
   */
  protected final String text;

  /**
   * Protected Constructor for invocation by subclass constructors. It initializes the fields to
   * given values. It throws an {@link IllegalArgumentException} if the given text is null or
   * empty.
   *
   * @param text text of the question
   * @throws IllegalArgumentException if the given text is null or empty
   */
  protected AbstractQuestion(String text) throws IllegalArgumentException {
    this.performSanityCheckForInput(text);
    this.text = text;
  }

  /**
   * Returns the {@link Result} based on the evaluation of the answer.
   *
   * @param answer answer {@link String} to evaluate
   * @return the {@link Result} of the evaluation
   * @throws IllegalArgumentException if the given answer is illegal
   */
  protected abstract Result eval(String answer) throws IllegalArgumentException;

  /**
   * Returns the rank for Ordering. Given 2 implementations of the {@link AbstractQuestion}, the one
   * with lower rank should be ranked first.
   *
   * @return the rank for ordering
   */
  protected abstract int getRankForOrdering();

  @Override
  public String getText() {
    return this.text;
  }

  /**
   * Returns the String "Correct" if the given answer is correct. For all invalid or incorrect
   * answers the return value will be "Incorrect".
   *
   * @param answer answer for the given {@link Question}
   */
  @Override
  public String evaluateAnswer(String answer) {
    if (Utils.isStringNotSet(answer)) {
      return Result.INCORRECT.getResultString();
    }

    try {
      Result result = eval(answer);
      return result.getResultString();
    } catch (IllegalArgumentException e) {
      return Result.INCORRECT.getResultString();
    }
  }

  /**
   * Compares the current {@link Question} with the given {@link Question}. Given 2 implementations
   * of {@link AbstractQuestion}, the one with lower rank is lower than the one with the higher
   * rank. If both Objects have same rank then, the
   *
   * @param otherQuestion the question to be compared
   * @return asd asd asd
   */
  @Override
  public int compareTo(Question otherQuestion) {
    if (otherQuestion instanceof AbstractQuestion) {
      AbstractQuestion otherAbstractQuestion = (AbstractQuestion) otherQuestion;
      int diff = this.getRankForOrdering() - otherAbstractQuestion.getRankForOrdering();
      if (diff == 0) {
        return this.text.compareTo(otherAbstractQuestion.text);
      } else {
        return diff;
      }
    }
    return 1;
  }

  /**
   * Checks if the given String is not null and not empty.
   *
   * @param text String to be checked
   * @throws IllegalArgumentException if the given string is null or empty
   */
  protected void performSanityCheckForInput(String text) throws IllegalArgumentException {
    if (Utils.isStringNotSet(text)) {
      throw new IllegalArgumentException(String.format("Invalid question text: %s", text));
    }
  }

  /**
   * Determines whether this {@link Question} is equal to given {@link YesNoQuestion}.
   *
   * @param yesNoQuestion the YesNoQuestion object to which this Question must be compared
   * @return false by default, subclasses may override
   */
  protected boolean equalsYesNoQuestion(YesNoQuestion yesNoQuestion) {
    return false;
  }

  /**
   * Determines whether this {@link Question} is equal to given {@link LikertQuestion}.
   *
   * @param likertQuestion the LikertQuestion object to which this Question must be compared
   * @return false by default, subclasses may override
   */
  protected boolean equalsLikertQuestion(LikertQuestion likertQuestion) {
    return false;
  }

  /**
   * Determines whether this {@link Question} is equal to given {@link MultipleChoiceQuestion}.
   *
   * @param multipleChoiceQuestion the MultipleChoiceQuestion object to which this Question must be
   *                               compared
   * @return false by default, subclasses may override
   */
  protected boolean equalsMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
    return false;
  }

  /**
   * Determines whether this {@link Question} is equal to given {@link MultipleAnswersQuestion}.
   *
   * @param multipleAnswersQuestion the MultipleAnswersQuestion object to which this Question must
   *                                be compared
   * @return false by default, subclasses may override
   */
  protected boolean equalsMultipleAnswersQuestion(MultipleAnswersQuestion multipleAnswersQuestion) {
    return false;
  }
}
