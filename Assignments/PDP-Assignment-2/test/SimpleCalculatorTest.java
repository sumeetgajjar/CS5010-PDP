import org.junit.Assert;
import org.junit.Test;

import calculator.Calculator;
import calculator.SimpleCalculator;

public class SimpleCalculatorTest {
  @Test
  public void testInitialization() {
    Calculator calculator = new SimpleCalculator();
    Assert.assertEquals(calculator.getResult(), "");
  }

  @Test
  public void testOperandGreaterThan32Bit() {
    try {
      String input = String.valueOf((2 ^ 31));
      Calculator calculator = new SimpleCalculator();

      for (int i = 0; i < input.length(); i++) {
        calculator = calculator.input(input.charAt(i));
      }

      Assert.fail("Test passed for input greater than 32 bits");
    } catch (RuntimeException e) {
      Assert.assertEquals(e.getMessage(), "Operand overflow: operand is greater than 32 bits");
    }
  }

  @Test
  public void test32BitOperand() {
    try {
      String input = String.valueOf((2 ^ 31) - 1);
      Calculator calculator = new SimpleCalculator();

      for (int i = 0; i < input.length(); i++) {
        calculator = calculator.input(input.charAt(i));
      }
    } catch (Exception ignored) {
      Assert.fail("Test failed for input equal to 32 bits");
    }
  }

  @Test
  public void testCorrectInputSequence() {
    Calculator calculator = new SimpleCalculator();
    calculator = calculator.input('3');
    Assert.assertEquals(calculator.getResult(), "3");

    calculator = calculator.input('4');
    Assert.assertEquals(calculator.getResult(), "34");

    calculator = calculator.input('+');
    Assert.assertEquals(calculator.getResult(), "34+");

    calculator = calculator.input('2');
    Assert.assertEquals(calculator.getResult(), "34+2");

    calculator = calculator.input('4');
    Assert.assertEquals(calculator.getResult(), "34+24");

    calculator = calculator.input('=');
    Assert.assertEquals(calculator.getResult(), "56");
  }

  @Test
  public void testIncorrectInputSequence() {
    Calculator calculator = new SimpleCalculator();

  }
}
