package question;

import java.util.Arrays;
import java.util.Objects;

import question.bean.NumericChoice;
import question.bean.Option;
import question.bean.Result;
import util.Utils;

public class MultipleAnswersQuestion extends AbstractQuestion {

  private static final int MINIMUM_OPTIONS = 3;
  private static final int MAXIMUM_OPTIONS = 8;

  protected final NumericChoice[] correctNumericChoices;
  protected final Option[] options;

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

  @Override
  protected Result eval(String answer) throws IllegalArgumentException {
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

  @Override
  protected boolean equalsMultipleAnswersQuestion(MultipleAnswersQuestion multipleAnswersQuestion) {
    return this.text.equals(multipleAnswersQuestion.text) &&
            Arrays.equals(this.options, multipleAnswersQuestion.options) &&
            Arrays.equals(this.correctNumericChoices, multipleAnswersQuestion.correctNumericChoices);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AbstractQuestion) {
      AbstractQuestion otherAbstractQuestion = (AbstractQuestion) other;
      return otherAbstractQuestion.equalsMultipleAnswersQuestion(this);
    }
    return false;
  }

  @Override
  protected int getRankForOrdering() {
    return 401;
  }

  @Override
  public int hashCode() {
    Object[] mergedArray = Utils.merge(
            Utils.merge(this.options, this.text),
            (Object[]) this.correctNumericChoices
    );

    return Objects.hash(mergedArray);
  }

  protected int getMaximumOptionThreshold() {
    return MAXIMUM_OPTIONS;
  }

  protected int getMinimumOptionThreshold() {
    return MINIMUM_OPTIONS;
  }

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

  private void checkIfOptionsContainsDuplicate(Option[] options) throws IllegalArgumentException {
    if (Utils.checkForDuplicatesInArray(options)) {
      throw new IllegalArgumentException("cannot contain duplicate options");
    }
  }

  private void checkIfNumericChoicesContainsDuplicate(NumericChoice[] correctNumericChoices) throws IllegalArgumentException {
    if (Utils.checkForDuplicatesInArray(correctNumericChoices)) {
      throw new IllegalArgumentException("cannot contain duplicate choices");
    }
  }

  private void checkIfCorrectChoicesAreNotPresentInOptions(NumericChoice[] correctNumericChoices, Option[] options) throws IllegalArgumentException {
    for (NumericChoice correctNumericChoice : correctNumericChoices) {
      if (correctNumericChoice.getValue() > options.length) {
        throw new IllegalArgumentException(String.format(
                "correct answer choice not found in given options, correctAnswerChoice: %d",
                correctNumericChoice.getValue())
        );
      }
    }
  }

  private void checkIfCorrectChoicesAreGreaterThanOptions(NumericChoice[] correctNumericChoices, Option[] options) throws IllegalArgumentException {
    if (correctNumericChoices.length > options.length) {
      throw new IllegalArgumentException("correct options cannot be greater than total options");
    }
  }

  private void checkIfAnyOptionIsNull(Option[] options) {
    if (Objects.isNull(options)) {
      throw new IllegalArgumentException("options cannot be null");
    }

    for (Option option : options) {
      if (Objects.isNull(option)) {
        throw new IllegalArgumentException("option cannot be null");
      }
    }
  }

  private void checkOptionsSizeIsLessThanMinimumThreshold(Option[] options) {
    if (options.length < getMinimumOptionThreshold()) {
      throw new IllegalArgumentException(
              String.format("Question should have at least %d options, found: %d",
                      getMinimumOptionThreshold(), options.length));
    }
  }

  private void checkOptionsSizeIsGreaterThanMaximumThreshold(Option[] options) {
    if (options.length > getMaximumOptionThreshold()) {
      throw new IllegalArgumentException(
              String.format("Question can have no more than %d options, found: %d",
                      getMaximumOptionThreshold(), options.length));
    }
  }

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
