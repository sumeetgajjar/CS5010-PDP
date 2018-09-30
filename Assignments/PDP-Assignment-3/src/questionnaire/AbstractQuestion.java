package questionnaire;

public abstract class AbstractQuestion implements Question {
  protected final String text;

  protected AbstractQuestion(String text) {
    this.text = text;
  }

  @Override
  public String getText() {
    return this.text;
  }
}
