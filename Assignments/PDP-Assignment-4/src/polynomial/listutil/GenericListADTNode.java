package polynomial.listutil;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import polynomial.bean.Pair;

/**
 * This interface represents all the operations to be supported by a list of objects of type T.
 */
public interface GenericListADTNode<T> {

  /**
   * A general map higher order function on nodes. This returns a list of identical structure, but
   * each data item of type T converted into R using the provided mapper method.
   *
   * @param mapper the function needed to convert T into R
   * @param <R>    the type of the data in the returned list
   * @return the head of a list that is structurally identical to this list, but contains data of
   * type R
   */
  <R> GenericListADTNode<R> map(Function<T, R> mapper);

  GenericListADTNode<T> filter(Predicate<T> predicate);

  <R> R fold(R initialValue, BiFunction<R, T, R> accumulator);

  GenericListADTNode<Pair<T, T>> zip(GenericListADTNode<T> list,
                                     Supplier<T> thisListDefaultValueSupplier,
                                     Supplier<T> thatListDefaultValueSupplier);

  GenericListADTNode<T> insert(T data, Comparator<T> comparator, BiFunction<T, T, T> mergeFunction);
}
