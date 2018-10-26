package cs5010.register.bean;

/**
 * Created by gajjar.s, on 1:56 PM, 10/26/18
 */
public enum TransactionType {
  DEPOSIT("Deposit"),
  WITHDRAWL("Withdraw");

  private final String typeString;

  TransactionType(String typeString) {
    this.typeString = typeString;
  }

  public String getTypeString() {
    return typeString;
  }
}
