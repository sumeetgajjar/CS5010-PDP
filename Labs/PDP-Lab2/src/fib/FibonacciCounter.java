package fib;

public interface FibonacciCounter {

  FibonacciCounter incrementCounter() throws ArithmeticException;

  FibonacciCounter decrementCounter();

  long getCurrentCount();

  long getCurrentFibonacciValue();
}
