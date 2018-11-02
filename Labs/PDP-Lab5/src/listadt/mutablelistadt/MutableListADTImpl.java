package listadt.mutablelistadt;

import java.util.function.Function;

import listadt.ListADT;
import listadt.ListADTImpl;
import listadt.immutablelistadt.ImmutableListADT;
import listadt.immutablelistadt.ImmutableListADTBuilder;
import listadt.immutablelistadt.ImmutableListADTImpl;

/**
 * Created by gajjar.s, on 1:22 PM, 11/2/18
 */
public class MutableListADTImpl<T> extends ListADTImpl<T> implements MutableListADT<T> {

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

  @Override
  public ImmutableListADT<T> getImmutableListADT() {
    ImmutableListADTBuilder<T> builder = ImmutableListADTImpl.getBuilder();
    int size = this.getSize();
    for (int i = 0; i < size; i++) {
      builder.add(this.get(i));
    }
    return builder.build();
  }
}
