package fib;

/**
 * This interface represents a set of operations for a FibonacciCounter. A Fibonacci counter counts
 * Fibonacci numbers. The count starts at 1 (the first Fibonacci number is 0, the second is 1).
 */
public interface FibonacciCounter {

  /**
   * First Fibonacci Number
   */
  long FIRST_FIBONACCI_NUMBER = 0;

  /**
   * Second Fibonacci Number
   */
  long SECOND_FIBONACCI_NUMBER = 1;

  /**
   * Checks if the counter can be incremented, if yes then increments the counter by 1 and returns a
   * FibonacciCounter Object else throws an ArithmeticException.
   *
   * @return a FibonacciCounter object
   * @throws ArithmeticException if FibonacciCounter Overflows.
   */
  FibonacciCounter incrementCounter() throws ArithmeticException;

  /**
   * Checks if the counter can be decremented, if yes then decrements the counter by 1 and returns a
   * FibonacciCounter Object else throws an IllegalStateException.
   *
   * @return a FibonacciCounter object
   * @throws IllegalStateException if FibonacciCounter is at count 1 and decrementCounter() is
   *                               invoked.
   */
  FibonacciCounter decrementCounter() throws IllegalStateException;

  /**
   * Returns the current of the FibonacciCounter.
   *
   * @return the count of the FibonacciCounter
   */
  long getCurrentCount();

  /**
   * Returns the current FibonacciValue of the counter
   *
   * @return the FibonacciValue of the counter
   */
  long getCurrentFibonacciValue();
}
