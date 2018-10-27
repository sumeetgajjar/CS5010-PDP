package cs5010.register;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cs5010.register.bean.Denomination;
import cs5010.register.bean.Transaction;
import cs5010.register.bean.TransactionType;

/**
 * This class represents a {@link SimpleRegister}. It implements {@link CashRegister} interface.
 */
public class SimpleRegister implements CashRegister {

  /**
   * The order in which the denominations will be selected for dispensing the given withdraw
   * amount.
   */
  private static final List<Denomination> DENOMINATION_ORDER = Arrays.asList(
          Denomination.TENS,
          Denomination.FIVES,
          Denomination.ONES,
          Denomination.QUARTER,
          Denomination.DIMES,
          Denomination.NICKELS,
          Denomination.PENNIES
  );

  private final List<Transaction> transactionList;
  private final Map<Denomination, Long> cash;

  /**
   * Constructs a {@link SimpleRegister} object.
   */
  public SimpleRegister() {
    this.transactionList = new LinkedList<>();
    this.cash = new EnumMap<>(Denomination.class);
  }

  /**
   * Add pennies to the register. It throws {@link IllegalArgumentException} if the given amount is
   * less than equal to zero. It throws {@link ArithmeticException} if the total number of pennies
   * in the register exceeds the  {@link Integer} limit after addition of the given number.
   *
   * @param num number of pennies to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   * @throws ArithmeticException      if the total number of pennies in the register exceeds the
   *                                  {@link Integer} limit after the addition of given number
   */
  @Override
  public void addPennies(int num) throws IllegalArgumentException, ArithmeticException {
    this.addCash(Denomination.PENNIES, num);
  }

  /**
   * Add nickels to the register. It throws {@link IllegalArgumentException} if the given amount is
   * less than equal to zero. It throws {@link ArithmeticException} if the total number of nickels
   * in the register exceeds the  {@link Integer} limit after addition of the given number.
   *
   * @param num number of nickels to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   * @throws ArithmeticException      if the total number of nickels in the register exceeds the
   *                                  {@link Integer} limit after the addition of given number
   */
  @Override
  public void addNickels(int num) throws IllegalArgumentException, ArithmeticException {
    this.addCash(Denomination.NICKELS, num);
  }

  /**
   * Add dimes to the register. It throws {@link IllegalArgumentException} if the given amount is
   * less than equal to zero. It throws {@link ArithmeticException} if the total number of dimes in
   * the register exceeds the  {@link Integer} limit after addition of the given number.
   *
   * @param num number of dimes to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   * @throws ArithmeticException      if the total number of dimes in the register exceeds the
   *                                  {@link Integer} limit after the addition of given number
   */
  @Override
  public void addDimes(int num) throws IllegalArgumentException, ArithmeticException {
    this.addCash(Denomination.DIMES, num);
  }

  /**
   * Add quarters to the register. It throws {@link IllegalArgumentException} if the given amount is
   * less than equal to zero. It throws {@link ArithmeticException} if the total number of quarters
   * in the register exceeds the  {@link Integer} limit after addition of the given number.
   *
   * @param num number of quarters to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   * @throws ArithmeticException      if the total number of quarters in the register exceeds the
   *                                  {@link Integer} limit after the addition of given number
   */
  @Override
  public void addQuarters(int num) throws IllegalArgumentException, ArithmeticException {
    this.addCash(Denomination.QUARTER, num);
  }

  /**
   * Add one-dollar bills to the register. It throws {@link IllegalArgumentException} if the given
   * amount is less than equal to zero. It throws {@link ArithmeticException} if the total number of
   * ones in the register exceeds the  {@link Integer} limit after addition of the given number.
   *
   * @param num number of ones to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   * @throws ArithmeticException      if the total number of ones in the register exceeds the {@link
   *                                  Integer} limit after the addition of given number
   */
  @Override
  public void addOnes(int num) throws IllegalArgumentException, ArithmeticException {
    this.addCash(Denomination.ONES, num);
  }

  /**
   * Add five-dollar bills to the register. It throws {@link IllegalArgumentException} if the given
   * amount is less than equal to zero. It throws {@link ArithmeticException} if the total number of
   * fives in the register exceeds the {@link Integer} limit after addition of the given number.
   *
   * @param num number of fives to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   * @throws ArithmeticException      if the total number of fives in the register exceeds the
   *                                  {@link Integer} limit after the addition of given number
   */
  @Override
  public void addFives(int num) throws IllegalArgumentException, ArithmeticException {
    this.addCash(Denomination.FIVES, num);
  }

  /**
   * Add ten-dollar bills to the register. It throws {@link IllegalArgumentException} if the given
   * amount is less than equal to zero. It throws {@link ArithmeticException} if the total number of
   * tens in the register exceeds the {@link Integer} limit after addition of the given number.
   *
   * @param num number of tens to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   * @throws ArithmeticException      if the total number of tens in the register exceeds the {@link
   *                                  Integer} limit after the addition of given number
   */
  @Override
  public void addTens(int num) throws IllegalArgumentException, ArithmeticException {
    this.addCash(Denomination.TENS, num);
  }

