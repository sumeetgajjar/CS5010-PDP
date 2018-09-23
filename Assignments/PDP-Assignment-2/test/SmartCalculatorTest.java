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
  public void testCorrectSequence() {
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


    List<Pair<Character, String>> testSequences1 = new ArrayList<>();
    testSequences1.add(Pair.of('2', "2"));
    testSequences1.add(Pair.of('+', "2+"));
    testSequences1.add(Pair.of('=', "2"));
    testSequences1.add(Pair.of('=', "4"));
    testSequences1.add(Pair.of('=', "8"));
    testSequences1.add(Pair.of('=', "10"));

    testSequences1.add(Pair.of('*', "10*"));
    testSequences1.add(Pair.of('=', "100"));
    testSequences1.add(Pair.of('=', "1000"));
    testSequences1.add(Pair.of('=', "10000"));
    testSequences1.add(Pair.of('=', "100000"));

    testSequences1.add(Pair.of('-', "100000-"));
    testSequences1.add(Pair.of('=', "0"));
    testSequences1.add(Pair.of('=', "-100000"));
    testSequences1.add(Pair.of('=', "-200000"));
    testSequences1.add(Pair.of('=', "-300000"));

    executeSequencesAndVerifyResult(testSequences1);


    List<Pair<Character, String>> testSequences2 = new ArrayList<>();
    testSequences2.add(Pair.of('1', "1"));
    testSequences2.add(Pair.of('-', "1-"));
    testSequences2.add(Pair.of('+', "1+"));
    testSequences2.add(Pair.of('*', "1*"));
    testSequences2.add(Pair.of('+', "1+"));
    testSequences2.add(Pair.of('1', "1+1"));
    testSequences2.add(Pair.of('=', "2"));

    executeSequencesAndVerifyResult(testSequences2);


    List<Pair<Character, String>> testSequences3 = new ArrayList<>();
    testSequences3.add(Pair.of('1', "1"));
    testSequences3.add(Pair.of('=', "1"));
    testSequences3.add(Pair.of('2', "2"));
    testSequences3.add(Pair.of('=', "2"));
    testSequences3.add(Pair.of('3', "3"));
    testSequences3.add(Pair.of('=', "3"));

    executeSequencesAndVerifyResult(testSequences3);

  }
}
