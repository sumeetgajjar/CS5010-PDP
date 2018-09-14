package fib;

public class SimpleFibonacciCounter implements FibonacciCounter {

  private final long currentCount;
  private final long currentValue;

  public SimpleFibonacciCounter() {
    currentValue = 0;
    currentCount = 1;
  }

  public SimpleFibonacciCounter(long currentCount, long currentValue) {
    this.currentCount = currentCount;
    this.currentValue = currentValue;
  }

  @Override
  public FibonacciCounter incrementCounter() {
    return null;
  }

  @Override
  public FibonacciCounter decrementCounter() {
    return null;
  }

  @Override
  public long getCurrentCount() {
    return 0;
  }

  @Override
  public long getCurrentFibonacciValue() {
    return 0;
  }
}
