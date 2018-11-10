import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import lookandsay.LookAndSayIterator;
import lookandsay.RIterator;

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
      Assert.assertEquals(new BigInteger("1"), rIterator.next());
      Assert.assertEquals(new BigInteger("11"), rIterator.prev());
    }
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
      new LookAndSayIterator(null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("input cannot be null", e.getMessage());
    }

    try {
      new LookAndSayIterator(BigInteger.ONE, null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("input cannot be null", e.getMessage());
    }

    try {
      new LookAndSayIterator(null, null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("input cannot be null", e.getMessage());
    }

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
  public void testSeedWithLeadingZero() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(new BigInteger("0001"));
    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("1"), rIterator.next());

    Assert.assertTrue(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("11"), rIterator.prev());
  }

  @Test
  public void testSeedWithLeadingZeroInPrevNumber() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(new BigInteger("102011"));
    Assert.assertTrue(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("102011"), rIterator.prev());
    Assert.assertFalse(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("1"), rIterator.prev());

  }

  @Test
  public void testNextReturnsZero() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(new BigInteger("10"));

    Assert.assertFalse(rIterator.hasPrevious());
    Assert.assertEquals(BigInteger.TEN, rIterator.prev());

    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(BigInteger.TEN, rIterator.next());

    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("1110"), rIterator.next());
  }

  @Test
  public void testEndValueWithDoubleDigitFrequencyWorks() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(new BigInteger("111111111"),
            new BigInteger("1111111111"));

    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("111111111"), rIterator.next());
    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("91"), rIterator.next());
  }

  @Test
  public void testNextWorks() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(BigInteger.ONE, new BigInteger("1211"
    ));

    int i = 0;
    List<BigInteger> bigIntegers = Arrays.asList(
            BigInteger.ONE,
            new BigInteger("11"),
            new BigInteger("21"),
            new BigInteger("1211"));

    while (rIterator.hasNext()) {
      Assert.assertEquals(bigIntegers.get(i++), rIterator.next());
    }

    Assert.assertFalse(rIterator.hasNext());
  }

  @Test
  public void testHasNextWorks() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(BigInteger.ONE, new BigInteger("11"));
    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(BigInteger.ONE, rIterator.next());
    Assert.assertEquals(new BigInteger("11"), rIterator.next());

    rIterator = new LookAndSayIterator(BigInteger.ONE, BigInteger.ONE);
    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(BigInteger.ONE, rIterator.next());
    Assert.assertFalse(rIterator.hasNext());
  }

  @Test
  public void testPrevWorks() {
    RIterator<BigInteger> rIterator = new LookAndSayIterator(new BigInteger("213"));
    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("213"), rIterator.next());

    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("121113"), rIterator.next());

    Assert.assertTrue(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("11123113"), rIterator.prev());

    Assert.assertTrue(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("121113"), rIterator.prev());

    Assert.assertFalse(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("213"), rIterator.prev());
  }

  @Test
  public void testRIteratorWithLargeSeedValue() {
    BigInteger seed = new BigInteger(getLargest100DigitValidSeed());
    RIterator<BigInteger> rIterator = new LookAndSayIterator(seed);

    Assert.assertTrue(rIterator.hasPrevious());
    Assert.assertEquals(seed, rIterator.prev());

    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(seed, rIterator.next());

    Assert.assertTrue(rIterator.hasNext());
    Assert.assertEquals(new BigInteger("999899989998999899989918"), rIterator.next());

    Assert.assertTrue(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("3918391839183918391829"), rIterator.prev());

    Assert.assertTrue(rIterator.hasPrevious());
    Assert.assertEquals(new BigInteger("9998999899989998999899"), rIterator.prev());
  }

  private String getLargest100DigitValidSeed() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 5; i++) {
      builder.append("999999999")
              .append("888888888");
    }
    builder.append("9999999998");
    return builder.toString();
  }
}
