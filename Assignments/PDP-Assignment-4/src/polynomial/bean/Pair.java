package polynomial.bean;

/**
 * This class represents a Generic Pair.
 */
public class Pair<F, S> {

  private final F first;
  private final S second;

  /**
   * Constructs a pair object with given parameters.
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
   * @return the second element of the pair
   */
  public S getSecond() {
    return second;
  }

  /**
   * Returns a new pair by reversing the order of elements in this pair.
   *
   * @return a new pair by reversing the order of elements in this pair
   */
  public Pair<S, F> reverse() {
    return new Pair<>(this.second, this.first);
  }
}
