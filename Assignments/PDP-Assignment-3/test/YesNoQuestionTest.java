import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import question.Question;
import question.YesNoQuestion;
import question.bean.Result;
import question.bean.YesNoQuestionAnswer;

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

    String[] options = question.getOptions();
    Assert.assertEquals(YesNoQuestionAnswer.YES.getAnswerString(), options[0]);
    Assert.assertEquals(YesNoQuestionAnswer.NO.getAnswerString(), options[1]);

    Assert.assertEquals(Result.CORRECT.getResultString(),
            question.evaluateAnswer(YesNoQuestionAnswer.YES.getAnswerString()));
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
      Assert.assertEquals("correct answer cannot be null", e.getMessage());
    }
    Assert.assertNull(question);
  }

  @Test
  public void testCaseInsensitivityOfAnswers() {
    Question question1 = getQuestionInstance();
    Assert.assertEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer("yes"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer("Yes"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer("yEs"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer("yeS"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer("YEs"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer("yES"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer("YeS"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer("YES"));

    Question question2 = new YesNoQuestion("Question-2?", YesNoQuestionAnswer.NO);
    Assert.assertEquals(Result.CORRECT.getResultString(), question2.evaluateAnswer("no"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question2.evaluateAnswer("No"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question2.evaluateAnswer("nO"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question2.evaluateAnswer("NO"));
  }

  @Override
  public void testCorrectAndIncorrectAnswer() {
    Question question1 = new YesNoQuestion("question-1?", YesNoQuestionAnswer.YES);
    Assert.assertEquals("question-1?", question1.getText());
    Assert.assertEquals(Result.CORRECT.getResultString(),
            question1.evaluateAnswer(YesNoQuestionAnswer.YES.getAnswerString()));

    Assert.assertEquals(Result.INCORRECT.getResultString(),
            question1.evaluateAnswer(YesNoQuestionAnswer.NO.getAnswerString()));

    Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer(null));

    Assert.assertNotEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer("random"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer("random"));

    Question question2 = new YesNoQuestion("question-2?", YesNoQuestionAnswer.NO);
    Assert.assertEquals("question-2?", question2.getText());

    Assert.assertEquals(Result.CORRECT.getResultString(),
            question2.evaluateAnswer(YesNoQuestionAnswer.NO.getAnswerString()));

    Assert.assertEquals(Result.INCORRECT.getResultString(),
            question2.evaluateAnswer(YesNoQuestionAnswer.YES.getAnswerString()));
  }

  @Override
  public void testSameQuestionTypeSorting() {

    Question question1 = new YesNoQuestion("Question-4?", YesNoQuestionAnswer.YES);
    Question question2 = new YesNoQuestion("Question-3?", YesNoQuestionAnswer.NO);
    Question question3 = new YesNoQuestion("Question-2?", YesNoQuestionAnswer.YES);
    Question question4 = new YesNoQuestion("Question-2?", YesNoQuestionAnswer.YES);
    Question question5 = new YesNoQuestion("Question-1?", YesNoQuestionAnswer.NO);

    Question[] questionArray =
            new Question[]{question1, question2, question3, question4, question5};
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
    Assert.assertEquals(Result.CORRECT.getResultString(),
            question.evaluateAnswer(YesNoQuestionAnswer.YES.getAnswerString()));

    Assert.assertEquals(Result.CORRECT.getResultString(),
            question.evaluateAnswer(YesNoQuestionAnswer.YES.getAnswerString()));

    Assert.assertEquals(Result.CORRECT.getResultString(),
            question.evaluateAnswer(YesNoQuestionAnswer.YES.getAnswerString()));

    Assert.assertEquals(Result.CORRECT.getResultString(),
            question.evaluateAnswer(YesNoQuestionAnswer.YES.getAnswerString()));
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

  @Override
  public void testQuestionObjectInequalityUsingCompareTo() {
    Question question1 = new YesNoQuestion("yes no question-2?", YesNoQuestionAnswer.NO);
    Question question2 = new YesNoQuestion("yes no question-1?", YesNoQuestionAnswer.NO);

    Assert.assertEquals(1, question1.compareTo(question2));
    Assert.assertEquals(-1, question2.compareTo(question1));
  }
}
