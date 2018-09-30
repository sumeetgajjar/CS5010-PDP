import org.junit.Assert;

import java.util.Arrays;

import questionnaire.Question;
import questionnaire.YesOrNoQuestion;
import questionnaire.bean.AnswerStatus;
import questionnaire.bean.YesNoQuestionAnswer;

public class YesOrNoQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    return new YesOrNoQuestion("question-1?", YesNoQuestionAnswer.YES);
  }

  /**
   * Test to check the initialization of the Question Object.
   */
  @Override
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(YesNoQuestionAnswer.YES.getAnswerString()));
  }

  @Override
  public void testInvalidConstructorArguments() {
    Question question = null;
    try {
      question = new YesOrNoQuestion("", YesNoQuestionAnswer.YES);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new YesOrNoQuestion(null, YesNoQuestionAnswer.YES);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new YesOrNoQuestion(null, null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new YesOrNoQuestion("Question 1", null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correct answer: null", e.getMessage());
    }
    Assert.assertNull(question);
  }

  @Override
  public void testCorrectAndIncorrectAnswer() {
    Question question1 = new YesOrNoQuestion("question-1?", YesNoQuestionAnswer.YES);
    Assert.assertEquals("Is Object Initialized?", question1.getText());
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question1.eval(YesNoQuestionAnswer.YES.getAnswerString()));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question1.eval(YesNoQuestionAnswer.NO.getAnswerString()));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question1.eval("random"));

    Question question2 = new YesOrNoQuestion("question-2?", YesNoQuestionAnswer.NO);
    Assert.assertEquals("question-2?", question2.getText());
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question2.eval(YesNoQuestionAnswer.NO.getAnswerString()));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question2.eval(YesNoQuestionAnswer.YES.getAnswerString()));
  }

  @Override
  public void testSameQuestionTypeSorting() {

    Question question1 = new YesOrNoQuestion("Question-4?", YesNoQuestionAnswer.YES);
    Question question2 = new YesOrNoQuestion("Question-3?", YesNoQuestionAnswer.NO);
    Question question3 = new YesOrNoQuestion("Question-2?", YesNoQuestionAnswer.YES);
    Question question4 = new YesOrNoQuestion("Question-2?", YesNoQuestionAnswer.YES);
    Question question5 = new YesOrNoQuestion("Question-1?", YesNoQuestionAnswer.NO);

    Question[] questionArray = new Question[]{question1, question2, question3, question4, question5};
    Arrays.sort(questionArray);

    Assert.assertEquals(questionArray[0], question5);
    Assert.assertEquals(questionArray[1], question4);
    Assert.assertEquals(questionArray[2], question3);
    Assert.assertEquals(questionArray[3], question2);
    Assert.assertEquals(questionArray[4], question1);
  }
}
