package polynomial.util.list;

import java.util.function.Function;

/**
 * This represents an empty node of the generic list implementation.
 */
public class GenericEmptyNode<T> implements GenericListADTNode<T> {

  @Override
  public int count() {
    return 0;
  }

  @Override
  public GenericListADTNode<T> addFront(T data) {
    return new GenericElementNode<>(data, this);
  }

  @Override
  public GenericListADTNode<T> addBack(T data) {
    return addFront(data);
  }

  @Override
  public GenericListADTNode<T> add(int index, T data) throws IllegalArgumentException {
    if (index == 0) {
      return addFront(data);
    }
    throw new IllegalArgumentException("Invalid index to add an element");
  }

  @Override
  public GenericListADTNode<T> remove(T data) {
    return this; //cannot remove from nothing!
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
  public String toString() {
    return "";
  }
}
