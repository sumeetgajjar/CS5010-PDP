package calculator.command.smartcalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.SmartCalculator;
import calculator.bean.CommandName;
import calculator.command.Command;
import calculator.command.simplecalculator.SimpleCalculatorClearInputCommand;

/**
 * This class represents ClearInput {@link Command} for {@link SmartCalculator}. It extends {@link
 * SimpleCalculatorClearInputCommand}.
 */
public class SmartCalculatorClearInputCommand extends SimpleCalculatorClearInputCommand {

  /**
   * Constructs the object of {@link SmartCalculatorClearInputCommand}. Its initializes it to the
   * given values.
   *
   * @param expression             current algebraic expression
   * @param clearInputCharacterSet set of characters supported by the command
   */
  public SmartCalculatorClearInputCommand(List<String> expression,
                                          Set<Character> clearInputCharacterSet) {

    super(expression, clearInputCharacterSet);
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
            CommandName.CLEAR, CommandName.OPERATOR, CommandName.OPERAND)));
  }
}
