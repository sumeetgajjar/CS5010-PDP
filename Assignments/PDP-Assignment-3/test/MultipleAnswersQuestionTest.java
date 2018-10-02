import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import question.MultipleAnswersQuestion;
import question.Question;
import question.bean.NumericChoice;
import question.bean.Option;
import question.bean.Result;

public class MultipleAnswersQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    Option[] options = getOptions(8);
    return new MultipleAnswersQuestion("question-1?", "1 2 3", options);
  }

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

    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1 2 3"));
  }

  @Test
  public void testTrailingSpacesInCorrectOptionAndAnswer() {
    Question question = new MultipleAnswersQuestion("question-1?", "1 2 3  ", getOptions(3));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1 2 3"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1 2 3  "));
  }

  @Override
  public void testInvalidConstructorArguments() {
    Option[] options = getOptions(8);
    Question question = null;
    try {
      question = new MultipleAnswersQuestion("", "1 2 3", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: ", e.getMessage());
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
      Assert.assertEquals("Invalid correctOption", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", null, options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctOption", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 2 3", null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("options cannot be null", e.getMessage());
    }
    Assert.assertNull(question);
  }

  @Override
  public void testCorrectAndIncorrectAnswer() {
    Option[] options = getOptions(8);

    for (int i = 1; i < 8; i++) {
      NumericChoice[] correctOptions = new NumericChoice[i];

      for (int j = 0; j < i; j++) {
        correctOptions[j] = NumericChoice.getChoice(j + 1);
      }

      String correctOptionString = Arrays.stream(correctOptions)
              .map(NumericChoice::getStringValue)
              .collect(Collectors.joining(" "));

      Question question = new MultipleAnswersQuestion("question-1?", correctOptionString, options);
      Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(correctOptionString));

      String answer = "";
      Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer(answer));

      for (int j = 1; j < i; j++) {
        answer += j;
        Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer(answer));
        answer += " ";
      }

      answer += i;
      Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(answer));
    }
  }

  @Override
  public void testSameQuestionTypeSorting() {
    Option[] options = getOptions(8);

    Question question1 = new MultipleAnswersQuestion("question-5?", "1 2 3 4 5 6 7 8", options);
    Question question2 = new MultipleAnswersQuestion("question-4?", "6 7 8", options);
    Question question3 = new MultipleAnswersQuestion("question-3?", "1 2", options);
    Question question4 = new MultipleAnswersQuestion("question-1?", "1 2", options);
    Question question5 = new MultipleAnswersQuestion("question-2?", "2 3", options);
    Question question6 = new MultipleAnswersQuestion("question-1?", "1 2", options);


    Question[] questionArray = new Question[]{question1, question2, question3, question4, question5, question6};
    Arrays.sort(questionArray);

    Assert.assertEquals(questionArray[0], question4);
    Assert.assertEquals(questionArray[1], question6);
    Assert.assertEquals(questionArray[2], question5);
    Assert.assertEquals(questionArray[3], question3);
    Assert.assertEquals(questionArray[4], question2);
    Assert.assertEquals(questionArray[5], question1);
  }

  @Override
  public void testOperationOnSameObjectMultipleTimes() {
    Question question = getQuestionInstance();
    String answer = String.format("%s %s %s",
            NumericChoice.ONE.getStringValue(),
            NumericChoice.TWO.getStringValue(),
            NumericChoice.THREE.getStringValue());

    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(answer));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(answer));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(answer));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(answer));
  }

  @Override
  public void testQuestionObjectInequalityUsingEquals() {
    Option[] options = getOptions(8);
    Question question1 = new MultipleAnswersQuestion("maq question-3?", "3", options);
    Question question2 = new MultipleAnswersQuestion("maq question-2?", "2 3", options);

    Assert.assertFalse(question1.equals(question2));
    Assert.assertFalse(question2.equals(question1));
  }

  @Override
  public void testQuestionObjectInequalityUsingHashcode() {
    Option[] options = getOptions(8);
    Question question1 = new MultipleAnswersQuestion("maq question-3?", "3", options);
    Question question2 = new MultipleAnswersQuestion("maq question-2?", "2 3", options);

    Assert.assertNotEquals(question1.hashCode(), question2.hashCode());
  }

  @Override
  public void testQuestionObjectInequalityUsingCompareTo() {
    Option[] options = getOptions(8);
    Question question1 = new MultipleAnswersQuestion("maq question-3?", "3", options);
    Question question2 = new MultipleAnswersQuestion("maq question-2?", "2 3", options);

    Assert.assertEquals(1, question1.compareTo(question2));
    Assert.assertEquals(-1, question2.compareTo(question1));
  }

  @Test
  public void testInvalidAnswerFormat() {
    Question question = null;
    Option[] options = getOptions(8);
    try {
      question = new MultipleAnswersQuestion("question-1?", "123", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid NumericChoice value: 123", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 12", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid NumericChoice value: 12", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", " 1", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid NumericChoice value: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 2 14", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid NumericChoice value: 14", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 2 3 14", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid NumericChoice value: 14", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "random", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid NumericChoice value: random", e.getMessage());
    }
    Assert.assertNull(question);

  }

  @Test
  public void testCorrectAnswersInDifferentSequence() {
    Question question = getQuestionInstance();
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1 2 3"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1 3 2"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("2 1 3"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("2 3 1"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("3 1 2"));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("3 2 1"));

    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("3 2 1 4"));
  }

  @Test
  public void testAnswerOptionRepetition() {
    Question question = getQuestionInstance();
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1 2 3"));

    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("1 1 1"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("2 2 2"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("3 3 3"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("1 2 2"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("2 3 3"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("3 1 1"));
  }

  @Test
  public void testIncorrectAnswerContainingInvalidOptions() {
    Question question = getQuestionInstance();
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer("1 2 3"));

    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("1 2 5"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("8"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("8 9"));
    Assert.assertEquals(Result.INCORRECT.getResultString(), question.evaluateAnswer("7 6 5"));
  }

  @Test
  public void testCardinalityOfAnswerOptions() {
    Question question2 = null;

    try {
      Option[] options = getOptions(0);
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question should have at least 3 options, found: 0", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      Option[] options = getOptions(1);
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question should have at least 3 options, found: 1", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      Option[] options = getOptions(2);
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question should have at least 3 options, found: 2", e.getMessage());
    }
    Assert.assertNull(question2);


    try {
      Option[] options = getOptions(3);
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3 4", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("correct options cannot be greater than total options", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      Option[] options = getOptions(9);
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question can have no more than 8 options, found: 9", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      Option[] options = getOptions(10);
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3", options);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question can have no more than 8 options, found: 10", e.getMessage());
    }
    Assert.assertNull(question2);
  }


  @Test
  public void testCorrectAnswerNotFoundInGivenOptions() {
    try {
      Option[] options = getOptions(5);
      Question question = new MultipleAnswersQuestion("question-1?", "6 8", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(
              "correct answer choice not found in given options, correctAnswerChoice: 6",
              e.getMessage());
    }
  }

  @Test
  public void testNullOptionInOptionsArray() {
    try {
      Option[] options = new Option[]{
              new Option("option-1"),
              null,
              new Option("option-2")
      };

      Question question = new MultipleAnswersQuestion("question-1?", "1 2", options);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("option cannot be null", e.getMessage());
    }
  }

  public static Option[] getOptions(int n) {
    Option[] options = new Option[n];
    for (int i = 0; i < n; i++) {
      options[i] = new Option(String.format("option-%d", i));
    }
    return options;
  }
}
