package questionnaire;

import questionnaire.bean.Result;

public class MultipleAnswersQuestion extends AbstractQuestion {

  public MultipleAnswersQuestion(String text, String correctOptionsString, Option[] options) {
    super(text);
  }

  @Override
  protected Result eval(String answer) throws IllegalArgumentException {
    return null;
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }
}
