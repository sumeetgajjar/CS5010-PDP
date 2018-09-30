package questionnaire;

public interface Question extends Comparable<Question> {

  String getText();

  String eval(String answer);
}
