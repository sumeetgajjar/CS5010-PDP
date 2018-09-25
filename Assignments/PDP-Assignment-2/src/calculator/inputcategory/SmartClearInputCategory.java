package calculator.inputcategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;

public class SmartClearInputCategory extends ClearInputCategory {

  public SmartClearInputCategory(List<String> expression, Set<Character> clearInputCharacterSet) {
    super(expression, clearInputCharacterSet);
  }

  @Override
  public Set<InputCategory> getNextValidInputCategorySet() {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InputCategory.CLEAR, InputCategory.OPERATOR, InputCategory.OPERAND)));
  }
}
