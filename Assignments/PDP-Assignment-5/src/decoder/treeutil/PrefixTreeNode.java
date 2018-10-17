package decoder.treeutil;

import java.util.List;

import decoder.bean.DecodedData;

/**
 * Created by gajjar.s, on 3:33 PM, 10/15/18
 */
public interface PrefixTreeNode<P, T> {

  PrefixTreeNode<P, T> addChild(P[] path, T dataToBeAdded);

  DecodedData<T> decode(int startIndex, List<P> encodedSequence);

  List<String> getAllCodes(String currentPath);

  boolean isTreeComplete();
}