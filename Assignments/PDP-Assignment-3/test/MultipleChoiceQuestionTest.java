import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import questionnaire.MultipleChoiceQuestion;
import questionnaire.Question;
import questionnaire.bean.OptionNumber;
import questionnaire.bean.Result;
import util.Utils;

public class MultipleChoiceQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleChoiceQuestion();
    return new MultipleChoiceQuestion("question-1?", OptionNumber.ONE, optionNumbers);
  }

  /**
   * Test to check the initialization of the Question Object.
   */
  @Override
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(OptionNumber.ONE.getOptionString()));
  }

  @Override
  public void testInvalidConstructorArguments() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleChoiceQuestion();
    Question question = null;
    try {
      question = new MultipleChoiceQuestion("", OptionNumber.ONE, optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion(null, OptionNumber.ONE, optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion("question-1?", null, optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctOption: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion("question-1?", OptionNumber.ONE, null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid answer optionNumbers: null", e.getMessage());
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

  @Override
  public void testSameQuestionTypeSorting() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleChoiceQuestion();

    Question question1 = new MultipleChoiceQuestion("question-4?", OptionNumber.FOUR, optionNumbers);
    Question question2 = new MultipleChoiceQuestion("question-3?", OptionNumber.THREE, optionNumbers);
    Question question3 = new MultipleChoiceQuestion("question-2?", OptionNumber.TWO, optionNumbers);
    Question question4 = new MultipleChoiceQuestion("question-2?", OptionNumber.TWO, optionNumbers);
    Question question5 = new MultipleChoiceQuestion("question-1?", OptionNumber.FOUR, optionNumbers);


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
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(OptionNumber.ONE.getOptionString()));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(OptionNumber.ONE.getOptionString()));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(OptionNumber.ONE.getOptionString()));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(OptionNumber.ONE.getOptionString()));
  }

  @Override
  public void testQuestionObjectInequalityUsingEquals() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleChoiceQuestion();
    Question question1 = new MultipleChoiceQuestion("mcq question-3?", OptionNumber.THREE, optionNumbers);
    Question question2 = new MultipleChoiceQuestion("mcq question-2?", OptionNumber.TWO, optionNumbers);

    Assert.assertFalse(question1.equals(question2));
    Assert.assertFalse(question2.equals(question1));
  }

  @Override
  public void testQuestionObjectInequalityUsingHashcode() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleChoiceQuestion();
    Question question1 = new MultipleChoiceQuestion("mcq question-3?", OptionNumber.THREE, optionNumbers);
    Question question2 = new MultipleChoiceQuestion("mcq question-2?", OptionNumber.TWO, optionNumbers);

    Assert.assertNotEquals(question1.hashCode(), question2.hashCode());
  }

  @Test
  public void testCorrectAndIncorrectAnswer() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleChoiceQuestion();

    for (int i = 1; i <= 8; i++) {
      OptionNumber correctOptionNumber = OptionNumber.getOption(String.valueOf(i));
      Question question1 = new MultipleChoiceQuestion("question-1?", correctOptionNumber, optionNumbers);

      Assert.assertEquals("question-1?", question1.getText());
      Assert.assertEquals(Result.CORRECT.getResultString(), question1.evaluateAnswer(correctOptionNumber.getOptionString()));

      List<OptionNumber> incorrectOptionNumbers = Arrays
              .stream(OptionNumber.values())
              .filter(o -> !Objects.equals(o, correctOptionNumber))
              .collect(Collectors.toList());

      for (OptionNumber incorrectOptionNumber : incorrectOptionNumbers) {
        Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer(incorrectOptionNumber.getOptionString()));
      }

      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer("random"));
      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer("12"));
      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer("0"));
      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer("-1"));
    }
  }

  @Test
  public void testCardinalityOfAnswerOptions() {
    Question question2 = null;
    try {
      OptionNumber[] optionNumbers = new OptionNumber[]{OptionNumber.ONE};
      question2 = new MultipleChoiceQuestion("question-2?", OptionNumber.ONE, optionNumbers);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question should have at least 3 options, found: 1", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      OptionNumber[] optionNumbers = new OptionNumber[]{OptionNumber.ONE, OptionNumber.TWO};
      question2 = new MultipleChoiceQuestion("question-2?", OptionNumber.ONE, optionNumbers);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question should have at least 3 options, found: 2", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      OptionNumber[] optionNumbers = new OptionNumber[]{
              OptionNumber.ONE, OptionNumber.TWO, OptionNumber.THREE, OptionNumber.FOUR,
              OptionNumber.FIVE, OptionNumber.SIX, OptionNumber.SEVEN, OptionNumber.EIGHT,
              OptionNumber.ONE};
      question2 = new MultipleChoiceQuestion("question-2?", OptionNumber.ONE, optionNumbers);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question can have no more than 8 options, found: 9", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      OptionNumber[] optionNumbers = new OptionNumber[]{
              OptionNumber.ONE, OptionNumber.TWO, OptionNumber.THREE, OptionNumber.FOUR,
              OptionNumber.FIVE, OptionNumber.SIX, OptionNumber.SEVEN, OptionNumber.EIGHT,
              OptionNumber.ONE, OptionNumber.TWO};
      question2 = new MultipleChoiceQuestion("question-2?", OptionNumber.ONE, optionNumbers);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question can have no more than 8 options, found: 10", e.getMessage());
    }
    Assert.assertNull(question2);
  }

  @Test
  public void testCorrectAnswerNotFoundInGivenOptions() {
    //todo:asd
  }
}
