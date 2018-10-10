package polynomial.parser;

import polynomial.bean.Term;

/**
 * This interface represents a Polynomial Term Parser.
 */
public interface PolynomialTermParser {

  /**
   * Returns a polynomial {@link Term} by parsing the given string. It throws a {@link
   * IllegalArgumentException} if the given string is invalid.
   *
   * @param isFirstTerm does the given string corresponds to the first term
   * @param termString  the string to parse
   * @return the parsed Term
   * @throws IllegalArgumentException if the given string is invalid
   */
  Term parsePolynomialTerm(boolean isFirstTerm, String termString) throws IllegalArgumentException;
}
