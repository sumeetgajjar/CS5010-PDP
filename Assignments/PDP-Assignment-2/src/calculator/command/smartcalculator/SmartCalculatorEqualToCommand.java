package calculator.command.smartcalculator;

import java.util.Deque;
import java.util.List;
import java.util.Set;

import calculator.bean.Operation;
import calculator.command.simplecalculator.SimpleCalculatorEqualToCommand;
import calculator.util.Utils;

public class SmartCalculatorEqualToCommand extends SimpleCalculatorEqualToCommand {

  private final Operation lastOperation;
  private final int lastOperand;

  public SmartCalculatorEqualToCommand(List<String> expression,
                                       Set<Character> supportedEqualToCharacters,
                                       Operation lastOperation,
                                       int lastOperand) {

    super(expression, supportedEqualToCharacters);
    this.lastOperation = lastOperation;
    this.lastOperand = lastOperand;
  }

  @Override
  public List<String> performAction(char input) {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);

    if (expressionDeque.size() == 1) {
      expressionDeque.addLast(String.valueOf(this.lastOperation.getSymbol()));
      expressionDeque.addLast(String.valueOf(this.lastOperand));
    } else if (expressionDeque.size() == 2) {
      expressionDeque.addLast(expressionDeque.peekFirst());
    }

    return evaluateExpression(Utils.getExpressionList(expressionDeque));
  }
}
