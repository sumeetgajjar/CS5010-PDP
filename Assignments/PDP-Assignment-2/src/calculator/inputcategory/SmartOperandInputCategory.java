package calculator.inputcategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;

public class SmartOperandInputCategory extends OperandInputCategory {


  public SmartOperandInputCategory(List<String> expression, Set<Character> supportedOperands, Set<Character> supportedOperators) {
    super(expression, supportedOperands, supportedOperators);
  }

  @Override
  public Set<InputCategory> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InputCategory.OPERAND, InputCategory.OPERATOR, InputCategory.EQUAL_TO, InputCategory.CLEAR)));
  }
}
