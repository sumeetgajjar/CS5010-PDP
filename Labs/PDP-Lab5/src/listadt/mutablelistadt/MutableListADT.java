package listadt.mutablelistadt;

import java.util.function.Function;

import listadt.ListADT;
import listadt.immutablelistadt.ImmutableListADT;

/**
 * Created by gajjar.s, on 12:27 PM, 11/2/18
 */
public interface MutableListADT<T> extends ListADT<T> {

  @Override
  <R> MutableListADT<R> map(Function<T, R> converter);

  ImmutableListADT<T> getImmutableListAdt();
}
