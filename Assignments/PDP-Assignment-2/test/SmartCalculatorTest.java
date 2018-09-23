import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import calculator.Calculator;
import calculator.SmartCalculator;
import calculator.bean.Pair;

public class SmartCalculatorTest extends AbstractCalculatorTest {

  @Override
  protected Calculator getCalculatorInstance() {
    return new SmartCalculator();
  }

  @Test
  public void testInputSequenceBeginsWithOperator() {
    for (char input : new char[]{'+', '-', '*'}) {
      Calculator calculator = getCalculatorInstance();
      calculator = calculator.input(input);
      Assert.assertEquals("", calculator.getResult());

      calculator = calculator.input(input).input('2');
      Assert.assertEquals("2", calculator.getResult());
    }
  }

  @Test
  public void testMultipleEqualsAfterExpression() {
    List<Pair<Character, String>> testSequences = new ArrayList<>();
    testSequences.add(Pair.of('=', ""));
    testSequences.add(Pair.of('3', "3"));
    testSequences.add(Pair.of('2', "32"));
    testSequences.add(Pair.of('=', "32"));
    testSequences.add(Pair.of('+', "32+"));
    testSequences.add(Pair.of('1', "32+1"));
    testSequences.add(Pair.of('=', "33"));
    testSequences.add(Pair.of('=', "34"));
    testSequences.add(Pair.of('=', "35"));
    testSequences.add(Pair.of('=', "36"));

    testSequences.add(Pair.of('-', "36-"));
    testSequences.add(Pair.of('1', "36-1"));
    testSequences.add(Pair.of('=', "35"));
    testSequences.add(Pair.of('=', "34"));
    testSequences.add(Pair.of('=', "33"));
    testSequences.add(Pair.of('=', "32"));

    testSequences.add(Pair.of('*', "32"));
    testSequences.add(Pair.of('2', "32*2"));
    testSequences.add(Pair.of('=', "64"));
    testSequences.add(Pair.of('=', "128"));
    testSequences.add(Pair.of('=', "256"));
    testSequences.add(Pair.of('=', "512"));

    executeSequencesAndVerifyResult(testSequences);
  }

  @Test
  public void testSkippingSecondOperand() {
    List<Pair<Character, String>> testSequences1 = new ArrayList<>();
    testSequences1.add(Pair.of('2', "2"));
    testSequences1.add(Pair.of('+', "2+"));
    testSequences1.add(Pair.of('=', "2"));
    testSequences1.add(Pair.of('=', "4"));
    testSequences1.add(Pair.of('=', "8"));
    testSequences1.add(Pair.of('=', "10"));

    executeSequencesAndVerifyResult(testSequences1);

    List<Pair<Character, String>> testSequences2 = new ArrayList<>();
    testSequences2.add(Pair.of('1', "1"));
    testSequences2.add(Pair.of('0', "10"));
    testSequences2.add(Pair.of('*', "10*"));
    testSequences2.add(Pair.of('=', "100"));
    testSequences2.add(Pair.of('=', "1000"));
    testSequences2.add(Pair.of('=', "10000"));
    testSequences2.add(Pair.of('=', "100000"));

    executeSequencesAndVerifyResult(testSequences2);

    List<Pair<Character, String>> testSequences3 = new ArrayList<>();
    testSequences2.add(Pair.of('1', "1"));
    testSequences2.add(Pair.of('-', "1-"));
    testSequences2.add(Pair.of('=', "0"));
    testSequences2.add(Pair.of('=', "-1"));
    testSequences2.add(Pair.of('=', "-2"));
    testSequences2.add(Pair.of('=', "-3"));

    executeSequencesAndVerifyResult(testSequences3);
  }

  @Test
  public void testConsecutiveOperators() {
    List<Pair<Character, String>> testSequences4 = new ArrayList<>();
    testSequences4.add(Pair.of('1', "1"));
    testSequences4.add(Pair.of('-', "1-"));
    testSequences4.add(Pair.of('+', "1+"));
    testSequences4.add(Pair.of('*', "1*"));
    testSequences4.add(Pair.of('+', "1+"));
    testSequences4.add(Pair.of('1', "1+1"));
    testSequences4.add(Pair.of('=', "2"));

    executeSequencesAndVerifyResult(testSequences4);
  }

  @Test
  public void testOnlyEqualToOperator() {
    List<Pair<Character, String>> testSequences = new ArrayList<>();

    testSequences.add(Pair.of('1', "1"));
    testSequences.add(Pair.of('=', "1"));
    testSequences.add(Pair.of('=', "1"));

    testSequences.add(Pair.of('1', "1"));
    testSequences.add(Pair.of('2', "12"));
    testSequences.add(Pair.of('=', "12"));
    testSequences.add(Pair.of('=', "12"));

    executeSequencesAndVerifyResult(testSequences);
  }

  @Test
  public void testMultipleEqualTo() {
    List<Pair<Character, String>> testSequences5 = new ArrayList<>();
    testSequences5.add(Pair.of('1', "1"));
    testSequences5.add(Pair.of('=', "1"));
    testSequences5.add(Pair.of('2', "2"));
    testSequences5.add(Pair.of('=', "2"));
    testSequences5.add(Pair.of('3', "3"));
    testSequences5.add(Pair.of('=', "3"));

    executeSequencesAndVerifyResult(testSequences5);
  }

  @Test
  public void testOperatorsAtBeginning() {
    List<Pair<Character, String>> testSequences = new ArrayList<>();
    testSequences.add(Pair.of('+', ""));
    testSequences.add(Pair.of('-', ""));
    testSequences.add(Pair.of('*', ""));
    testSequences.add(Pair.of('+', ""));
    testSequences.add(Pair.of('3', "3"));
    testSequences.add(Pair.of('+', "3+"));
    testSequences.add(Pair.of('1', "3+1"));
    testSequences.add(Pair.of('=', "4"));

    executeSequencesAndVerifyResult(testSequences);
  }
}
