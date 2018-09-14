package fib;

public interface FibonacciCounter {

  FibonacciCounter incrementCounter() throws ArithmeticException;

  FibonacciCounter decrementCounter() throws ArithmeticException;

  long getCurrentCount();

  long getCurrentFibonacciValue();
}
