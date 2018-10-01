import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import questionnaire.MultipleAnswersQuestion;
import questionnaire.Question;
import questionnaire.bean.OptionNumber;
import questionnaire.bean.Result;
import util.Utils;

public class MultipleAnswersQuestionTest extends AbstractQuestionTest {

  @Override
  protected Question getQuestionInstance() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleAnswersQuestion();
    return new MultipleAnswersQuestion("question-1?", "1 2 3", optionNumbers);
  }

  @Override
  public void testInitializationOfQuestionObject() {
    Question question = getQuestionInstance();
    Assert.assertEquals("question-1?", question.getText());
  }

  @Override
  public void testInvalidConstructorArguments() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleAnswersQuestion();
    Question question = null;
    try {
      question = new MultipleAnswersQuestion("", "1 2 3", optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question Text: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion(null, "1 2 3", optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid question text: null", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "", optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctOption: ", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", null, optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctOption: 'null'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 2 3", null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid answer optionNumbers: null", e.getMessage());
    }
    Assert.assertNull(question);
  }

  @Override
  public void testCorrectAndIncorrectAnswer() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleAnswersQuestion();

    for (int i = 1; i <= 8; i++) {
      OptionNumber[] correctOptionNumbers = new OptionNumber[i];

      for (int j = 1; j <= i; j++) {
        correctOptionNumbers[j] = optionNumbers[j];
      }

      String correctOptionString = Arrays.stream(correctOptionNumbers)
              .map(OptionNumber::getOptionString)
              .collect(Collectors.joining(" "));

      Question question = new MultipleAnswersQuestion("question-1?", correctOptionString, optionNumbers);
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
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleAnswersQuestion();

    Question question1 = new MultipleAnswersQuestion("question-5?", "1 2 3 4 5 6 7 8", optionNumbers);
    Question question2 = new MultipleAnswersQuestion("question-4?", "6 7 8", optionNumbers);
    Question question3 = new MultipleAnswersQuestion("question-3?", "1 2", optionNumbers);
    Question question4 = new MultipleAnswersQuestion("question-1?", "1 2", optionNumbers);
    Question question5 = new MultipleAnswersQuestion("question-2?", "2 3", optionNumbers);
    Question question6 = new MultipleAnswersQuestion("question-1?", "1 2", optionNumbers);


    Question[] questionArray = new Question[]{question1, question2, question3, question4, question5, question6};
    Arrays.sort(questionArray);

    Assert.assertEquals(questionArray[0], question6);
    Assert.assertEquals(questionArray[1], question5);
    Assert.assertEquals(questionArray[2], question4);
    Assert.assertEquals(questionArray[3], question3);
    Assert.assertEquals(questionArray[4], question2);
    Assert.assertEquals(questionArray[5], question1);
  }

  @Override
  public void testOperationOnSameObjectMultipleTimes() {
    Question question = getQuestionInstance();
    String answer = String.format("%s %s %s",
            OptionNumber.ONE.getOptionString(),
            OptionNumber.TWO.getOptionString(),
            OptionNumber.THREE.getOptionString());

    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(answer));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(answer));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(answer));
    Assert.assertEquals(Result.CORRECT.getResultString(), question.evaluateAnswer(answer));
  }

  @Override
  public void testQuestionObjectInequalityUsingEquals() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleAnswersQuestion();
    Question question1 = new MultipleAnswersQuestion("maq question-3?", "3", optionNumbers);
    Question question2 = new MultipleAnswersQuestion("maq question-2?", "2 3", optionNumbers);

    Assert.assertFalse(question1.equals(question2));
    Assert.assertFalse(question2.equals(question1));
  }

  @Override
  public void testQuestionObjectInequalityUsingHashcode() {
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleAnswersQuestion();
    Question question1 = new MultipleAnswersQuestion("maq question-3?", "3", optionNumbers);
    Question question2 = new MultipleAnswersQuestion("maq question-2?", "2 3", optionNumbers);

    Assert.assertNotEquals(question1.hashCode(), question2.hashCode());
  }

  @Test
  public void testInvalidAnswerFormat() {
    Question question = null;
    OptionNumber[] optionNumbers = Utils.getAllValidOptionsForMultipleAnswersQuestion();
    try {
      question = new MultipleAnswersQuestion("question-1?", "123", optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: '123'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 12", optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: '1 12'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 2 14", optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: '1 2 14'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "1 2 3 14", optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: '1 2 3 14'", e.getMessage());
    }
    Assert.assertNull(question);

    try {
      question = new MultipleAnswersQuestion("question-1?", "random", optionNumbers);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid correctionOption: 'random'", e.getMessage());
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
      OptionNumber[] optionNumbers = new OptionNumber[]{OptionNumber.ONE};
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3", optionNumbers);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question should have at least 3 options", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      OptionNumber[] optionNumbers = new OptionNumber[]{OptionNumber.ONE, OptionNumber.TWO};
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3", optionNumbers);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question should have at least 3 options", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      OptionNumber[] optionNumbers = new OptionNumber[]{
              OptionNumber.ONE, OptionNumber.TWO, OptionNumber.THREE, OptionNumber.FOUR,
              OptionNumber.FIVE, OptionNumber.SIX, OptionNumber.SEVEN, OptionNumber.EIGHT,
              OptionNumber.ONE};
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3", optionNumbers);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question can have no more than 8 options", e.getMessage());
    }
    Assert.assertNull(question2);

    try {
      OptionNumber[] optionNumbers = new OptionNumber[]{
              OptionNumber.ONE, OptionNumber.TWO, OptionNumber.THREE, OptionNumber.FOUR,
              OptionNumber.FIVE, OptionNumber.SIX, OptionNumber.SEVEN, OptionNumber.EIGHT,
              OptionNumber.ONE, OptionNumber.TWO};
      question2 = new MultipleAnswersQuestion("question-1?", "1 2 3", optionNumbers);
      Assert.fail("should have failed");
    } catch (Exception e) {
      Assert.assertEquals("Question can have no more than 8 options", e.getMessage());
    }
    Assert.assertNull(question2);
  }


  @Test
  public void testCorrectAnswerNotFoundInGivenOptions() {
    //todo:asd
  }
}
