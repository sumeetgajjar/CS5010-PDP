package cs5010.register;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cs5010.register.bean.Transaction;

/**
 * Created by gajjar.s, on 1:37 PM, 10/26/18
 */
public class SimpleRegister implements CashRegister {

  private final List<Transaction> transactionList;
  private final Map<Integer, Integer> cash;

  /**
   * Constructs a {@link SimpleRegister} object.
   */
  public SimpleRegister() {
    transactionList = new LinkedList<>();
    cash = new HashMap<>();
  }

  @Override
  public void addPennies(int num) throws IllegalArgumentException {

  }

  @Override
  public void addNickels(int num) throws IllegalArgumentException {

  }

  @Override
  public void addDimes(int num) throws IllegalArgumentException {

  }

  @Override
  public void addQuarters(int num) throws IllegalArgumentException {

  }

  @Override
  public void addOnes(int num) throws IllegalArgumentException {

  }

  @Override
  public void addFives(int num) throws IllegalArgumentException {

  }

  @Override
  public void addTens(int num) throws IllegalArgumentException {

  }

  @Override
  public Map<Integer, Integer> withdraw(int dollars, int cents) throws InsufficientCashException {
    return null;
  }

  @Override
  public Map<Integer, Integer> getContents() {
    return new HashMap<>(this.cash);
  }

  @Override
  public String getAuditLog() {
    return this.transactionList.stream()
            .map(Transaction::toString)
            .collect(Collectors.joining(System.lineSeparator()));
  }

  /**
   * Checks if the given deposit amount is invalid. It throws IllegalArgumentException if the given
   * deposit amount is less than equal to zero.
   *
   * @param num the given amount to check
   * @throws IllegalArgumentException if the given deposit amount is less than equal to zero
   */
  private void checkIfDepositAmountIsInvalid(int num) throws IllegalArgumentException {
    if (num <= 0) {
      throw new IllegalArgumentException("invalid deposit amount");
    }
  }
}
