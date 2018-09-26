package calculator.command.smartcalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.command.simplecalculator.SimpleCalculatorOperandInputCommand;

public class SmartCalculatorOperandInputCommand extends SimpleCalculatorOperandInputCommand {


  public SmartCalculatorOperandInputCommand(List<String> expression,
                                            Set<Character> supportedOperands,
                                            Set<Character> supportedOperators) {

    super(expression, supportedOperands, supportedOperators);
  }

  /**
   * Returns a {@link Set} of valid {@link CommandName} that the next input character can represent
   * after successful execution of the current command.
   *
   * @return a set of valid commandName that the next input character can represent after successful
   * execution of the current command.
   */
  @Override
  public Set<CommandName> getNextValidCommands() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandName.OPERAND,
            CommandName.OPERATOR,
            CommandName.EQUAL_TO,
            CommandName.CLEAR)));
  }
}
