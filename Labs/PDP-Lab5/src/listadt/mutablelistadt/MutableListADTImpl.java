package listadt.mutablelistadt;

import java.util.function.Function;

import listadt.ListADTImpl;
import listadt.immutablelistadt.ImmutableListADT;

/**
 * Created by gajjar.s, on 1:22 PM, 11/2/18
 */
public class MutableListADTImpl<T> extends ListADTImpl<T> implements MutableListADT<T> {

  @Override
  public <R> MutableListADT<R> map(Function<T, R> converter) {
    return null;
  }

  @Override
  public ImmutableListADT<T> getImmutableListAdt() {
    return null;
  }
}
