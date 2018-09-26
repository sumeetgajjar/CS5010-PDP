import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import calculator.Calculator;

/**
 * This class represents common tests that applies to both Simple And Smart Calculator. In order to
 * use this class the programmer needs to extend this and implement method
 * <tt>getCalculatorInstance</tt>.
 */
public abstract class AbstractCalculatorTest {

  protected abstract Calculator getCalculatorInstance();

  /**
   * test of check if the Object is initialized with proper initial values.
   */
  @Test
  public void testInitialization() {
    Calculator calculator = getCalculatorInstance();
    Assert.assertEquals(calculator.getResult(), "");
  }

  @Test
  public void testAllValidOperandOperatorCombinations() {
    char[] validOperands = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    char[] validOperators = new char[]{'+', '-', '*'};

    for (char input1 : validOperands) {
      for (char operator : validOperators) {
        for (char input2 : validOperands) {
          Calculator calculator = getCalculatorInstance();
          calculator = calculator.input(input1);
          Assert.assertEquals(String.format("%s", input1), calculator.getResult());

          calculator = calculator.input(operator);
          Assert.assertEquals(String.format("%s%s", input1, operator), calculator.getResult());

          calculator = calculator.input(input2);
          Assert.assertEquals(String.format("%s%s%s", input1, operator, input2),
                  calculator.getResult());

        }
      }
    }
  }

