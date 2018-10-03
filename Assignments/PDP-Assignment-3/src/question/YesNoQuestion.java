package question;

import java.util.Objects;

import question.bean.Result;
import question.bean.YesNoQuestionAnswer;
import util.Utils;

/**
 * This question represents a Yes or No Question. It extends {@link AbstractQuestion}. This question
 * can be answered in one of two ways: "Yes" or "No". The only valid answer to this type of question
 * is a “Yes” or “No”. If the given answer is not "Yes" or "No" it will considered as "Incorrect".
 */
public class YesNoQuestion extends AbstractQuestion {

  private final YesNoQuestionAnswer correctAnswer;

  /**
   * Constructs a YesNoQuestion Object using the given question text and correctAnswer.
   *
   * @param text          the text of the question
   * @param correctAnswer the correctAnswer of the question
   * @throws IllegalArgumentException if the given text or correctAnswer is null
   */
  public YesNoQuestion(String text, YesNoQuestionAnswer correctAnswer)
          throws IllegalArgumentException {

    super(text);
    this.performSanityCheckForInput(correctAnswer);
    this.correctAnswer = correctAnswer;
  }

  @Override
  public String[] getOptions() {
    return Utils.mapToStringArray(
            YesNoQuestionAnswer::getAnswerString, YesNoQuestionAnswer.values());
  }

  /**
   * Returns {@link Result#CORRECT} if the given answer is correct, else returns {@link
   * Result#INCORRECT} for all incorrect and invalid answers.
   *
   * @param answer answer {@link String} to evaluate
   * @return the {@link Result} based on the evaluation of the answer.
   * @throws IllegalArgumentException if the given answer is not "Yes" or "No"
   */
  @Override
  protected Result getResult(String answer) throws IllegalArgumentException {
    YesNoQuestionAnswer givenAnswer = YesNoQuestionAnswer.getYesNoQuestionAnswer(answer);
    return this.correctAnswer.equals(givenAnswer) ? Result.CORRECT : Result.INCORRECT;
  }

  /**
   * Determines whether this {@link YesNoQuestion} is equal to given {@link YesNoQuestion}.
   *
   * @param yesNoQuestion the YesNoQuestion object to which this YesNoQuestion must be compared
   * @return whether this {@link YesNoQuestion} is equal to given {@link YesNoQuestion}.
   */
  @Override
  protected boolean equalsYesNoQuestion(YesNoQuestion yesNoQuestion) {
    return this.text.equals(yesNoQuestion.text)
            && this.correctAnswer.equals(yesNoQuestion.correctAnswer);
  }

  /**
   * Returns the rank of this {@link YesNoQuestion}.
   *
   * @return the rank of this {@link YesNoQuestion}
   */
  @Override
  protected int getRankForOrdering() {
    return 101;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AbstractQuestion) {
      AbstractQuestion otherAbstractQuestion = (AbstractQuestion) other;
      return otherAbstractQuestion.equalsYesNoQuestion(this);
    }
    return false;
  }

  /**
   * Returns the hashCode which is generated based on the question text and correct answer.
   *
   * @return the hashCode which is generated based on the question text and correct answer
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.text, this.correctAnswer);
  }

  /**
   * Checks if the given {@link YesNoQuestionAnswer} is null.
   *
   * @param yesNoQuestionAnswer the {@link YesNoQuestionAnswer} object to check
   * @throws IllegalArgumentException if the given {@link YesNoQuestionAnswer} is null
   */
  private void performSanityCheckForInput(YesNoQuestionAnswer yesNoQuestionAnswer)
          throws IllegalArgumentException {

    if (Objects.isNull(yesNoQuestionAnswer)) {
      throw new IllegalArgumentException("correct answer cannot be null");
    }
  }
}
