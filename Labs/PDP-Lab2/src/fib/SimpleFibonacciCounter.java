package fib;

/**
 * This class represents a Simple Fibonacci Counter and it implements interface FibonacciCounter.
 */
public class SimpleFibonacciCounter implements FibonacciCounter {

  private final long currentCount;
  private final long currentFibonacciValue;

  /**
   * Constructs a SimpleFibonacciCounter object with given count and compute its corresponding
   * fibonacci number.
   *
   * @param currentCount initial count of the counter
   */
  private SimpleFibonacciCounter(long currentCount) {
    this.currentCount = currentCount;
    this.currentFibonacciValue = getNthFibonacciNumber(this.currentCount);
  }

  /**
   * Constructs a SimpleFibonacciCounter object and initializes its count to 1.
   */
  public SimpleFibonacciCounter() {
    this(1);
  }

  @Override
  public FibonacciCounter incrementCounter() throws ArithmeticException {
    long newCount = Math.addExact(this.currentCount, 1);
    return new SimpleFibonacciCounter(newCount);
  }

  @Override
  public FibonacciCounter decrementCounter() throws IllegalStateException {
    if (this.currentCount - 1 <= 0) {
      throw new IllegalStateException(
              "Min value of the counter reached, cannot decrement further.");
    }

    long newCount = this.currentCount - 1;
    return new SimpleFibonacciCounter(newCount);
  }

  @Override
  public long getCurrentCount() {
    return this.currentCount;
  }

  @Override
  public long getCurrentFibonacciValue() {
    return this.currentFibonacciValue;
  }

  /**
   * Generates nth Fibonacci Number given n.
   *
   * @param n nth fibonacci number to generate
   * @return nth fibonacci number
   * @throws ArithmeticException if overflow occurs i.e. if nth fibonacci number is out of range of
   *                             long.
   */
  private long getNthFibonacciNumber(long n) throws ArithmeticException {
    if (n == 1) {
      return FibonacciCounter.FIRST_FIBONACCI_NUMBER;
    } else if (n == 2) {
      return FibonacciCounter.SECOND_FIBONACCI_NUMBER;
    } else {

      long first = FibonacciCounter.FIRST_FIBONACCI_NUMBER;
      long second = FibonacciCounter.SECOND_FIBONACCI_NUMBER;
      long sum = 0;
      for (long i = 2; i <= n; i++) {
        sum = Math.addExact(first, second);
        second = first;
        first = sum;
      }
      return sum;
    }
  }
}
