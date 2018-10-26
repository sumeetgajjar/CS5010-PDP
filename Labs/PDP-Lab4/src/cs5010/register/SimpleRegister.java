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
 * Created by gajjar.s, on 1:37 PM, 10/26/18
 */
public class SimpleRegister implements CashRegister {

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
  private final Map<Denomination, Integer> cash;

  /**
   * Constructs a {@link SimpleRegister} object.
   */
  public SimpleRegister() {
    this.transactionList = new LinkedList<>();
    this.cash = new EnumMap<>(Denomination.class);
  }

  /**
   * Add pennies to the register. It throws {@link IllegalArgumentException} if the given amount is
   * less than equal to zero.
   *
   * @param num number of pennies to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  @Override
  public void addPennies(int num) throws IllegalArgumentException {
    this.addCash(Denomination.PENNIES, num);
  }

  /**
   * Add nickels to the register. It throws {@link IllegalArgumentException} if the given amount is
   * less than equal to zero.
   *
   * @param num number of nickels to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  @Override
  public void addNickels(int num) throws IllegalArgumentException {
    this.addCash(Denomination.NICKELS, num);
  }

  /**
   * Add dimes to the register. It throws {@link IllegalArgumentException} if the given amount is
   * less than equal to zero.
   *
   * @param num number of dimes to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  @Override
  public void addDimes(int num) throws IllegalArgumentException {
    this.addCash(Denomination.DIMES, num);
  }

  /**
   * Add quarters to the register. It throws {@link IllegalArgumentException} if the given amount is
   * less than equal to zero.
   *
   * @param num number of quarters to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  @Override
  public void addQuarters(int num) throws IllegalArgumentException {
    this.addCash(Denomination.QUARTER, num);
  }

  /**
   * Add one-dollar bills to the register. It throws {@link IllegalArgumentException} if the given
   * amount is less than equal to zero.
   *
   * @param num number of ones to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  @Override
  public void addOnes(int num) throws IllegalArgumentException {
    this.addCash(Denomination.ONES, num);
  }

  /**
   * Add five-dollar bills to the register. It throws {@link IllegalArgumentException} if the given
   * amount is less than equal to zero.
   *
   * @param num number of fives to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  @Override
  public void addFives(int num) throws IllegalArgumentException {
    this.addCash(Denomination.FIVES, num);
  }

  /**
   * Add ten-dollar bills to the register. It throws {@link IllegalArgumentException} if the given
   * amount is less than equal to zero.
   *
   * @param num number of tens to be added
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  @Override
  public void addTens(int num) throws IllegalArgumentException {
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
   * are no more denominations, throw an exception because the amount cannot be dispensed with what
   * the register contains. It throws an {@link IllegalArgumentException} is the given amount is
   * less than equal to zero.
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
    int givenAmountInPennies = (dollars * 100) + cents;

    Map<Denomination, Integer> updatedCash = new EnumMap<>(Denomination.class);
    Map<Denomination, Integer> withDrawl = new EnumMap<>(Denomination.class);

    for (Denomination denomination : DENOMINATION_ORDER) {
      int denominationCountInRegister = this.cash.getOrDefault(denomination, 0);
      int denominationCountDispense = denomination.getDenominationCount(givenAmountInPennies);
      int denominationCount = Math.min(denominationCountInRegister, denominationCountDispense);

      if (denominationCount != 0) {
        updatedCash.put(denomination, denominationCountInRegister - denominationCount);
        withDrawl.put(denomination, denominationCount);
        givenAmountInPennies = givenAmountInPennies - denomination.getPennies(denominationCount);
      }
    }

    if (givenAmountInPennies == 0) {
      this.transactionList.add(new Transaction(TransactionType.WITHDRAW, dollars, cents));
      for (Map.Entry<Denomination, Integer> entry : updatedCash.entrySet()) {
        this.cash.put(entry.getKey(), entry.getValue());
      }
      return this.convertDenominationMap(withDrawl);
    } else {
      throw new InsufficientCashException("insufficient cash in the register");
    }
  }

  @Override
  public Map<Integer, Integer> getContents() {
    return convertDenominationMap(this.cash);
  }

  @Override
  public String getAuditLog() {
    return this.transactionList.stream()
            .map(Transaction::toString)
            .collect(Collectors.joining(System.lineSeparator()));
  }

  /**
   * Adds the given denomination with given count in register. Throws {@link
   * IllegalArgumentException} if the given amount is less than equal to zero.
   *
   * @param denomination the denomination type
   * @param num          the given amount to check
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  private void addCash(Denomination denomination, int num) throws IllegalArgumentException {
    checkIfDepositDenominationCountIsInvalid(num);
    int dollars = denomination.getDollars(num);
    int pennies = denomination.getPennies(num) - Denomination.ONES.getPennies(dollars);
    this.transactionList.add(new Transaction(TransactionType.DEPOSIT, dollars, pennies));

    this.cash.put(denomination, num);
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
  private Map<Integer, Integer> convertDenominationMap(Map<Denomination, Integer> map) {
    return map.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey().getPennies(1), Map.Entry::getValue));
  }
}
