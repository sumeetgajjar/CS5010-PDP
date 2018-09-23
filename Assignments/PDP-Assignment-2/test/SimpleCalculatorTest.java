import org.junit.Assert;
import org.junit.Test;

import calculator.Calculator;
import calculator.SimpleCalculator;

public class SimpleCalculatorTest extends AbstractCalculatorTest {

  @Override
  protected SimpleCalculator getCalculatorInstance() {
    return new SimpleCalculator();
  }

  @Test
  public void testInputSequenceBeginsWithOperator() {
    for (char input : new char[]{'=', '+', '-', '*'}) {
      Calculator calculator = getCalculatorInstance();
      try {
        calculator = calculator.input(input);

        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ignored) {
      }
      Assert.assertEquals("", calculator.getResult());
    }
  }

  @Test
  public void testIncorrectInputSequence() {
    Calculator calculator = getCalculatorInstance();

    try {
      calculator = calculator.input('3').input('2');
      calculator = calculator.input('=');

      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("32", calculator.getResult());

    try {
      calculator = calculator.input('-');
      calculator = calculator.input('+');

      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("32-", calculator.getResult());

    try {
      calculator = calculator.input('=');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("32-", calculator.getResult());

    try {
      calculator = calculator.input('*');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("32-", calculator.getResult());

    calculator = calculator.input('2').input('=');
    Assert.assertEquals("30", calculator.getResult());

    try {
      calculator = calculator.input('*');

      calculator = calculator.input('+');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("30*", calculator.getResult());

    try {
      calculator = calculator.input('=');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("30*", calculator.getResult());

    try {
      calculator = calculator.input('-');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("32*", calculator.getResult());

    calculator = calculator.input('1').input('=');
    Assert.assertEquals("32", calculator.getResult());

    try {
      calculator = calculator.input('+');
      calculator = calculator.input('-');

      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("32+", calculator.getResult());

    try {
      calculator = calculator.input('=');

      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("32+", calculator.getResult());

    try {
      calculator = calculator.input('*');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("32+", calculator.getResult());
  }

}
