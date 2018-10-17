package decoder.treeutil;

/**
 * Created by gajjar.s, on 4:10 PM, 10/15/18
 */
public abstract class AbstractPrefixTreeNode<P, T> implements PrefixTreeNode<P, T> {

  protected boolean isGroupNode() {
    return false;
  }

  protected boolean isLeafNode() {
    return false;
  }
}
