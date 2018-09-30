package questionnaire;

import questionnaire.bean.Option;

public class MultipleChoiceQuestion extends AbstractQuestion {

  private final Option correctOption;
  private final Option[] validOptions;

  public MultipleChoiceQuestion(String text, Option correctOption, Option[] validOptions) {
    super(text);
    this.correctOption = correctOption;
    this.validOptions = validOptions;
  }

  @Override
  public String eval(String answer) {
    return null;
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }
}
