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
  protected Result eval(String answer) {
    YesNoQuestionAnswer givenAnswer = YesNoQuestionAnswer.getYesNoQuestionAnswer(answer);
    return this.correctAnswer.equals(givenAnswer) ? Result.CORRECT : Result.INCORRECT;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.text, this.correctAnswer);
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }

  private void performSanityCheckForInput(YesNoQuestionAnswer yesNoQuestionAnswer) throws IllegalArgumentException {
    if (Objects.isNull(yesNoQuestionAnswer)) {
      throw new IllegalArgumentException("Invalid correct answer: null");
    }
  }
}
