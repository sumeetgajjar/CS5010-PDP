package question;

import java.util.Objects;

import util.Utils;

public class Option {

  private final String text;

  public Option(String text) throws IllegalArgumentException {
    if (Utils.isStringNotSet(text)) {
      throw new IllegalArgumentException("option text cannot be empty");
    }

    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.text);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Option)) {
      return false;
    }

    Option that = (Option) o;
    return Objects.equals(text, that.text);
  }
}
