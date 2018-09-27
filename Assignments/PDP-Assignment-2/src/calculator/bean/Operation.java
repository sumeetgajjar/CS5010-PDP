package calculator.bean;

/**
 * This enum represents Set of arithmetic operations.
 */
public enum Operation {

  /**
   * Represents addition operation.
   */
  ADD('+') {
    /**
     * Returns the addition of given numbers and throws ArithmeticException if overflow occurs.
     *
     * @param n1 number 1
     * @param n2 number 2
     * @return the addition of given numbers
     * @throws ArithmeticException if operation results in 32-bit overflow
     */
    @Override
    public int perform(int n1, int n2) throws ArithmeticException {
      return Math.addExact(n1, n2);
    }
  },

  /**
   * Represents subtraction operation.
   */
  SUBTRACT('-') {
    /**
     * Returns the subtraction of given numbers and throws ArithmeticException if overflow occurs.
     *
     * @param n1 number1
     * @param n2 number2
     * @return the subtraction of given numbers
     * @throws ArithmeticException if operation results in 32-bit overflow
     */
    @Override
    public int perform(int n1, int n2) throws ArithmeticException {
      return Math.subtractExact(n1, n2);
    }
  },

  /**
   * Represents multiplication operation.
   */
  MULTIPLY('*') {
    /**
     * Returns product of given numbers and throws ArithmeticException if overflow occurs.
     *
     * @param n1 number1
     * @param n2 number2
     * @return the multiplication of given numbers
     * @throws ArithmeticException if operation results in 32-bit overflow
     */
    @Override
    public int perform(int n1, int n2) throws ArithmeticException {
      return Math.multiplyExact(n1, n2);
    }
  };

  private final char symbol;

  Operation(char symbol) {
    this.symbol = symbol;
  }

  /**
   * Returns the result of arithmetic operation on given numbers.
   *
   * @param n1 number1
   * @param n2 number2
   * @return the result of arithmetic operation on given numbers
   * @throws ArithmeticException if operation results in 32-bit overflow
   */
  public abstract int perform(int n1, int n2) throws ArithmeticException;

  /**
   * Returns the symbol associated with the operation.
   *
   * @return the symbol associated with the operation
   */
  public char getSymbol() {
    return symbol;
  }

  /**
   * Returns the Operation associated with the symbol.
   *
   * @param symbol symbol
   * @return the Operation associated with the symbol
   * @throws IllegalArgumentException if no Operation is associated with the given symbol
   */
  public static Operation getOperation(char symbol) throws IllegalArgumentException {
    for (Operation operation : Operation.values()) {
      if (operation.getSymbol() == symbol) {
        return operation;
      }
    }
    throw new IllegalArgumentException(String.format("Operation not found: %s", symbol));
  }
}
