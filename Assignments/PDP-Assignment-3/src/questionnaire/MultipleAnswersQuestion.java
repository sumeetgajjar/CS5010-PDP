package questionnaire;

import questionnaire.bean.OptionNumber;
import questionnaire.bean.Result;

public class MultipleAnswersQuestion extends AbstractQuestion {

  public MultipleAnswersQuestion(String text, String correctOptionsString, OptionNumber[] validOptionNumbers) {
    super(text);
  }

  @Override
  protected Result eval(String answer) {
    return null;
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }
}
