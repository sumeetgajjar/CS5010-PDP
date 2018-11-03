package listadt.mutablelistadt;

import java.util.function.Function;

import listadt.ListADT;
import listadt.ListADTFactory;
import listadt.ListADTImpl;
import listadt.immutablelistadt.ImmutableListADT;
import listadt.immutablelistadt.ImmutableListADTBuilder;

/**
 * This class represents a Mutable List. It extends {@link ListADTImpl} and it implements {@link
 * MutableListADT}.
 */
public class MutableListADTImpl<T> extends ListADTImpl<T> implements MutableListADT<T> {

  /**
   * A general purpose map higher order function on this {@link MutableListADT}, that returns the
   * corresponding {@link MutableListADT} of type R.
   *
   * @param converter the function that converts T into R
   * @param <R>       the type of data in the resulting list
   * @return the resulting list that is identical in structure to this list, but has data of type R
   */
  @Override
  public <R> MutableListADT<R> map(Function<T, R> converter) {
    ListADT<R> mappedList = super.map(converter);

    MutableListADTImpl<R> mappedMutableListADT = new MutableListADTImpl<>();
    int size = mappedList.getSize();
    for (int i = 0; i < size; i++) {
      mappedMutableListADT.addBack(mappedList.get(i));
    }
    return mappedMutableListADT;
  }

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
  @Override
  public ImmutableListADT<T> getImmutableListADT() {
    ImmutableListADTBuilder<T> builder = ListADTFactory.getNewImmutableListADTBuilder();
    int size = this.getSize();
    for (int i = 0; i < size; i++) {
      builder.add(this.get(i));
    }
    return builder.build();
  }
}
