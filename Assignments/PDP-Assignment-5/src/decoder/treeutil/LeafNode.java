package decoder.treeutil;

import java.util.Collections;
import java.util.List;

import decoder.bean.DecodedData;

/**
 * This class represents a LeafNode for the PrefixTree. It implements the {@link PrefixTreeNode}
 * interface. A {@link LeafNode} It is a generic class of Type <code>P</code> and <code>T</code>.
 * Type <code>P</code> is the type of path to this {@link LeafNode} from its parent. <code>T</code>
 * is the type of Data that will be stored at {@link LeafNode}. A {@link LeafNode} can only store
 * data, it cannot have children.
 */
public class LeafNode<P, T> implements PrefixTreeNode<P, T> {

  private final T data;

  /**
   * Constructs a {@link LeafNode} object with the given data.
   *
   * @param data the data to be stored at leaf node
   */
  public LeafNode(T data) {
    this.data = data;
  }

  /**
   * Throws exception if this method is invoked. In this tree a leaf node is not allowed to add
   * children to itself.
   *
   * @param pathSequence the path at which data is to be stored
   * @param data         the data to be stored
   * @throws IllegalStateException if this method is invoked
   */
  @Override
  public void addChild(List<P> pathSequence, T data) throws IllegalStateException {
    throw new IllegalStateException("children cannot be added to leafNode");
  }

  /**
   * Returns the data at this node encapsulated in {@link DecodedData} object.
   *
   * @param startIndex      the index of the sequence to start decoding from
   * @param encodedSequence the encoded sequence to decode
   * @return the data at this node encapsulated in {@link DecodedData} object
   */
  @Override
  public DecodedData<T> decode(int startIndex, List<P> encodedSequence) {
    return new DecodedData<>(startIndex, this.data);
  }

  /**
   * Returns the path of this leaf node.
   *
   * @param currentPath the path till this node
   * @return the path of this leaf node
   */
  @Override
  public List<String> getAllLeavesPath(String currentPath) {
    return Collections.singletonList(
            String.format("%s:%s", this.data, currentPath));
  }

  /**
   * Returns true since this node is a leaf node.
   *
   * @return true since this node is a leaf node.
   */
  @Override
  public boolean isTreeComplete() {
    return true;
  }
}
