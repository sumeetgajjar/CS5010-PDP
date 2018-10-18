package decoder.treeutil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import decoder.bean.DecodedData;


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
  public PrefixTreeNode<P, T> addChild(List<P> pathSequence, T data) {

    if (pathSequence.size() == 1) {
      P path = pathSequence.get(0);
      checkIfChildrenAlreadyExists(path);
      this.children.put(path, new LeafNode<>(data));
      return this;
    }

    P path = pathSequence.get(0);
    PrefixTreeNode<P, T> nodeAtPath = this.children.get(path);
    List<P> reducedPath = pathSequence.subList(1, pathSequence.size());

    if (Objects.nonNull(nodeAtPath)) {
      if (nodeAtPath instanceof AbstractPrefixTreeNode) {
        AbstractPrefixTreeNode<P, T> abstractPrefixTreeNode = (AbstractPrefixTreeNode<P, T>) nodeAtPath;
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
      this.children.put(path, groupNode.addChild(reducedPath, data));
      return this;
    }
    throw new IllegalStateException("Cannot reach here");
  }

  @Override
  public DecodedData<T> decode(int startIndex, List<P> encodedSequence) {

    P codingSymbol = encodedSequence.get(startIndex);
    if (this.children.containsKey(codingSymbol)) {
      PrefixTreeNode<P, T> node = this.children.get(codingSymbol);
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
      P path = entry.getKey();
      allCodes.addAll(node.getAllCodes(String.format("%s%s", currentPath, path)));
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

  private void checkIfChildrenAlreadyExists(P path) {
    if (this.children.containsKey(path)) {
      throw new IllegalStateException("data already exists at given path");
    }
  }
}
