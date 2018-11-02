package listadt.immutablelistadt;

import java.util.function.Function;

import listadt.ListADT;
import listadt.mutablelistadt.MutableListADT;

/**
 * Created by gajjar.s, on 1:24 PM, 11/2/18
 */
public class ImmutableListADTImpl<T> implements ImmutableListADT<T> {

  public ImmutableListADTImpl(ListADT<T> listADT) {

  }

  @Override
  public void addFront(T b) {

  }

  @Override
  public void addBack(T b) {

  }

  @Override
  public void add(int index, T b) {

  }

  @Override
  public int getSize() {
    return 0;
  }

  @Override
  public void remove(T b) {

  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    return null;
  }

  @Override
  public <R> ImmutableListADT<R> map(Function<T, R> converter) {
    return null;
  }

  @Override
  public MutableListADT<T> getMutableListAdt() {
    return null;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  public static <E> ImmutableListADTBuilder<E> getBuilder() {
    return new ImmutableListADTImplBuilder<>();
  }

  private static class ImmutableListADTImplBuilder<E> implements ImmutableListADTBuilder<E> {

    private ImmutableListADTImplBuilder() {
    }

    @Override
    public ImmutableListADTBuilder<E> add(E data) {
      return this;
    }

    @Override
    public ImmutableListADT<E> build() {
      return null;
    }
  }
}
