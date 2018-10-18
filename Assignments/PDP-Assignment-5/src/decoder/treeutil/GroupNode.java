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
 * Created by gajjar.s, on 4:10 PM, 10/15/18
 */
public class GroupNode<P, T> extends AbstractPrefixTreeNode<P, T> {

  private final Map<P, PrefixTreeNode<P, T>> children;
  private final Set<P> validCodingSymbols;

  public GroupNode(Set<P> validCodingSymbols) {
    this.validCodingSymbols = validCodingSymbols;
    this.children = new LinkedHashMap<>();
  }

  @Override
  public PrefixTreeNode<P, T> addChild(P[] pathSequence, T data) {

    if (pathSequence.length == 1) {

      if (this.children.containsKey(pathSequence[0])) {
        throw new IllegalStateException("data already exists at given path");
      }

      PrefixTreeNode<P, T> leafNode = new LeafNode<>(data);
      this.children.put(pathSequence[0], leafNode);
      return this;

    }

    P immediatePath = pathSequence[0];
    PrefixTreeNode<P, T> nodeAtImmediateNextPath = this.children.get(immediatePath);
    P[] reducedPath = Utils.getSubArray(pathSequence, 1, pathSequence.length);

    if (Objects.nonNull(nodeAtImmediateNextPath)) {

      if (nodeAtImmediateNextPath instanceof AbstractPrefixTreeNode) {
        AbstractPrefixTreeNode<P, T> abstractPrefixTreeNode = (AbstractPrefixTreeNode<P, T>) nodeAtImmediateNextPath;

        if (abstractPrefixTreeNode.isGroupNode()) {
          abstractPrefixTreeNode.addChild(reducedPath, data);
          return this;
        }

        if (abstractPrefixTreeNode.isLeafNode()) {
          throw new IllegalStateException("data already exists at given path");
        }
      }

    } else {

      PrefixTreeNode<P, T> groupNode = new GroupNode<>(validCodingSymbols);
      this.children.put(immediatePath, groupNode.addChild(reducedPath, data));
      return this;

    }
    throw new IllegalStateException("Cannot reach here");
  }

  @Override
  public DecodedData<T> decode(int startIndex, List<P> encodedSequence) {

    P p = encodedSequence.get(startIndex);

    if (this.children.containsKey(p)) {
      PrefixTreeNode<P, T> node = this.children.get(p);
      return node.decode(startIndex + 1, encodedSequence);
    } else {
      throw new IllegalStateException("cannot decode given sequence");
    }
  }

  @Override
  public List<String> getAllCodes(String currentPath) {

    List<String> allCodes = new ArrayList<>();
    for (Map.Entry<P, PrefixTreeNode<P, T>> entry : this.children.entrySet()) {
      PrefixTreeNode<P, T> node = entry.getValue();
      P key = entry.getKey();
      allCodes.addAll(node.getAllCodes(currentPath + key));
    }

    return allCodes;
  }

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

  @Override
  protected boolean isGroupNode() {
    return true;
  }
}
