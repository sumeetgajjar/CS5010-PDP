package question;

import java.util.Arrays;
import java.util.Objects;

import question.bean.NumericChoice;
import question.bean.Option;
import util.Utils;

/**
 * This class represents a Multiple Choice Question. It extends {@link MultipleAnswersQuestion}.
 * This offers several options, only one of which is correct. A {@link MultipleChoiceQuestion} may
 * have at least 3 options, but no more than 8. If this conditions are not satisfied it will throw
 * {@link IllegalArgumentException} while creating its object. An answer can be entered as one of
 * the option numbers in a string. For example, “1”, “3”, etc. Option numbers start at 1. If the
 * answer string contains more than one option then the answer will be evaluated as "Incorrect".
 *
 * <p>A correct answer string cannot have leading spaces. However trailing spaces are allowed and
 * are ignored while evaluating the answer.
 */
public class MultipleChoiceQuestion extends MultipleAnswersQuestion {

  private static final int MINIMUM_OPTIONS = 3;
  private static final int MAXIMUM_OPTIONS = 8;

  /**
   * Constructs a {@link MultipleChoiceQuestion} with the given params.
   *
   * @param text                 the text of the question
   * @param correctOptionsString the correctOptionString
   * @param options              the options for the question
   * @throws IllegalArgumentException if input params does not pass the Sanity tests. Sanity tests
   *                                  fall in following categories:
   *                                  <ul>
   *                                  <li>Question text is null or empty</li>
   *                                  <li>correctOptionsString is null or empty</li>
   *                                  <li>if correctOptionsString contains some invalid
   *                                  character</li>
   *                                  <li>Null is passed for Options or any option in the specified
   *                                  Options is null</li>
   *                                  <li>Options size is less than 3</li>
   *                                  <li>Options size is greater than 8</li>
   *                                  <li>Duplicate Options exists</li>
   *                                  <li>correctOptionsString contains more than 1 correct
   *                                  option</li>
   *                                  <li>If given correct Option is not present in all
   *                                  Options</li>
   *                                  </ul>
   */
  public MultipleChoiceQuestion(String text, String correctOptionsString, Option[] options)
          throws IllegalArgumentException {

    super(text, correctOptionsString, options);
    this.performSanityCheckForInput(this.correctNumericChoices);
  }

  /**
   * Checks if the numericChoices size is 1 or not.
   *
   * @param correctNumericChoices the numericChoices to check
   * @throws IllegalArgumentException if the numericChoices size is not equal to 1
   */
  private void performSanityCheckForInput(NumericChoice[] correctNumericChoices)
          throws IllegalArgumentException {

    if (correctNumericChoices.length != 1) {
      throw new IllegalArgumentException("cannot have more than 1 correct option");
    }
  }

  @Override
  protected int getMaximumOptionThreshold() {
    return MAXIMUM_OPTIONS;
  }

  @Override
  protected int getMinimumOptionThreshold() {
    return MINIMUM_OPTIONS;
  }

  /**
   * Determines whether this {@link MultipleChoiceQuestion} is equal to given {@link
   * MultipleChoiceQuestion}.
   *
   * @param multipleChoiceQuestion the MultipleChoiceQuestion object to which this Question must be
   *                               compared
   * @return whether this {@link MultipleChoiceQuestion} is equal to given {@link
   * MultipleChoiceQuestion}.
   */
  @Override
  protected boolean equalsMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
    return this.text.equals(multipleChoiceQuestion.text)
            && Arrays.equals(this.options, multipleChoiceQuestion.options)
            && this.correctNumericChoices[0]
            .equals(multipleChoiceQuestion.correctNumericChoices[0]);
  }

  /**
   * Determines whether this {@link MultipleChoiceQuestion} is equal to given {@link
   * MultipleAnswersQuestion}.
   *
   * @param multipleAnswersQuestion the MultipleAnswersQuestion object to which this Question must
   *                                be compared
   */
  @Override
  protected boolean equalsMultipleAnswersQuestion(MultipleAnswersQuestion multipleAnswersQuestion) {
    return false; //by default "this" Question is not equal to a MultipleAnswersQuestion
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AbstractQuestion) {
      AbstractQuestion otherAbstractQuestion = (AbstractQuestion) other;
      return otherAbstractQuestion.equalsMultipleChoiceQuestion(this);
    }
    return false;
  }

  /**
   * Returns the hashCode for this {@link MultipleChoiceQuestion}. Hashcode is generated based on
   * the question text, correct options and all options for this question.
   *
   * @return the hashCode for this {@link MultipleChoiceQuestion}
   */
  @Override
  public int hashCode() {
    /* adding String "MultipleChoiceQuestion"
     * to reduce collisions with MultipleAnswersQuestion objects
     */
    Object[] mergedArray = Utils.mergeArray(
            Utils.mergeArray(this.options, this.text), this.correctNumericChoices[0],
            "MultipleChoiceQuestion"
    );

    return Objects.hash(mergedArray);
  }

  /**
   * Returns the rank of this {@link MultipleChoiceQuestion}.
   *
   * @return the rank of this {@link MultipleChoiceQuestion}
   */
  @Override
  protected int getRankForOrdering() {
    return 301;
  }
}