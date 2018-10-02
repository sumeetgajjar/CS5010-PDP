package question;

import java.util.Objects;

import question.bean.LikertScale;
import question.bean.Result;
import util.Utils;

/**
 * This class represents a LikertQuestion. It extends {@link AbstractQuestion}. {@link
 * LikertQuestion#evaluateAnswer(String)} will return "Correct" if the answer belongs to given
 * list.
 * <ul>
 * <li>"Strongly Agree"</li>
 * <li>"Agree"</li>
 * <li>"Neither Agree nor Disagree"</li>
 * <li>"Disagree"</li>
 * <li>"Strongly Disagree"</li>
 * </ul>
 * If the answer does not belong to above list it will return "Incorrect".
 */
public class LikertQuestion extends AbstractQuestion {

  private static final LikertScale[] VALID_OPTIONS = LikertScale.values();

  public LikertQuestion(String text) throws IllegalArgumentException {
    super(text);
  }

  @Override
  public String[] getOptions() {
    return Utils.mapToStringArray(LikertScale::getLikertScaleString, VALID_OPTIONS);
  }

  @Override
  protected Result getResult(String answer) throws IllegalArgumentException {
    try {
      int givenOptionNumber = Integer.parseInt(answer);
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
  protected int getRankForOrdering() {
    return 201;
  }

  private boolean isOptionValid(int givenOptionNumber) {
    return givenOptionNumber >= 1 && givenOptionNumber <= VALID_OPTIONS.length;
  }
}
