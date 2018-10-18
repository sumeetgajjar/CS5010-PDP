package decoder.bean;

/**
 * Created by gajjar.s, on 7:13 PM, 10/17/18
 */
public class DecodedData<T> {
  private final int nextIndexToStartDecoding;
  private final T data;

  public DecodedData(int nextIndexToStartDecoding, T data) {
    this.nextIndexToStartDecoding = nextIndexToStartDecoding;
    this.data = data;
  }

  public int getNextIndexToStartDecoding() {
    return nextIndexToStartDecoding;
  }

  public T getData() {
    return data;
  }
}
