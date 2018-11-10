import java.math.BigInteger;
import java.util.Objects;

/**
 * Created by gajjar.s, on 8:24 PM, 11/9/18
 */
public class LookAndSayIterator implements RIterator<BigInteger> {

  private static final BigInteger DEFAULT_END_VALUE = new BigInteger(get100DigitRepeatingString(9));
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
      BigInteger tempCurrent = this.current;
      this.current = this.prev;
      this.prev = getPrev(tempCurrent);
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
    return this.end.compareTo(this.next) > 0;
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
      this.current = this.next;
      this.next = this.getNext(this.current);
    }
    return valueToReturn;
  }

  private BigInteger getNext(BigInteger current) {
    BigInteger next = BigInteger.ZERO;

    int frequency = 1;
    int lastDigit = current.mod(BigInteger.TEN).intValue();
    current = current.divide(BigInteger.TEN);
    int secondLastDigit;

    while (!current.equals(BigInteger.ZERO)) {
      secondLastDigit = current.mod(BigInteger.TEN).intValue();

      if (lastDigit == secondLastDigit) {
        frequency++;
      } else {
        next = next.add(BigInteger.valueOf(frequency))
                .multiply(BigInteger.TEN)
                .add(BigInteger.valueOf(lastDigit));
        frequency = 1;
      }
      lastDigit = secondLastDigit;
      current = current.divide(BigInteger.TEN);
    }

    next = next.add(BigInteger.valueOf(frequency))
            .multiply(BigInteger.TEN)
            .add(BigInteger.valueOf(lastDigit));

    return next;
  }

  private BigInteger getPrev(BigInteger current) {
    int digitCount;
    int digit;
    StringBuilder builder = new StringBuilder();
    BigInteger hundred = BigInteger.TEN;
    while (current.compareTo(BigInteger.ZERO) > 0) {
      digit = current.mod(BigInteger.TEN).intValue();
      digitCount = current.mod(BigInteger.TEN).intValue();
      for (int i = 0; i < digitCount; i++) {
        builder.append(digit);
      }
      current = current.divide(hundred);
    }

    return new BigInteger(builder.reverse().toString());
  }

  private void areParamsValid(BigInteger seed, BigInteger end) {
    if (Objects.isNull(seed) || Objects.isNull(end)) {
      throw new IllegalArgumentException("input cannot be null");
    }

    if (seed.compareTo(end) > 0) {
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

  private static String get100DigitRepeatingString(int number) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 100; i++) {
      builder.append(number);
    }

    return builder.toString();
  }

  public static void main(String[] args) {
    System.out.println(new BigInteger("123").mod(BigInteger.TEN));
  }
}
