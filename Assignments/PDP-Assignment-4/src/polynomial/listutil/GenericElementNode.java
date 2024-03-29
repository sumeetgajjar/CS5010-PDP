package polynomial.listutil;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import polynomial.bean.Pair;

/**
 * This represents an element node of the generic list implementation. It implements {@link
 * GenericListADTNode}. It contains the data of Type <code>T</code> and a reference to the rest of
 * the list
 */
public class GenericElementNode<T> extends AbstractGenericElementNode<T> {
  private T data;
  private GenericListADTNode<T> rest;

  /**
   * Constructs a list with the given data and the rest of the list.
   *
   * @param data data for this node of the list
   * @param rest rest of the list for this node
   */
  public GenericElementNode(T data, GenericListADTNode<T> rest) {
    this.data = data;
    this.rest = rest;
  }

  /**
   * A insert higher order function for this list. This method iterates over the list, compares the
   * data in each node in list with the given data using the given {@link Comparator} and inserts
   * the given data in descending order of data. If this list already contains given the data then
   * it merges the given data into the data already present in the list using the given {@link
   * BinaryOperator}.
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

  /**
   * A general map higher order function for this list. This returns a list of identical structure,
   * but each data item of type T converted into R using the provided mapper method.
   *
   * @param mapper the function needed to convert T into R
   * @param <R>    the type of the data in the returned list
   * @return the head of a list that is structurally identical to this list, but contains data of
   *         type R
   */
  @Override
  public <R> GenericListADTNode<R> map(Function<T, R> mapper) {
    /* Starting from this list of T, the resulting list of type R is an
    element that contains this data converted to T, followed by the rest of
    the converted list
     */
    return new GenericElementNode<>(mapper.apply(this.data), this.rest.map(mapper));
  }

  /**
   * A general filter higher order function for this list. This returns a list of each data item of
   * type T that match the given predicate.
   *
   * @param predicate to apply to each node to determine if it should be included
   * @return the head of a filtered list of data item of Type T
   */
  @Override
  public GenericListADTNode<T> filter(Predicate<T> predicate) {
    if (predicate.test(this.data)) {
      return new GenericElementNode<>(this.data, this.rest.filter(predicate));
    } else {
      return this.rest.filter(predicate);
    }
  }

  /**
   * A general foldLeft higher order function for this list. Applies a {@link BiFunction} to a
   * initial value and all elements of this list, going left to right.
   *
   * @param <R>          the data type of output of {@link BiFunction}
   * @param initialValue the initial value
   * @param accumulator  the {@link BiFunction} to fold initial value and data in the current node
   *                     of list
   * @return the result of applying {@link BiFunction} to a initial value and all elements of this
   *         list
   */
  @Override
  public <R> R foldLeft(R initialValue, BiFunction<R, T, R> accumulator) {
    return this.rest.foldLeft(accumulator.apply(initialValue, this.data), accumulator);
  }

  /**
   * A general zipAll higher order function for this list. Returns a list of {@link Pair} formed
   * from this list and given list, by combining corresponding elements in {@link Pair}. If one of
   * the two list is shorter than the other, corresponding default values are used to extend the
   * shorter list to the length of the longer. e.g.<ul>
   * <li>A={a,b,c}, B={d,e,f}, v1 is the default value for A and v2 is default value for B then
   * zipped list = {(a,d),(b,e),(c,f)}</li>
   * <li>A={a,b}, B={d,e,f}, v1 is the default value for A and v2 is default value for B then
   * zipped list = {(a,d),(b,e),(v1,f)}</li>
   * <li>A={a,b,c}, B={d,e}, v1 is the default value for A and v2 is default value for B then
   * zipped list = {(a,d),(b,e),(c,v2)}</li>
   * </ul>
   *
   * <p>All implementations should make sure that the first value in pair belongs to this list and
   * the second value in the pair belongs to the specified list.
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
      AbstractGenericElementNode<T> thatAbstractGenericElementNode =
              (AbstractGenericElementNode<T>) thatList;

      if (thatAbstractGenericElementNode.isGenericElementNode()) {

        /*
        If thatList is of type GenericElementNode then encapsulate the data of this list and
        thatList in a pair in that order and zip the rest of this list with rest of thatList
        else if thatList is of type GenericEmptyNode encapsulate the data of this list and
        default value for thatList in a pair in that order and zip the rest of this list with
        thatList.
         */

        GenericElementNode<T> thatGenericElementNode = (GenericElementNode<T>) thatList;
        Pair<T, T> pair = new Pair<>(this.data, thatGenericElementNode.data);
        return new GenericElementNode<>(
                pair,
                this.rest.zipAll(
                        thatGenericElementNode.rest,
                        thisListDefaultValueSupplier,
                        thatListDefaultValueSupplier));

      } else if (thatAbstractGenericElementNode.isGenericEmptyNode()) {

        Pair<T, T> pair = new Pair<>(this.data, thatListDefaultValueSupplier.get());
        return new GenericElementNode<>(
                pair,
                this.rest.zipAll(
                        thatList,
                        thisListDefaultValueSupplier,
                        thatListDefaultValueSupplier));
      }
    }
    throw new IllegalArgumentException(
            "cannot zip list which is not GenericElementNode or GenericEmptyNode");
  }

  /**
   * Returns if this {@link GenericListADTNode} is {@link GenericElementNode}.
   *
   * @return true by default, subclasses may override
   */
  @Override
  protected boolean isGenericElementNode() {
    return true;
  }
}
