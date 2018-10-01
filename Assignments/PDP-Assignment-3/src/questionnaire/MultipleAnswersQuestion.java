package questionnaire;

import questionnaire.bean.Result;

public class MultipleAnswersQuestion extends AbstractQuestionWithDynamicOptions {

  private static final int MINIMUM_OPTIONS = 3;
  private static final int MAXIMUM_OPTIONS = 8;


  public MultipleAnswersQuestion(String text, String correctOptionsString, Option[] options) {
    super(text, options);

  }

  @Override
  protected Result eval(String answer) throws IllegalArgumentException {
    return null;
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }

  @Override
  protected int getMaximumOptionThreshold() {
    return MAXIMUM_OPTIONS;
  }

  @Override
  protected int getMinimumOptionThreshold() {
    return MINIMUM_OPTIONS;
  }
}
