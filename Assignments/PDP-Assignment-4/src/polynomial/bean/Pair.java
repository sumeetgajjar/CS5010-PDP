package polynomial.bean;

/**
 * This class represents a Generic Pair.
 */
public class Pair<F, S> {

  private final F first;
  private final S second;

  /**
   * Constructs a pair of given parameters.
   *
   * @param first  the first element of the pair
   * @param second the second element of the pair
   */
  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Returns the first element of the pair.
   *
   * @return the first element of the pair
   */
  public F getFirst() {
    return first;
  }

  /**
   * Returns the second element of the pair.
   *
   * @return the first element of the pair
   */
  public S getSecond() {
    return second;
  }
}
