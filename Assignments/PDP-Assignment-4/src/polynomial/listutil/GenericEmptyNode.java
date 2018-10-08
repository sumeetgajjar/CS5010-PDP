package polynomial.listutil;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import polynomial.bean.Pair;

/**
 * This represents an empty node of the generic list implementation.
 */
public class GenericEmptyNode<T> implements GenericListADTNode<T> {

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
  public GenericListADTNode<Pair<T, T>> zip(GenericListADTNode<T> thatList, Supplier<T> thisListDefaultValueSupplier, Supplier<T> thatListDefaultValueSupplier) {
    if (thatList instanceof GenericEmptyNode) {
      return new GenericEmptyNode<>();
    } else {
      return thatList.zip(this, thatListDefaultValueSupplier, thatListDefaultValueSupplier);
    }
  }

  @Override
  public GenericListADTNode<T> insert(T data,
                                      Comparator<T> comparator,
                                      BiFunction<T, T, T> mergeFunction) {

    return new GenericElementNode<>(data, new GenericEmptyNode<>());
  }
}
