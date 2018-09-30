package questionnaire;

public class LikertQuestion extends AbstractQuestion {

  public LikertQuestion(String text) {
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
