package questionnaire;

import questionnaire.bean.Option;

public class MultipleChoiceQuestion extends AbstractQuestion {

  private final Option correctOption;
  private final Option[] choices;

  public MultipleChoiceQuestion(String text, Option correctOption, Option[] choices) {
    super(text);
    this.correctOption = correctOption;
    this.choices = choices;
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
