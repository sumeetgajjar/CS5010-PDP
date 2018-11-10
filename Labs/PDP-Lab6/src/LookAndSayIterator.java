import java.math.BigInteger;

/**
 * Created by gajjar.s, on 8:24 PM, 11/9/18
 */
public class LookAndSayIterator implements RIterator<BigInteger> {

  private static final BigInteger LARGEST_END_BIG_INTEGER = get100DigitRepeatingString(9);

  /**
   * Constructs a {@link LookAndSayIterator} object with the given params. It throws an {@link
   * IllegalArgumentException} if the number of continuous digits in the given seed is greater than
   * 9 or if the seed is not a positive number, or is not less than the end.
   *
   * @param seed the given seed
   * @param end  the given end number
   * @throws IllegalArgumentException if the given seed is invalid
   */
  public LookAndSayIterator(BigInteger seed, BigInteger end) throws IllegalArgumentException {

  }

  /**
   * Constructs a {@link LookAndSayIterator} object with the given params. The end value will be a
   * number with 100 9s (the largest 100 digit number). It throws an {@link
   * IllegalArgumentException} if the number of continuous digits in the given seed is greater than
   * 9.
   *
   * @param seed the given seed
   * @throws IllegalArgumentException if the given seed is invalid
   */
  public LookAndSayIterator(BigInteger seed) throws IllegalArgumentException {

  }

  /**
   * Constructs a {@link LookAndSayIterator} object with the given params. The seed value will be 1.
   * The end value will be a number with 100 9s (the largest 100 digit number).
   */
  public LookAndSayIterator() {
  }

  /**
   * Return the current number in the sequence and reverts to the previous number in the sequence.
   * It only reverts to the previous number if {@link RIterator#hasPrevious()} returns true, else it
   * returns the current number for all subsequent calls to this method.
   *
   * @return the current number in the sequence and revert to the previous number in the sequence
   */
  @Override
  public BigInteger prev() {
    return null;
  }

  /**
   * Returns true if it is possible to go back one step, false otherwise. It cannot go one step
   * behind in following cases
   * <ul>
   * <li>If the current number contains odd number of digits</li>
   * </ul>
   *
   * @return true if it is possible to go back one step, false otherwise
   */
  @Override
  public boolean hasPrevious() {
    return false;
  }

  /**
   * Returns true if the next number to be returned is still lesser than end value, false
   * otherwise.
   *
   * @return true if the next number to be returned is still lesser than end value, false otherwise
   */
  @Override
  public boolean hasNext() {
    return false;
  }

  /**
   * Returns the current number in the sequence and advance to the next number. It only advances to
   * the next number in the sequence if {@link RIterator#hasNext()} returns true, else it will not
   * advance and will return current number for all subsequent calls to this method.
   *
   * @return the current number in the sequence and advance to the next number
   */
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
