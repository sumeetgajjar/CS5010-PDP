package fib;

/**
 * This interface represents a set of operations for a FibonacciCounter. A Fibonacci counter counts
 * Fibonacci numbers. The count starts at 1 (the first Fibonacci number is 0, the second is 1).
 */
public interface FibonacciCounter {

  FibonacciCounter incrementCounter() throws ArithmeticException;

  FibonacciCounter decrementCounter();

  long getCurrentCount();

  long getCurrentFibonacciValue();
}
