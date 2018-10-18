package decoder.bean;

/**
 * This class represents a container class for Decoded Data of type<code>T</code>. It contains the
 * current decoded data and the index of the next location of the encoded sequence to continue
 * decoding. The index of next location can lie outside bounds of sequence and should be checked
 * before continuing the decoding process.
 */
public class DecodedData<T> {
  private final int nextIndexToStartDecoding;
  private final T data;

  /**
   * Constructs a DecodedData object with the given params.
   *
   * @param nextIndexToStartDecoding the index of the next location of the encoded sequence to
   *                                 continue decoding
   * @param data                     the decoded data
   */
  public DecodedData(int nextIndexToStartDecoding, T data) {
    this.nextIndexToStartDecoding = nextIndexToStartDecoding;
    this.data = data;
  }

  /**
   * Returns the index of the next location of the encoded sequence to continue decoding.
   *
   * @return the index of the next location of the encoded sequence to continue decoding
   */
  public int getNextIndexToStartDecoding() {
    return nextIndexToStartDecoding;
  }

  /**
   * Returns the decoded data.
   *
   * @return the decoded data
   */
  public T getData() {
    return data;
  }
}
