package cs5010.register.bean;

/**
 * This enum represents all Types of {@link Denomination} a cash register can have.
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

  /**
   * Private constructor to create the enum object.
   *
   * @param numberOfPenniesInDenomination the number of pennies in one unit of denomination.
   */
  private Denomination(int numberOfPenniesInDenomination) {
    this.numberOfPenniesInDenomination = numberOfPenniesInDenomination;
  }

  /**
   * Returns the number of pennies for the given denomination count in this {@link Denomination}.
   *
   * @param denominationCount the denomination count
   * @return the number of pennies for the given denomination count in this {@link Denomination}
   */
  public int getPennies(int denominationCount) {
    return denominationCount * this.numberOfPenniesInDenomination;
  }

  /**
   * Returns the maximum equivalent count of this denomination for given value of pennies. For e.g
   * if number of pennies is 17 and this is {@link Denomination#TENS} then it will return 1. If the
   * given amount is 9 and this is {@link Denomination#TENS} then it will return 0. If the given
   * amount is 20 and this is {@link Denomination#TENS} then it will return 2.
   *
   * @param pennies the pennies
   * @return the maximum equivalent count of this denomination for given value of pennies
   */
  public int getDenominationCount(int pennies) {
    return pennies / this.numberOfPenniesInDenomination;
  }

  /**
   * Returns the maximum equivalent dollar count for the given count of this denomination. For e.g
   * if given count is 1700 and this is {@link Denomination#PENNIES} then it will return 17. If the
   * given amount is 9 and this is {@link Denomination#PENNIES} then it will return 0. If the given
   * amount is 200 and this is {@link Denomination#PENNIES} then it will return 2.
   *
   * @param denominationCount the denomination count
   * @return the maximum equivalent dollar count for the given count of this denomination
   */
  public int getDollars(int denominationCount) {
    return ONES.getDenominationCount(getPennies(denominationCount));
  }
}
