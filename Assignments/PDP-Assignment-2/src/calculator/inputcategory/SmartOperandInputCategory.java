package calculator.inputcategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategoryName;

public class SmartOperandInputCategory extends SimpleOperandInputCategory {


  public SmartOperandInputCategory(List<String> expression, Set<Character> supportedOperands, Set<Character> supportedOperators) {
    super(expression, supportedOperands, supportedOperators);
  }

  @Override
  public Set<InputCategoryName> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InputCategoryName.OPERAND, InputCategoryName.OPERATOR, InputCategoryName.EQUAL_TO, InputCategoryName.CLEAR)));
  }
}
