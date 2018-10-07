package util.list;

import java.util.function.Function;

/**
 * This is a non-empty node in a generic list. It contains the data data and the rest of the list
 */
public class GenericElementNode<T> implements GenericListADTNode<T> {
  private T data;
  private GenericListADTNode<T> rest;

  public GenericElementNode(T data, GenericListADTNode<T> rest) {
    this.data = data;
    this.rest = rest;
  }

  @Override
  public int count() {
    return 1 + this.rest.count();
  }

  @Override
  public GenericListADTNode<T> addFront(T data) {
    return new GenericElementNode<>(data, this);
  }

  @Override
  public GenericListADTNode<T> addBack(T data) {
    this.rest = this.rest.addBack(data);
    return this;
  }

  @Override
  public GenericListADTNode<T> add(int index, T data) {
    if (index == 0) {
      return addFront(data);
    } else {
      this.rest = this.rest.add(index - 1, data);
      return this;
    }
  }

  @Override
  public GenericListADTNode<T> remove(T data) {
    if (this.data.equals(data)) {
      return this.rest;
    } else {
      this.rest = this.rest.remove(data);
      return this;
    }
  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    if (index == 0) return this.data;
    return this.rest.get(index - 1);
  }

  @Override
  public <R> GenericListADTNode<R> map(Function<T, R> mapper) {
    /* Starting from this list of T, the resulting list of type R is an
    element that contains this data converted to T, followed by the rest of
    the converted list
     */
    return new GenericElementNode<>(mapper.apply(this.data), this.rest.map(mapper));
  }

  @Override
  public String toString() {
    String objString = this.data.toString();
    String rest = this.rest.toString();
    if (rest.length() > 0)
      return objString + " " + rest;
    else
      return objString;
  }
}