  @Test
  public void testMultipleClearInput() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());

  }

  @Test
  public void testClearInputWithFirstOperand() {
    Calculator calculator = getCalculatorInstance();

    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
  }

  @Test
  public void testClearInputWithFirstOperandOperator() {
    Calculator calculator = getCalculatorInstance();

    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('-');
    Assert.assertEquals("1-", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());

  }

  @Test
  public void testClearInputWithMultipleOperands() {
    Calculator calculator = getCalculatorInstance();

    calculator = calculator.input('4');
    Assert.assertEquals("4", calculator.getResult());
    calculator = calculator.input('-');
    Assert.assertEquals("4-", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("4-2", calculator.getResult());

    Calculator calculatorClear = calculator.input('C');
    Assert.assertEquals("", calculatorClear.getResult());
    calculatorClear = calculatorClear.input('C');
    Assert.assertEquals("", calculatorClear.getResult());
    calculatorClear = calculatorClear.input('C');
    Assert.assertEquals("", calculatorClear.getResult());

    Calculator calculator1 = calculator.input('+');
    Assert.assertEquals("4-2+", calculator1.getResult());

    calculator1 = calculator1.input('3');
    Assert.assertEquals("4-2+3", calculator1.getResult());

    Calculator calculatorClear2 = calculator1.input('C');
    Assert.assertEquals("", calculatorClear2.getResult());
    calculatorClear2 = calculatorClear2.input('C');
    Assert.assertEquals("", calculatorClear2.getResult());
    calculatorClear2 = calculatorClear2.input('C');
    Assert.assertEquals("", calculatorClear2.getResult());
  }

  @Test
  public void testClearInputAfterEqualTo() {
    Calculator calculator = getCalculatorInstance();

    calculator = calculator.input('4');
    Assert.assertEquals("4", calculator.getResult());
    calculator = calculator.input('-');
    Assert.assertEquals("4-", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("4-2", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("2", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
    calculator = calculator.input('C');
    Assert.assertEquals("", calculator.getResult());
  }

  @Test
  public void testIllegalInputAfterNthOperand() {
    char[] validOperands = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    char[] validOperators = new char[]{'+', '-', '*'};

    Calculator calculator = getCalculatorInstance();
    String expectedResult = "";
    Random random = new Random(1);
    for (int i = 0; i < 4; i++) {
      char operand = validOperands[random.nextInt(validOperands.length)];
      calculator = calculator.input(operand);
      expectedResult += operand;

      try {
        calculator = calculator.input('/');
        Assert.fail("should have failed");
      } catch (IllegalArgumentException e) {
        Assert.assertEquals("Input: '/' is illegal", e.getMessage());
      }

      try {
        calculator = calculator.input('a');
        Assert.fail("should have failed");
      } catch (IllegalArgumentException e) {
        Assert.assertEquals("Input: 'a' is illegal", e.getMessage());
      }
      Assert.assertEquals(expectedResult, calculator.getResult());

      char operator = validOperators[random.nextInt(validOperators.length)];
      calculator = calculator.input(operator);
      expectedResult += operator;
    }
  }

  @Test
  public void testFirstOperandGreaterThan32Bit() {
    Calculator calculator = getCalculatorInstance();
    try {
      String input = String.valueOf(("2147483648"));

      for (int i = 0; i < input.length(); i++) {
        calculator = calculator.input(input.charAt(i));
      }

      Assert.fail("Test passed for input greater than 32 bits");
    } catch (RuntimeException e) {
      Assert.assertEquals("Operand overflow: operand is greater than 32 bits",
              e.getMessage());
    }
    Assert.assertEquals("214748364", calculator.getResult());

    calculator = calculator.input('+');
    Assert.assertEquals("214748364+", calculator.getResult());

    calculator = calculator.input('1');
    Assert.assertEquals("214748364+1", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("214748365", calculator.getResult());
  }

  @Test
  public void testSecondOperandGreaterThan32Bit() {
    Calculator calculator1 = getCalculatorInstance();
    try {
      calculator1 = calculator1.input('1').input('+');

      String input = String.valueOf(("2147483648"));

      for (int i = 0; i < input.length(); i++) {
        calculator1 = calculator1.input(input.charAt(i));
      }

      Assert.fail("Test passed for input greater than 32 bits");
    } catch (RuntimeException e) {
      Assert.assertEquals("Operand overflow: operand is greater than 32 bits",
              e.getMessage());
    }
    Assert.assertEquals("1+214748364", calculator1.getResult());

    calculator1 = calculator1.input('=');
    Assert.assertEquals("214748365", calculator1.getResult());
  }

  @Test
  public void testMultiple32BitOperand() {
    String input = String.valueOf(Integer.MAX_VALUE);
    Calculator calculator = getCalculatorInstance();

    for (int i = 0; i < input.length(); i++) {
      calculator = calculator.input(input.charAt(i));
      Assert.assertEquals(input.substring(0, i + 1), calculator.getResult());
    }
    Assert.assertEquals("2147483647", calculator.getResult());

    calculator = calculator.input('+');
    Assert.assertEquals("2147483647+", calculator.getResult());

    for (int i = 0; i < input.length(); i++) {
      calculator = calculator.input(input.charAt(i));
      Assert.assertEquals("2147483647+" + input.substring(0, i + 1),
              calculator.getResult());
    }
    Assert.assertEquals("2147483647+2147483647", calculator.getResult());
    Calculator calculator1 = calculator.input('=');
    Assert.assertEquals("0", calculator1.getResult());

    calculator = calculator.input('+');
    Assert.assertEquals("2147483647+2147483647+", calculator.getResult());
    for (int i = 0; i < input.length(); i++) {
      calculator = calculator.input(input.charAt(i));
      Assert.assertEquals("2147483647+2147483647+" + input.substring(0, i + 1),
              calculator.getResult());
    }
    Assert.assertEquals("2147483647+2147483647+2147483647", calculator.getResult());
    Calculator calculator2 = calculator.input('=');
    Assert.assertEquals("2147483647", calculator2.getResult());
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

    calculator = calculator.input('+');
    Assert.assertEquals("-10+", calculator.getResult());

    calculator = calculator.input('1');
    Assert.assertEquals("-10+1", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("-9", calculator.getResult());

    calculator = calculator.input('*');
    Assert.assertEquals("-9*", calculator.getResult());

    calculator = calculator.input('3');
    Assert.assertEquals("-9*3", calculator.getResult());

    calculator = calculator.input('=');
    Assert.assertEquals("-27", calculator.getResult());


    Calculator calculatorSub = calculator
            .input('-')
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');

    Assert.assertEquals("-27-2147483647", calculatorSub.getResult());

    calculatorSub = calculatorSub.input('=');
    Assert.assertEquals("0", calculatorSub.getResult());

    Calculator calculatorMul = calculator
            .input('*')
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');

    Assert.assertEquals("-27*2147483647", calculatorMul.getResult());

    calculatorMul = calculatorMul.input('=');
    Assert.assertEquals("0", calculatorMul.getResult());
  }

  @Test
  public void testResultOverflowFirstOperand() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');

    Calculator calculatorAdd = calculator
            .input('+').input('1').input('0');
    Assert.assertEquals("2147483647+10", calculatorAdd.getResult());

    calculatorAdd = calculatorAdd.input('=');
    Assert.assertEquals("0", calculatorAdd.getResult());

    Calculator calculatorMul = calculator
            .input('*').input('2').input('0');
    Assert.assertEquals("2147483647*20", calculatorMul.getResult());

    calculatorMul = calculatorMul.input('=');
    Assert.assertEquals("0", calculatorMul.getResult());
  }

  @Test
  public void testResultOverflowSecondOperand() {
    Calculator calculator2 = getCalculatorInstance();

    Calculator calculator2Add = calculator2.input('1').input('+');
    Assert.assertEquals("1+", calculator2Add.getResult());

    calculator2Add = calculator2Add
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');
    Assert.assertEquals("1+2147483647", calculator2Add.getResult());

    calculator2Add = calculator2Add.input('=');
    Assert.assertEquals("0", calculator2Add.getResult());

    Calculator calculator2Mul = calculator2.input('2').input('*');
    calculator2Mul = calculator2Mul
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');
    Assert.assertEquals("2*2147483647", calculator2Mul.getResult());

    calculator2Mul = calculator2Mul.input('=');
    Assert.assertEquals("0", calculator2Mul.getResult());
  }

  @Test
  public void test1DigitOperations() {
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
  }

  @Test
  public void test2DigitOperations() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("12", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("12+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("12+1", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("12+10", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("22", calculator.getResult());

    calculator = calculator.input('-');
    Assert.assertEquals("22-", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("22-2", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("22-22", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("0", calculator.getResult());

    calculator = calculator.input('*');
    Assert.assertEquals("0*", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("0*1", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("0*10", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("0", calculator.getResult());
  }

  @Test
  public void test3DigitOperations() {
    Calculator calculator = getCalculatorInstance();
    calculator = calculator.input('1');
    Assert.assertEquals("1", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("12", calculator.getResult());
    calculator = calculator.input('3');
    Assert.assertEquals("123", calculator.getResult());
    calculator = calculator.input('+');
    Assert.assertEquals("123+", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("123+1", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("123+10", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("123+100", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("223", calculator.getResult());

    calculator = calculator.input('-');
    Assert.assertEquals("223-", calculator.getResult());
    calculator = calculator.input('2');
    Assert.assertEquals("223-2", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("223-20", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("223-200", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("23", calculator.getResult());

    calculator = calculator.input('*');
    Assert.assertEquals("23*", calculator.getResult());
    calculator = calculator.input('1');
    Assert.assertEquals("23*1", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("23*10", calculator.getResult());
    calculator = calculator.input('0');
    Assert.assertEquals("23*100", calculator.getResult());
    calculator = calculator.input('=');
    Assert.assertEquals("2300", calculator.getResult());
  }
}
