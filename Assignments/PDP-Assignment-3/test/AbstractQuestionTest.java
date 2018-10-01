import org.junit.Assert;
import org.junit.Test;

import question.Question;
import question.bean.Result;

public abstract class AbstractQuestionTest {

  protected abstract Question getQuestionInstance();

  @Test
  public abstract void testInitializationOfQuestionObject();

  @Test
  public abstract void testInvalidConstructorArguments();

  @Test
  public abstract void testCorrectAndIncorrectAnswer();

  @Test
  public abstract void testSameQuestionTypeSorting();

  @Test
  public abstract void testOperationOnSameObjectMultipleTimes();

  @Test
  public abstract void testQuestionObjectInequalityUsingEquals();

  @Test
  public abstract void testQuestionObjectInequalityUsingHashcode();

  @Test
  public void testEvaluatingNullResult() {
    Question question = getQuestionInstance();
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer(null));
  }

  @Test
  public void testQuestionObjectEqualityUsingEquals() {
    Question question1 = getQuestionInstance();
    Question question2 = getQuestionInstance();
    Question question3 = getQuestionInstance();

    //Checking reflexivity
    Assert.assertTrue(question1.equals(question1));

    //Checking symmetry
    Assert.assertEquals(question1.equals(question2), question2.equals(question1));

    //Checking transitivity
    Assert.assertTrue(question1.equals(question2));
    Assert.assertTrue(question2.equals(question3));
    Assert.assertTrue(question1.equals(question3));
  }

  @Test
  public void testQuestionObjectEqualityUsingHashCode() {
    Question question1 = getQuestionInstance();
    Question question2 = getQuestionInstance();
    Question question3 = getQuestionInstance();

    Assert.assertEquals(question1.hashCode(), question2.hashCode());
    Assert.assertEquals(question2.hashCode(), question3.hashCode());
    Assert.assertEquals(question1.hashCode(), question3.hashCode());
  }
}
