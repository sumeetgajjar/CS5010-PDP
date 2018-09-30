package util;

import questionnaire.bean.Option;

public class Utils {

  public static Option[] getAllValidOptionsForMultipleAnswerQuestion() {
    return new Option[]{
            Option.ONE, Option.TWO, Option.THREE, Option.FOUR,
            Option.FIVE, Option.SIX, Option.SEVEN, Option.EIGHT,
    };
  }
}
