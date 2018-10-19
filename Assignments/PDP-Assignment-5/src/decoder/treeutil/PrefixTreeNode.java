package decoder.treeutil;

import java.util.List;

import decoder.bean.DecodedData;

/**
 * This interface represents a PrefixTree and all the operations that can be performed on it. This
 * tree stores data only at the leaf nodes. Data can be added a particular path in the tree and can
 * be retrieved by providing the same path.
 *
 * @param <P> the type of the path symbols that will be used for traversing the tree.
 * @param <T> the type of the data which will be stored at the leaf nodes.
 */
public interface PrefixTreeNode<P, T> {

  /**
   * Adds the given data to the given path in the tree. It throws a {@link IllegalStateException} if
   * the given data cannot be added to the given path or a leaf node is invoked to add children to
   * itself.
   *
   * @param pathSequence the path at which data is to be stored
   * @param data         the data to be stored
   * @throws IllegalStateException if the data cannot be added at the given path
   */
  void addChild(List<P> pathSequence, T data) throws IllegalStateException;

  /**
   * Decodes a part of the given sequence from the given index and returns the {@link DecodedData}.
   * It starts decoding the encodedSequence by traversing the tree from the given index of the given
   * sequence. It uses the given encodedSequence as the traversal path and as soon as it reaches a
   * Leaf node it returns the data at the leaf node along with the new index of the given sequence
   * to continue decoding.
   *
   * @param startIndex      the index of the sequence to start decoding from
   * @param encodedSequence the encoded sequence to decode
   * @return the decoded data for the part of given sequence
   * @throws IllegalStateException if the decoding fails due to any reason
   */
  DecodedData<T> decode(int startIndex, List<P> encodedSequence) throws IllegalStateException;

  /**
   * Returns list of path to all leaf nodes of this tree. The format of the path of a leaf will be
   * as: "data:path". For e.g. if the tree contains data 'a' at "110" then the path of 'a' will be
   * "a:110".
   *
   * @param currentPath the path till this node
   * @return list of path of all leaf nodes of this tree
   */
  List<String> getAllLeavesPath(String currentPath);

  /**
   * Returns true if the tree is complete, false otherwise. A Tree is said to complete if every
   * non-leaf node has exactly the same number of children, equal to the number of distinct path
   * symbols in the tree. Returns false in case, a node has no children, except in case of leaf node
   * which does not have any children it return true.
   *
   * @return true if the tree is complete, false otherwise
   */
  boolean isTreeComplete();
}