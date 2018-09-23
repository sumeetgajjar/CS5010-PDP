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
  public void testBasicOperations() {
    List<Pair<List<Character>, List<String>>> testSequences = new ArrayList<>();
    testSequences.add(new Pair<>(Arrays.asList('8', '+', '2', '='),
            Arrays.asList("8", "8+", "8+2", "10")));

    testSequences.add(Pair.of(Arrays.asList('-', '4', '='),
            Arrays.asList("10-", "10-4", "6")));

    testSequences.add(Pair.of(Arrays.asList('*', '2', '='),
            Arrays.asList("6*", "6*2", "12")));

    testSequences.add(Pair.of(Arrays.asList('='),
            Arrays.asList("12")));

    testSequences.add(Pair.of(Arrays.asList('=', '='),
            Arrays.asList("12")));

    testSequences.add(Pair.of(Arrays.asList('=', '=', '='),
            Arrays.asList("12")));

    testSequences.add(Pair.of(Arrays.asList('=', '=', '=', '='),
            Arrays.asList("12")));


    executeSequencesAndVerifyResult(testSequences);
  }

  private void executeSequencesAndVerifyResult(Pair<List<Character>, List<String>> testSequence) {
    executeSequencesAndVerifyResult(Collections.singletonList(testSequence));
  }

  private void executeSequencesAndVerifyResult(List<Pair<List<Character>, List<String>>> testSequences) throws IllegalStateException {
    Calculator calculator = new SimpleCalculator();

    for (Pair<List<Character>, List<String>> pair : testSequences) {
      List<Character> inputSequence = pair.first;
      List<String> resultSequence = pair.second;

      if (inputSequence.size() != resultSequence.size()) {
        throw new IllegalStateException("Size mismatch for input sequence and result sequence");
      }

      for (int i = 0; i < inputSequence.size(); i++) {
        calculator = calculator.input(inputSequence.get(i));

        String actualResult = calculator.getResult();
        String expectedResult = resultSequence.get(i);

        if (!expectedResult.equals(actualResult)) {
          throw new IllegalStateException(
                  String.format("Mismatch found. InputSequence: %s , Expected: %s , Actual: %s"
                          , pair.first, expectedResult, actualResult));
        }
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
}
