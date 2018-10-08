package polynomial.listutil;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import polynomial.bean.Pair;

/**
 * This interface represents all the operations to be supported by a list of objects of type T.
 */
public interface GenericListADTNode<T> {

  /**
   * A insert higher order function for nodes. This method iterates over the list, compares the data
   * in each node in list with the given data using the given {@link Comparator} and inserts the
   * given data in descending order of data. If this list already contains given the data then it
   * merges the given data into the data already present in the list using the given {@link
   * BinaryOperator}.
   *
   * @param data          the data to be inserted
   * @param comparator    the Comparator to compare the given type of data
   * @param mergeFunction the BinaryOperator for merging
   * @return the head of the list
   */
  GenericListADTNode<T> insert(T data, Comparator<T> comparator, BinaryOperator<T> mergeFunction);

  /**
   * A general map higher order function for nodes. This returns a list of identical structure, but
   * each data item of type T converted into R using the provided mapper method.
   *
   * @param mapper the function needed to convert T into R
   * @param <R>    the type of the data in the returned list
   * @return the head of a list that is structurally identical to this list, but contains data of
   * type R
   */
  <R> GenericListADTNode<R> map(Function<T, R> mapper);

  /**
   * A general filter higher order function for nodes. This returns a list of each data item of type
   * T that match the given predicate.
   *
   * @param predicate to apply to each node to determine if it should be included
   * @return the head of a filtered list of data item of Type T
   */
  GenericListADTNode<T> filter(Predicate<T> predicate);

  /**
   * A general foldLeft higher order function for nodes. Applies a {@link BiFunction} to a initial
   * value and all elements of this list, going left to right.
   *
   * @param <R>          the data type of output of {@link BiFunction}
   * @param initialValue the initial value
   * @param accumulator  the {@link BiFunction} to fold initial value and data in the current node
   * @return the result of applying {@link BiFunction} to a initial value and all elements of this
   * list
   */
  <R> R foldLeft(R initialValue, BiFunction<R, T, R> accumulator);

  /**
   * A general zipAll higher order function for nodes. Returns a list of {@link Pair} formed from
   * this list and given list, by combining corresponding elements in {@link Pair}. If one of the
   * two list is shorter than the other, corresponding default values are used to extend the shorter
   * list to the length of the longer.
   *
   * @param thatList                     other list to zip with
   * @param thisListDefaultValueSupplier default value supplier for this list
   * @param thatListDefaultValueSupplier default value supplier for other list
   * @return the list of Pair formed from this list and given list, by combining corresponding
   * elements in {@link Pair}
   */
  GenericListADTNode<Pair<T, T>> zipAll(GenericListADTNode<T> thatList,
                                        Supplier<T> thisListDefaultValueSupplier,
                                        Supplier<T> thatListDefaultValueSupplier);
}
