import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import calculator.Calculator;
import calculator.SimpleCalculator;
import calculator.bean.Pair;

public class SimpleCalculatorTest {

  @Test
  public void testInitialization() {
    Calculator calculator = new SimpleCalculator();
    Assert.assertEquals(calculator.getResult(), "");
  }

  @Test
  public void testValidOperandAndOperator() {
    char[] validInput = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '+', '-', '*', '=', 'C'};

    for (char input : validInput) {
      Calculator calculator = new SimpleCalculator();
      calculator.input(input);

      Assert.assertEquals(String.valueOf(input), calculator.getResult());
    }
  }

  @Test
  public void testExecuteSequencesAndVerifyResult() {
    try {
      executeSequencesAndVerifyResult(Collections.emptyList());
      Assert.fail("Should have failed");
    } catch (Exception ignored) {
    }

    try {
      List<Pair<Character, String>> testSequences = new ArrayList<>();
      testSequences.add(Pair.of('1', "1"));
      testSequences.add(Pair.of('+', "1-"));
      testSequences.add(Pair.of('3', "1+3"));
      testSequences.add(Pair.of('=', "4"));

      executeSequencesAndVerifyResult(testSequences);

      Assert.fail("Should have failed");
    } catch (Exception ignored) {
    }

    List<Pair<Character, String>> testSequences = new ArrayList<>();
    testSequences.add(Pair.of('1', "1"));
    testSequences.add(Pair.of('+', "1+"));
    testSequences.add(Pair.of('3', "1+3"));
    testSequences.add(Pair.of('=', "4"));

    executeSequencesAndVerifyResult(testSequences);
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
      Assert.assertEquals("Operand overflow: operand is greater than 32 bits", e.getMessage());
    }
  }

  @Test
  public void test32BitOperand() {
    String input = String.valueOf((2 ^ 31) - 1);
    Calculator calculator = new SimpleCalculator();

    for (int i = 0; i < input.length(); i++) {
      calculator = calculator.input(input.charAt(i));
    }
    Assert.assertEquals("2147483647", calculator.getResult());
  }

  @Test
  public void testNegativeOperand() {
    Calculator calculator = new SimpleCalculator();
    try {
      calculator = calculator.input('-');
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(), "Invalid Input");
    }
    Assert.assertEquals("", calculator.getResult());
  }

  @Test
  public void testIncorrectInputOperand() {
    Calculator calculator = new SimpleCalculator();
    calculator.input('1');
    Assert.assertEquals(calculator.getResult(), "1");

    try {
      calculator.input('a');
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(), "Invalid Input");
    }
    Assert.assertEquals(calculator.getResult(), "1");

    try {
      calculator.input('/');
    } catch (Exception e) {
      Assert.assertEquals(e.getMessage(), "Invalid Input");
    }
    Assert.assertEquals(calculator.getResult(), "1");
  }

  @Test
  public void testMultipleOperators() {
    Calculator calculator = new SimpleCalculator();
    calculator = calculator.input('8').input('+').input('2')
            .input('-').input('2')
            .input('*').input('4');

    Assert.assertEquals("48", calculator.getResult());
  }

  @Test
  public void testMultipleOperationsOnSameObject() {
    Calculator calculator = new SimpleCalculator();
    calculator = calculator.input('1').input('+').input('9');
    Assert.assertEquals("1+9", calculator.getResult());

    Calculator calculator1 = calculator.input('=');
    Assert.assertEquals("10", calculator1.getResult());

    Calculator calculator2 = calculator.input('=');
    Assert.assertEquals("10", calculator2.getResult());
  }

  @Test
  public void testNegativeResult() {
    Calculator calculator = new SimpleCalculator();
    calculator = calculator.input('1').input('0').input('-').input('2').input('0').input('=');
    Assert.assertEquals("-20", calculator.getResult());

    calculator = calculator
            .input('-')
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');

    calculator = calculator.input('+').input('1');
    Assert.assertEquals("1", calculator.getResult());
  }

  @Test
  public void testResultOverflow() {
    Calculator calculator = new SimpleCalculator();
    calculator = calculator
            .input('2').input('1').input('4').input('7')
            .input('4').input('8').input('3').input('6')
            .input('4').input('7');

    Calculator calculator1 = calculator
            .input('+').input('1').input('0').input('=');
    Assert.assertEquals("0", calculator1.getResult());

    Calculator calculator2 = calculator
            .input('+').input('1').input('0')
            .input('+').input('1').input('1');
    Assert.assertEquals("11", calculator2.getResult());
  }

  @Test
  public void testIncorrectInputSequence() {
    Calculator calculator = new SimpleCalculator();
    try {
      calculator.input('=');

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

  @Test
  public void testBasicOperations() {
    List<Pair<Character, String>> oneDigitTestSequences = new ArrayList<>();
    oneDigitTestSequences.add(Pair.of('8', "8"));
    oneDigitTestSequences.add(Pair.of('+', "8+"));
    oneDigitTestSequences.add(Pair.of('2', "8+2"));
    oneDigitTestSequences.add(Pair.of('=', "10"));

    oneDigitTestSequences.add(Pair.of('-', "10-"));
    oneDigitTestSequences.add(Pair.of('4', "10-4"));
    oneDigitTestSequences.add(Pair.of('=', "6"));

    oneDigitTestSequences.add(Pair.of('*', "6*"));
    oneDigitTestSequences.add(Pair.of('2', "6*2"));
    oneDigitTestSequences.add(Pair.of('=', "12"));

    executeSequencesAndVerifyResult(oneDigitTestSequences);

    List<Pair<Character, String>> twoDigitTestSequences = new ArrayList<>();
    twoDigitTestSequences.add(Pair.of('1', "1"));
    twoDigitTestSequences.add(Pair.of('2', "12"));
    twoDigitTestSequences.add(Pair.of('+', "12+"));
    twoDigitTestSequences.add(Pair.of('1', "12+1"));
    twoDigitTestSequences.add(Pair.of('0', "12+10"));
    twoDigitTestSequences.add(Pair.of('=', "22"));

    twoDigitTestSequences.add(Pair.of('-', "22-"));
    twoDigitTestSequences.add(Pair.of('2', "22-2"));
    twoDigitTestSequences.add(Pair.of('2', "22-22"));
    twoDigitTestSequences.add(Pair.of('=', "0"));

    twoDigitTestSequences.add(Pair.of('*', "0*"));
    twoDigitTestSequences.add(Pair.of('1', "0*1"));
    twoDigitTestSequences.add(Pair.of('0', "0*10"));
    twoDigitTestSequences.add(Pair.of('=', "0"));

    executeSequencesAndVerifyResult(twoDigitTestSequences);

    List<Pair<Character, String>> threeDigitTestSequences = new ArrayList<>();
    threeDigitTestSequences.add(Pair.of('1', "1"));
    threeDigitTestSequences.add(Pair.of('2', "12"));
    threeDigitTestSequences.add(Pair.of('3', "123"));
    threeDigitTestSequences.add(Pair.of('+', "123+"));
    threeDigitTestSequences.add(Pair.of('1', "123+1"));
    threeDigitTestSequences.add(Pair.of('0', "123+10"));
    threeDigitTestSequences.add(Pair.of('0', "123+100"));
    threeDigitTestSequences.add(Pair.of('=', "223"));

    threeDigitTestSequences.add(Pair.of('-', "223-"));
    threeDigitTestSequences.add(Pair.of('2', "223-2"));
    threeDigitTestSequences.add(Pair.of('0', "223-20"));
    threeDigitTestSequences.add(Pair.of('0', "223-200"));
    threeDigitTestSequences.add(Pair.of('=', "23"));

    threeDigitTestSequences.add(Pair.of('*', "23*"));
    threeDigitTestSequences.add(Pair.of('1', "23*1"));
    threeDigitTestSequences.add(Pair.of('0', "23*10"));
    threeDigitTestSequences.add(Pair.of('0', "23*100"));
    threeDigitTestSequences.add(Pair.of('=', "2300"));

    executeSequencesAndVerifyResult(threeDigitTestSequences);

    List<Pair<Character, String>> clearInputTestSequences = new ArrayList<>();
    clearInputTestSequences.add(Pair.of('C', ""));
    clearInputTestSequences.add(Pair.of('C', ""));
    clearInputTestSequences.add(Pair.of('C', ""));
    clearInputTestSequences.add(Pair.of('1', "1"));
    clearInputTestSequences.add(Pair.of('C', ""));
    clearInputTestSequences.add(Pair.of('1', "1"));
    clearInputTestSequences.add(Pair.of('-', "1-"));
    clearInputTestSequences.add(Pair.of('C', ""));
    clearInputTestSequences.add(Pair.of('4', "4"));
    clearInputTestSequences.add(Pair.of('-', "4-"));
    clearInputTestSequences.add(Pair.of('2', "4-2"));
    clearInputTestSequences.add(Pair.of('=', "2"));
    clearInputTestSequences.add(Pair.of('C', ""));
    clearInputTestSequences.add(Pair.of('C', ""));
    clearInputTestSequences.add(Pair.of('C', ""));
    clearInputTestSequences.add(Pair.of('C', ""));

    executeSequencesAndVerifyResult(clearInputTestSequences);
  }

  private void executeSequencesAndVerifyResult(char input, String result) throws IllegalStateException {
    executeSequencesAndVerifyResult(Collections.singletonList(Pair.of(input, result)));
  }

  private void executeSequencesAndVerifyResult(List<Pair<Character, String>> sequencePairs) throws IllegalStateException {

    if (sequencePairs.size() == 0) {
      throw new IllegalStateException("sequencePairs size cannot be zero");
    }

    Calculator calculator = new SimpleCalculator();

    for (int i = 0; i < sequencePairs.size(); i++) {
      char input = sequencePairs.get(i).first;
      calculator = calculator.input(input);

      String actualResult = calculator.getResult();
      String expectedResult = sequencePairs.get(i).second;

      if (!expectedResult.equals(actualResult)) {
        throw new IllegalStateException(
                String.format("Mismatch found at Pair: %d . Expected Result: %s , Actual Result: %s"
                        , i + 1, expectedResult, actualResult));
      }
    }
  }
}
