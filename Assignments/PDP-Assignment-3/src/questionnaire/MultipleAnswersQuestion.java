package questionnaire;

public class MultipleAnswersQuestion extends AbstractQuestion {

  public MultipleAnswersQuestion(String text) {
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
