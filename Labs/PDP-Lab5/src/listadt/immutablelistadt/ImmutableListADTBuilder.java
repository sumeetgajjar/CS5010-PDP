package listadt.immutablelistadt;

/**
 * Created by gajjar.s, on 2:35 PM, 11/2/18
 */
public interface ImmutableListADTBuilder<T> {

  ImmutableListADTBuilder<T> add(T data);

  ImmutableListADT<T> build();
}
