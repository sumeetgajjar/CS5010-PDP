package polynomial;

/**
 * Created by gajjar.s, on 9:51 PM, 10/6/18
 */
public interface PolynomialNode {
  PolynomialNode insert(Term term);

  Term get(int index);

  int getCoefficient(int power);
}
