package question;

import java.util.Objects;

import question.bean.NumericChoice;
import question.bean.Option;
import question.bean.Result;
import util.Utils;

public class MultipleChoiceQuestion extends AbstractQuestionWithDynamicOptions {

  private static final int MINIMUM_OPTIONS = 3;
  private static final int MAXIMUM_OPTIONS = 8;

  private final NumericChoice correctNumericChoice;

  public MultipleChoiceQuestion(String text, NumericChoice correctNumericChoice, Option[] options) {
    super(text, options);
    this.performSanityCheckForInput(correctNumericChoice, options);
    this.correctNumericChoice = correctNumericChoice;
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
  protected Result eval(String answer) throws IllegalArgumentException {
    NumericChoice givenNumericChoice = NumericChoice.getChoice(answer);
    return correctNumericChoice.equals(givenNumericChoice) ? Result.CORRECT : Result.INCORRECT;
  }

  @Override
  public int hashCode() {
    return Objects.hash(Utils.merge(this.options, this.text, this.correctNumericChoice));
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }

  private void performSanityCheckForInput(NumericChoice correctAnswerChoice, Option[] options)
          throws IllegalArgumentException {

    if (Objects.isNull(correctAnswerChoice)) {
      throw new IllegalArgumentException("correct answer cannot be null");
    }

    if (correctAnswerChoice.getValue() > options.length) {
      throw new IllegalArgumentException(
              String.format(
                      "correct answer choice not found in given options, correctAnswerChoice: %d",
                      correctAnswerChoice.getValue())
      );
    }
  }
}