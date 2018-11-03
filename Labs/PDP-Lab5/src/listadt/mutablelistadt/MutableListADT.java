package listadt.mutablelistadt;

import java.util.function.Function;

import listadt.ListADT;
import listadt.immutablelistadt.ImmutableListADT;

/**
 * This interface represents MutableListADT. It extends all functionality of {@link ListADT}. It
 * overrides the {@link ListADT#map(Function)} method. The <code>map</code> method now returns
 * {@link MutableListADT} instead of {@link ListADT}. This interface also has a method to get a
 * {@link ImmutableListADT} which has same elements as this {@link MutableListADT}.
 */
public interface MutableListADT<T> extends ListADT<T> {

  /**
   * A general purpose map higher order function on this {@link MutableListADT}, that returns the
   * corresponding {@link MutableListADT} of type R.
   *
   * @param converter the function that converts T into R
   * @param <R>       the type of data in the resulting list
   * @return the resulting mutable list that is identical in structure to this list, but has data
   *         of type R
   */
  @Override
  <R> MutableListADT<R> map(Function<T, R> converter);

  /**
   * Returns a {@link ImmutableListADT} containing all elements of this {@link MutableListADT}.
   * Modifying the contents of this {@link MutableListADT} will not modify the previously created
   * {@link ImmutableListADT} of this {@link MutableListADT}. For e.g. if
   * <code>mutableList=[1,2,3,4]</code> and we create {@link ImmutableListADT} of
   * "mutableList", <code>immutableListBeforeUpdate = mutableList.getImmutableListADT()</code> then
   * immutableListBeforeUpdate=[1,2,3,4]. Now lets add "5" at the end of the mutableList, so
   * mutableList = [1,2,3,4,5]. The immutableListBeforeUpdate will contain only 4 elements i.e.
   * immutableListBeforeUpdate=[1,2,3,4].
   *
   * @return a {@link ImmutableListADT} containing all elements of this {@link MutableListADT}
   */
  ImmutableListADT<T> getImmutableListADT();
}
