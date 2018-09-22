package calculator;

public class SimpleCalculator implements Calculator {

  @Override
  public Calculator input(char input) {
    return new SimpleCalculator();
  }

  @Override
  public String getResult() {
    return "";
  }
}
