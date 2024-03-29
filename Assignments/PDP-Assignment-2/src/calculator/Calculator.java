package calculator;

/**
 * This interface represents a Calculator. It takes input as One character at a time. The input is
 * given using <tt>input(char input)</tt> method. To get the current result of the Calculator
 * <tt>getResult()</tt> should be used, which returns a String Object.
 */
public interface Calculator {

  /**
   * Takes a char as its only argument and returns a {@link Calculator} object. The {@link
   * Calculator} object is the result of processing the input.
   *
   * @param input the input for the {@link Calculator}
   * @return a {@link Calculator} object as result of processing input
   */
  Calculator input(char input);

  /**
   * Returns the current result of the {@link Calculator } as {@link String} object.
   *
   * @return the current result of the {@link Calculator}
   */
  String getResult();
}
