import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import questionnaire.MultipleAnswersQuestion;
import questionnaire.Question;
import questionnaire.bean.AnswerStatus;
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
    Option[] options = Utils.getAllValidOptionsForMultipleAnswersQuestion();

    for (int i = 1; i <= 8; i++) {
      Option[] correctOptions = new Option[i];

      for (int j = 1; j <= i; j++) {
        correctOptions[j] = options[j];
      }

      String correctOptionString = Arrays.stream(correctOptions)
              .map(Option::getOptionString)
              .collect(Collectors.joining(" "));

      Question question = new MultipleAnswersQuestion("question-1?", correctOptionString, options);
      Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(correctOptionString));

      String answer = "";
      Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval(answer));

      for (int j = 1; j < i; j++) {
        answer += j;
        Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval(answer));
        answer += " ";
      }

      answer += i;
      Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(answer));
    }
  }

  @Override
  public void testSameQuestionTypeSorting() {
    Option[] options = Utils.getAllValidOptionsForMultipleAnswersQuestion();

    Question question1 = new MultipleAnswersQuestion("question-5?", "1 2 3 4 5 6 7 8", options);
    Question question2 = new MultipleAnswersQuestion("question-4?", "6 7 8", options);
    Question question3 = new MultipleAnswersQuestion("question-3?", "1 2", options);
    Question question4 = new MultipleAnswersQuestion("question-1?", "1 2", options);
    Question question5 = new MultipleAnswersQuestion("question-2?", "2 3", options);
    Question question6 = new MultipleAnswersQuestion("question-1?", "1 2", options);


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
            Option.ONE.getOptionString(),
            Option.TWO.getOptionString(),
            Option.THREE.getOptionString());

    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(answer));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(answer));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(answer));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval(answer));
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

  @Test
  public void testCorrectAnswersInDifferentSequence() {
    Question question = getQuestionInstance();
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("1 2 3"));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("1 3 2"));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("2 1 3"));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("2 3 1"));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("3 1 2"));
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("3 2 1"));

    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("3 2 1 4"));
  }

  @Test
  public void testAnswerOptionRepetition() {
    Question question = getQuestionInstance();
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("1 2 3"));

    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("1 1 1"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("2 2 2"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("3 3 3"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("1 2 2"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("2 3 3"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("3 1 1"));
  }

  @Test
  public void testIncorrectAnswerContainingInvalidOptions() {
    Question question = getQuestionInstance();
    Assert.assertEquals(AnswerStatus.CORRECT.getAnswerStatusString(), question.eval("1 2 3"));

    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("1 2 5"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("8"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("8 9"));
    Assert.assertEquals(AnswerStatus.INCORRECT.getAnswerStatusString(), question.eval("7 6 5"));
  }
}
