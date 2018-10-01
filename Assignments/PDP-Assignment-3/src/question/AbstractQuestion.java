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

  protected boolean equalsYesNoQuestion(YesNoQuestion yesNoQuestion) {
    return false;
  }

  protected boolean equalsLikertQuestion(LikertQuestion likertQuestion) {
    return false;
  }

  protected boolean equalMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
    return false;
  }

  protected boolean equalMultipleAnswersQuestion(MultipleAnswersQuestion multipleAnswersQuestion) {
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

  protected void performSanityCheckForInput(String text) throws IllegalArgumentException {
    if (Utils.isStringNotSet(text)) {
      throw new IllegalArgumentException(String.format("Invalid question text: %s", text));
    }
  }
}
