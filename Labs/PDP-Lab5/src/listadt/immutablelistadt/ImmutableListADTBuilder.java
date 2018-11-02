package listadt.immutablelistadt;

/**
 * This Generic interface represents a Builder for creating {@link ImmutableListADT} instances.
 * Builder instances can be reused; it is safe to call <code>build()</code> multiple times to build
 * multiple ImmutableListADT in series.
 */
public interface ImmutableListADTBuilder<T> {

  /**
   * Add the element to the ImmutableListADT.
   *
   * @param element the element to add
   * @return this builder object
   */
  ImmutableListADTBuilder<T> add(T element);

  /**
   * Returns a newly-created ImmutableListADT based on the contents of this Builder.
   *
   * @return a newly-created ImmutableListADT based on the contents of this Builder
   */
  ImmutableListADT<T> build();
}
