package calculator.inputcategory;

import java.util.List;
import java.util.Set;

public abstract class AbstractInputCategory implements InputCategoryInterface {

  protected final List<String> expression;
  protected final Set<Character> supportedCharacters;

  protected AbstractInputCategory(List<String> expression, Set<Character> supportCharacters) {
    this.expression = expression;
    this.supportedCharacters = supportCharacters;
  }

  @Override
  public boolean belongToInputCategory(char input) {
    return this.supportedCharacters.contains(input);
  }
}
