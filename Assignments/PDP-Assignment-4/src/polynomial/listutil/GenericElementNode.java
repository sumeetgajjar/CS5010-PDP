package polynomial.listutil;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import polynomial.bean.Pair;

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
  public <R> GenericListADTNode<R> map(Function<T, R> mapper) {
    /* Starting from this list of T, the resulting list of type R is an
    element that contains this data converted to T, followed by the rest of
    the converted list
     */
    return new GenericElementNode<>(mapper.apply(this.data), this.rest.map(mapper));
  }

  @Override
  public GenericListADTNode<T> filter(Predicate<T> predicate) {
    if (predicate.test(this.data)) {
      return new GenericElementNode<>(this.data, this.rest.filter(predicate));
    } else {
      return this.rest.filter(predicate);
    }
  }

  @Override
  public <R> R fold(R initialValue, BiFunction<R, T, R> accumulator) {
    return this.rest.fold(accumulator.apply(initialValue, this.data), accumulator);
  }

  @Override
  public GenericListADTNode<Pair<T, T>> zip(GenericListADTNode<T> thatList,
                                            Supplier<T> thisListDefaultValueSupplier,
                                            Supplier<T> thatListDefaultValueSupplier) {

    if (thatList instanceof GenericElementNode) {
      GenericElementNode<T> thatGenericElementNode = (GenericElementNode<T>) thatList;
      Pair<T, T> pair = Pair.of(this.data, thatGenericElementNode.data);
      return new GenericElementNode<>(
              pair,
              this.rest.zip(
                      thatGenericElementNode.rest,
                      thisListDefaultValueSupplier,
                      thatListDefaultValueSupplier));
    } else {
      Pair<T, T> pair = Pair.of(this.data, thatListDefaultValueSupplier.get());
      return new GenericElementNode<>(
              pair,
              this.rest.zip(
                      thatList,
                      thisListDefaultValueSupplier,
                      thatListDefaultValueSupplier));
    }
  }

  @Override
  public GenericListADTNode<T> insert(T data,
                                      Comparator<T> comparator,
                                      BiFunction<T, T, T> mergeFunction) {

    int compare = comparator.compare(this.data, data);

    if (compare < 0) {
      return new GenericElementNode<>(data, this);
    } else if (compare == 0) {
      T accumulatedData = mergeFunction.apply(this.data, data);
      return new GenericElementNode<>(accumulatedData, this.rest);
    } else {
      this.rest = this.rest.insert(data, comparator, mergeFunction);
      return this;
    }

  }
}
