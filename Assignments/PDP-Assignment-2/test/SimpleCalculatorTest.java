import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
    List<Pair<List<Character>, String>> testSequences = new ArrayList<>();
    testSequences.add(new Pair<>(Arrays.asList('8', '+', '2', '='), "10"));
    testSequences.add(new Pair<>(Arrays.asList('-', '4', '='), "6"));
    testSequences.add(new Pair<>(Arrays.asList('*', '2', '='), "12"));
    testSequences.add(new Pair<>(Arrays.asList('='), "12"));
    testSequences.add(new Pair<>(Arrays.asList('=', '='), "12"));
    testSequences.add(new Pair<>(Arrays.asList('=', '=', '='), "12"));
    testSequences.add(new Pair<>(Arrays.asList('=', '=', '=', '='), "12"));

    executeSequencesAndVerifyResult(testSequences);
  }

  private void executeSequencesAndVerifyResult(List<Pair<List<Character>, String>> testSequences) throws IllegalStateException {
    Calculator calculator = new SimpleCalculator();

    for (Pair<List<Character>, String> pair : testSequences) {
      List<Character> sequences = pair.first;
      for (Character sequence : sequences) {
        calculator.input(sequence);
      }

      String expectedResult = pair.second;
      String actualResult = calculator.getResult();
      if (!expectedResult.equals(actualResult)) {
        throw new IllegalStateException(
                String.format("Mismatch found. InputSequence: %s , Expected: %s , Actual: %s"
                        , pair.first, expectedResult, actualResult));
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
      Assert.assertEquals(e.getMessage(), "Operand overflow: operand is greater than 32 bits");
    }
  }

  @Test
  public void test32BitOperand() {
    String input = String.valueOf((2 ^ 31) - 1);
    Calculator calculator = new SimpleCalculator();

    for (int i = 0; i < input.length(); i++) {
      calculator = calculator.input(input.charAt(i));
    }
  }

  @Test
  public void testNegativeOperand() {
    Calculator calculator = new SimpleCalculator();
    try {
      calculator = calculator.input('-');
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(), "Invalid Input");
    }
    Assert.assertEquals(calculator.getResult(), "");
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
