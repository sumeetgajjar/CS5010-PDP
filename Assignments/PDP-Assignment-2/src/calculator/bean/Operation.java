package calculator.bean;

public enum Operation {

  ADD('+') {
    @Override
    public int perform(int n1, int n2) {
      return Math.addExact(n1, n2);
    }
  },
  SUBTRACT('-') {
    @Override
    public int perform(int n1, int n2) {
      return Math.subtractExact(n1, n2);
    }
  },
  MULTIPLY('*') {
    @Override
    public int perform(int n1, int n2) {
      return Math.multiplyExact(n1, n2);
    }
  };

  private final char symbol;

  Operation(char symbol) {
    this.symbol = symbol;
  }

  public abstract int perform(int n1, int n2) throws ArithmeticException;

  public char getSymbol() {
    return symbol;
  }

  public static Operation getOperation(char symbol) {
    for (Operation operation : Operation.values()) {
      if (operation.getSymbol() == symbol) {
        return operation;
      }
    }
    throw new IllegalArgumentException(String.format("Operation not found: %s", symbol));
  }
}
