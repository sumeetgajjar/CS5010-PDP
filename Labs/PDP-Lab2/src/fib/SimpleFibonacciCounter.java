package fib;

public class SimpleFibonacciCounter implements FibonacciCounter {

  private final long currentCount;
  private final long currentFibonacciValue;

  private SimpleFibonacciCounter(long currentCount, long currentFibonacciValue) {
    this.currentCount = currentCount;
    this.currentFibonacciValue = currentFibonacciValue;
  }

  public SimpleFibonacciCounter() {
    this(1, 0);
  }

  @Override
  public FibonacciCounter incrementCounter() throws ArithmeticException {
    long newCount = Math.addExact(this.currentCount, 1);
    long newFibonacciValue = getNthFibonacciNumber(newCount);
    return new SimpleFibonacciCounter(newCount, newFibonacciValue);
  }

  @Override
  public FibonacciCounter decrementCounter() throws IllegalStateException {
    if (this.currentCount - 1 <= 0) {
      throw new IllegalStateException(
              "Min value of the counter reached, cannot decrement further.");
    }

    long newCount = this.currentCount - 1;
    long newFibonacciValue = getNthFibonacciNumber(newCount);
    return new SimpleFibonacciCounter(newCount, newFibonacciValue);
  }

  @Override
  public long getCurrentCount() {
    return this.currentCount;
  }

  @Override
  public long getCurrentFibonacciValue() {
    return this.currentFibonacciValue;
  }

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
