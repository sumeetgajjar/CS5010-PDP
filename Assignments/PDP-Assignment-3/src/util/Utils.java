package util;

import java.util.Objects;

import questionnaire.bean.OptionNumber;

public class Utils {

  public static OptionNumber[] getAllValidOptionsForMultipleAnswersQuestion() {
    return new OptionNumber[]{
            OptionNumber.ONE, OptionNumber.TWO, OptionNumber.THREE, OptionNumber.FOUR,
            OptionNumber.FIVE, OptionNumber.SIX, OptionNumber.SEVEN, OptionNumber.EIGHT,
    };
  }

  public static OptionNumber[] getAllValidOptionsForMultipleChoiceQuestion() {
    return new OptionNumber[]{
            OptionNumber.ONE, OptionNumber.TWO, OptionNumber.THREE, OptionNumber.FOUR,
            OptionNumber.FIVE, OptionNumber.SIX, OptionNumber.SEVEN, OptionNumber.EIGHT,
    };
  }

  public static boolean isStringNotSet(String text) {
    return !isStringSet(text);
  }

  public static boolean isStringSet(String text) {
    return Objects.nonNull(text) && text.length() > 0;
  }
}
