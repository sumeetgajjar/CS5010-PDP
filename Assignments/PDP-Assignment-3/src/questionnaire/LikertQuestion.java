package questionnaire;

import java.util.Objects;

import questionnaire.bean.LikertScale;
import questionnaire.bean.Result;

public class LikertQuestion extends AbstractQuestion {

  public LikertQuestion(String text) {
    super(text);
  }

  @Override
  public String eval(String answer) {
    try {
      LikertScale givenAnswer = LikertScale.getLikertScale(answer);
      return Result.CORRECT.getResultString();
    } catch (IllegalArgumentException e) {
      return Result.INCORRECT.getResultString();
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.text);
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }
}
