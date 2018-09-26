package calculator.inputcategory.smartcalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;
import calculator.inputcategory.simplecalculator.SimpleCalculatorClearInputCategory;

public class SmartCalculatorClearInputCategory extends SimpleCalculatorClearInputCategory {

  public SmartCalculatorClearInputCategory(List<String> expression,
                                           Set<Character> clearInputCharacterSet) {

    super(expression, clearInputCharacterSet);
  }

  @Override
  public Set<CommandName> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandName.CLEAR, CommandName.OPERATOR, CommandName.OPERAND)));
  }
}
