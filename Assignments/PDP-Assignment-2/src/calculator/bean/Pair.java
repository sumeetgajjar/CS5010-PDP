package calculator.bean;

import java.util.Objects;

public class Pair<U, V> {
  public final U first;
  public final V second;

  public Pair(U first, V second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) return true;
    if (that == null || getClass() != that.getClass()) return false;
    Pair<?, ?> pair = (Pair<?, ?>) that;
    return Objects.equals(first, pair.first) &&
            Objects.equals(second, pair.second);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
  }
}
