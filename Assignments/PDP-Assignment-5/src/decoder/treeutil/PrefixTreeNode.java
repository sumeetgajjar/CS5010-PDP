package decoder.treeutil;

import java.util.List;

import decoder.bean.DecodedData;

/**
 * This interface represents a PrefixTree and all the operations that can be performed on it.
 *
 * @param <P> the type of the path which will be used for traversing the tree.
 * @param <T> the type of the data which will be stored at the leaf nodes.
 */
public interface PrefixTreeNode<P, T> {

  PrefixTreeNode<P, T> addChild(List<P> pathSequence, T data) throws IllegalStateException;

  DecodedData<T> decode(int startIndex, List<P> encodedSequence) throws IllegalStateException;

  List<String> getAllCodes(String currentPath);

  boolean isTreeComplete();
}