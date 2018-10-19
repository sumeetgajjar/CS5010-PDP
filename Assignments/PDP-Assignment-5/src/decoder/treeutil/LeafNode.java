package decoder.treeutil;

import java.util.Collections;
import java.util.List;

import decoder.bean.DecodedData;

/**
 * Created by gajjar.s, on 4:11 PM, 10/15/18
 */
public class LeafNode<P, T> implements PrefixTreeNode<P, T> {

  private final T data;

  public LeafNode(T data) {
    this.data = data;
  }

  @Override
  public void addChild(List<P> pathSequence, T data) throws IllegalStateException {
    throw new IllegalStateException("children cannot be added to leafNode");
  }

  @Override
  public DecodedData<T> decode(int startIndex, List<P> encodedSequence) {
    return new DecodedData<>(startIndex, this.data);
  }

  @Override
  public List<String> getAllLeavesPath(String currentPath) {
    return Collections.singletonList(
            String.format("%s:%s", this.data, currentPath));
  }

  @Override
  public boolean isTreeComplete() {
    return true;
  }
}
