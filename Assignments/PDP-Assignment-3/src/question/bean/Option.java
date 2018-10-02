package question.bean;

import java.util.Objects;

import util.Utils;

/**
 * This class represents a container to store an answer choice for an question. It stores the text
 * of the choice and throws {@link IllegalArgumentException} if the given text for the choice is
 * null or empty.
 */
public class Option {

  private final String text;

  /**
   * Constructs a Option object with the given text.
   *
   * @param text text for the Option choice
   * @throws IllegalArgumentException if text is null or empty
   */
  public Option(String text) throws IllegalArgumentException {
    if (Utils.isStringNotSet(text)) {
      throw new IllegalArgumentException("Option text cannot be empty");
    }

    this.text = text;
  }

  /**
   * Returns the text associated with the Option.
   *
   * @return the text associated with the Option
   */
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
