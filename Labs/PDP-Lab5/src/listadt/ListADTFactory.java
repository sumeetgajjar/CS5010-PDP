package listadt;

import listadt.immutablelistadt.ImmutableListADTBuilder;
import listadt.immutablelistadt.ImmutableListADTImpl;
import listadt.mutablelistadt.MutableListADTImpl;

/**
 * Created by gajjar.s, on 12:55 PM, 11/3/18
 */
public class ListADTFactory {

  public static <V> ImmutableListADTBuilder<V> getNewImmutableListADTBuilder() {
    return ImmutableListADTImpl.getBuilder();
  }

  public static <V> MutableListADTImpl<V> getNewMutableListADT() {
    return new MutableListADTImpl<>();
  }
}
