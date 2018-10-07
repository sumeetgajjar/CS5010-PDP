package polynomial;

import java.util.Comparator;

/**
 * Created by gajjar.s, on 9:51 PM, 10/6/18
 */
public class PolynomialElementNode implements PolynomialNode {

  private final Term term;
  private PolynomialNode rest;

  public PolynomialElementNode(Term term, PolynomialNode rest) {
    this.term = term;
    this.rest = rest;
  }

  @Override
  public PolynomialNode insert(Term term) {
    int compare = Comparator.comparingInt(Term::getPower)
            .reversed()
            .compare(this.term, term);

    if (compare < 0) {
      return new PolynomialElementNode(term, this);
    } else if (compare == 0) {
      Term additionResult = this.term.addTerm(term);
      return new PolynomialElementNode(additionResult, this.rest);
    } else {
      this.rest = this.rest.insert(term);
      return this;
    }
  }

  @Override
  public Term get(int index) {
    if (index == 0) {
      return this.term;
    }
    return this.rest.get(index - 1);
  }

  @Override
  public int getCoefficient(int power) {
    if (power == this.term.getPower()) {
      return this.term.getCoefficient();
    }
    return this.term.getCoefficient();
  }
}
