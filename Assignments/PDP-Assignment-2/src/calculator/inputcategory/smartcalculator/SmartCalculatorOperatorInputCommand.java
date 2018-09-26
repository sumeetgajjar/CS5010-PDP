package calculator.inputcategory.smartcalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.inputcategory.simplecalculator.SimpleCalculatorOperatorInputCommand;
import calculator.util.Utils;

public class SmartCalculatorOperatorInputCommand extends SimpleCalculatorOperatorInputCommand {

  private final Set<Character> supportedDigits;

  public SmartCalculatorOperatorInputCommand(List<String> expression,
                                             Set<Character> supportedOperators,
                                             Set<Character> supportedDigits) {

    super(expression, supportedOperators);
    this.supportedDigits = supportedDigits;
  }

  @Override
  public List<String> performAction(char input) throws RuntimeException {
    Deque<String> expressionDeque = Utils.getExpressionDeque(this.expression);

    String lastElement = expressionDeque.peekLast();
    if (Objects.nonNull(lastElement)) {

      char ch = lastElement.charAt(lastElement.length() - 1);
      if (this.supportedDigits.contains(ch)) {
        expressionDeque.addLast(String.valueOf(input));
      } else if (this.supportedCharacters.contains(ch)) {
        expressionDeque.removeLast();
        expressionDeque.addLast(String.valueOf(input));
      } else {
        throw new IllegalStateException(String.format("Cannot Handle Input: %s", input));
      }
    }

    return Utils.getExpressionList(expressionDeque);
  }

  @Override
  public Set<CommandName> getNextValidCommands() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandName.OPERAND,
            CommandName.OPERATOR,
            CommandName.EQUAL_TO,
            CommandName.CLEAR)));
  }
}
