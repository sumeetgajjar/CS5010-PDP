package question;

import java.util.Objects;

import question.bean.LikertScale;
import question.bean.Result;
import util.Utils;

public class LikertQuestion extends AbstractQuestion {

  private static final LikertScale[] VALID_OPTIONS = new LikertScale[]{
          LikertScale.STRONGLY_AGREE,
          LikertScale.AGREE,
          LikertScale.NEITHER_AGREE_NOR_DISAGREE,
          LikertScale.DISAGREE,
          LikertScale.STRONGLY_DISAGREE
  };

  public LikertQuestion(String text) throws IllegalArgumentException {
    super(text);
  }

  @Override
  protected Result eval(String answer) throws IllegalArgumentException {
    try {
      int givenOptionNumber = Integer.parseInt(answer) - 1;
      return this.isOptionValid(givenOptionNumber) ? Result.CORRECT : Result.INCORRECT;
    } catch (NumberFormatException e) {
      return Result.INCORRECT;
    }
  }

  @Override
  protected boolean equalsLikertQuestion(LikertQuestion likertQuestion) {
    return this.text.equals(likertQuestion.text);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AbstractQuestion) {
      AbstractQuestion otherAbstractQuestion = (AbstractQuestion) other;
      return otherAbstractQuestion.equalsLikertQuestion(this);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(Utils.merge(VALID_OPTIONS, this.text));
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }

  private boolean isOptionValid(int givenOptionNumber) {
    return givenOptionNumber >= 0 && givenOptionNumber < VALID_OPTIONS.length;
  }
}
