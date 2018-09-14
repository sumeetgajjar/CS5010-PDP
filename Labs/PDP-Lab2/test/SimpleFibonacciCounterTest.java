import org.junit.Assert;
import org.junit.Test;

import fib.FibonacciCounter;
import fib.SimpleFibonacciCounter;

/**
 * A Junit Test class for SimpleFibonacciCounter.
 */
public class SimpleFibonacciCounterTest {

  @Test
  public void testInitialState() {
    FibonacciCounter fibonacciCounter = new SimpleFibonacciCounter();
    Assert.assertEquals(fibonacciCounter.getCurrentCount(), 1);
    Assert.assertEquals(fibonacciCounter.getCurrentFibonacciValue(), 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testDecrementCounterAtCount1() {
    FibonacciCounter fibonacciCounter = new SimpleFibonacciCounter();
    fibonacciCounter.decrementCounter();
  }

  @Test
  public void testIncrementCounter() {
    FibonacciCounter fibonacciCounter = new SimpleFibonacciCounter();
    fibonacciCounter = fibonacciCounter.incrementCounter().incrementCounter().incrementCounter();

    Assert.assertEquals(fibonacciCounter.getCurrentCount(), 4);
    Assert.assertEquals(fibonacciCounter.getCurrentFibonacciValue(), 2);
  }

  @Test
  public void testDecrementCounter() {
    FibonacciCounter fibonacciCounter = new SimpleFibonacciCounter();
    fibonacciCounter = fibonacciCounter.incrementCounter().decrementCounter();

    Assert.assertEquals(fibonacciCounter.getCurrentCount(), 1);
    Assert.assertEquals(fibonacciCounter.getCurrentFibonacciValue(), 0);
  }
}
