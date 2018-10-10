package polynomial.listutil;


/**
 * This class represents an AbstractGenericElement of Type <code>T</code>. It has helper methods to
 * be used by subclasses to check if a {@link GenericListADTNode} is of Type {@link
 * GenericEmptyNode} or {@link GenericElementNode}.
 */
public abstract class AbstractGenericElementNode<T> implements GenericListADTNode<T> {

  /**
   * Returns if this {@link GenericListADTNode} is of Type {@link GenericElementNode}.
   *
   * @return false by default, subclasses may override
   */
  protected boolean isGenericElementNode() {
    return false; //by default "this" GenericListADTNode is not an GenericElementNode
  }

  /**
   * Returns if this {@link GenericListADTNode} is of Type {@link GenericEmptyNode}.
   *
   * @return false by default, subclasses may override
   */
  protected boolean isGenericEmptyNode() {
    return false; //by default "this" GenericListADTNode is not an GenericEmptyNode
  }
}
