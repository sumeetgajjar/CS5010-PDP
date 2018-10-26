package cs5010.register.bean;

/**
 * Created by gajjar.s, on 1:56 PM, 10/26/18
 */
public class Transaction {

  private final TransactionType transactionType;
  private final int dollars;
  private final int pennies;

  public Transaction(TransactionType transactionType, int dollars, int pennies) {
    this.transactionType = transactionType;
    this.dollars = dollars;
    this.pennies = pennies;
  }

  public String getFomattedString() {
    return String.format("%s: %d.%d", this.transactionType.getTypeString(), this.dollars, this.pennies);
  }
}
