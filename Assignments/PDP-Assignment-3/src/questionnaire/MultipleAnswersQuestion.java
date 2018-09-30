package questionnaire;

import questionnaire.bean.Option;

public class MultipleAnswersQuestion extends AbstractQuestion {

  public MultipleAnswersQuestion(String text, String correctOptionsString, Option[] validOptions) {
    super(text);
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
