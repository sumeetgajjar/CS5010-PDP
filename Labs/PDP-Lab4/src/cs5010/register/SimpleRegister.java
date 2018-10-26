package cs5010.register;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cs5010.register.bean.Denomination;
import cs5010.register.bean.Transaction;

/**
 * Created by gajjar.s, on 1:37 PM, 10/26/18
 */
public class SimpleRegister implements CashRegister {

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
    checkIfAmountIsInvalid(num);
    this.cash.put(Denomination.PENNIES, num);
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
    checkIfAmountIsInvalid(num);
    this.cash.put(Denomination.NICKELS, num);
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
    checkIfAmountIsInvalid(num);
    this.cash.put(Denomination.DIMES, num);
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
    checkIfAmountIsInvalid(num);
    this.cash.put(Denomination.QUARTER, num);
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
    checkIfAmountIsInvalid(num);
    this.cash.put(Denomination.ONES, num);
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
    checkIfAmountIsInvalid(num);
    this.cash.put(Denomination.FIVES, num);
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
    checkIfAmountIsInvalid(num);
    this.cash.put(Denomination.TENS, num);
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
   * coins/bills&gt; that represents the change
   * @throws InsufficientCashException if dispensing the amount is not possible.
   * @throws IllegalArgumentException  if the given params are less than or equal to zero
   */
  @Override
  public Map<Integer, Integer> withdraw(int dollars, int cents) throws InsufficientCashException, IllegalArgumentException {
    return null;
  }

  @Override
  public Map<Integer, Integer> getContents() {
    return this.cash.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey().getPennies(1), Map.Entry::getValue));
  }

  @Override
  public String getAuditLog() {
    return this.transactionList.stream()
            .map(Transaction::toString)
            .collect(Collectors.joining(System.lineSeparator()));
  }

  /**
   * Checks if the given amount is invalid. It throws IllegalArgumentException if the given amount
   * is less than equal to zero.
   *
   * @param num the given amount to check
   * @throws IllegalArgumentException if the given amount is less than equal to zero
   */
  private void checkIfAmountIsInvalid(int num) throws IllegalArgumentException {
    if (num <= 0) {
      throw new IllegalArgumentException("invalid amount");
    }
  }
}
