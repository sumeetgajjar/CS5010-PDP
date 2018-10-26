package cs5010.register.bean;

/**
 * This enum represents the type of transaction that can be performed on a CashRegister.
 */
public enum TransactionType {
  DEPOSIT("Deposit"),
  WITHDRAW("Withdraw");

  private final String typeString;

  /**
   * private constructor to construct {@link TransactionType} object.
   *
   * @param typeString the typeString
   */
  private TransactionType(String typeString) {
    this.typeString = typeString;
  }

  /**
   * Returns the typeString of this {@link TransactionType}.
   *
   * @return the typeString of this {@link TransactionType}
   */
  public String getTypeString() {
    return typeString;
  }
}
