import org.junit.Assert;

import java.util.Arrays;

import question.LikertQuestion;
import question.Question;
import question.bean.LikertScale;
import question.bean.Result;

public class LikertQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    return new LikertQuestion("question-1?");
  }

  @Override
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());

    String[] options = question.getOptions();
    Assert.assertEquals(LikertScale.STRONGLY_AGREE.getLikertScaleString(), options[0]);
    Assert.assertEquals(LikertScale.AGREE.getLikertScaleString(), options[1]);
    Assert.assertEquals(LikertScale.NEITHER_AGREE_NOR_DISAGREE.getLikertScaleString(), options[2]);
    Assert.assertEquals(LikertScale.DISAGREE.getLikertScaleString(), options[3]);
    Assert.assertEquals(LikertScale.STRONGLY_DISAGREE.getLikertScaleString(), options[4]);

    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1"));
  }

  @Override
  public void testInvalidConstructorArguments() {
    Question question = null;
    try {
      question = new LikertQuestion("");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new LikertQuestion(null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);
  }

  @Override
  public void testCorrectAndIncorrectAnswer() {
    Question question = getQuestionInstance();
    for (int i = 1; i <= 5; i++) {
      Assert.assertEquals(Result.CORRECT.getResultString(),
              question.evaluateAnswer(String.valueOf(i)));
    }

    Assert.assertNotEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("random"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("random"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("0"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("12"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("1 2"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer(null));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("-1"));
  }

  @Override
  public void testSameQuestionTypeSorting() {
    Question question1 = new LikertQuestion("Question-4?");
    Question question2 = new LikertQuestion("Question-3?");
    Question question3 = new LikertQuestion("Question-2?");
    Question question4 = new LikertQuestion("Question-2?");
    Question question5 = new LikertQuestion("Question-1?");

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
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1"));
  }

  @Override
  public void testQuestionObjectInequalityUsingEquals() {
    Question question1 = new LikertQuestion("question-1?");
    Question question2 = new LikertQuestion("question-2?");

    Assert.assertFalse(question1.equals(question2));
    Assert.assertFalse(question2.equals(question1));
  }

  @Override
  public void testQuestionObjectInequalityUsingHashcode() {
    Question question1 = new LikertQuestion("question-1?");
    Question question2 = new LikertQuestion("question-2?");

    Assert.assertNotEquals(question1.hashCode(), question2.hashCode());
  }

  @Override
  public void testQuestionObjectInequalityUsingCompareTo() {
    Question question1 = new LikertQuestion("question-2?");
    Question question2 = new LikertQuestion("question-1?");

    Assert.assertEquals(1, question1.compareTo(question2));
    Assert.assertEquals(-1, question2.compareTo(question1));
  }
}
