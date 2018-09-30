import org.junit.Assert;
import org.junit.Test;

import questionnaire.Question;

public abstract class AbstractQuestionTest {

  protected abstract Question getQuestionInstance();

  @Test
  public void testQuestionEqualityAndInEquality() {
    Question question1 = getQuestionInstance();
    Question question2 = getQuestionInstance();
    Question question3 = getQuestionInstance();

    Question question4 = getQuestionInstance();
    Question question5 = getQuestionInstance();
    Question question6 = getQuestionInstance();

    //Checking reflexivity
    Assert.assertEquals(question1, question1);

    //Checking symmetry
    Assert.assertEquals(question1.equals(question2), question2.equals(question1));

    //Checking transitivity
    Assert.assertTrue(question1.equals(question2));
    Assert.assertTrue(question2.equals(question3));
    Assert.assertTrue(question1.equals(question3));

    //checking same question different answer
    Assert.assertFalse(question1.equals(question4));

    //checking different question same answer
    Assert.assertFalse(question1.equals(question5));

    //checking different question different answer
    Assert.assertFalse(question1.equals(question6));
  }
}
