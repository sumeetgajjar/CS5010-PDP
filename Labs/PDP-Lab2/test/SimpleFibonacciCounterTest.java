import org.junit.Assert;
import org.junit.Test;

import fib.FibonacciCounter;
import fib.SimpleFibonacciCounter;

public class SimpleFibonacciCounterTest {

  @Test
  public void testInitialState() {
    FibonacciCounter fibonacciCounter = new SimpleFibonacciCounter();
    Assert.assertEquals(fibonacciCounter.getCurrentCount(), 1);
    Assert.assertEquals(fibonacciCounter.getCurrentFibonacciValue(), 0);
  }
}
