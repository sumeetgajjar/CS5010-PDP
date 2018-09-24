import org.junit.Assert;
import org.junit.Test;

import calculator.Calculator;

public abstract class AbstractCalculatorTest {

  protected abstract Calculator getCalculatorInstance();

  @Test
  public void testInitialization() {
    Calculator calculator = getCalculatorInstance();
    Assert.assertEquals(calculator.getResult(), "");
  }

  @Test
  public void testInputOperandAndOperator() {
    char[] validInput = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    for (char input : validInput) {
      Calculator calculator = getCalculatorInstance();
      calculator = calculator.input(input);

      Assert.assertEquals(String.valueOf(input), calculator.getResult());
    }

    Calculator calculator1 = getCalculatorInstance();
    calculator1 = calculator1.input('C');
    Assert.assertEquals("", calculator1.getResult());

    Calculator calculator2 = getCalculatorInstance();
    calculator2 = calculator2.input('1');
    Assert.assertEquals("1", calculator2.getResult());

    try {
      calculator2 = calculator2.input('a');
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(), "Input: 'a' is illegal");
    }
    Assert.assertEquals("1", calculator2.getResult());

    try {
      calculator2 = calculator2.input('/');
    } catch (Exception e) {
      Assert.assertEquals(e.getMessage(), "Input: '/' is illegal");
    }
    Assert.assertEquals("1", calculator2.getResult());
  }

  @Test
  public void testOperandGreaterThan32Bit() {
    try {
      String input = String.valueOf((1L + Integer.MAX_VALUE));
      Calculator calculator = getCalculatorInstance();

      for (int i = 0; i < input.length(); i++) {
        calculator = calculator.input(input.charAt(i));
      }

      Assert.fail("Test passed for input greater than 32 bits");
    } catch (RuntimeException e) {
      Assert.assertEquals("Operand overflow: operand is greater than 32 bits", e.getMessage());
    }

    try {
      Calculator calculator = getCalculatorInstance();
      calculator = calculator.input('1').input('+');

      String input = String.valueOf((1L + Integer.MAX_VALUE));

      for (int i = 0; i < input.length(); i++) {
        calculator = calculator.input(input.charAt(i));
      }

      Assert.fail("Test passed for input greater than 32 bits");
    } catch (RuntimeException e) {
      Assert.assertEquals("Operand overflow: operand is greater than 32 bits", e.getMessage());
    }
  }

  @Test
  public void test32BitOperand() {
    String input = String.valueOf(Integer.MAX_VALUE);
    Calculator calculator = getCalculatorInstance();

    for (int i = 0; i < input.length(); i++) {
      calculator = calculator.input(input.charAt(i));
    }
    Assert.assertEquals("2147483647", calculator.getResult());
  }

  @Test
  public void testNegativeOperand() {
    Calculator calculator = getCalculatorInstance();
    try {
      calculator = calculator.input('-');
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(), "Input: '-' is illegal");
    }
    Assert.assertEquals("", calculator.getResult());
  }

  @Test
  public void testMultipleOperationsOnSameObject() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1').input('+').input('9');
    Assert.assertEquals("1+9", calculator.getResult());

    Calculator calculator1 = calculator.input('=');
    Assert.assertEquals("10", calculator1.getResult());

    Calculator calculator2 = calculator.input('=');
    Assert.assertEquals("10", calculator2.getResult());
  }

  @Test
  public void testNegativeResult() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1').input('0').input('-').input('2').input('0').input('=');
    Assert.assertEquals("-10", calculator.getResult());

    calculator = calculator
            .input('-')
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');

    calculator = calculator.input('+').input('1').input('=');
    Assert.assertEquals("1", calculator.getResult());
  }

  @Test
  public void testResultOverflow() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');

    calculator = calculator
            .input('+').input('1').input('0').input('=');
    Assert.assertEquals("0", calculator.getResult());

    calculator = calculator
            .input('+').input('1').input('0');
    Assert.assertEquals("0+10", calculator.getResult());

    calculator = calculator.input('+')
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');
    Assert.assertEquals("0+10+2147483647", calculator.getResult());

    calculator = calculator.input('+').input('8');
    Assert.assertEquals("0+10+2147483647+8", calculator.getResult());


    calculator = calculator.input('=');
    Assert.assertEquals("8", calculator.getResult());
  }

  @Test
  public void testMultipleOperators() {
    Calculator calculator = getCalculatorInstance();

    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("10", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("10+", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("10+2", calculator.getResult());
    calculator = calculator.input('-');
    Assert.assertEquals("10+2-", calculator.getResult());
    calculator = calculator.input('4');
    Assert.assertEquals("10+2-4", calculator.getResult());
    calculator = calculator.input('*');
    Assert.assertEquals("10+2-4*", calculator.getResult());
    calculator = calculator.input('3');
    Assert.assertEquals("10+2-4*3", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("10+2-4*3+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("10+2-4*3+1", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("25", calculator.getResult());
  }

  @Test
  public void testBasicOperations() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('8');
    Assert.assertEquals("8", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("8+", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("8+2", calculator.getResult());
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
    calculator = calculator.input('2');
    Assert.assertEquals("6*2", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("12", calculator.getResult());

    Calculator calculator1 = getCalculatorInstance();
    calculator1 = calculator1.input('1');
    Assert.assertEquals("1", calculator1.getResult());
    calculator1 = calculator1.input('2');
    Assert.assertEquals("12", calculator1.getResult());
    calculator1 = calculator1.input('+');
    Assert.assertEquals("12+", calculator1.getResult());
    calculator1 = calculator1.input('1');
    Assert.assertEquals("12+1", calculator1.getResult());
    calculator1 = calculator1.input('0');
    Assert.assertEquals("12+10", calculator1.getResult());
    calculator1 = calculator1.input('=');
    Assert.assertEquals("22", calculator1.getResult());

    calculator1 = calculator1.input('-');
    Assert.assertEquals("22-", calculator1.getResult());
    calculator1 = calculator1.input('2');
    Assert.assertEquals("22-2", calculator1.getResult());
    calculator1 = calculator1.input('2');
    Assert.assertEquals("22-22", calculator1.getResult());
    calculator1 = calculator1.input('=');
    Assert.assertEquals("0", calculator1.getResult());

    calculator1 = calculator1.input('*');
    Assert.assertEquals("0*", calculator1.getResult());
    calculator1 = calculator1.input('1');
    Assert.assertEquals("0*1", calculator1.getResult());
    calculator1 = calculator1.input('0');
    Assert.assertEquals("0*10", calculator1.getResult());
    calculator1 = calculator1.input('=');
    Assert.assertEquals("0", calculator1.getResult());

    Calculator calculator2 = getCalculatorInstance();
    calculator2 = calculator2.input('1');
    Assert.assertEquals("1", calculator2.getResult());
    calculator2 = calculator2.input('2');
    Assert.assertEquals("12", calculator2.getResult());
    calculator2 = calculator2.input('3');
    Assert.assertEquals("123", calculator2.getResult());
    calculator2 = calculator2.input('+');
    Assert.assertEquals("123+", calculator2.getResult());
    calculator2 = calculator2.input('1');
    Assert.assertEquals("123+1", calculator2.getResult());
    calculator2 = calculator2.input('0');
    Assert.assertEquals("123+10", calculator2.getResult());
    calculator2 = calculator2.input('0');
    Assert.assertEquals("123+100", calculator2.getResult());
    calculator2 = calculator2.input('=');
    Assert.assertEquals("223", calculator2.getResult());

    calculator2 = calculator2.input('-');
    Assert.assertEquals("223-", calculator2.getResult());
    calculator2 = calculator2.input('2');
    Assert.assertEquals("223-2", calculator2.getResult());
    calculator2 = calculator2.input('0');
    Assert.assertEquals("223-20", calculator2.getResult());
    calculator2 = calculator2.input('0');
    Assert.assertEquals("223-200", calculator2.getResult());
    calculator2 = calculator2.input('=');
    Assert.assertEquals("23", calculator2.getResult());

    calculator2 = calculator2.input('*');
    Assert.assertEquals("23*", calculator2.getResult());
    calculator2 = calculator2.input('1');
    Assert.assertEquals("23*1", calculator2.getResult());
    calculator2 = calculator2.input('0');
    Assert.assertEquals("23*10", calculator2.getResult());
    calculator2 = calculator2.input('0');
    Assert.assertEquals("23*100", calculator2.getResult());
    calculator2 = calculator2.input('=');
    Assert.assertEquals("2300", calculator2.getResult());

    Calculator calculator3 = getCalculatorInstance();
    calculator3 = calculator3.input('C');
    Assert.assertEquals("", calculator3.getResult());
    calculator3 = calculator3.input('C');
    Assert.assertEquals("", calculator3.getResult());
    calculator3 = calculator3.input('C');
    Assert.assertEquals("", calculator3.getResult());
    calculator3 = calculator3.input('1');
    Assert.assertEquals("1", calculator3.getResult());
    calculator3 = calculator3.input('C');
    Assert.assertEquals("", calculator3.getResult());
    calculator3 = calculator3.input('1');
    Assert.assertEquals("1", calculator3.getResult());
    calculator3 = calculator3.input('-');
    Assert.assertEquals("1-", calculator3.getResult());
    calculator3 = calculator3.input('C');
    Assert.assertEquals("", calculator3.getResult());
    calculator3 = calculator3.input('4');
    Assert.assertEquals("4", calculator3.getResult());
    calculator3 = calculator3.input('-');
    Assert.assertEquals("4-", calculator3.getResult());
    calculator3 = calculator3.input('2');
    Assert.assertEquals("4-2", calculator3.getResult());
    calculator3 = calculator3.input('=');
    Assert.assertEquals("2", calculator3.getResult());
    calculator3 = calculator3.input('C');
    Assert.assertEquals("", calculator3.getResult());
    calculator3 = calculator3.input('C');
    Assert.assertEquals("", calculator3.getResult());
    calculator3 = calculator3.input('C');
    Assert.assertEquals("", calculator3.getResult());
    calculator3 = calculator3.input('C');
    Assert.assertEquals("", calculator3.getResult());
  }
}
