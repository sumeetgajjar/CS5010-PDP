package lookandsay;

import java.math.BigInteger;
import java.util.Objects;

/**
 * This class represents a look-and-say sequence. It implements {@link RIterator} of type {@link
 * BigInteger}. A  look-and-say sequence is a sequence of numbers. Given the current number, the
 * next number is obtained by reading the current number out loud, writing what we say. For example,
 * if the current number is 1, then we read that as “one 1” and thus the next number in the sequence
 * will be 11. Similarly, if the current number is 112321, then we read that as “two 1s, one 2, one
 * 3, one 2, one 1”, producing the next number as 2112131211, and so on. A look-and-say sequence is
 * a simple example of run-length encoding.
 *
 * The look-and-say sequence can also be computed in reverse. For example 2112131211 can be reversed
 * by taking digits two at a time, and writing them out (write two 1s, then one 2, and so on). This
 * produces 112321.
 */
public class LookAndSayIterator implements RIterator<BigInteger> {

  /**
   * Default end value for the sequence.
   */
  private static final BigInteger DEFAULT_END_VALUE =
          new BigInteger(getStringWith100RepeatingDigits(9));

  /**
   * Default seed value for the sequence.
   */
  private static final BigInteger DEFAULT_START_VALUE = BigInteger.ONE;

  private final BigInteger end;
  private BigInteger current;
  private BigInteger next;
  private BigInteger prev;

  /**
   * Constructs a {@link LookAndSayIterator} object with the given params. It throws an {@link
   * IllegalArgumentException} if the number of continuous digits in the given seed is greater than
   * 9 or if the seed is not a positive number, or is not less than the end. It throws {@link
   * IllegalArgumentException} if any of the given params are null.
   *
   * @param seed the given seed
   * @param end  the given end number
   * @throws IllegalArgumentException if the given seed is invalid
   */
  public LookAndSayIterator(BigInteger seed, BigInteger end) throws IllegalArgumentException {
    this.areParamsValid(seed, end);
    this.current = seed;
    this.prev = getPrev(this.current);
    this.next = getNext(this.current);
    this.end = end;
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
    this(seed, DEFAULT_END_VALUE);
  }

  /**
   * Constructs a {@link LookAndSayIterator} object with the given params. The seed value will be 1.
   * The end value will be a number with 100 9s (the largest 100 digit number).
   */
  public LookAndSayIterator() {
    this(DEFAULT_START_VALUE, DEFAULT_END_VALUE);
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
    BigInteger valueToReturn = this.current;
    if (this.hasPrevious()) {
      this.next = this.current;
      this.current = this.prev;
      this.prev = getPrev(this.prev);
    }
    return valueToReturn;
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
    return this.prev.compareTo(this.current) != 0 && this.prev.compareTo(BigInteger.ONE) >= 0;
  }

  /**
   * Returns true if the next number to be returned is still lesser than end value, false
   * otherwise.
   *
   * @return true if the next number to be returned is still lesser than end value, false otherwise
   */
  @Override
  public boolean hasNext() {
    return this.end.compareTo(this.current) >= 0;
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
    BigInteger valueToReturn = this.current;
    if (this.hasNext()) {
      this.prev = this.current;
      this.current = this.next;
      this.next = this.getNext(this.current);
    }
    return valueToReturn;
  }

  private BigInteger getNext(BigInteger current) {
    char[] digits = current.toString().toCharArray();
    int count = 1;
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < digits.length - 1; i++) {
      if (digits[i] == digits[i + 1]) {
        count++;
      } else {
        builder.append(count);
        builder.append(digits[i]);
        count = 1;
      }
    }
    builder.append(count);
    builder.append(digits[digits.length - 1]);
    return new BigInteger(builder.toString());
  }

  private BigInteger getPrev(BigInteger current) {
    char[] digits = current.toString().toCharArray();
    if (digits.length % 2 == 1) {
      return current;
    }

    int digitCount;
    char digit;
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < digits.length; i += 2) {
      digitCount = digits[i] - '0';
      digit = digits[i + 1];
      for (int j = 0; j < digitCount; j++) {
        builder.append(digit);
      }
    }

    if (builder.length() == 0) {
      return BigInteger.ZERO;
    } else {
      return new BigInteger(builder.toString());
    }
  }

  private void areParamsValid(BigInteger seed, BigInteger end) {
    if (Objects.isNull(seed) || Objects.isNull(end)) {
      throw new IllegalArgumentException("input cannot be null");
    }

    if (seed.compareTo(end) > 0 || seed.compareTo(DEFAULT_START_VALUE) < 0) {
      throw new IllegalArgumentException("invalid seed value");
    }

    if (this.containsDigitWithOddFrequencyGreaterThan10(seed)) {
      throw new IllegalArgumentException("double digit frequencies not allowed");
    }
  }

  private boolean containsDigitWithOddFrequencyGreaterThan10(BigInteger seed) {
    int frequency = 1;
    int lastDigit = seed.mod(BigInteger.TEN).intValue();
    seed = seed.divide(BigInteger.TEN);
    int secondLastDigit;

    while (seed.compareTo(BigInteger.ZERO) > 0) {
      secondLastDigit = seed.mod(BigInteger.TEN).intValue();

      if (lastDigit == secondLastDigit) {
        frequency++;
      } else {
        if (frequency > 9) {
          return true;
        }
        frequency = 1;
      }
      lastDigit = secondLastDigit;
      seed = seed.divide(BigInteger.TEN);
    }

    return frequency > 9;
  }

  private static String getStringWith100RepeatingDigits(int number) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 100; i++) {
      builder.append(number);
    }

    return builder.toString();
  }
}
