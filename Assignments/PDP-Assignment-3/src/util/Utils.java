package util;

import java.util.Objects;

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

  public static boolean isStringNotSet(String text) {
    return !isStringSet(text);
  }

  public static boolean isStringSet(String text) {
    return Objects.nonNull(text) && text.length() > 0;
  }
}
