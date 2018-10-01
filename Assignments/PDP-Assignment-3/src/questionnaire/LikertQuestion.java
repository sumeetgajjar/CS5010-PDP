package questionnaire;

import java.util.Objects;

import questionnaire.bean.LikertScale;
import questionnaire.bean.Result;

public class LikertQuestion extends AbstractQuestion {

  private static final LikertScale[] VALID_OPTIONS = new LikertScale[]{
          LikertScale.STRONGLY_AGREE,
          LikertScale.AGREE,
          LikertScale.NEITHER_AGREE_NOR_DISAGREE,
          LikertScale.DISAGREE,
          LikertScale.STRONGLY_DISAGREE
  };

  public LikertQuestion(String text) {
    super(text);
  }

  @Override
  protected Result eval(String answer) {
    try {
      int givenOptionNumber = Integer.parseInt(answer) - 1;
      return this.isOptionValid(givenOptionNumber) ? Result.CORRECT : Result.INCORRECT;
    } catch (NumberFormatException e) {
      return Result.INCORRECT;
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

  private boolean isOptionValid(int givenOptionNumber) {
    return givenOptionNumber >= 0 && givenOptionNumber < VALID_OPTIONS.length;
  }
}
