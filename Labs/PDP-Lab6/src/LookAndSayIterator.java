import java.math.BigInteger;

/**
 * Created by gajjar.s, on 8:24 PM, 11/9/18
 */
public class LookAndSayIterator implements RIterator<BigInteger> {

  @Override
  public BigInteger prev() {
    return null;
  }

  @Override
  public boolean hasPrevious() {
    return false;
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public BigInteger next() {
    return null;
  }
}
