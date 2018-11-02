package listadt.immutablelistadt;

import java.util.function.Function;

import listadt.ListADT;
import listadt.mutablelistadt.MutableListADT;

/**
 * Created by gajjar.s, on 12:27 PM, 11/2/18
 */
public interface ImmutableListADT<T> extends ListADT<T> {

  @Override
  <R> ImmutableListADT<R> map(Function<T, R> converter);

  MutableListADT<T> getMutableListADT();
}