  /**
   * Withdraw the provided amount from the cash register, if there is enough. The input is provided
   * in dollars and cents, instead of a floating-point number to avoid precision errors, as only two
   * decimal places of precision are realistic.
   *
   * <p>It works as follows:
   * 1. Start from highest denomination. 2. Find the highest number of coins/notes of current
   * denomination such that their value is less than required value. 3. Take that many notes/coins
   * out of the cash register, and reduce the required value by the appropriate amount. 4. If the
   * required value is greater than 0 and there is a lesser denomination, go to step 2. 5. If there
   * are no more denominations, throw an {@link InsufficientCashException} because the amount cannot
   * be dispensed with what the register contains. It throws an {@link IllegalArgumentException} is
   * the given amount is less than equal to zero.
   *
   * @param dollars the dollar amount to be withdrawn
   * @param cents   the cent amount to be withdrawn
   * @return if dispensing is possible, a map of &lt;value of coin/bill in cents, number of
   *         coins/bills&gt; that represents the change
   * @throws InsufficientCashException if dispensing the amount is not possible.
   * @throws IllegalArgumentException  if the given params are less than or equal to zero
   */
  @Override
  public Map<Integer, Integer> withdraw(int dollars, int cents)
          throws InsufficientCashException, IllegalArgumentException {

    checkIfWithdrawAmountIsInvalid(dollars, cents);
    long givenAmountInPennies = (dollars * 100L) + cents;

    Map<Denomination, Long> updatedCash = new EnumMap<>(Denomination.class);
    Map<Denomination, Long> withDrawl = new EnumMap<>(Denomination.class);

    for (Denomination denomination : DENOMINATION_ORDER) {
      long denominationCountInRegister = this.cash.getOrDefault(denomination, 0L);
      long denominationCountDispense = denomination.getDenominationCount(givenAmountInPennies);
      long denominationCount = Math.min(denominationCountInRegister, denominationCountDispense);

      if (denominationCount != 0) {
        updatedCash.put(denomination, denominationCountInRegister - denominationCount);
        withDrawl.put(denomination, denominationCount);
        givenAmountInPennies = givenAmountInPennies - denomination.getPennies(denominationCount);
      }
    }

    if (givenAmountInPennies == 0) {
      this.transactionList.add(new Transaction(TransactionType.WITHDRAW, dollars, cents));
      for (Map.Entry<Denomination, Long> entry : updatedCash.entrySet()) {
        this.cash.put(entry.getKey(), entry.getValue());
      }
      return this.convertDenominationMap(withDrawl);
    } else {
      throw new InsufficientCashException("insufficient cash in the register");
    }
  }

  /**
   * Return the contents of this register as a map of &lt; value of coin/bill in cents,number of
   * coins/bills &gt;. The map will only contain coin/bill with non-zero count in the register. If
   * the cash register is empty it will return an empty map.
   *
   * @return the contents of this register as a map
   */
  @Override
  public Map<Integer, Integer> getContents() {
    return convertDenominationMap(this.cash);
  }

  /**
   * Returns a string describing the history of transactions performed on the cash register. The
   * audit log is a series of transactions (1 per line). Each line is of the form: "Deposit: x.y" or
   * "Withdraw: x.y", where x is the dollar amount and y is cents up to 2 decimal places.
   *
   * @return the string of the audit log
   */
  @Override
  public String getAuditLog() {
    return this.transactionList.stream()
            .map(Transaction::toString)
            .collect(Collectors.joining(System.lineSeparator()));
  }

  /**
   * Adds the given denomination with given count in register. Throws {@link
   * IllegalArgumentException} if the given amount is less than equal to zero. It throws an {@link
   * ArithmeticException} if the total number of the given Denomination in the register exceeds the
   * {@link Integer} limit after addition of the given number.
   *
   * @param denomination the denomination type
   * @param num          the given amount to check
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   * @throws ArithmeticException      if the total number of the given Denomination in the register
   *                                  exceeds the {@link Integer} limit after addition of the given
   *                                  number
   */
  private void addCash(Denomination denomination, int num)
          throws IllegalArgumentException, ArithmeticException {

    checkIfDepositDenominationCountIsInvalid(num);
    int cashInRegister = Math.toIntExact(this.cash.getOrDefault(denomination, 0L));
    this.cash.put(denomination, (long) Math.addExact(cashInRegister, num));

    int dollars = Math.toIntExact(denomination.getDollars(num));
    int pennies = Math.toIntExact(
            denomination.getPennies(num) - Denomination.ONES.getPennies(dollars));
    this.transactionList.add(new Transaction(TransactionType.DEPOSIT, dollars, pennies));
  }

  /**
   * Checks if the given amount is invalid. It throws IllegalArgumentException if the given amount
   * is less than equal to zero.
   *
   * @param denominationCount the given amount to check
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  private void checkIfDepositDenominationCountIsInvalid(int denominationCount)
          throws IllegalArgumentException {

    if (denominationCount <= 0) {
      throw new IllegalArgumentException("invalid deposit amount");
    }
  }

  /**
   * Checks if the given amount to withdraw is valid. Throws {@link IllegalArgumentException} if any
   * of the given param is less than zero or both the params are zero.
   *
   * @param dollars dollars
   * @param cents   cents
   * @throws IllegalArgumentException if the given amount to withdraw is invalid
   */
  private void checkIfWithdrawAmountIsInvalid(int dollars, int cents)
          throws IllegalArgumentException {
    if (dollars < 0 || cents < 0) {
      throw new IllegalArgumentException("invalid withdraw amount");
    }

    if (dollars == 0 && cents == 0) {
      throw new IllegalArgumentException("invalid withdraw amount");
    }
  }

  /**
   * Converts the given Map of (Denomination,Integer) to Map of (Integer,Integer).
   *
   * @param map the given map to convert
   * @return the converted map
   */
  private Map<Integer, Integer> convertDenominationMap(Map<Denomination, Long> map) {
    return map.entrySet().stream()
            .filter(e -> e.getValue() != 0)
            .collect(Collectors.toMap(
                e -> Math.toIntExact(e.getKey().getPennies(1)),
                e -> Math.toIntExact(e.getValue())));
  }
}
