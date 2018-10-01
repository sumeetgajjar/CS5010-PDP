package question;

import java.util.Objects;

import question.bean.NumericChoice;
import question.bean.Option;
import question.bean.Result;
import util.Utils;

public class MultipleAnswersQuestion extends AbstractQuestionWithDynamicOptions {

  private static final int MINIMUM_OPTIONS = 3;
  private static final int MAXIMUM_OPTIONS = 8;

  private final NumericChoice[] correctNumericChoices;

  public MultipleAnswersQuestion(String text, String correctOptionsString, Option[] options)
          throws IllegalArgumentException {

    super(text, options);
    this.correctNumericChoices = this.parseNumberChoices(correctOptionsString);
    this.performSanityCheckForInput(this.correctNumericChoices, this.options);
  }

  @Override
  protected Result eval(String answer) throws IllegalArgumentException {
    NumericChoice[] givenNumericChoices = parseNumberChoices(answer);
    this.checkForDuplicateChoices(givenNumericChoices);

    if (correctNumericChoices.length != givenNumericChoices.length) {
      return Result.INCORRECT;
    }

    boolean isOptionMissing;
    for (int i = 0; i < correctNumericChoices.length; i++) {
      isOptionMissing = true;

      for (int j = 0; j < givenNumericChoices.length; j++) {
        if (correctNumericChoices[i].equals(givenNumericChoices[j])) {
          isOptionMissing = false;
        }
      }

      if (isOptionMissing) {
        return Result.INCORRECT;
      }
    }

    return Result.CORRECT;
  }

  private void checkForDuplicateChoices(NumericChoice[] numericChoices) {
    for (int i = 0; i < numericChoices.length; i++) {
      for (int j = i + 1; j < numericChoices.length; j++) {
        if (numericChoices[i] == numericChoices[j]) {
          throw new IllegalArgumentException("cannot contain duplicate choices");
        }
      }
    }
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }

  @Override
  public int hashCode() {
    Object[] mergedArray = Utils.merge(
            Utils.merge(this.options, this.text),
            (Object[]) this.correctNumericChoices
    );

    return Objects.hash(mergedArray);
  }

  @Override
  protected int getMaximumOptionThreshold() {
    return MAXIMUM_OPTIONS;
  }

  @Override
  protected int getMinimumOptionThreshold() {
    return MINIMUM_OPTIONS;
  }

  private void performSanityCheckForInput(NumericChoice[] correctNumericChoices, Option[] options)
          throws IllegalArgumentException {

    if (correctNumericChoices.length > options.length) {
      throw new IllegalArgumentException("correct options cannot be greater than total options");
    }

    for (NumericChoice correctNumericChoice : correctNumericChoices) {
      if (correctNumericChoice.getValue() > options.length) {
        throw new IllegalArgumentException(String.format(
                "correct answer choice not found in given options, correctAnswerChoice: %d",
                correctNumericChoice.getValue())
        );
      }
    }

    this.checkForDuplicateChoices(correctNumericChoices);
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
