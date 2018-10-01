package question;

import question.bean.Result;
import util.Utils;

//todo: document every thing carefully, like valid input and output
public abstract class AbstractQuestion implements Question {

  protected final String text;

  protected AbstractQuestion(String text) throws IllegalArgumentException {
    this.performSanityCheckForInput(text);
    this.text = text;
  }

  protected abstract Result eval(String answer) throws IllegalArgumentException;

  protected int getWeight() {
    return 0;
  }

  protected boolean equalsYesNoQuestion(YesNoQuestion yesNoQuestion) {
    return false;
  }

  protected boolean equalsLikertQuestion(LikertQuestion likertQuestion) {
    return false;
  }

  protected boolean equalsMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
    return false;
  }

  protected boolean equalsMultipleAnswersQuestion(MultipleAnswersQuestion multipleAnswersQuestion) {
    return false;
  }

  @Override
  public String getText() {
    return this.text;
  }

  @Override
  public String evaluateAnswer(String answer) {
    if (Utils.isStringNotSet(answer)) {
      return Result.INCORRECT.getResultString();
    }

    try {
      Result result = eval(answer);
      return result.getResultString();
    } catch (IllegalArgumentException e) {
      return Result.INCORRECT.getResultString();
    }
  }

  @Override
  public int compareTo(Question question) {
    if (question instanceof AbstractQuestion) {
      AbstractQuestion abstractQuestion = (AbstractQuestion) question;
      int diff = this.getWeight() - abstractQuestion.getWeight();
      if (diff == 0) {
        return this.text.compareTo(abstractQuestion.text);
      } else {
        return diff;
      }
    }
    return 1;
  }

  protected void performSanityCheckForInput(String text) throws IllegalArgumentException {
    if (Utils.isStringNotSet(text)) {
      throw new IllegalArgumentException(String.format("Invalid question text: %s", text));
    }
  }
}
