package util;

import questionnaire.bean.Option;

public class Utils {

  public static Option[] getAllValidOptionsForMultipleAnswersQuestion() {
    return new Option[]{
            Option.ONE, Option.TWO, Option.THREE, Option.FOUR,
            Option.FIVE, Option.SIX, Option.SEVEN, Option.EIGHT,
    };
  }

  public static Option[] getAllValidOptionsForMultipleChoiceQuestion() {
    return new Option[]{
            Option.ONE, Option.TWO, Option.THREE, Option.FOUR,
            Option.FIVE, Option.SIX, Option.SEVEN, Option.EIGHT,
    };
  }
}
