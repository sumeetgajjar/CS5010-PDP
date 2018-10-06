package polynomial.util.list;

import java.util.function.Function;

/**
 * This is the implementation of a generic list. Specifically it implements the listadt.ListADT
 * interface
 */
public class ListADTImpl<T> implements ListADT<T> {
  private GenericListADTNode<T> head;

  public ListADTImpl() {
    head = new GenericEmptyNode<>();
  }

  //a private constructor that is used internally (see map)
  private ListADTImpl(GenericListADTNode<T> head) {
    this.head = head;
  }

  @Override
  public void addFront(T data) {
    head = head.addFront(data);
  }

  @Override
  public void addBack(T data) {
    head = head.addBack(data);
  }

  @Override
  public void add(int index, T data) {
    head = head.add(index, data);
  }

  @Override
  public int getSize() {
    return head.count();
  }

  @Override
  public void remove(T data) {
    head = head.remove(data);
  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    if ((index >= 0) && (index < getSize())) {
      return head.get(index);
    } else throw new IllegalArgumentException("Invalid index");

  }

  @Override
  public <R> ListADT<R> map(Function<T, R> mapper) {
    return new ListADTImpl(head.map(mapper));
  }

  @Override
  public String toString() {
    return "(" + head.toString() + ")";
  }
}
