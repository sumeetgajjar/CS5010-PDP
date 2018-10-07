package polynomial.bean;

/**
 * Created by gajjar.s, on 2:07 PM, 10/7/18
 */
public class Pair<F, S> {

  private final F first;
  private final S second;

  private Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  public F getFirst() {
    return first;
  }

  public S getSecond() {
    return second;
  }

  public static <U, V> Pair<U, V> of(U first, V second) {
    return new Pair<>(first, second);
  }
}
