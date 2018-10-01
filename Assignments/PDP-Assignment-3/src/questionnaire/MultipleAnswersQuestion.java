package questionnaire;

import questionnaire.bean.NumericChoice;
import questionnaire.bean.Result;
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
    return null;
  }

  @Override
  public int compareTo(Question o) {
    return 0;
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
  }

  private NumericChoice[] parseNumberChoices(String correctOptionsString)
          throws IllegalArgumentException {

    if (Utils.isStringNotSet(correctOptionsString)) {
      throw new IllegalArgumentException("invalid correct option");
    }

    String[] choices = correctOptionsString.split(" ");
    NumericChoice[] numericChoices = new NumericChoice[choices.length];
    for (int i = 0; i < choices.length; i++) {
      numericChoices[i] = NumericChoice.getChoice(choices[i]);
    }

    return numericChoices;
  }
}
