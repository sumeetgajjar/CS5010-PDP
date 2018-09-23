import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
  public void testExecuteSequencesAndVerifyResult() {
    try {
      executeSequencesAndVerifyResult(Collections.emptyList(),
              Collections.singletonList("1"));

      Assert.fail("Should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Size mismatch for input sequence and result sequence",
              e.getMessage());
    }

    try {
      executeSequencesAndVerifyResult(Collections.singletonList('1'),
              Arrays.asList("1", "2"));

      Assert.fail("Should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Size mismatch for input sequence and result sequence",
              e.getMessage());
    }


    try {
      executeSequencesAndVerifyResult(Collections.singletonList('1'),
              Collections.emptyList());

      Assert.fail("Should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Size mismatch for input sequence and result sequence",
              e.getMessage());
    }

    try {
      executeSequencesAndVerifyResult(Arrays.asList('1', '+', '3', '='),
              Arrays.asList("1", "1-", "1+3", "4"));

      Assert.fail("Should have failed");
    } catch (Exception ignored) {
    }

    executeSequencesAndVerifyResult(Arrays.asList('1', '+', '3', '='),
            Arrays.asList("1", "1+", "1+3", "4"));
  }

  @Test
  public void testBasicOperations() {
    List<Character> oneDigitInputSequence = new ArrayList<>(Arrays.asList(
            '8', '+', '2', '=',
            '-', '4', '=',
            '*', '2', '='));

    List<String> oneDigitResultSequence = new ArrayList<>(Arrays.asList(
            "8", "8+", "8+2", "10",
            "10-", "10-4", "6",
            "6*", "6*2", "12"));

    executeSequencesAndVerifyResult(oneDigitInputSequence, oneDigitResultSequence);

    List<Character> twoDigitInputSequence = new ArrayList<>(Arrays.asList(
            '1', '2', '+', '1', '0', '=',
            '-', '2', '2', '=',
            '*', '1', '0', '='));

    List<String> twoDigitResultSequence = new ArrayList<>(Arrays.asList(
            "1", "12", "12+", "12+1", "12+10", "22",
            "22-", "22-2", "22-22", "0",
            "0*", "0*1", "0*10", "0"));

    executeSequencesAndVerifyResult(twoDigitInputSequence, twoDigitResultSequence);

    List<Character> threeDigitInputSequence = new ArrayList<>(Arrays.asList(
            '1', '2', '3', '+', '1', '0', '0', '=',
            '-', '2', '0', '0', '=',
            '*', '1', '0', '0', '=');

    List<String> threeDigitResultSequence = new ArrayList<>(Arrays.asList(
            "1", "12", "123", "123+", "123+1", "123+10", "123+100", "223",
            "223-", "223-2", "223-20", "223-200", "23",
            "23*", "23*1", "23*10", "23*100", "2300"));

    executeSequencesAndVerifyResult(threeDigitInputSequence, threeDigitResultSequence);
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
  public void testClearButton() {
    List<Pair<List<Character>, List<String>>> testSequences = new ArrayList<>();
//    testSequences.add(Pair.of(Arrays.asList('1', '+', '2', 'C')))
  }
}
