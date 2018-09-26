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
     * Returns the sum of given numbers and throws exception if overflow occurs.
     *
     * @param n1 number 1
     * @param n2 number 2
     * @return the sum of given numbers
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
     * Returns the difference of given numbers and throws exception if overflow occurs.
     *
     * @param n1 number1
     * @param n2 number2
     * @return the difference of given numbers
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
     * Returns the product of given numbers and throws exception if overflow occurs.
     *
     * @param n1 number1
     * @param n2 number2
     * @return the producr of given numbers
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
   */
  public static Operation getOperation(char symbol) {
    for (Operation operation : Operation.values()) {
      if (operation.getSymbol() == symbol) {
        return operation;
      }
    }
    throw new IllegalArgumentException(String.format("Operation not found: %s", symbol));
  }
}
