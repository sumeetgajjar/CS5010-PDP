package lookandsay;

import java.util.Iterator;

/**
 * This interface represents a generic reversible Iterator. It extends {@link Iterator}. It has 2
 * methods to check and go backwards while iterating.
 */
public interface RIterator<T> extends Iterator<T> {

  /**
   * Returns the next element in the iteration.
   *
   * @return the previous element in the iteration
   */
  T prev();

  /**
   * Returns true if there exists a previous element to iterate on, false otherwise.
   *
   * @return true if there exists a previous element to iterate on, false otherwise
   */
  boolean hasPrevious();
}
