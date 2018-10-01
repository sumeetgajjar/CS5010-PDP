import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import questionnaire.MultipleChoiceQuestion;
import questionnaire.Question;
import questionnaire.bean.Result;
import questionnaire.bean.Option;
import util.Utils;

public class MultipleChoiceQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    Option[] options = Utils.getAllValidOptionsForMultipleChoiceQuestion();
    return new MultipleChoiceQuestion("question-1?", Option.ONE, options);
  }

  /**
   * Test to check the initialization of the Question Object.
   */
  @Test
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(Option.ONE.getOptionString()));
  }

  @Test
  public void testInvalidConstructorArguments() {
    Option[] options = Utils.getAllValidOptionsForMultipleChoiceQuestion();
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
      Assert.assertEquals("Invalid answer options: null", e.getMessage());
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
  public void testInvalidAnswerOptions() {
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
    Option[] options = Utils.getAllValidOptionsForMultipleChoiceQuestion();

    for (int i = 1; i <= 8; i++) {
      Option correctOption = Option.getOption(String.valueOf(i));
      Question question1 = new MultipleChoiceQuestion("question-1?", correctOption, options);

      Assert.assertEquals("question-1?", question1.getText());
      Assert.assertEquals(Result.CORRECT.getResultString(), question1.eval(correctOption.getOptionString()));

      List<Option> incorrectOptions = Arrays
              .stream(Option.values())
              .filter(o -> !Objects.equals(o, correctOption))
              .collect(Collectors.toList());

      for (Option incorrectOption : incorrectOptions) {
        Assert.assertEquals(Result.INCORRECT.getResultString(), question1.eval(incorrectOption.getOptionString()));
      }

      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.eval("random"));
      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.eval("12"));
      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.eval("0"));
      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.eval("-1"));
    }
  }

  @Test
  public void testSameQuestionTypeSorting() {
    Option[] options = Utils.getAllValidOptionsForMultipleChoiceQuestion();

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

  @Override
  public void testOperationOnSameObjectMultipleTimes() {
    Question question = getQuestionInstance();
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(Option.ONE.getOptionString()));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(Option.ONE.getOptionString()));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(Option.ONE.getOptionString()));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.eval(Option.ONE.getOptionString()));
  }

  @Override
  public void testQuestionObjectInequalityUsingEquals() {
    Option[] options = Utils.getAllValidOptionsForMultipleChoiceQuestion();
    Question question1 = new MultipleChoiceQuestion("mcq question-3?", Option.THREE, options);
    Question question2 = new MultipleChoiceQuestion("mcq question-2?", Option.TWO, options);

    Assert.assertFalse(question1.equals(question2));
    Assert.assertFalse(question2.equals(question1));
  }

  @Override
  public void testQuestionObjectInequalityUsingHashcode() {
    Option[] options = Utils.getAllValidOptionsForMultipleChoiceQuestion();
    Question question1 = new MultipleChoiceQuestion("mcq question-3?", Option.THREE, options);
    Question question2 = new MultipleChoiceQuestion("mcq question-2?", Option.TWO, options);

    Assert.assertNotEquals(question1.hashCode(), question2.hashCode());
  }
}
