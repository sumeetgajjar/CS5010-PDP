package question;

import java.util.Arrays;
import java.util.Objects;

import question.bean.NumericChoice;
import question.bean.Option;
import question.bean.Result;
import util.Utils;

/**
 * This class represents a Multiple Answers Question. It extends {@link AbstractQuestion}. This
 * offers several options, and there are multiple correct answers. A {@link MultipleAnswersQuestion}
 * may have at least 3 options, but no more than 8. If this conditions are not satisfied it will
 * throw {@link IllegalArgumentException} while creating its object.
 *
 * <p>Both the correct answer and the user’s answer are entered as the option numbers in a string.
 * For example, “1”, “1 3”, “4 1”, etc. Option numbers start at 1. An answer is correct if and only
 * if it contains all the correct options and none of the incorrect ones. The order of the options
 * in answer string is not important as long as they are correct and complete.
 *
 * <p>The answer string should contain a correct option only once. If the answer string contains
 * a correct option more than once then the answer will be evaluated as "Incorrect".
 *
 * <p>A correct answer string cannot have leading spaces. However trailing spaces are allowed and
 * are ignored while evaluating the answer.
 *
 * <p>The answer string should not have more than single space in between two options. If there
 * exists multiple spaces between two options then the answer will evaluated as incorrect.
 */
public class MultipleAnswersQuestion extends AbstractQuestion {

  private static final int MINIMUM_OPTIONS = 3;
  private static final int MAXIMUM_OPTIONS = 8;

  protected final NumericChoice[] correctNumericChoices;
  protected final Option[] options;

  /**
   * Constructs a MultipleAnswersQuestion object with given parameters. It throws {@link
   * IllegalArgumentException} if any of the sanity tests fails.
   *
   * @param text                 the text of the question
   * @param correctOptionsString the correctOptionString
   * @param options              the options for the question
   * @throws IllegalArgumentException if input params does not pass the Sanity tests. Sanity tests
   *                                  fall in following categories:
   *                                  <ul>
   *                                  <li>Question text cannot be null or empty</li>
   *                                  <li>correctOptionsString cannot be null or empty</li>
   *                                  <li>if correctOptionsString contains some invalid
   *                                  character</li>
   *                                  <li>Null is passed for Options or any option in the specified
   *                                  Options is null</li>
   *                                  <li>Options size is less than 3</li>
   *                                  <li>Options size is greater than 8</li>
   *                                  <li>Duplicate Options exists</li>
   *                                  <li>Duplicate correct Options exists</li>
   *                                  <li>If number of correct Options are greater than number of
   *                                  all Options</li>
   *                                  <li>If given correct Option is not present in all
   *                                  Options</li>
   *                                  </ul>
   */
  public MultipleAnswersQuestion(String text, String correctOptionsString, Option[] options)
          throws IllegalArgumentException {

    super(text);
    this.correctNumericChoices = this.parseNumberChoices(correctOptionsString);
    this.options = options;

    this.performSanityCheckForInput(this.correctNumericChoices, this.options);
  }

  @Override
  public String[] getOptions() {
    return Utils.mapToStringArray(Option::getText, this.options);
  }

  /**
   * Returns {@link Result#CORRECT} if the answer string only contains all correct options. The
   * options should be separated by single space and should occur only once. The order in which the
   * options occur in the answer string is not important. For all invalid or incorrect option string
   * it returns {@link Result#INCORRECT}.
   *
   * @param answer answer {@link String} to evaluate
   * @return the {@link Result} based on the evaluation of the answer.
   * @throws IllegalArgumentException if the given answer contains some invalid character
   */
  @Override
  protected Result getResult(String answer) throws IllegalArgumentException {
    NumericChoice[] givenNumericChoices = parseNumberChoices(answer);
    checkIfNumericChoicesContainsDuplicate(givenNumericChoices);

    if (correctNumericChoices.length != givenNumericChoices.length) {
      return Result.INCORRECT;
    }

    boolean isOptionMissing;
    for (NumericChoice correctNumericChoice : correctNumericChoices) {
      isOptionMissing = true;

      for (NumericChoice givenNumericChoice : givenNumericChoices) {
        if (correctNumericChoice.equals(givenNumericChoice)) {
          isOptionMissing = false;
        }
      }

      if (isOptionMissing) {
        return Result.INCORRECT;
      }
    }

    return Result.CORRECT;
  }

