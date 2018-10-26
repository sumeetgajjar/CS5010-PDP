package cs5010.register.bean;

import java.util.function.Function;

/**
 * Created by gajjar.s, on 1:48 PM, 10/26/18
 */
public enum Denomination {

  PENNIES(pennies -> pennies),
  NICKELS(nickels -> nickels * 5),
  DIMES(dimes -> dimes * 10),
  QUARTER(quarter -> quarter * 25),
  ONES(ones -> ones * 100),
  FIVES(fives -> fives * 500),
  TENS(tens -> tens * 10 * 100);

  private final Function<Integer, Integer> converter;

  private Denomination(Function<Integer, Integer> converter) {
    this.converter = converter;
  }

  public int getPennies(int count) {
    return this.converter.apply(count);
  }
}
