package questionnaire;

import java.util.Objects;

public abstract class AbstractQuestionWithDynamicOptions extends AbstractQuestion {


  protected final Option[] options;

  protected AbstractQuestionWithDynamicOptions(String text, Option[] options) throws IllegalArgumentException {
    super(text);
    this.performSanityCheckForInput(options);
    this.options = options;
  }

  protected abstract int getMaximumOptionThreshold();

  protected abstract int getMinimumOptionThreshold();

  private void performSanityCheckForInput(Option[] options)
          throws IllegalArgumentException {

    if (Objects.isNull(options)) {
      throw new IllegalArgumentException("options cannot be null");
    }

    for (Option option : options) {
      if (Objects.isNull(option)) {
        throw new IllegalArgumentException("option cannot be null");
      }
    }

    if (options.length < getMinimumOptionThreshold()) {
      throw new IllegalArgumentException(
              String.format("Question should have at least %d options, found: %d",
                      getMinimumOptionThreshold(), options.length));
    }

    if (options.length > getMaximumOptionThreshold()) {
      throw new IllegalArgumentException(
              String.format("Question can have no more than %d options, found: %d",
                      getMaximumOptionThreshold(), options.length));
    }
  }
}
