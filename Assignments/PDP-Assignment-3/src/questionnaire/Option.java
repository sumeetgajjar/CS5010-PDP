package questionnaire;

import java.util.Objects;

public class Option {

  private final String text;

  public Option(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.text);
  }
}
