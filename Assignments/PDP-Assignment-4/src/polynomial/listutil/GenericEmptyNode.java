package polynomial.listutil;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import polynomial.bean.Pair;

/**
 * This represents an empty node of the generic list implementation. It implements {@link
 * GenericListADTNode}.
 */
public class GenericEmptyNode<T> extends AbstractGenericElementNode<T> {

  /**
   * Returns a head of the list with data in an element node and an empty node at its rest.
   *
   * @param data          the data to be inserted
   * @param comparator    the Comparator to compare the given type of data
   * @param mergeFunction the BinaryOperator for merging
   * @return the head of the list
   */
  @Override
  public GenericListADTNode<T> insert(T data,
                                      Comparator<T> comparator,
                                      BinaryOperator<T> mergeFunction) {

    return new GenericElementNode<>(data, new GenericEmptyNode<>());
  }

  /**
   * Returns the head of a empty list.
   *
   * @param mapper the function needed to convert T into R
   * @param <R>    the type of the data in the returned list
   * @return the head of a empty list
   */
  @Override
  public <R> GenericListADTNode<R> map(Function<T, R> mapper) {
    return new GenericEmptyNode<>();
  }

  /**
   * Returns the head of a empty list.
   *
   * @param predicate to apply to each node to determine if it should be included
   * @return the head of a empty list
   */
  @Override
  public GenericListADTNode<T> filter(Predicate<T> predicate) {
    return new GenericEmptyNode<>();
  }

  /**
   * Returns the initial value as folded value.
   *
   * @param initialValue the initial value
   * @param accumulator  the {@link BiFunction} to fold initial value and data in the current node
   * @param <R>          the data type of output of {@link BiFunction}
   * @return the initial value as folded value
   */
  @Override
  public <R> R foldLeft(R initialValue, BiFunction<R, T, R> accumulator) {
    return initialValue;
  }

  /**
   * Zips this list with the given list. Returns an empty list if the specified list is empty, else
   * calls zipAll of the specified list with this list as parameter and respective default value
   * supplier. E.g <code>thatList.zipAll(this, thatListDefaultValueSupplier,
   * thatListDefaultValueSupplier)</code>
   *
   * @param thatList                     other list to zip with
   * @param thisListDefaultValueSupplier default value supplier for this list
   * @param thatListDefaultValueSupplier default value supplier for other list
   * @return the list of Pair formed from this list and given list, by combining corresponding
   *         elements in {@link Pair}
   * @throws IllegalArgumentException if the specified list is not of type {@link
   *                                  GenericElementNode} or {@link GenericEmptyNode}
   */
  @Override
  public GenericListADTNode<Pair<T, T>> zipAll(GenericListADTNode<T> thatList,
                                               Supplier<T> thisListDefaultValueSupplier,
                                               Supplier<T> thatListDefaultValueSupplier)
          throws IllegalArgumentException {

    if (thatList instanceof AbstractGenericElementNode) {
      AbstractGenericElementNode<T> thatTAbstractGenericElementNode =
              (AbstractGenericElementNode<T>) thatList;

      if (thatTAbstractGenericElementNode.isGenericEmptyNode()) {
        return new GenericEmptyNode<>();
      } else if (thatTAbstractGenericElementNode.isGenericElementNode()) {
        return thatList.zipAll(this,
                thatListDefaultValueSupplier,
                thatListDefaultValueSupplier);
      }
    }
    throw new IllegalArgumentException(
            "cannot zip list which is not GenericElementNode or GenericEmptyNode");
  }

  /**
   * Returns if this {@link GenericListADTNode} is {@link GenericEmptyNode}.
   *
   * @return true by default, subclasses may override
   */
  @Override
  protected boolean isGenericEmptyNode() {
    return true;
  }
}
