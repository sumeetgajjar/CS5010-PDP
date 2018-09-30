import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import questionnaire.Question;
import questionnaire.YesOrNoQuestion;
import questionnaire.bean.AnswerStatus;
import questionnaire.bean.YesNoQuestionAnswer;

public class YesOrNoQuestionTest {

  /**
   * Test to check the initialization of the Question Object.
   */
  @Test
  public void testInitializationOfQuestionObject() {
    Question question = new YesOrNoQuestion("Is Object Initialized?", YesNoQuestionAnswer.YES);
    Assert.assertEquals("Is Object Initialized?", question.getText());
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("Yes"));
  }

  @Test
  public void testInvalidConstructorArguments() {
    Question question = null;
    try {
      question = new YesOrNoQuestion("", YesNoQuestionAnswer.YES);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid Question Text: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new YesOrNoQuestion(null, YesNoQuestionAnswer.YES);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid Question Text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new YesOrNoQuestion(null, null);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid Question Text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new YesOrNoQuestion("Question 1", null);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid Correct Answer: null", e.getMessage());
    }
    Assert.assertNull(question);
  }

  @Test
  public void testCorrectAndIncorrectAnswer() {
    Question question1 = new YesOrNoQuestion("question-1?", YesNoQuestionAnswer.YES);
    Assert.assertEquals("Is Object Initialized?", question1.getText());
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question1.eval("Yes"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question1.eval("No"));

    Question question2 = new YesOrNoQuestion("question-2?", YesNoQuestionAnswer.NO);
    Assert.assertEquals("question-2?", question2.getText());
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question2.eval("No"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question2.eval("Yes"));
  }

  @Test
  public void testQuestionEqualityAndInEquality() {
    Question question1 = new YesOrNoQuestion("Question-1?", YesNoQuestionAnswer.YES);
    Question question2 = new YesOrNoQuestion("Question-1?", YesNoQuestionAnswer.YES);
    Question question3 = new YesOrNoQuestion("Question-1?", YesNoQuestionAnswer.YES);

    Question question4 = new YesOrNoQuestion("Question-1?", YesNoQuestionAnswer.NO);
    Question question5 = new YesOrNoQuestion("Question-2?", YesNoQuestionAnswer.YES);
    Question question6 = new YesOrNoQuestion("Question-2?", YesNoQuestionAnswer.NO);

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

  @Test
  public void testSameQuestionTypeSorting() {

    Question question1 = new YesOrNoQuestion("Question-4?", YesNoQuestionAnswer.YES);
    Question question2 = new YesOrNoQuestion("Question-3?", YesNoQuestionAnswer.NO);
    Question question3 = new YesOrNoQuestion("Question-2?", YesNoQuestionAnswer.YES);
    Question question4 = new YesOrNoQuestion("Question-1?", YesNoQuestionAnswer.YES);

    Question[] questionArray = new Question[]{question1, question2, question3, question4};
    Arrays.sort(questionArray);

    Assert.assertEquals(questionArray[0], question4);
    Assert.assertEquals(questionArray[1], question3);
    Assert.assertEquals(questionArray[2], question2);
    Assert.assertEquals(questionArray[3], question1);
  }
}
