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
    } catch (Exception e) {
      Assert.assertEquals("", e.getMessage());
    }

    executeSequencesAndVerifyResult(Arrays.asList('1', '+', '3', '='),
            Arrays.asList("1", "1+", "1+3", "4"));
  }

  @Test
  public void testBasicOperations() {
    List<Character> inputSequence = new ArrayList<>();
    List<String> resultSequence = new ArrayList<>();

    inputSequence.addAll(Arrays.asList('8', '+', '2', '='));
    resultSequence.addAll(Arrays.asList("8", "8+", "8+2", "10"));

    inputSequence.addAll(Arrays.asList('-', '4', '='));
    resultSequence.addAll(Arrays.asList("10-", "10-4", "6"));

    inputSequence.addAll(Arrays.asList('*', '2', '='));
    resultSequence.addAll(Arrays.asList("6*", "6*2", "12"));

    inputSequence.addAll(Arrays.asList('='));
    resultSequence.addAll(Arrays.asList("12"));

    inputSequence.addAll(Arrays.asList('=', '='));
    resultSequence.addAll(Arrays.asList("12", "12"));

    inputSequence.addAll(Arrays.asList('=', '=', '='));
    resultSequence.addAll(Arrays.asList("12", "12", "12"));

    inputSequence.addAll(Arrays.asList('=', '=', '=', '='));
    resultSequence.addAll(Arrays.asList("12", "12", "12", "12"));

    executeSequencesAndVerifyResult(inputSequence, resultSequence);
  }

  private void executeSequencesAndVerifyResult(
          List<Character> inputSequence, List<String> resultSequence) throws IllegalStateException {

    Calculator calculator = new SimpleCalculator();

    if (inputSequence.size() != resultSequence.size()) {
      throw new IllegalStateException("Size mismatch for input sequence and result sequence");
    }

    for (int i = 0; i < inputSequence.size(); i++) {
      calculator = calculator.input(inputSequence.get(i));

      String actualResult = calculator.getResult();
      String expectedResult = resultSequence.get(i);

      if (!expectedResult.equals(actualResult)) {
        throw new IllegalStateException(
                String.format("Mismatch found at %d. InputSequence: %s , Expected: %s , Actual: %s"
                        , i + 1, inputSequence, expectedResult, actualResult));
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
