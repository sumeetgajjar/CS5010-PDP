import org.junit.Assert;
import org.junit.Test;

import calculator.Calculator;
import calculator.SimpleCalculator;

/**
 * A junit class to test SimpleCalculator. It extends {@link AbstractCalculatorTest} and overrides
 * <tt>getCalculatorInstance()</tt>.
 */
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
  public void testOperatorFailureAfterOperandOperator() {
    char[] validOperands = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    char[] validOperators1 = new char[]{'+', '-', '*'};
    char[] validOperators2 = new char[]{'=', '+', '-', '*'};

    for (char operand : validOperands) {
      for (char operator1 : validOperators1) {
        Calculator calculator = getCalculatorInstance();
        calculator = calculator.input(operand).input(operator1);
        Assert.assertEquals(String.format("%s%s", operand, operator1), calculator.getResult());

        try {
          calculator = calculator.input('a');
          Assert.fail("should have failed");
        } catch (IllegalArgumentException e) {
          Assert.assertEquals(String.format("Input: '%s' is illegal", 'a'), e.getMessage());
        }

        for (char operator2 : validOperators2) {
          try {
            calculator = calculator.input(operator2);
            Assert.fail("should have failed");
          } catch (IllegalArgumentException e) {
            Assert.assertEquals(String.format("Input: '%s' is illegal", operator2), e.getMessage());
          }
        }
        Assert.assertEquals(String.format("%s%s", operand, operator1), calculator.getResult());
      }
    }
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

    calculator = calculator.input('+').input('4');
    Assert.assertEquals("28+4", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("32", calculator.getResult());
    try {
      calculator = calculator.input('7');
      Assert.fail("Should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertEquals("32", calculator.getResult());
  }
}
