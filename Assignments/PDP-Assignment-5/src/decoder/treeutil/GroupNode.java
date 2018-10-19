package decoder.treeutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import decoder.bean.DecodedData;


/**
 * Created by gajjar.s, on 4:10 PM, 10/15/18
 */
public class GroupNode<P, T> implements PrefixTreeNode<P, T> {

  private final Map<P, PrefixTreeNode<P, T>> children;
  private final Set<P> validCodingSymbols;

  public GroupNode(Set<P> validCodingSymbols) {
    this.validCodingSymbols = validCodingSymbols;
    this.children = new LinkedHashMap<>();
  }

  @Override
  public void addChild(List<P> pathSequence, T data) throws IllegalStateException {

    if (pathSequence.size() == 1) {
      P path = pathSequence.get(0);
      checkIfChildrenAlreadyExists(path);
      this.children.put(path, new LeafNode<>(data));
      return;
    }

    P path = pathSequence.get(0);
    PrefixTreeNode<P, T> nodeAtPath = this.children.get(path);
    List<P> reducedPath =
            Collections.unmodifiableList(pathSequence.subList(1, pathSequence.size()));

    if (Objects.nonNull(nodeAtPath)) {
      nodeAtPath.addChild(reducedPath, data);
    } else {
      PrefixTreeNode<P, T> groupNode = new GroupNode<>(validCodingSymbols);
      groupNode.addChild(reducedPath, data);
      this.children.put(path, groupNode);
    }
  }

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
    throw new IllegalStateException("cannot decode given sequence");
  }

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

  private void checkIfChildrenAlreadyExists(P path) throws IllegalStateException {
    if (this.children.containsKey(path)) {
      throw new IllegalStateException("data already exists at given path");
    }
  }
}
