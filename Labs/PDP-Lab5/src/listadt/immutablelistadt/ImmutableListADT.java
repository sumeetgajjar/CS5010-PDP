package listadt.immutablelistadt;

import listadt.ListADT;
import listadt.mutablelistadt.MutableListADT;

/**
 * Created by gajjar.s, on 12:27 PM, 11/2/18
 */
public interface ImmutableListADT<T> extends ListADT<T> {

  MutableListADT<T> getMutableListAdt();
}
