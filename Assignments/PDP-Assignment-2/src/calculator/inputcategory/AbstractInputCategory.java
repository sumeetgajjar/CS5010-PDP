package calculator.inputcategory;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class AbstractInputCategory implements Command {

  protected final List<String> expression;
  protected final Set<Character> supportedCharacters;

  protected AbstractInputCategory(List<String> expression, Set<Character> supportCharacters) {
    this.expression = Collections.unmodifiableList(expression);
    this.supportedCharacters = Collections.unmodifiableSet(supportCharacters);
  }

  @Override
  public boolean belongToInputCategory(char input) {
    return this.supportedCharacters.contains(input);
  }
}
