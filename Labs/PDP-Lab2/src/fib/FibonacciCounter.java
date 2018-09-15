package fib;

/**
 * This interface represents a set of operations for a FibonacciCounter. A Fibonacci counter counts
 * Fibonacci numbers from 1. The first Fibonacci number is 0 and the second is 1).
 */
public interface FibonacciCounter {

  /**
   * First Fibonacci Number is 0.
   */
  long FIRST_FIBONACCI_NUMBER = 0;

  /**
   * Second Fibonacci Number is 1.
   */
  long SECOND_FIBONACCI_NUMBER = 1;

  /**
   * Increments the counter by 1 and returns a FibonacciCounter Object. If the counter cannot be
   * incremented further, it throws an ArithmeticException.
   *
   * @return a FibonacciCounter object
   * @throws ArithmeticException if FibonacciCounter Overflows.
   */
  FibonacciCounter incrementCounter() throws ArithmeticException;

  /**
   * Decrements the counter by 1 and returns a FibonacciCounter Object. If the counter cannot be
   * decremented further, it throws an IllegalStateException.
   *
   * @return a FibonacciCounter object
   * @throws IllegalStateException if FibonacciCounter is at count 1 and decrementCounter() is
   *                               invoked.
   */
  FibonacciCounter decrementCounter() throws IllegalStateException;

  /**
   * Returns the current count of the FibonacciCounter.
   *
   * @return the count of the FibonacciCounter
   */
  long getCurrentCount();

  /**
   * Returns the current FibonacciValue of the counter.
   *
   * @return the FibonacciValue of the counter
   */
  long getCurrentFibonacciValue();
}
