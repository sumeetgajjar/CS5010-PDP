package questionnaire;

import java.util.Objects;

import questionnaire.bean.Option;
import questionnaire.bean.Result;

public class MultipleChoiceQuestion extends AbstractQuestion {

  private final Option correctOption;
  private final Option[] validOptions;

  public MultipleChoiceQuestion(String text, Option correctOption, Option[] validOptions) {
    super(text);

    this.performSanityCheckForInput(correctOption, validOptions);

    this.correctOption = correctOption;
    this.validOptions = validOptions;
  }

  private void performSanityCheckForInput(Option correctOption, Option[] validOptions) throws IllegalArgumentException {
    if (Objects.isNull(correctOption)) {
      throw new IllegalArgumentException("Invalid correct answer: null");
    }

    if (Objects.isNull(validOptions)) {
      throw new IllegalArgumentException("Invalid answer options: null");
    }

    for (Option validOption : validOptions) {
      if (Objects.isNull(validOption)) {
        throw new IllegalArgumentException("Invalid correct answer: null");
      }
    }

    if (validOptions.length < 3) {
      throw new IllegalArgumentException(String.format("Question should have at least 3 options, found: %d", validOptions.length));
    }

    if (validOptions.length > 8) {
      throw new IllegalArgumentException(String.format("Question can have no more than 8 options, found: %d", validOptions.length));
    }
  }

  @Override
  public String eval(String answer) {
    try {
      Option givenAnswer = Option.getOption(answer);
      Result result = correctOption.equals(givenAnswer) ? Result.CORRECT : Result.INCORRECT;
      return result.getResultString();
    } catch (IllegalArgumentException e) {
      return Result.INCORRECT.getResultString();
    }
  }

  @Override
  public int hashCode() {
    Object[] objects = new Object[this.validOptions.length + 2];

    objects[0] = this.text;
    objects[1] = this.correctOption;

    for (int i = 0, j = 2; i < this.validOptions.length; i++, j++) {
      objects[j] = this.validOptions[i];
    }

    return Objects.hash(objects);
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }
}
