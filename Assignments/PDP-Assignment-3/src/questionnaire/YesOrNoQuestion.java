package questionnaire;

import java.util.Objects;

import questionnaire.bean.Result;
import questionnaire.bean.YesNoQuestionAnswer;

public class YesOrNoQuestion extends AbstractQuestion {

  private final YesNoQuestionAnswer correctAnswer;

  public YesOrNoQuestion(String text, YesNoQuestionAnswer correctAnswer) {
    super(text);
    this.performSanityCheckForInput(correctAnswer);
    this.correctAnswer = correctAnswer;
  }

  private void performSanityCheckForInput(YesNoQuestionAnswer yesNoQuestionAnswer) throws IllegalArgumentException {
    if (Objects.isNull(yesNoQuestionAnswer)) {
      throw new IllegalArgumentException(String.format("Invalid correct answer: %s", yesNoQuestionAnswer));
    }
  }

  @Override
  public String eval(String answer) {
    try {
      YesNoQuestionAnswer givenAnswer = YesNoQuestionAnswer.getYesNoQuestionAnswer(answer);
      Result result = this.correctAnswer.equals(givenAnswer) ? Result.CORRECT : Result.INCORRECT;
      return result.getAnswerStatusString();
    } catch (IllegalArgumentException e) {
      return Result.INCORRECT.getAnswerStatusString();
    }
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }
}
