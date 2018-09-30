import org.junit.Assert;

import java.util.Arrays;

import questionnaire.LikertQuestion;
import questionnaire.Question;
import questionnaire.bean.AnswerStatus;
import questionnaire.bean.LikertScale;

public class LikertQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    return new LikertQuestion("question-1?");
  }

  @Override
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());
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
    for (LikertScale likertScale : LikertScale.values()) {
      Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(likertScale.getLikerScaleString()));
    }

    Assert.assertNotEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("random"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("random"));
  }

  @Override
  public void testSameQuestionTypeSorting() {
    Question question1 = new LikertQuestion("Question-4?");
    Question question2 = new LikertQuestion("Question-3?");
    Question question3 = new LikertQuestion("Question-2?");
    Question question4 = new LikertQuestion("Question-2?");
    Question question5 = new LikertQuestion("Question-1?");

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
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(LikertScale.AGREE.getLikerScaleString()));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(LikertScale.AGREE.getLikerScaleString()));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(LikertScale.AGREE.getLikerScaleString()));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(LikertScale.AGREE.getLikerScaleString()));
  }
}
