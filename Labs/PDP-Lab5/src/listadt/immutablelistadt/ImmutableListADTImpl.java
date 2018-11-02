package listadt.immutablelistadt;

import java.util.function.Function;

import listadt.ListADT;
import listadt.ListADTImpl;
import listadt.mutablelistadt.MutableListADT;
import listadt.mutablelistadt.MutableListADTImpl;

/**
 * Created by gajjar.s, on 1:24 PM, 11/2/18
 */
public class ImmutableListADTImpl<T> implements ImmutableListADT<T> {

  private final ListADT<T> listADT;

  private ImmutableListADTImpl(ListADT<T> listADT) {
    this.listADT = listADT;
  }

  @Override
  public void addFront(T b) {
    throw new UnsupportedOperationException("operation not allowed");
  }

  @Override
  public void addBack(T b) {
    throw new UnsupportedOperationException("operation not allowed");
  }

  @Override
  public void add(int index, T b) {
    throw new UnsupportedOperationException("operation not allowed");
  }

  @Override
  public int getSize() {
    return this.listADT.getSize();
  }

  @Override
  public void remove(T b) {
    throw new UnsupportedOperationException("operation not allowed");
  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    return this.listADT.get(index);
  }

  @Override
  public <R> ImmutableListADT<R> map(Function<T, R> converter) {
    ListADT<R> mappedListADT = this.listADT.map(converter);
    return new ImmutableListADTImpl<>(mappedListADT);
  }

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

  public static <E> ImmutableListADTBuilder<E> getBuilder() {
    return new ImmutableListADTImplBuilder<>();
  }

  private static class ImmutableListADTImplBuilder<E> implements ImmutableListADTBuilder<E> {

    private final ListADT<E> listADT;

    private ImmutableListADTImplBuilder() {
      this.listADT = new ListADTImpl<>();
    }

    @Override
    public ImmutableListADTBuilder<E> add(E data) {
      this.listADT.addBack(data);
      return this;
    }

    @Override
    public ImmutableListADT<E> build() {
      return new ImmutableListADTImpl<>(listADT);
    }
  }
}
