package listadt;

import listadt.immutablelistadt.ImmutableListADTBuilder;
import listadt.immutablelistadt.ImmutableListADTImpl;
import listadt.mutablelistadt.MutableListADT;
import listadt.mutablelistadt.MutableListADTImpl;

/**
 * This class represents a factory to obtain a ImmutableListADTBuilder and a MutableListADT.
 */
public class ListADTFactory {

  /**
   * Returns a {@link ImmutableListADTBuilder} of Type V.
   *
   * @param <V> the type of the Builder
   * @return a {@link ImmutableListADTBuilder} of Type V
   */
  public static <V> ImmutableListADTBuilder<V> getNewImmutableListADTBuilder() {
    return ImmutableListADTImpl.getBuilder();
  }

  /**
   * Returns a {@link MutableListADT} of Type V.
   *
   * @param <V> the type of the List
   * @return a {@link MutableListADT} of Type V
   */
  public static <V> MutableListADT<V> getNewMutableListADT() {
    return new MutableListADTImpl<>();
  }
}
