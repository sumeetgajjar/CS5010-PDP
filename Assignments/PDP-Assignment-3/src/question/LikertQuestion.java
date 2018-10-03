package question;

import java.util.Objects;

import question.bean.LikertScale;
import question.bean.Result;
import util.Utils;

/**
 * This class represents a LikertQuestion. It extends {@link AbstractQuestion}.
 *
 * This question can be answered on fixed, 5-point Likert scale (Strongly Agree, Agree, Neither
 * Agree nor Disagree, Disagree, Strongly Disagree). An answer can be entered as one of the option
 * numbers, numbered from 1 in the above order.
 * <ul>
 * <li>1 corresponds to "Strongly Agree"</li>
 * <li>2 corresponds to "Agree"</li>
 * <li>3 corresponds to "Neither Agree nor Disagree"</li>
 * <li>4 corresponds to "Disagree"</li>
 * <li>5 corresponds to "Strongly Disagree"</li>
 * </ul>
 * Any valid option number is a “Correct” answer. If the given answer does not belong to the above
 * list it will be considered as "Incorrect" answer.
 */
public class LikertQuestion extends AbstractQuestion {

  private static final LikertScale[] VALID_OPTIONS = LikertScale.values();

  /**
   * Constructs a LikerQuestion object with the given text.
   *
   * @param text the text of the question
   * @throws IllegalArgumentException if the given text is null
   */
  public LikertQuestion(String text) throws IllegalArgumentException {
    super(text);
  }

  @Override
  public String[] getOptions() {
    return Utils.mapToStringArray(LikertScale::getLikertScaleString, VALID_OPTIONS);
  }

  /**
   * Checks if the given answer corresponds to an option from Likert Scale list. If the answer is
   * invalid it will return {@link Result#INCORRECT}.
   *
   * @param answer answer {@link String} to evaluate
   * @return {@link Result#CORRECT} if the given optionNumber corresponds to any option in Likert
   *         Scale list, {@link Result#INCORRECT} otherwise
   */
  @Override
  protected Result getResult(String answer) {
    try {
      int givenOptionNumber = Integer.parseInt(answer);
      return this.isOptionValid(givenOptionNumber) ? Result.CORRECT : Result.INCORRECT;
    } catch (NumberFormatException e) {
      return Result.INCORRECT;
    }
  }

  /**
   * Determines whether this {@link LikertQuestion} is equal to given {@link LikertQuestion}.
   *
   * @param likertQuestion the LikertQuestion object to which this LikertQuestion must be compared
   * @return whether this {@link LikertQuestion} is equal to given {@link LikertQuestion}.
   */
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

  /**
   * Returns the hashCode which is generated based on the question text and valid options.
   *
   * @return the hashCode which is generated based on the question text and valid options
   */
  @Override
  public int hashCode() {
    return Objects.hash(Utils.merge(VALID_OPTIONS, this.text));
  }

  /**
   * Returns the rank of this {@link LikertQuestion}.
   *
   * @return the rank of this {@link LikertQuestion}
   */
  @Override
  protected int getRankForOrdering() {
    return 201;
  }

  /**
   * Checks if the given optionNumber corresponds to any option in Likert Scale list.
   *
   * @param givenOptionNumber given option number
   * @return true if the given optionNumber corresponds to any option in Likert Scale list, false
   * otherwise
   */
  private boolean isOptionValid(int givenOptionNumber) {
    return givenOptionNumber >= 1 && givenOptionNumber <= VALID_OPTIONS.length;
  }
}
