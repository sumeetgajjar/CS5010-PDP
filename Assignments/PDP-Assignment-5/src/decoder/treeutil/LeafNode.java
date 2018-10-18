package decoder.treeutil;

import java.util.Collections;
import java.util.List;

import decoder.bean.DecodedData;

/**
 * Created by gajjar.s, on 4:11 PM, 10/15/18
 */
public class LeafNode<P, T> extends AbstractPrefixTreeNode<P, T> {

  private final T data;

  public LeafNode(T data) {
    this.data = data;
  }

  @Override
  public PrefixTreeNode<P, T> addChild(List<P> pathSequence, T data) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("A leafNode cannot add children to itself");
  }

  @Override
  public DecodedData<T> decode(int startIndex, List<P> encodedSequence) {
    return new DecodedData<>(startIndex, this.data);
  }

  @Override
  public List<String> getAllCodes(String currentPath) {
    return Collections.singletonList(
            String.format("%s:%s", this.data, currentPath));
  }

  @Override
  public boolean isTreeComplete() {
    return true;
  }

  @Override
  protected boolean isLeafNode() {
    return true;
  }
}
