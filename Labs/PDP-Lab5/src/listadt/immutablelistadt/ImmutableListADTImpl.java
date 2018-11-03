package listadt.immutablelistadt;

import java.util.function.Function;

import listadt.ListADT;
import listadt.ListADTImpl;
import listadt.mutablelistadt.MutableListADT;
import listadt.mutablelistadt.MutableListADTImpl;

/**
 * This class represents a Immutable List. It implements {@link ImmutableListADT}. This class uses
 * {@link ListADTImpl} as its underlying DataStructure to store the list elements. This class will
 * throw an {@link UnsupportedOperationException} if a method which tries to modify this list is
 * invoked. All subclasses, if any should ensure this behavior is respected.
 */
public class ImmutableListADTImpl<T> implements ImmutableListADT<T> {

  private final ListADT<T> listADT;

  /**
   * Private constructor to create a {@link ImmutableListADTImpl} object with the given list.
   *
   * @param listADT the given list
   */
  private ImmutableListADTImpl(ListADT<T> listADT) {
    this.listADT = listADT;
  }

  /**
   * Throws {@link UnsupportedOperationException} if invoked.
   *
   * @throws UnsupportedOperationException if invoked
   */
  @Override
  public void addFront(T b) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("operation not allowed");
  }

  /**
   * Throws {@link UnsupportedOperationException} if invoked.
   *
   * @throws UnsupportedOperationException if invoked
   */
  @Override
  public void addBack(T b) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("operation not allowed");
  }

  /**
   * Throws {@link UnsupportedOperationException} if invoked.
   *
   * @throws UnsupportedOperationException if invoked
   */
  @Override
  public void add(int index, T b) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("operation not allowed");
  }

  /**
   * Throws {@link UnsupportedOperationException} if invoked.
   *
   * @throws UnsupportedOperationException if invoked
   */
  @Override
  public void remove(T b) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("operation not allowed");
  }

  @Override
  public int getSize() {
    return this.listADT.getSize();
  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    return this.listADT.get(index);
  }

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
  public <R> ImmutableListADT<R> map(Function<T, R> converter) {
    ListADT<R> mappedListADT = this.listADT.map(converter);
    return new ImmutableListADTImpl<>(mappedListADT);
  }

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
  @Override
  public MutableListADT<T> getMutableListADT() {
    MutableListADT<T> mutableListADT = new MutableListADTImpl<>();

    int size = this.listADT.getSize();
    for (int i = 0; i < size; i++) {
      mutableListADT.addBack(this.listADT.get(i));
    }

    return mutableListADT;
  }

  @Override
  public String toString() {
    return this.listADT.toString();
  }

  /**
   * Returns a new {@link ImmutableListADTBuilder}.
   *
   * @param <E> Type of the Builder
   * @return a new {@link ImmutableListADTBuilder}
   */
  public static <E> ImmutableListADTBuilder<E> getBuilder() {
    return new ImmutableListADTImplBuilder<>();
  }

  /**
   * This class represents a Builder to build {@link ImmutableListADTImpl}. It implements {@link
   * ImmutableListADTBuilder}.
   */
  private static class ImmutableListADTImplBuilder<E> implements ImmutableListADTBuilder<E> {

    private final ListADT<E> listADT;

    /**
     * Private constructor to create a {@link ImmutableListADTImplBuilder} object.
     */
    private ImmutableListADTImplBuilder() {
      this.listADT = new ListADTImpl<>();
    }

    /**
     * Add the element to the end of ImmutableListADT.
     *
     * @param element the element to add
     * @return the builder object
     */
    @Override
    public ImmutableListADTBuilder<E> add(E element) {
      this.listADT.addBack(element);
      return this;
    }

    /**
     * Returns a newly-created ImmutableListADT based on the contents of this Builder.
     *
     * @return a newly-created ImmutableListADT based on the contents of this Builder
     */
    @Override
    public ImmutableListADT<E> build() {
      return new ImmutableListADTImpl<>(listADT);
    }
  }
}
