package listadt.immutablelistadt;

import java.util.function.Function;

import listadt.ListADT;
import listadt.mutablelistadt.MutableListADT;

/**
 * This interface represents a {@link ImmutableListADT}. It extends {@link ListADT}. All
 * implementations should make sure to throw {@link UnsupportedOperationException} if any method
 * which modifies this List is invoked.
 */
public interface ImmutableListADT<T> extends ListADT<T> {

  /**
   * All implementation should throw {@link UnsupportedOperationException} when invoked.
   *
   * @throws UnsupportedOperationException if invoked
   */
  @Override
  void addFront(T b) throws UnsupportedOperationException;

  /**
   * All implementation should throw {@link UnsupportedOperationException} when invoked.
   *
   * @throws UnsupportedOperationException if invoked
   */
  @Override
  void addBack(T b) throws UnsupportedOperationException;

  /**
   * All implementation should throw {@link UnsupportedOperationException} when invoked.
   *
   * @throws UnsupportedOperationException if invoked
   */
  @Override
  void add(int index, T b) throws UnsupportedOperationException;

  /**
   * All implementation should throw {@link UnsupportedOperationException} when invoked.
   *
   * @throws UnsupportedOperationException if invoked
   */
  @Override
  void remove(T b) throws UnsupportedOperationException;

  /**
   * A general purpose map higher order function on this {@link ImmutableListADT}, that returns the
   * corresponding {@link ImmutableListADT} of type R.
   *
   * @param converter the function that converts T into R
   * @param <R>       the type of data in the resulting list
   * @return the resulting Immutable list that is identical in structure to this list, but has data
   *         of type R
   */
  @Override
  <R> ImmutableListADT<R> map(Function<T, R> converter);

  /**
   * Returns a {@link MutableListADT} containing all elements of this {@link ImmutableListADT}.
   * Modifying the contents of the {@link MutableListADT} version of this {@link ImmutableListADT}
   * will not modify this {@link ImmutableListADT}. For e.g. if <code>immutableList=[1,2,3,
   * 4]</code> and we create {@link MutableListADT} of "immutableList",
   * <code> mutableList = immutableList.getMutableListADT() </code> then
   * immutableList=[1,2,3,4]. Now lets add "5" at the end of the mutableList, so mutableList =
   * [1,2,3,4,5]. The immutableList will still contain only 4 elements i.e.
   * immutableList=[1,2,3,4].
   *
   * @return a {@link ImmutableListADT} containing all elements of this {@link MutableListADT}
   */
  MutableListADT<T> getMutableListADT();
}
