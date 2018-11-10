package lookandsay;

import java.util.Iterator;

/**
 * Created by gajjar.s, on 8:22 PM, 11/9/18
 */
public interface RIterator<T> extends Iterator<T> {

  T prev();

  boolean hasPrevious();
}
