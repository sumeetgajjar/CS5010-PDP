package calculator.command.smartcalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.SmartCalculator;
import calculator.bean.CommandName;
import calculator.command.Command;
import calculator.command.simplecalculator.SimpleCalculatorOperandInputCommand;

/**
 * This class represents Operand Input {@link Command} for {@link SmartCalculator}. It extends
 * {@link SimpleCalculatorOperandInputCommand}. It throws an Runtime exception if the given input
 * causes a Operand Overflow.
 */
public class SmartCalculatorOperandInputCommand extends SimpleCalculatorOperandInputCommand {

  /**
   * Constructs the object of {@link SmartCalculatorOperandInputCommand}. Its initializes it to the
   * given values
   *
   * @param expression         current algebraic expression
   * @param supportedOperands  set of characters supported by the command
   * @param supportedOperators set of supported operators by the command
   */
  public SmartCalculatorOperandInputCommand(List<String> expression,
                                            Set<Character> supportedOperands,
                                            Set<Character> supportedOperators) {

    super(expression, supportedOperands, supportedOperators);
  }

  /**
   * Returns a {@link Set} of valid {@link CommandName} that the next input character can represent
   * after execution of the current command.
   *
   * @return a set of valid CommandName that the next input character can belong to
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
