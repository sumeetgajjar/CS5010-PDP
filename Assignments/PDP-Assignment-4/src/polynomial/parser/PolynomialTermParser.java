package polynomial.parser;

import polynomial.Term;

/**
 * Created by gajjar.s,on 6:18 PM, 10/6/18
 */
public interface PolynomialTermParser {

  Term parsePolynomialTerm(boolean isFirstTerm, String termString) throws IllegalArgumentException;

}
