import org.junit.Assert;

import java.util.Arrays;

import questionnaire.Question;
import questionnaire.YesNoQuestion;
import questionnaire.bean.Result;
import questionnaire.bean.YesNoQuestionAnswer;

public class YesNoQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    return new YesNoQuestion("question-1?", YesNoQuestionAnswer.YES);
  }

  /**
   * Test to check the initialization of the Question Object.
   */
  @Override
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(YesNoQuestionAnswer.YES.getAnswerString()));
  }

  @Override
  public void testInvalidConstructorArguments() {
    Question question = null;
    try {
      question = new YesNoQuestion("", YesNoQuestionAnswer.YES);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new YesNoQuestion(null, YesNoQuestionAnswer.YES);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new YesNoQuestion(null, null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new YesNoQuestion("Question 1", null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correct answer: null", e.getMessage());
    }
    Assert.assertNull(question);
  }

  @Override
  public void testCorrectAndIncorrectAnswer() {
    Question question1 = new YesNoQuestion("question-1?", YesNoQuestionAnswer.YES);
    Assert.assertEquals("question-1?", question1.getText());
    Assert.assertEquals(Result.CORRECT.getResultString(), question1.eval(YesNoQuestionAnswer.YES.getAnswerString()));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question1.eval(YesNoQuestionAnswer.NO.getAnswerString()));

    Assert.assertNotEquals(Result.CORRECT.getResultString(), question1.eval("random"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question1.eval("random"));

    Question question2 = new YesNoQuestion("question-2?", YesNoQuestionAnswer.NO);
    Assert.assertEquals("question-2?", question2.getText());
    Assert.assertEquals(Result.CORRECT.getResultString(), question2.eval(YesNoQuestionAnswer.NO.getAnswerString()));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question2.eval(YesNoQuestionAnswer.YES.getAnswerString()));
  }

  @Override
  public void testSameQuestionTypeSorting() {

    Question question1 = new YesNoQuestion("Question-4?", YesNoQuestionAnswer.YES);
    Question question2 = new YesNoQuestion("Question-3?", YesNoQuestionAnswer.NO);
    Question question3 = new YesNoQuestion("Question-2?", YesNoQuestionAnswer.YES);
    Question question4 = new YesNoQuestion("Question-2?", YesNoQuestionAnswer.YES);
    Question question5 = new YesNoQuestion("Question-1?", YesNoQuestionAnswer.NO);

    Question[] questionArray = new Question[]{question1, question2, question3, question4, question5};
    Arrays.sort(questionArray);

    Assert.assertEquals(questionArray[0], question5);
    Assert.assertEquals(questionArray[1], question4);
    Assert.assertEquals(questionArray[2], question3);
    Assert.assertEquals(questionArray[3], question2);
    Assert.assertEquals(questionArray[4], question1);
  }

  @Override
  public void testOperationOnSameObjectMultipleTimes() {
    Question question = getQuestionInstance();
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(YesNoQuestionAnswer.YES.getAnswerString()));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(YesNoQuestionAnswer.YES.getAnswerString()));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(YesNoQuestionAnswer.YES.getAnswerString()));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(YesNoQuestionAnswer.YES.getAnswerString()));
  }

  @Override
  public void testQuestionObjectInequalityUsingEquals() {
    Question question1 = new YesNoQuestion("yes no question-2?", YesNoQuestionAnswer.NO);
    Question question2 = new YesNoQuestion("yes no question-1?", YesNoQuestionAnswer.NO);

    Assert.assertFalse(question1.equals(question2));
    Assert.assertFalse(question2.equals(question1));
  }

  @Override
  public void testQuestionObjectInequalityUsingHashcode() {
    Question question1 = new YesNoQuestion("yes no question-2?", YesNoQuestionAnswer.NO);
    Question question2 = new YesNoQuestion("yes no question-1?", YesNoQuestionAnswer.NO);

    Assert.assertNotEquals(question1.hashCode(), question2.hashCode());
  }
}
