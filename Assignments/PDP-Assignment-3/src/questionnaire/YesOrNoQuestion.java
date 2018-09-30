package questionnaire;

import questionnaire.bean.YesNoQuestionAnswer;

public class YesOrNoQuestion extends AbstractQuestion {

  private final YesNoQuestionAnswer correctAnswer;

  public YesOrNoQuestion(String text, YesNoQuestionAnswer correctAnswer) {
    super(text);
    this.correctAnswer = correctAnswer;
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
