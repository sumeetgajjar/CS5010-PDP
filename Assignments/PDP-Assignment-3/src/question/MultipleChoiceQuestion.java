package question;

import java.util.Arrays;

import question.bean.NumericChoice;
import question.bean.Option;

public class MultipleChoiceQuestion extends MultipleAnswersQuestion {

  private static final int MINIMUM_OPTIONS = 3;
  private static final int MAXIMUM_OPTIONS = 8;

  public MultipleChoiceQuestion(String text, String correctOptionsString, Option[] options) throws IllegalArgumentException {
    super(text, correctOptionsString, options);
    this.performSanityCheckForInput(this.correctNumericChoices);
  }

  private void performSanityCheckForInput(NumericChoice[] correctNumericChoices) {
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

  @Override
  protected boolean equalsMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
    return this.text.equals(multipleChoiceQuestion.text) &&
            Arrays.equals(this.options, multipleChoiceQuestion.options) &&
            this.correctNumericChoices[0].equals(multipleChoiceQuestion.correctNumericChoices[0]);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AbstractQuestion) {
      AbstractQuestion otherAbstractQuestion = (AbstractQuestion) other;
      return otherAbstractQuestion.equalsMultipleChoiceQuestion(this);
    }
    return false;
  }

  @Override
  protected int getRankForOrdering() {
    return 301;
  }
}