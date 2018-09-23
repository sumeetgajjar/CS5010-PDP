import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import calculator.Calculator;
import calculator.SimpleCalculator;
import calculator.bean.Pair;

public class SimpleCalculatorTest extends AbstractCalculatorTest {

  @Override
  protected SimpleCalculator getCalculatorInstance() {
    return new SimpleCalculator();
  }

  @Test
  public void testMultipleOperators() {
    List<Pair<Character, String>> testSequences = new ArrayList<>();
    testSequences.add(Pair.of('1', "1"));
    testSequences.add(Pair.of('0', "10"));
    testSequences.add(Pair.of('+', "10+"));
    testSequences.add(Pair.of('2', "10+2"));
    testSequences.add(Pair.of('-', "10+2-"));
    testSequences.add(Pair.of('4', "10+2-4"));
    testSequences.add(Pair.of('*', "10+2-4*"));
    testSequences.add(Pair.of('3', "10+2-4*3"));
    testSequences.add(Pair.of('+', "10+2-4*3+"));
    testSequences.add(Pair.of('1', "10+2-4*3+1"));
    testSequences.add(Pair.of('=', "25"));

    executeSequencesAndVerifyResult(testSequences);
  }

  @Test
  public void testIncorrectInputSequence() {
    Calculator calculator = getCalculatorInstance();
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

}
