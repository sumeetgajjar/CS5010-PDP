package questionnaire;

import java.util.Objects;

import questionnaire.bean.Result;
import questionnaire.bean.YesNoQuestionAnswer;

public class YesNoQuestion extends AbstractQuestion {

  private final YesNoQuestionAnswer correctAnswer;

  public YesNoQuestion(String text, YesNoQuestionAnswer correctAnswer) {
    super(text);
    this.performSanityCheckForInput(correctAnswer);
    this.correctAnswer = correctAnswer;
  }

  @Override
  public String eval(String answer) {
    try {
      YesNoQuestionAnswer givenAnswer = YesNoQuestionAnswer.getYesNoQuestionAnswer(answer);
      Result result = this.correctAnswer.equals(givenAnswer) ? Result.CORRECT : Result.INCORRECT;
      return result.getResultString();
    } catch (IllegalArgumentException e) {
      return Result.INCORRECT.getResultString();
    }
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.text, this.correctAnswer);
  }

  private void performSanityCheckForInput(YesNoQuestionAnswer yesNoQuestionAnswer) throws IllegalArgumentException {
    if (Objects.isNull(yesNoQuestionAnswer)) {
      throw new IllegalArgumentException("Invalid correct answer: null");
    }
  }
}
