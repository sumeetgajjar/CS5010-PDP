package calculator.inputcategory.smartcalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.inputcategory.simplecalculator.SimpleCalculatorOperandInputCommand;

public class SmartCalculatorOperandInputCommand extends SimpleCalculatorOperandInputCommand {


  public SmartCalculatorOperandInputCommand(List<String> expression,
                                            Set<Character> supportedOperands,
                                            Set<Character> supportedOperators) {

    super(expression, supportedOperands, supportedOperators);
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
