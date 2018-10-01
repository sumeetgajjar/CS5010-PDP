package questionnaire;

import util.Utils;

public abstract class AbstractQuestion implements Question {

  protected final String text;

  protected AbstractQuestion(String text) throws IllegalArgumentException {
    this.performSanityCheckForInput(text);
    this.text = text;
  }

  protected void performSanityCheckForInput(String text) throws IllegalArgumentException {
    if (Utils.isStringNotSet(text)) {
      throw new IllegalArgumentException(String.format("Invalid question text: %s", text));
    }
  }

  @Override
  public String getText() {
    return this.text;
  }
}
