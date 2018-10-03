import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import question.MultipleChoiceQuestion;
import question.Question;
import question.bean.NumericChoice;
import question.bean.Option;
import question.bean.Result;

public class MultipleChoiceQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    Option[] options = getOptions(8);
    return new MultipleChoiceQuestion("question-1?", "1", options);
  }

  /**
   * Test to check the initialization of the Question Object.
   */
  @Override
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());

    String[] actualOptions = question.getOptions();
    Option[] expectedOptions = getOptions(8);
    Assert.assertEquals(expectedOptions.length, actualOptions.length);

    for (int i = 0; i < expectedOptions.length; i++) {
      Assert.assertEquals(expectedOptions[i].getText(), actualOptions[i]);
    }

    Assert.assertEquals(
            Result.CORRECT.getResultString(),
            question.evaluateAnswer(NumericChoice.ONE.getStringValue()));
  }

  @Override
  public void testInvalidConstructorArguments() {
    Option[] options = getOptions(8);
    Question question = null;
    try {
      question = new MultipleChoiceQuestion("", "1", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion(null, "1", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion("question-1?", null, options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctOption", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion("question-1?", "1", null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("options cannot be null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleChoiceQuestion("question-1?", "1 ", null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("options cannot be null", e.getMessage());
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
    Option[] options = getOptions(8);

    Question question1 = new MultipleChoiceQuestion("question-4?", "4",
            options);
    Question question2 = new MultipleChoiceQuestion("question-3?", "3",
            options);
    Question question3 = new MultipleChoiceQuestion("question-2?", "2",
            options);
    Question question4 = new MultipleChoiceQuestion("question-2?", "2",
            options);
    Question question5 = new MultipleChoiceQuestion("question-1?", "4",
            options);


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
    Assert.assertEquals(
            Result.CORRECT.getResultString(),
            question.evaluateAnswer(NumericChoice.ONE.getStringValue()));

    Assert.assertEquals(
            Result.CORRECT.getResultString(),
            question.evaluateAnswer(NumericChoice.ONE.getStringValue()));

    Assert.assertEquals(
            Result.CORRECT.getResultString(),
            question.evaluateAnswer(NumericChoice.ONE.getStringValue()));

    Assert.assertEquals(
            Result.CORRECT.getResultString(),
            question.evaluateAnswer(NumericChoice.ONE.getStringValue()));
  }

  @Override
  public void testQuestionObjectInequalityUsingEquals() {
    Option[] options = getOptions(8);
    Question question1 = new MultipleChoiceQuestion("mcq question-3?", "3",
            options);
    Question question2 = new MultipleChoiceQuestion("mcq question-2?", "2",
            options);

    Assert.assertFalse(question1.equals(question2));
    Assert.assertFalse(question2.equals(question1));
  }

  @Override
  public void testQuestionObjectInequalityUsingHashcode() {
    Option[] options = getOptions(8);
    Question question1 = new MultipleChoiceQuestion("mcq question-3?", "3",
            options);
    Question question2 = new MultipleChoiceQuestion("mcq question-2?", "2",
            options);

    Assert.assertNotEquals(question1.hashCode(), question2.hashCode());
  }

  @Override
  public void testQuestionObjectInequalityUsingCompareTo() {
    Option[] options = getOptions(8);
    Question question1 = new MultipleChoiceQuestion("mcq question-3?", "3",
            options);
    Question question2 = new MultipleChoiceQuestion("mcq question-2?", "2",
            options);

    Assert.assertEquals(1, question1.compareTo(question2));
    Assert.assertEquals(-1, question2.compareTo(question1));
  }

  @Test
  public void testCorrectAndIncorrectAnswer() {
    Option[] options = getOptions(8);

    for (int i = 1; i <= 8; i++) {
      NumericChoice correctNumericChoice = NumericChoice.getChoice(String.valueOf(i));
      Question question1 = new MultipleChoiceQuestion("question-1?",
              correctNumericChoice.getStringValue(), options);

      Assert.assertEquals("question-1?", question1.getText());
      Assert.assertEquals(
              Result.CORRECT.getResultString(),
              question1.evaluateAnswer(correctNumericChoice.getStringValue()));

      List<NumericChoice> incorrectNumericOptions = Arrays
              .stream(NumericChoice.values())
              .filter(o -> !Objects.equals(o, correctNumericChoice))
              .collect(Collectors.toList());

      for (NumericChoice incorrectNumericChoice : incorrectNumericOptions) {
        Assert.assertEquals(
                Result.INCORRECT.getResultString(),
                question1.evaluateAnswer(incorrectNumericChoice.getStringValue()));
      }

      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer(null));
      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer(" "));
      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer(" 1 2 3"));
      Assert.assertEquals(Result.INCORRECT.getResultString(), question1.evaluateAnswer("1 2 3"));
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
      Option[] options = getOptions(1);
      question2 = new MultipleChoiceQuestion("question-2?", "1", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals(
              "Question should have at least 3 options, found: 1",
              e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      Option[] options = getOptions(2);
      question2 = new MultipleChoiceQuestion("question-2?", "1", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals(
              "Question should have at least 3 options, found: 2",
              e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      Option[] options = getOptions(9);
      question2 = new MultipleChoiceQuestion("question-2?", "1", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals(
              "Question can have no more than 8 options, found: 9",
              e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      Option[] options = getOptions(10);
      question2 = new MultipleChoiceQuestion("question-2?", "1", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals(
              "Question can have no more than 8 options, found: 10",
              e.getMessage());
    }
    Assert.assertNull(question2);

    Option[] options = getOptions(3);
    question2 = new MultipleChoiceQuestion("question-2?", "1", options);
    Assert.assertEquals(Result.CORRECT.getResultString(), question2.evaluateAnswer("1"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question2.evaluateAnswer("2"));
  }

  @Test
  public void testCorrectAnswerNotFoundInGivenOptions() {
    try {
      Option[] options = getOptions(5);
      Question question = new MultipleChoiceQuestion("question-1?", "8",
              options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(
              "correct answer choice not found in given options, correctAnswerChoice: 8",
              e.getMessage());
    }
  }

  @Test
  public void testMoreThanOneCorrectAnswer() {
    try {
      Option[] options = getOptions(5);
      Question question = new MultipleChoiceQuestion("question-1?", "1 2",
              options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("cannot have more than 1 correct option", e.getMessage());
    }
  }

  @Test
  public void testNullOptionInOptionsArray() {
    try {
      Option[] options = new Option[]{new Option("option-1"),null,new Option("option-2")};
      Question question = new MultipleChoiceQuestion("question-1?", "1",
              options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("option cannot be null", e.getMessage());
    }
  }

  @Test
  public void testTrailingSpacesInCorrectOptionAndAnswer() {
    Question question = new MultipleChoiceQuestion("question-1?", "1  ",
            getOptions(3));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1  "));

    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("2"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("1  2"));
  }

  /**
   * Returns a array of n Option.
   * @param n size of the array
   * @return a array of n Option
   */
  public static Option[] getOptions(int n) {
    Option[] options = new Option[n];
    for (int i = 0; i < n; i++) {
      options[i] = new Option(String.format("option-%d", i));
    }
    return options;
  }
}
