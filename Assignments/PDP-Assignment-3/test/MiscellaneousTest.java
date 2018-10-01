import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import question.LikertQuestion;
import question.MultipleAnswersQuestion;
import question.MultipleChoiceQuestion;
import question.bean.Option;
import question.Question;
import question.YesNoQuestion;
import question.bean.NumericChoice;
import question.bean.YesNoQuestionAnswer;

public class MiscellaneousTest {

  @Test
  public void testSortYesNoQuestionAndLikertQuestion() {
    Question question8 = new LikertQuestion("likert question-3?");
    Question question7 = new LikertQuestion("likert question-2?");
    Question question6 = new LikertQuestion("likert question-1?");
    Question question5 = new LikertQuestion("likert question-1?");

    Question question4 = new YesNoQuestion("yes no question-2?", YesNoQuestionAnswer.NO);
    Question question3 = new YesNoQuestion("yes no question-1?", YesNoQuestionAnswer.NO);
    Question question2 = new YesNoQuestion("yes no question-1?", YesNoQuestionAnswer.YES);
    Question question1 = new YesNoQuestion("yes no question-1?", YesNoQuestionAnswer.YES);

    Question[] questions = new Question[]{
            question8, question7, question6, question5,
            question4, question3, question2, question1
    };

    Arrays.sort(questions);

    Assert.assertEquals(questions[0], question1);
    Assert.assertEquals(questions[1], question2);
    Assert.assertEquals(questions[2], question3);
    Assert.assertEquals(questions[3], question4);

    Assert.assertEquals(questions[4], question5);
    Assert.assertEquals(questions[5], question6);
    Assert.assertEquals(questions[6], question7);
    Assert.assertEquals(questions[7], question8);
  }

  @Test
  public void testSortMultipleChoiceQuestionAndMultipleAnswersQuestion() {
    Option[] multipleAnswersQuestionNumericOptions = MultipleAnswersQuestionTest.getOptions(8);
    Question question8 = new MultipleAnswersQuestion("maq question-3?", "3", multipleAnswersQuestionNumericOptions);
    Question question7 = new MultipleAnswersQuestion("maq question-2?", "2 3", multipleAnswersQuestionNumericOptions);
    Question question6 = new MultipleAnswersQuestion("maq question-1?", "1 2 3", multipleAnswersQuestionNumericOptions);
    Question question5 = new MultipleAnswersQuestion("maq question-1?", "1 2 3", multipleAnswersQuestionNumericOptions);

    Option[] multipleChoiceQuestionNumericOptions = MultipleChoiceQuestionTest.getOptions(8);
    Question question4 = new MultipleChoiceQuestion("mcq question-3?", NumericChoice.THREE, multipleChoiceQuestionNumericOptions);
    Question question3 = new MultipleChoiceQuestion("mcq question-2?", NumericChoice.TWO, multipleChoiceQuestionNumericOptions);
    Question question2 = new MultipleChoiceQuestion("mcq question-2?", NumericChoice.TWO, multipleChoiceQuestionNumericOptions);
    Question question1 = new MultipleChoiceQuestion("mcq question-1?", NumericChoice.ONE, multipleChoiceQuestionNumericOptions);

    Question[] questions = new Question[]{
            question4, question3, question2, question1,
            question8, question7, question6, question5
    };

    Arrays.sort(questions);

    Assert.assertEquals(questions[0], question1);
    Assert.assertEquals(questions[1], question2);
    Assert.assertEquals(questions[2], question3);
    Assert.assertEquals(questions[3], question4);

    Assert.assertEquals(questions[4], question5);
    Assert.assertEquals(questions[5], question6);
    Assert.assertEquals(questions[6], question7);
    Assert.assertEquals(questions[7], question8);
  }

