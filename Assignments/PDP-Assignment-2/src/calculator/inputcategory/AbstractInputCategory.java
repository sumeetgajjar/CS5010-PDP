package calculator.inputcategory;

import java.util.List;
import java.util.Set;

public abstract class AbstractInputCategory implements InputCategoryInterface {

  protected final List<String> expression;
  protected final Set<Character> supportCharacters;

  protected AbstractInputCategory(List<String> expression, Set<Character> clearInputCharacterSet) {
    this.expression = expression;
    this.supportCharacters = clearInputCharacterSet;
  }

  @Override
  public boolean belongToInputCategory(char input) {
    return this.supportCharacters.contains(input);
  }
}
