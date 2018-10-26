package cs5010.register.bean;

/**
 * Created by gajjar.s, on 1:48 PM, 10/26/18
 */
public enum Denomination {

  PENNIES(1),
  NICKELS(5),
  DIMES(10),
  QUARTER(25),
  ONES(100),
  FIVES(500),
  TENS(1000);

  private final int numberOfPenniesInDenomination;

  Denomination(int numberOfPenniesInDenomination) {
    this.numberOfPenniesInDenomination = numberOfPenniesInDenomination;
  }

  public int getPennies(int count) {
    return count * this.numberOfPenniesInDenomination;
  }

  public int getDenominationCount(int pennies) {
    return pennies / this.numberOfPenniesInDenomination;
  }

  public int getDollars(int count) {
    return getPennies(count) / 100;
  }
}