  @Test
  public void testSortLikertQuestionAndMultipleChoiceQuestion() {
    Option[] multipleChoiceQuestionNumericOptions = MultipleChoiceQuestionTest.getOptions(8);
    Question question8 = new MultipleChoiceQuestion("mcq question-3?", NumericChoice.THREE, multipleChoiceQuestionNumericOptions);
    Question question7 = new MultipleChoiceQuestion("mcq question-2?", NumericChoice.TWO, multipleChoiceQuestionNumericOptions);
    Question question6 = new MultipleChoiceQuestion("mcq question-2?", NumericChoice.TWO, multipleChoiceQuestionNumericOptions);
    Question question5 = new MultipleChoiceQuestion("mcq question-1?", NumericChoice.ONE, multipleChoiceQuestionNumericOptions);

    Question question4 = new LikertQuestion("likert question-3?");
    Question question3 = new LikertQuestion("likert question-2?");
    Question question2 = new LikertQuestion("likert question-1?");
    Question question1 = new LikertQuestion("likert question-1?");

    Question[] questions = new Question[]{
            question8, question7, question6, question5,
            question4, question3, question2, question1,
    };

    Arrays.sort(questions);

    Assert.assertEquals(questions[0], question1);
    Assert.assertEquals(questions[1], question2);
    Assert.assertEquals(questions[2], question3);
    Assert.assertEquals(questions[3], question4);

    Assert.assertEquals(questions[4], question5);
    Assert.assertEquals(questions[5], question6);
    Assert.assertEquals(questions[6], question7);
    Assert.assertEquals(questions[7], question8);
  }

  @Test
  public void sortQuestionsOfAllType() {
    Option[] multipleAnswersQuestionNumericOptions = MultipleAnswersQuestionTest.getOptions(8);
    Question question16 = new MultipleAnswersQuestion("maq question-3?", "3", multipleAnswersQuestionNumericOptions);
    Question question15 = new MultipleAnswersQuestion("maq question-2?", "2 3", multipleAnswersQuestionNumericOptions);
    Question question14 = new MultipleAnswersQuestion("maq question-1?", "1 2 3", multipleAnswersQuestionNumericOptions);
    Question question13 = new MultipleAnswersQuestion("maq question-1?", "1 2 3", multipleAnswersQuestionNumericOptions);

    Option[] multipleChoiceQuestionNumericOptions = MultipleChoiceQuestionTest.getOptions(8);
    Question question12 = new MultipleChoiceQuestion("mcq question-3?", NumericChoice.THREE, multipleChoiceQuestionNumericOptions);
    Question question11 = new MultipleChoiceQuestion("mcq question-2?", NumericChoice.TWO, multipleChoiceQuestionNumericOptions);
    Question question10 = new MultipleChoiceQuestion("mcq question-2?", NumericChoice.TWO, multipleChoiceQuestionNumericOptions);
    Question question9 = new MultipleChoiceQuestion("mcq question-1?", NumericChoice.ONE, multipleChoiceQuestionNumericOptions);

    Question question8 = new LikertQuestion("likert question-3?");
    Question question7 = new LikertQuestion("likert question-2?");
    Question question6 = new LikertQuestion("likert question-1?");
    Question question5 = new LikertQuestion("likert question-1?");

    Question question4 = new YesNoQuestion("yes no question-2?", YesNoQuestionAnswer.NO);
    Question question3 = new YesNoQuestion("yes no question-1?", YesNoQuestionAnswer.NO);
    Question question2 = new YesNoQuestion("yes no question-1?", YesNoQuestionAnswer.YES);
    Question question1 = new YesNoQuestion("yes no question-1?", YesNoQuestionAnswer.YES);

    Question[] questions = new Question[]{
            question16, question15, question14, question13,
            question12, question11, question10, question9,
            question8, question7, question6, question5,
            question4, question3, question2, question1
    };

    Arrays.sort(questions);

    Assert.assertEquals(questions[0], question1);
    Assert.assertEquals(questions[1], question2);
    Assert.assertEquals(questions[2], question3);
    Assert.assertEquals(questions[3], question4);

    Assert.assertEquals(questions[4], question5);
    Assert.assertEquals(questions[5], question6);
    Assert.assertEquals(questions[6], question7);
    Assert.assertEquals(questions[7], question8);

    Assert.assertEquals(questions[8], question9);
    Assert.assertEquals(questions[9], question10);
    Assert.assertEquals(questions[10], question11);
    Assert.assertEquals(questions[11], question12);

    Assert.assertEquals(questions[12], question13);
    Assert.assertEquals(questions[13], question14);
    Assert.assertEquals(questions[14], question15);
    Assert.assertEquals(questions[15], question16);
  }
}
