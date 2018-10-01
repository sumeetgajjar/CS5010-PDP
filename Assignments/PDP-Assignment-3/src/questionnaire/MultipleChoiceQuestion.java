package questionnaire;

import java.util.Objects;

import questionnaire.bean.NumericChoice;
import questionnaire.bean.Result;
import util.Utils;

public class MultipleChoiceQuestion extends AbstractQuestion {

  private static final int MINIMUM_OPTIONS = 3;
  private static final int MAXIMUM_OPTIONS = 8;

  private final NumericChoice correctNumericChoice;
  private final Option[] options;

  public MultipleChoiceQuestion(String text, NumericChoice correctNumericChoice, Option[] options) {
    super(text);

    this.performSanityCheckForInput(correctNumericChoice, options);

    this.correctNumericChoice = correctNumericChoice;
    this.options = options;
  }

  @Override
  protected Result eval(String answer) throws IllegalArgumentException {
    NumericChoice givenNumericChoice = NumericChoice.getChoice(answer);
    return correctNumericChoice.equals(givenNumericChoice) ? Result.CORRECT : Result.INCORRECT;
  }

  @Override
  public int hashCode() {
    return Objects.hash(Utils.merge(options, this.text, this.correctNumericChoice));
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

    if (Objects.isNull(options)) {
      throw new IllegalArgumentException("options cannot be null");
    }

    for (Option option : options) {
      if (Objects.isNull(option)) {
        throw new IllegalArgumentException("option cannot be null");
      }
    }

    if (options.length < MINIMUM_OPTIONS) {
      throw new IllegalArgumentException(
              String.format("Question should have at least %d options, found: %d",
                      MINIMUM_OPTIONS, options.length));
    }

    if (options.length > MAXIMUM_OPTIONS) {
      throw new IllegalArgumentException(
              String.format("Question can have no more than %d options, found: %d",
                      MAXIMUM_OPTIONS, options.length));
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