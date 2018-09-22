package calculator;

public class SmartCalculator implements Calculator {
  @Override
  public Calculator input(char input) {
    return new SmartCalculator();
  }

  @Override
  public String getResult() {
    return "";
  }
}
