package fib;

public interface FibonacciCounter {
  FibonacciCounter incrmentCounter();

  FibonacciCounter decrementCounter();

  int getCurrentCount();

  int getCurrentFibonnaciValue();
}
