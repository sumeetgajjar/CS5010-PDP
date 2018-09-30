package questionnaire;

public class YesOrNoQuestion extends AbstractQuestion {

  public YesOrNoQuestion(String text) {
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
