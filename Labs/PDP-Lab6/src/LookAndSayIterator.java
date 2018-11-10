import java.math.BigInteger;

/**
 * Created by gajjar.s, on 8:24 PM, 11/9/18
 */
public class LookAndSayIterator implements RIterator<BigInteger> {

  private static final BigInteger LARGEST_END_BIG_INTEGER = get100DigitRepeatingString(9);

  public LookAndSayIterator(BigInteger seed, BigInteger end) {

  }

  public LookAndSayIterator(BigInteger seed) {

  }

  public LookAndSayIterator() {
  }

  @Override
  public BigInteger prev() {
    return null;
  }

  @Override
  public boolean hasPrevious() {
    return false;
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public BigInteger next() {
    return null;
  }

  private static BigInteger get100DigitRepeatingString(int number) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 100; i++) {
      builder.append(number);
    }

    return new BigInteger(builder.toString(), 10);
  }
}
