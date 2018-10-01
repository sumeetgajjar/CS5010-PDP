package question;

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

    checkIfAnyOptionIsNull(options);
    checkOptionsSizeIsLessThanMinimumThreshold(options);
    checkOptionsSizeIsGreaterThanMaximumThreshold(options);
    checkIfDuplicateOptionsExists(options);
  }

  private void checkIfAnyOptionIsNull(Option[] options) {
    if (Objects.isNull(options)) {
      throw new IllegalArgumentException("options cannot be null");
    }

    for (Option option : options) {
      if (Objects.isNull(option)) {
        throw new IllegalArgumentException("option cannot be null");
      }
    }
  }

  private void checkOptionsSizeIsLessThanMinimumThreshold(Option[] options) {
    if (options.length < getMinimumOptionThreshold()) {
      throw new IllegalArgumentException(
              String.format("Question should have at least %d options, found: %d",
                      getMinimumOptionThreshold(), options.length));
    }
  }

  private void checkOptionsSizeIsGreaterThanMaximumThreshold(Option[] options) {
    if (options.length > getMaximumOptionThreshold()) {
      throw new IllegalArgumentException(
              String.format("Question can have no more than %d options, found: %d",
                      getMaximumOptionThreshold(), options.length));
    }
  }

  private void checkIfDuplicateOptionsExists(Option[] options) {
    for (int i = 0; i < options.length; i++) {
      for (int j = i + 1; j < options.length; j++) {
        if (options[i].equals(options[j])) {
          throw new IllegalArgumentException("options cannot contain duplicate values");
        }
      }
    }
  }
}
