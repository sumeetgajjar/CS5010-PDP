import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by gajjar.s, on 8:26 PM, 11/9/18
 */
public class LookAndSayIteratorTest {

  @Test
  public void testInitializationOfLookAndSayIterator() {
    for (RIterator<BigInteger> rIterator : Arrays.asList(
            new LookAndSayIterator(),
            new LookAndSayIterator(BigInteger.ONE),
            new LookAndSayIterator(BigInteger.ONE, new BigInteger("12")))) {

      Assert.assertTrue(rIterator.hasNext());
      Assert.assertFalse(rIterator.hasPrevious());
      Assert.assertEquals(new BigInteger("11"), rIterator.next());
      Assert.assertEquals(BigInteger.ONE, rIterator.prev());
    }
  }

  @Test
  public void testIteratorNextAndPrev() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(new BigInteger("112321"));
    Assert.assertEquals(new BigInteger("2112131211"), rIterator.next());
    Assert.assertEquals(new BigInteger("112321"), rIterator.prev());
  }

  @Test
  public void testSeedWithDoubleDigitFrequencyFails() {
    try {
      new LookAndSayIterator(new BigInteger("11111111112222222222"));
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("double digit frequencies not allowed", e.getMessage());
    }

    try {
      new LookAndSayIterator(new BigInteger("1111111111"), new BigInteger("1111111111"));
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("double digit frequencies not allowed", e.getMessage());
    }
  }

  @Test
  public void testInvalidSeedValue() {
    try {
      new LookAndSayIterator(new BigInteger("0"));
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("invalid seed value", e.getMessage());
    }

    try {
      new LookAndSayIterator(new BigInteger("-1"));
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("invalid seed value", e.getMessage());
    }

    try {
      new LookAndSayIterator(BigInteger.TEN, BigInteger.ONE);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("invalid seed value", e.getMessage());
    }
  }

  @Test
  public void testEndValueWithDoubleDigitFrequencyWorks() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(new BigInteger("111111111"),
            new BigInteger("1111111111"));
    Assert.assertEquals(new BigInteger("91"), rIterator.next());
  }

  @Test
  public void testNextWorks() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(BigInteger.ONE, new BigInteger("100"));
    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("11"), rIterator.next());

    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("21"), rIterator.next());

    Assert.assertFalse(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("21"), rIterator.next());
    Assert.assertFalse(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("21"), rIterator.next());
    Assert.assertFalse(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("21"), rIterator.next());
  }

  @Test
  public void testPrevWorks() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(new BigInteger("213"));
    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("121113"), rIterator.next());

    Assert.assertTrue(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("213"), rIterator.prev());

    Assert.assertFalse(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("213"), rIterator.prev());
  }
}
