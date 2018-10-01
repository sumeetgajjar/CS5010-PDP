package question;

import java.util.Objects;

import question.bean.Result;
import question.bean.YesNoQuestionAnswer;

public class YesNoQuestion extends AbstractQuestion {

  private final YesNoQuestionAnswer correctAnswer;

  public YesNoQuestion(String text, YesNoQuestionAnswer correctAnswer) throws IllegalArgumentException {
    super(text);
    this.performSanityCheckForInput(correctAnswer);
    this.correctAnswer = correctAnswer;
  }

  @Override
  protected Result eval(String answer) throws IllegalArgumentException {
    YesNoQuestionAnswer givenAnswer = YesNoQuestionAnswer.getYesNoQuestionAnswer(answer);
    return this.correctAnswer.equals(givenAnswer) ? Result.CORRECT : Result.INCORRECT;
  }

  @Override
  protected boolean equalsYesNoQuestion(YesNoQuestion yesNoQuestion) {
    return
            this.text.equals(yesNoQuestion.text) &&
                    this.correctAnswer.equals(yesNoQuestion.correctAnswer);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AbstractQuestion) {
      AbstractQuestion otherAbstractQuestion = (AbstractQuestion) other;
      return otherAbstractQuestion.equalsYesNoQuestion(this);
    }
    return false;
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
      throw new IllegalArgumentException("correct answer cannot be null");
    }
  }
}
