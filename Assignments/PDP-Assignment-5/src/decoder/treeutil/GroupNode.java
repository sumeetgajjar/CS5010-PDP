package decoder.treeutil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import decoder.bean.DecodedData;
import util.Utils;


/**
 * This class represents a {@link GroupNode} for the PrefixTree. It implements {@link
 * PrefixTreeNode} interface. It is a generic class of Type <code>P</code> and <code>T</code>. Type
 * <code>P</code> is the type of path which will be used to reach children. <code>T</code> is the
 * type of Data that will be stored at {@link LeafNode}. The {@link GroupNode} does not store any
 * data. It has references of its children and a corresponding path to reach each children. Its
 * children can be a {@link GroupNode} or an {@link LeafNode}.
 */
public class GroupNode<P, T> implements PrefixTreeNode<P, T> {

  private final Map<P, PrefixTreeNode<P, T>> children;
  private final Set<P> validCodingSymbols;

  /**
   * Constructs a {@link GroupNode} with the given params.
   *
   * @param validCodingSymbols the valid coding symbols for this tree
   */
  public GroupNode(Set<P> validCodingSymbols) {
    Utils.checkNullOrEmptyCollection(validCodingSymbols);
    this.validCodingSymbols = validCodingSymbols;
    this.children = new LinkedHashMap<>();
  }

  /**
   * Adds the given data to the given path in the tree. It throws a {@link IllegalStateException} if
   * a node already exists at the given path.
   *
   * @param pathSequence the path at which data is to be stored
   * @param data         the data to be stored
   * @throws IllegalStateException if the data cannot be added at the given path
   */
  @Override
  public void addChild(List<P> pathSequence, T data) throws IllegalStateException {

    P path = pathSequence.get(0);
    if (pathSequence.size() == 1) {
      checkIfChildrenAlreadyExists(path);
      this.children.put(path, new LeafNode<>(data));
      return;
    }

    PrefixTreeNode<P, T> nodeAtPath = this.children.get(path);
    List<P> reducedPath = pathSequence.subList(1, pathSequence.size());

    if (Objects.nonNull(nodeAtPath)) {
      nodeAtPath.addChild(reducedPath, data);
    } else {
      PrefixTreeNode<P, T> groupNode = new GroupNode<>(validCodingSymbols);
      groupNode.addChild(reducedPath, data);
      this.children.put(path, groupNode);
    }
  }

  /**
   * Decodes a part of the given sequence from the given startIndex and returns the {@link
   * DecodedData}. It starts decoding the encodedSequence by traversing the tree from the given
   * startIndex of the given encodedSequence. It uses the given encodedSequence as the traversal
   * path and as soon as it reaches a Leaf node it returns the data at the leaf node along with the
   * new startIndex of the given encodedSequence to continue decoding. It throws {@link
   * IllegalStateException} if the given encodedSequence is invalid and cannot be decoded.
   *
   * @param startIndex      the index of the sequence to start decoding from
   * @param encodedSequence the encoded sequence to decode
   * @return the decoded data for the part of given sequence
   * @throws IllegalStateException if the decoding fails due to any reason
   */
  @Override
  public DecodedData<T> decode(int startIndex, List<P> encodedSequence)
          throws IllegalStateException {

    if (startIndex < encodedSequence.size()) {
      P codingSymbol = encodedSequence.get(startIndex);
      if (this.children.containsKey(codingSymbol)) {
        PrefixTreeNode<P, T> node = this.children.get(codingSymbol);
        return node.decode(startIndex + 1, encodedSequence);
      }
    }
    throw new IllegalStateException("cannot decode given encodedSequence");
  }

  /**
   * Returns list of path to all leaf nodes of this tree. The format of the path of a leaf will be
   * as: "data:path". For e.g. if the tree contains data 'a' at "110" then the path of 'a' will be
   * "a:110".
   *
   * @param currentPath the path till this node
   * @return list of path of all leaf nodes of this tree
   */
  @Override
  public List<String> getAllLeavesPath(String currentPath) {

    List<String> allCodes = new ArrayList<>();
    for (Map.Entry<P, PrefixTreeNode<P, T>> entry : this.children.entrySet()) {
      PrefixTreeNode<P, T> node = entry.getValue();
      P path = entry.getKey();
      allCodes.addAll(node.getAllLeavesPath(String.format("%s%s", currentPath, path)));
    }

    return allCodes;
  }

  /**
   * Returns true if the tree is complete, false otherwise. A Tree is said to complete if every
   * non-leaf node has exactly the same number of children, equal to the size of {@link
   * GroupNode#validCodingSymbols} in the tree.
   *
   * @return true if the tree is complete, false otherwise
   */
  @Override
  public boolean isTreeComplete() {
    boolean isTreeComplete = this.children.size() == validCodingSymbols.size();

    if (isTreeComplete) {
      for (PrefixTreeNode<P, T> node : this.children.values()) {

        isTreeComplete = node.isTreeComplete();
        if (!isTreeComplete) {
          return false;
        }
      }
    }
    return isTreeComplete;
  }

  /**
   * Check if a children exists at the given path.
   *
   * @param path the path to check
   * @throws IllegalStateException if a children already exists at the given path
   */
  private void checkIfChildrenAlreadyExists(P path) throws IllegalStateException {
    if (this.children.containsKey(path)) {
      throw new IllegalStateException("data already exists at given path");
    }
  }
}