  /**
   * Determines whether this {@link MultipleAnswersQuestion} is equal to given {@link
   * MultipleAnswersQuestion}.
   *
   * @param multipleAnswersQuestion the MultipleAnswersQuestion object to which this Question must
   *                                be compared
   * @return whether this {@link MultipleAnswersQuestion} is equal to given {@link
   * MultipleAnswersQuestion}.
   */
  @Override
  protected boolean equalsMultipleAnswersQuestion(MultipleAnswersQuestion multipleAnswersQuestion) {
    return this.text.equals(multipleAnswersQuestion.text)
            && Arrays.equals(this.options, multipleAnswersQuestion.options)
            && Arrays.equals(
            this.correctNumericChoices, multipleAnswersQuestion.correctNumericChoices);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AbstractQuestion) {
      AbstractQuestion otherAbstractQuestion = (AbstractQuestion) other;
      return otherAbstractQuestion.equalsMultipleAnswersQuestion(this);
    }
    return false;
  }

  /**
   * Returns the rank of this {@link MultipleAnswersQuestion}.
   *
   * @return the rank of this {@link MultipleAnswersQuestion}
   */
  @Override
  protected int getRankForOrdering() {
    return 401;
  }

  /**
   * Returns the hashCode for this {@link MultipleAnswersQuestion}. Hashcode is based on the
   * question text, correct options and all options for this question.
   *
   * @return the hashCode for this {@link MultipleAnswersQuestion}
   */
  @Override
  public int hashCode() {
    Object[] mergedArray = Utils.merge(
            Utils.merge(this.options, this.text),
            (Object[]) this.correctNumericChoices
    );

    return Objects.hash(mergedArray);
  }

  /**
   * Returns the maximum options this question can have.
   *
   * @return the maximum options this question can have
   */
  protected int getMaximumOptionThreshold() {
    return MAXIMUM_OPTIONS;
  }

  /**
   * Returns the minimum options this question can have.
   *
   * @return the minimum options this question can have
   */
  protected int getMinimumOptionThreshold() {
    return MINIMUM_OPTIONS;
  }

  /**
   * Performs sanity checks on given params.
   *
   * @param correctNumericChoices the correct NumericChoices for this question
   * @param options               all the options for this question
   * @throws IllegalArgumentException if any of the following sanity check fails.
   *                                  <ul>
   *                                  <li>Null is passed for Options or any option in the specified
   *                                  Options is null</li>
   *                                  <li>Options size is less than 3</li>
   *                                  <li>Options size is greater than 8</li>
   *                                  <li>Duplicate Options exists</li>
   *                                  <li>Duplicate correct Options exists</li>
   *                                  <li>If number of correct Options are greater than number of
   *                                  all Options</li>
   *                                  <li>If given correct Option is not present in all
   *                                  Options</li>
   *                                  </ul>
   */
  private void performSanityCheckForInput(NumericChoice[] correctNumericChoices, Option[] options)
          throws IllegalArgumentException {

    checkIfAnyOptionIsNull(options);
    checkOptionsSizeIsLessThanMinimumThreshold(options);
    checkOptionsSizeIsGreaterThanMaximumThreshold(options);
    checkIfNumericChoicesContainsDuplicate(correctNumericChoices);
    checkIfOptionsContainsDuplicate(options);
    checkIfCorrectChoicesAreGreaterThanOptions(correctNumericChoices, options);
    checkIfCorrectChoicesAreNotPresentInOptions(correctNumericChoices, options);
  }

  /**
   * Checks if the given options contain duplicates.
   *
   * @param options the options to check
   * @throws IllegalArgumentException if the given options contain duplicates
   */
  private void checkIfOptionsContainsDuplicate(Option[] options) throws IllegalArgumentException {
    if (Utils.checkForDuplicatesInArray(options)) {
      throw new IllegalArgumentException("cannot contain duplicate options");
    }
  }

  /**
   * Checks if the given numeric choices contain duplicates.
   *
   * @param correctNumericChoices the numeric choice to check
   * @throws IllegalArgumentException if the given numeric choices contain duplicates
   */
  private void checkIfNumericChoicesContainsDuplicate(NumericChoice[] correctNumericChoices)
          throws IllegalArgumentException {

    if (Utils.checkForDuplicatesInArray(correctNumericChoices)) {
      throw new IllegalArgumentException("cannot contain duplicate choices");
    }
  }

  /**
   * Checks if the given numeric choice is present in the given option.
   *
   * @param correctNumericChoices the numeric choice to check
   * @param options               the options to check in
   * @throws IllegalArgumentException if the given numeric choice is not present in the given
   *                                  option
   */
  private void checkIfCorrectChoicesAreNotPresentInOptions(
          NumericChoice[] correctNumericChoices, Option[] options) throws IllegalArgumentException {

    for (NumericChoice correctNumericChoice : correctNumericChoices) {
      if (correctNumericChoice.getValue() > options.length) {
        throw new IllegalArgumentException(String.format(
                "correct answer choice not found in given options, correctAnswerChoice: %d",
                correctNumericChoice.getValue())
        );
      }
    }
  }

  /**
   * Checks if there are more numeric choices than options.
   *
   * @param correctNumericChoices the numeric choices to check
   * @param options               the options to check
   * @throws IllegalArgumentException if there are more numeric choices than options
   */
  private void checkIfCorrectChoicesAreGreaterThanOptions(
          NumericChoice[] correctNumericChoices, Option[] options) throws IllegalArgumentException {

    if (correctNumericChoices.length > options.length) {
      throw new IllegalArgumentException("correct options cannot be greater than total options");
    }
  }

  /**
   * Checks if the given option array or any option in the array is null.
   *
   * @param options the option array to check
   * @throws IllegalArgumentException if the given option array or any option in the array is null
   */
  private void checkIfAnyOptionIsNull(Option[] options) throws IllegalArgumentException {
    if (Objects.isNull(options)) {
      throw new IllegalArgumentException("options cannot be null");
    }

    for (Option option : options) {
      if (Objects.isNull(option)) {
        throw new IllegalArgumentException("option cannot be null");
      }
    }
  }

  /**
   * Checks if the options size is less than the minimum threshold.
   *
   * @param options the option array to check
   * @throws IllegalArgumentException if the options size is less than the minimum threshold
   */
  private void checkOptionsSizeIsLessThanMinimumThreshold(Option[] options)
          throws IllegalArgumentException {

    if (options.length < getMinimumOptionThreshold()) {
      throw new IllegalArgumentException(
              String.format("Question should have at least %d options, found: %d",
                      getMinimumOptionThreshold(), options.length));
    }
  }

  /**
   * Checks if the options size is greater than the maximum threshold.
   *
   * @param options the option array to check
   * @throws IllegalArgumentException if the options size is greater than the maximum threshold
   */
  private void checkOptionsSizeIsGreaterThanMaximumThreshold(Option[] options)
          throws IllegalArgumentException {

    if (options.length > getMaximumOptionThreshold()) {
      throw new IllegalArgumentException(
              String.format("Question can have no more than %d options, found: %d",
                      getMaximumOptionThreshold(), options.length));
    }
  }

  /**
   * Parse the given string and returns the NumericChoice array.
   *
   * @param choiceString the string to parse
   * @return the NumericChoice array
   * @throws IllegalArgumentException if the string contains invalid characters which cannot be
   *                                  mapped to NumericChoice
   */
  private NumericChoice[] parseNumberChoices(String choiceString)
          throws IllegalArgumentException {

    if (Utils.isStringNotSet(choiceString)) {
      throw new IllegalArgumentException("Invalid correctOption");
    }

    String[] choices = choiceString.split(" ");
    NumericChoice[] numericChoices = new NumericChoice[choices.length];
    for (int i = 0; i < choices.length; i++) {
      numericChoices[i] = NumericChoice.getChoice(choices[i]);
    }

    return numericChoices;
  }
}
