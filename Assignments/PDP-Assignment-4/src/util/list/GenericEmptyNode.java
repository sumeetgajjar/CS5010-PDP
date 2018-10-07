package util.list;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This represents an empty node of the generic list implementation.
 */
public class GenericEmptyNode<T> implements GenericListADTNode<T> {

  @Override
  public int count() {
    return 0;
  }

  private GenericListADTNode<T> addFront(T data) {
    return new GenericElementNode<>(data, this);
  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    throw new IllegalArgumentException("Wrong index");
  }

  @Override
  public <R> GenericListADTNode<R> map(Function<T, R> mapper) {
    return new GenericEmptyNode<>();
  }

  @Override
  public GenericListADTNode<T> filter(Predicate<T> predicate) {
    return new GenericEmptyNode<>();
  }

  @Override
  public <R> R fold(R initialValue, BiFunction<R, T, R> accumulator) {
    return initialValue;
  }

  @Override
  public GenericListADTNode<T> insert(T data, Comparator<T> comparator, BiFunction<T, T, T> accumulator) {
    return new GenericElementNode<>(data, this);
  }

  @Override
  public GenericListADTNode<T> combine(GenericListADTNode<T> genericListADTNode, Comparator<T> comparator, BiFunction<T, T, T> accumulator) {
    return genericListADTNode;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof GenericEmptyNode)) {
      return false;
    }

    GenericEmptyNode that = (GenericEmptyNode) obj;
    return true;
  }

  @Override
  public int hashCode() {
    return 93213139;
  }

  @Override
  public String toString() {
    return "";
  }
}
