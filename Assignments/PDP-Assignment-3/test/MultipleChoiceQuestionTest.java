import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import questionnaire.MultipleChoiceQuestion;
import questionnaire.Question;
import questionnaire.YesOrNoQuestion;
import questionnaire.bean.AnswerStatus;
import questionnaire.bean.Option;
import questionnaire.bean.YesNoQuestionAnswer;

public class MultipleChoiceQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    Option[] options = new Option[]{
            Option.ONE, Option.TWO, Option.THREE, Option.FOUR,
            Option.FIVE, Option.SIX, Option.SEVEN, Option.EIGHT,
    };

    return new MultipleChoiceQuestion("question-1?", Option.ONE, options);
  }

  /**
   * Test to check the initialization of the Question Object.
   */
  @Test
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());
    Assert.assertEquals(Option.ONE.getOptionString(), question.eval("1"));
  }

  @Test
  public void testInvalidConstructorArguments() {
    Option[] options = new Option[]{
            Option.ONE, Option.TWO, Option.THREE, Option.FOUR,
            Option.FIVE, Option.SIX, Option.SEVEN, Option.EIGHT,
    };

    Question question = null;
    try {
      question = new MultipleChoiceQuestion("", Option.ONE, options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question Text: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion(null, Option.ONE, options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion("question-1?", null, options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctOption: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion("question-1?", Option.ONE, null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid answer choices: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion(null, null, null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);
  }

  @Test
  public void testInvalidAnswerChoices() {
    Question question2 = null;
    try {
      Option[] options = new Option[]{Option.ONE};
      question2 = new MultipleChoiceQuestion("question-2?", Option.ONE, options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question should have at least 3 options", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      Option[] options = new Option[]{
              Option.ONE, Option.TWO, Option.THREE, Option.FOUR,
              Option.FIVE, Option.SIX, Option.SEVEN, Option.EIGHT,
              Option.ONE};
      question2 = new MultipleChoiceQuestion("question-2?", Option.ONE, options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question can have no more than 8 options", e.getMessage());
    }
    Assert.assertNull(question2);
  }

  @Test
  public void testCorrectAndIncorrectAnswer() {
    Question question1 = new MultipleChoiceQuestion("question-1?", Option.FOUR, Option.values());
    Assert.assertEquals("Is Object Initialized?", question1.getText());
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question1.eval("Yes"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question1.eval("No"));

    Question question2 = new YesOrNoQuestion("question-2?", YesNoQuestionAnswer.NO);
    Assert.assertEquals("question-2?", question2.getText());
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question2.eval("No"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question2.eval("Yes"));
  }

  @Test
  public void testSameQuestionTypeSorting() {
    Option[] options = new Option[]{
            Option.ONE, Option.TWO, Option.THREE, Option.FOUR,
            Option.FIVE, Option.SIX, Option.SEVEN, Option.EIGHT,
    };

    Question question1 = new MultipleChoiceQuestion("question-4?", Option.FOUR, options);
    Question question2 = new MultipleChoiceQuestion("question-3?", Option.THREE, options);
    Question question3 = new MultipleChoiceQuestion("question-2?", Option.TWO, options);
    Question question4 = new MultipleChoiceQuestion("question-2?", Option.TWO, options);
    Question question5 = new MultipleChoiceQuestion("question-1?", Option.FOUR, options);


    Question[] questionArray = new Question[]{question1, question2, question3, question4, question5};
    Arrays.sort(questionArray);

    Assert.assertEquals(questionArray[0], question5);
    Assert.assertEquals(questionArray[1], question4);
    Assert.assertEquals(questionArray[2], question3);
    Assert.assertEquals(questionArray[3], question2);
    Assert.assertEquals(questionArray[4], question1);
  }
}
