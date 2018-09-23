import calculator.Calculator;
import calculator.SmartCalculator;

public class SmartCalculatorTest extends AbstractCalculatorTest {

  @Override
  protected Calculator getCalculatorInstance() {
    return new SmartCalculator();
  }
}
