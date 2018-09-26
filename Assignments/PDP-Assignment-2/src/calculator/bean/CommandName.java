package calculator.bean;

/**
 * This enum represents the Name of the Command an input can have.
 */
public enum CommandName {
  /**
   * If the input is operand. e.g. "0", "1" and so on.
   */
  OPERAND,

  /**
   * If the input is operator. e.g. "+", "-" and so on.
   */
  OPERATOR,

  /**
   * If the input is equal to. e.g. "=".
   */
  EQUAL_TO,

  /**
   * If the input is clear. e.g. "C".
   */
  CLEAR
}
