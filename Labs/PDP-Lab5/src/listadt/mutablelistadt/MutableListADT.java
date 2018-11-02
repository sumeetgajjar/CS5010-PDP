package listadt.mutablelistadt;

import listadt.ListADT;
import listadt.immutablelistadt.ImmutableListADT;

/**
 * Created by gajjar.s, on 12:27 PM, 11/2/18
 */
public interface MutableListADT<T> extends ListADT<T> {

  ImmutableListADT<T> getImmutableListAdt();
}
