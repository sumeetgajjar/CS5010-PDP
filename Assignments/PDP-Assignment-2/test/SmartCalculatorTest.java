import org.junit.Assert;
import org.junit.Test;

import calculator.Calculator;
import calculator.SmartCalculator;

public class SmartCalculatorTest extends AbstractCalculatorTest {

  @Override
  protected Calculator getCalculatorInstance() {
    return new SmartCalculator();
  }

  @Test
  public void testInputSequenceBeginsWithOperator() {
    for (char input : new char[]{'+', '-', '*'}) {
      Calculator calculator = getCalculatorInstance();
      calculator = calculator.input(input);
      Assert.assertEquals("", calculator.getResult());

      calculator = calculator.input(input).input('2');
      Assert.assertEquals("2", calculator.getResult());
    }
  }

  @Test
  public void name() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1').input('0').input('-').input('1');
    Assert.assertEquals("10-1", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("9", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("8", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("7", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("6", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("5", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("4", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("3", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("2", calculator.getResult());
  }

  @Test
  public void testMultipleEqualsAfterExpression() {
    Calculator calculator = getCalculatorInstance();

    calculator = calculator.input('3');
    Assert.assertEquals("3", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("32", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("32", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("32+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("32+1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("33", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("34", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("35", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("36", calculator.getResult());

    calculator = calculator.input('-');
    Assert.assertEquals("36-", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("36-1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("35", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("34", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("33", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("32", calculator.getResult());

    calculator = calculator.input('*');
    Assert.assertEquals("32*", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("32*2", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("64", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("128", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("256", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("512", calculator.getResult());
  }

  @Test
  public void testSkippingSecondOperand() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('2');
    Assert.assertEquals("2", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("2+", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("4", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("6", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("8", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("10", calculator.getResult());

    Calculator calculator1 = getCalculatorInstance();
    calculator1 = calculator1.input('1');
    Assert.assertEquals("1", calculator1.getResult());
    calculator1 = calculator1.input('0');
    Assert.assertEquals("10", calculator1.getResult());
    calculator1 = calculator1.input('*');
    Assert.assertEquals("10*", calculator1.getResult());
    calculator1 = calculator1.input('=');
    Assert.assertEquals("100", calculator1.getResult());
    calculator1 = calculator1.input('=');
    Assert.assertEquals("1000", calculator1.getResult());
    calculator1 = calculator1.input('=');
    Assert.assertEquals("10000", calculator1.getResult());
    calculator1 = calculator1.input('=');
    Assert.assertEquals("100000", calculator1.getResult());

    Calculator calculator2 = getCalculatorInstance();
    calculator2 = calculator2.input('1');
    Assert.assertEquals("1", calculator2.getResult());
    calculator2 = calculator2.input('-');
    Assert.assertEquals("1-", calculator2.getResult());
    calculator2 = calculator2.input('=');
    Assert.assertEquals("0", calculator2.getResult());
    calculator2 = calculator2.input('=');
    Assert.assertEquals("-1", calculator2.getResult());
    calculator2 = calculator2.input('=');
    Assert.assertEquals("-2", calculator2.getResult());
    calculator2 = calculator2.input('=');
    Assert.assertEquals("-3", calculator2.getResult());
  }

  @Test
  public void testConsecutiveOperators() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('-');
    Assert.assertEquals("1-", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("1+", calculator.getResult());
    calculator = calculator.input('*');
    Assert.assertEquals("1*", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("1+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("1+1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("2", calculator.getResult());
  }

  @Test
  public void testOnlyEqualToOperator() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("1", calculator.getResult());

    try {
      calculator = calculator.input('1');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Input: '1' is illegal", e.getMessage());
    }

    calculator = calculator.input('=');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("1", calculator.getResult());
  }

  @Test
  public void testOperatorsAtBeginning() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('+');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('-');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('*');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('3');
    Assert.assertEquals("3", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("3+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("3+1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("4", calculator.getResult());
  }

  @Test
  public void testEqualToAfterExpressionEval() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("1+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("1+1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("2", calculator.getResult());

    try {
      calculator = calculator.input('2');
      Assert.fail("should have fail");
    } catch (Exception e) {
      Assert.assertEquals("Input: '2' is illegal", e.getMessage());
    }

    calculator = calculator.input('=');
    Assert.assertEquals("3", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("4", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("5", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("5+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("5+1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("6", calculator.getResult());
  }
}
