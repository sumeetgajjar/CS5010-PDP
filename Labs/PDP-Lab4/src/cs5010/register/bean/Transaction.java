package cs5010.register.bean;

/**
 * This class represents a {@link Transaction} that is processed by the Cash Register.
 */
public class Transaction {

  private final TransactionType transactionType;
  private final int dollars;
  private final int pennies;

  /**
   * Constructs a {@link Transaction} with the given params.
   *
   * @param transactionType the type of transaction
   * @param dollars         the dollar amount in transaction
   * @param pennies         the cent amount in transaction
   */
  public Transaction(TransactionType transactionType, int dollars, int pennies) {
    this.transactionType = transactionType;
    this.dollars = dollars;
    this.pennies = pennies;
  }

  @Override
  public String toString() {
    double dollars = this.dollars + (this.pennies / 100D);
    return String.format("%s: %.2f", this.transactionType.getTypeString(), dollars);
  }
}
