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
  public void testNegativeOperand() {
    Calculator calculator = getCalculatorInstance();
    try {
      calculator = calculator.input('-');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(), "Input: '-' is illegal");
    }
    Assert.assertEquals("", calculator.getResult());

    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());

    calculator = calculator.input('+');
    Assert.assertEquals("1+", calculator.getResult());

    try {
      calculator = calculator.input('-');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(), "Input: '-' is illegal");
    }
    Assert.assertEquals("1+", calculator.getResult());
  }

  @Test
  public void testMultipleEqualAfterMultipleOperands() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("10", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("10+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("10+1", calculator.getResult());

    Calculator calculatorTwoOperand = calculator.input('=');
    Assert.assertEquals("11", calculatorTwoOperand.getResult());
    calculatorTwoOperand = calculatorTwoOperand.input('=');
    Assert.assertEquals("11", calculatorTwoOperand.getResult());
    calculatorTwoOperand = calculatorTwoOperand.input('=');
    Assert.assertEquals("11", calculatorTwoOperand.getResult());

    calculator = calculator.input('-');
    Assert.assertEquals("10+1-", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("10+1-2", calculator.getResult());
    Calculator calculatorThreeOperand = calculator.input('=');
    Assert.assertEquals("9", calculatorThreeOperand.getResult());
    calculatorThreeOperand = calculatorThreeOperand.input('=');
    Assert.assertEquals("9", calculatorThreeOperand.getResult());
    calculatorThreeOperand = calculatorThreeOperand.input('=');
    Assert.assertEquals("9", calculatorThreeOperand.getResult());

    Calculator calculatorFourOperand = calculator.input('*');
    Assert.assertEquals("10+1-2*", calculatorFourOperand.getResult());
    calculatorFourOperand = calculatorFourOperand.input('3');
    Assert.assertEquals("10+1-2*3", calculatorFourOperand.getResult());
    calculatorFourOperand = calculatorFourOperand.input('=');
    Assert.assertEquals("27", calculatorFourOperand.getResult());
    calculatorFourOperand = calculatorFourOperand.input('=');
    Assert.assertEquals("27", calculatorFourOperand.getResult());
    calculatorFourOperand = calculatorFourOperand.input('=');
    Assert.assertEquals("27", calculatorFourOperand.getResult());
  }

  @Test
  public void testInputBeginsWithOperator() {
    for (char input : new char[]{'=', '+', '-', '*'}) {
      Calculator calculator = getCalculatorInstance();
      try {
        calculator = calculator.input(input);

        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ignored) {
      }
      Assert.assertEquals("", calculator.getResult());

      calculator = calculator.input('1');
      Assert.assertEquals("1", calculator.getResult());
    }
  }

  @Test
  public void testAdditionForMultipleInputs() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1').input('+').input('2');
    Assert.assertEquals("1+2", calculator.getResult());
    Calculator calculator1 = calculator.input('=');
    Assert.assertEquals("3", calculator1.getResult());

    calculator = calculator.input('+').input('3');
    Assert.assertEquals("1+2+3", calculator.getResult());
    Calculator calculator2 = calculator.input('=');
    Assert.assertEquals("6", calculator2.getResult());

    calculator = calculator.input('+').input('4');
    Assert.assertEquals("1+2+3+4", calculator.getResult());
    Calculator calculator3 = calculator.input('=');
    Assert.assertEquals("10", calculator3.getResult());
  }


  @Test
  public void testSubtractionForMultipleInputs() {
    Calculator calculator = getCalculatorInstance();

    calculator = calculator.input('1').input('0').input('-').input('2');
    Assert.assertEquals("10-2", calculator.getResult());
    Calculator calculator1 = calculator.input('=');
    Assert.assertEquals("8", calculator1.getResult());

    calculator = calculator.input('-').input('3');
    Assert.assertEquals("10-2-3", calculator.getResult());
    Calculator calculator2 = calculator.input('=');
    Assert.assertEquals("5", calculator2.getResult());

    calculator = calculator.input('-').input('4');
    Assert.assertEquals("10-2-3-4", calculator.getResult());
    Calculator calculator3 = calculator.input('=');
    Assert.assertEquals("1", calculator3.getResult());
  }


  @Test
  public void testMultiplicationForMultipleInputs() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('6').input('*').input('7');
    Assert.assertEquals("6*7", calculator.getResult());
    Calculator calculator1 = calculator.input('=');
    Assert.assertEquals("42", calculator1.getResult());

    calculator = calculator.input('*').input('3');
    Assert.assertEquals("6*7*3", calculator.getResult());
    Calculator calculator2 = calculator.input('=');
    Assert.assertEquals("126", calculator2.getResult());

    calculator = calculator.input('*').input('4');
    Assert.assertEquals("6*7*3*4", calculator.getResult());
    Calculator calculator3 = calculator.input('=');
    Assert.assertEquals("504", calculator3.getResult());
  }

  @Test
  public void testIncorrectInputSequence() {
    Calculator calculator = getCalculatorInstance();

    try {
      calculator = calculator.input('=');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("", calculator.getResult());

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
    Assert.assertEquals("30*", calculator.getResult());

    calculator = calculator.input('1').input('=');
    Assert.assertEquals("30", calculator.getResult());

    try {
      calculator = calculator.input('+');
      calculator = calculator.input('-');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("30+", calculator.getResult());

    try {
      calculator = calculator.input('=');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("30+", calculator.getResult());

    try {
      calculator = calculator.input('*');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("30+", calculator.getResult());
  }

  @Test
  public void testOperatorOperandAfterResultForAllOperators() {
    Calculator calculator = getCalculatorInstance();

    calculator = calculator.input('9');
    Assert.assertEquals("9", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("9+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("9+1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("10", calculator.getResult());
    calculator = calculator.input('-');
    Assert.assertEquals("10-", calculator.getResult());
    calculator = calculator.input('4');
    Assert.assertEquals("10-4", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("6", calculator.getResult());
    calculator = calculator.input('*');
    Assert.assertEquals("6*", calculator.getResult());
    calculator = calculator.input('8');
    Assert.assertEquals("6*8", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("48", calculator.getResult());
    calculator = calculator.input('-');
    Assert.assertEquals("48-", calculator.getResult());
    calculator = calculator.input('7');
    Assert.assertEquals("48-7", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("41", calculator.getResult());
  }

  @Test
  public void testOperandAfterEqualForAllOperators() {
    Calculator calculator = getCalculatorInstance();

    calculator = calculator.input('9').input('+').input('1');
    Assert.assertEquals("9+1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("10", calculator.getResult());
    try {
      calculator = calculator.input('3');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("10", calculator.getResult());

    calculator = calculator.input('-').input('3');
    Assert.assertEquals("10-3", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("7", calculator.getResult());
    try {
      calculator = calculator.input('6');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("7", calculator.getResult());

    calculator = calculator.input('*').input('4');
    Assert.assertEquals("7*4", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("28", calculator.getResult());
    try {
      calculator = calculator.input('7');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("28", calculator.getResult());
  }
}
