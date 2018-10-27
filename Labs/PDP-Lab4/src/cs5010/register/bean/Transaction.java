package cs5010.register.bean;

/**
 * This class represents a {@link Transaction} that is processed by the Cash Register. A {@link
 * Transaction} consists of {@link TransactionType} and the number of dollars and cents associated
 * with it.
 */
public class Transaction {

  private final TransactionType transactionType;
  private final int dollars;
  private final int pennies;

  /**
   * Constructs a {@link Transaction} with the given params.
   *
   * @param transactionType the type of the transaction
   * @param dollars         the dollar amount associated with the transaction
   * @param pennies         the cent amount associated with the transaction
   */
  public Transaction(TransactionType transactionType, int dollars, int pennies) {
    this.transactionType = transactionType;
    this.dollars = dollars;
    this.pennies = pennies;
  }

  /**
   * Returns the string format of the {@link Transaction}. The format is as follows: "Deposit: x.y"
   * or "Withdraw: x.y", where x is the dollar amount and y is cents up to 2 decimal places.
   *
   * @return the string format of the Transaction
   */
  @Override
  public String toString() {
    double dollars = this.dollars + (this.pennies / 100D);
    return String.format("%s: %.2f", this.transactionType.getTypeString(), dollars);
  }
}
