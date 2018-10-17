package decoder.bean;

/**
 * Created by gajjar.s, on 7:13 PM, 10/17/18
 */
public class DecodedData<T> {
  private final int nextStartPointer;
  private final T data;

  public DecodedData(int nextStartPointer, T data) {
    this.nextStartPointer = nextStartPointer;
    this.data = data;
  }

  public int getNextStartPointer() {
    return nextStartPointer;
  }

  public T getData() {
    return data;
  }
}
