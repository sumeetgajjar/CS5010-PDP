package fib;

public interface FibonacciCounter {

  FibonacciCounter incrementCounter();

  FibonacciCounter decrementCounter();

  long getCurrentCount();

  long getCurrentFibonacciValue();
}
