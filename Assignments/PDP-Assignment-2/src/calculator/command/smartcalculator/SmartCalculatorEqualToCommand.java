package calculator.command.smartcalculator;

import java.util.Deque;
import java.util.List;
import java.util.Set;

import calculator.SmartCalculator;
import calculator.bean.Operation;
import calculator.command.Command;
import calculator.command.simplecalculator.SimpleCalculatorEqualToCommand;
import calculator.util.Utils;

/**
 * This class represents ClearInput {@link Command} for {@link SmartCalculator}. It extends {@link
 * SimpleCalculatorEqualToCommand}.
 */
public class SmartCalculatorEqualToCommand extends SimpleCalculatorEqualToCommand {

  private final Operation lastOperation;
  private final int lastOperand;

  /**
   * Constructs the object of {@link SmartCalculatorEqualToCommand}. Its initializes it to the given
   * values.
   *
   * @param expression               current algebraic expression
   * @param equalToInputCharacterSet set of characters supported by the command
   * @param lastOperation            last operation in the previous algebraic expression
   * @param lastOperand              last operand in the previous algebraic expression
   */
  public SmartCalculatorEqualToCommand(List<String> expression,
                                       Set<Character> equalToInputCharacterSet,
                                       Operation lastOperation,
                                       int lastOperand) {

    super(expression, equalToInputCharacterSet);
    this.lastOperation = lastOperation;
    this.lastOperand = lastOperand;
  }

  /**
   * Returns the algebraic expression generated after evaluating the current expression.
   *
   * @param input input character
   * @return the algebraic expression generated after evaluating the current expression
   */
  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);

    /* If the expression size is 1 it means,
     * it needs to infer the operator and operand as lastOperator and lastOperand resp.
     * If the expression size is 2 it means,
     * it needs to infer the operand as the lastOperand.
     */
    if (expressionDeque.size() == 1) {
      expressionDeque.addLast(String.valueOf(this.lastOperation.getSymbol()));
      expressionDeque.addLast(String.valueOf(this.lastOperand));
    } else if (expressionDeque.size() == 2) {
      expressionDeque.addLast(expressionDeque.peekFirst());
    }

    return evaluateExpression(Utils.getExpressionList(expressionDeque));
  }
}
