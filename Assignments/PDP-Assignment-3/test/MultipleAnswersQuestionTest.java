import org.junit.Assert;
import org.junit.Test;

import questionnaire.MultipleAnswersQuestion;
import questionnaire.Question;
import questionnaire.bean.Option;
import util.Utils;

public class MultipleAnswersQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    Option[] options = Utils.getAllValidOptionsForMultipleAnswersQuestion();
    return new MultipleAnswersQuestion("question-1?", "1 2 3", options);
  }

  @Override
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());
  }

  @Override
  public void testInvalidConstructorArguments() {
    Option[] options = Utils.getAllValidOptionsForMultipleAnswersQuestion();
    Question question = null;
    try {
      question = new MultipleAnswersQuestion("", "1 2 3", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question Text: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion(null, "1 2 3", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctOption: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", null, options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctOption: 'null'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 2 3", null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid answer options: null", e.getMessage());
    }
    Assert.assertNull(question);
  }

  @Override
  public void testCorrectAndIncorrectAnswer() {

  }

  @Override
  public void testSameQuestionTypeSorting() {

  }

  @Override
  public void testOperationOnSameObjectMultipleTimes() {

  }

  @Test
  public void testInvalidAnswerFormat() {
    Question question = null;
    Option[] options = Utils.getAllValidOptionsForMultipleAnswersQuestion();
    try {
      question = new MultipleAnswersQuestion("question-1?", "123", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: '123'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 12", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: '1 12'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 2 14", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: '1 2 14'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 2 3 14", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: '1 2 3 14'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "random", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: 'random'", e.getMessage());
    }
    Assert.assertNull(question);

  }
}
