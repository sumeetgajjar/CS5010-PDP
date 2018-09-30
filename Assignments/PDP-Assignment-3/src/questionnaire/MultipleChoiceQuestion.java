package questionnaire;

public class MultipleChoiceQuestion extends AbstractQuestion {

  public MultipleChoiceQuestion(String text) {
    super(text);
  }

  @Override
  public String eval(String answer) {
    return null;
  }

  @Override
  public int compareTo(Question o) {
    return 0;
  }
}
