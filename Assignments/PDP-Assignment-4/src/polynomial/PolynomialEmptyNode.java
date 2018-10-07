package polynomial;

/**
 * Created by gajjar.s, on 9:51 PM, 10/6/18
 */
public class PolynomialEmptyNode implements PolynomialNode {

  @Override
  public PolynomialNode insert(Term term) {
    return new PolynomialElementNode(term, this);
  }

  @Override
  public Term get(int index) throws IllegalArgumentException {
    throw new IllegalArgumentException("Wrong index");
  }

  @Override
  public int getCoefficient(int power) {
    return 0;
  }
}
