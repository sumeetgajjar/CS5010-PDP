package questionnaire;

import java.util.Objects;

import questionnaire.bean.OptionNumber;
import questionnaire.bean.Result;

public class MultipleChoiceQuestion extends AbstractQuestion {

  private final OptionNumber correctOptionNumber;
  private final OptionNumber[] validOptionNumbers;

  public MultipleChoiceQuestion(String text, OptionNumber correctOptionNumber, OptionNumber[] validOptionNumbers) {
    super(text);

    this.performSanityCheckForInput(correctOptionNumber, validOptionNumbers);

    this.correctOptionNumber = correctOptionNumber;
    this.validOptionNumbers = validOptionNumbers;
  }

  private void performSanityCheckForInput(OptionNumber correctOptionNumber, OptionNumber[] validOptionNumbers) throws IllegalArgumentException {
    if (Objects.isNull(correctOptionNumber)) {
      throw new IllegalArgumentException("Invalid correct answer: null");
    }

    if (Objects.isNull(validOptionNumbers)) {
      throw new IllegalArgumentException("Invalid answer options: null");
    }

    for (OptionNumber validOptionNumber : validOptionNumbers) {
      if (Objects.isNull(validOptionNumber)) {
        throw new IllegalArgumentException("Invalid correct answer: null");
      }
    }

    if (validOptionNumbers.length < 3) {
      throw new IllegalArgumentException(String.format("Question should have at least 3 options, found: %d", validOptionNumbers.length));
    }

    if (validOptionNumbers.length > 8) {
      throw new IllegalArgumentException(String.format("Question can have no more than 8 options, found: %d", validOptionNumbers.length));
    }
  }


  @Override
  public int compareTo(Question o) {
    return 0;
  }

  @Override
  protected Result eval(String answer) {
    return null;
  }
}
