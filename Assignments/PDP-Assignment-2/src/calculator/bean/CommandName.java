package calculator.bean;

/**
 * This enum represents the Name of a Command that a Calculator can perform.
 */
public enum CommandName {
  /**
   * Indicates the operand input command i.e. if the input character is operand. e.g. "0", "1" and
   * so on.
   */
  OPERAND,

  /**
   * Indicates the operator input command i.e. if the input character is operator. e.g. "+", "-" and
   * so on.
   */
  OPERATOR,

  /**
   * Indicates the "Equal To" input command i.e. If the input character is "=".
   */
  EQUAL_TO,

  /**
   * Indicates the "Clear Input" command i.e. If the input character is "C".
   */
  CLEAR
}
